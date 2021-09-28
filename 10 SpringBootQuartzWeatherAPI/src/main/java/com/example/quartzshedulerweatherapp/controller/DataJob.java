package com.example.quartzshedulerweatherapp.controller;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.example.quartzshedulerweatherapp.entity.Temperature;
import com.example.quartzshedulerweatherapp.repository.DataRepository;
import com.example.quartzshedulerweatherapp.repository.TemperatureRepository;

import org.json.JSONObject;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataJob extends QuartzJobBean {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    TemperatureRepository temperatureRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        Long id = jobDataMap.getLong("id");

        if (jobDataMap.containsKey("name")) {
            String name = jobDataMap.getString("name");
            getWeather(id, name);
        } else {
            Double lon = jobDataMap.getDouble("lon");
            Double lat = jobDataMap.getDouble("lat");
            getWeather(id, lon, lat);
        }
    }

    private void getWeather(Long id, String cityName) {

        System.out.println(String.format("Getting weather from %s", cityName));

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();

        Request request = new Request.Builder().url("http://api.openweathermap.org/data/2.5/weather?q=" + cityName
                + "&units=metric&appid=4abd26b96c010f1493a74f9263f2f2b8").build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            JSONObject weatherData = new JSONObject(response.body().string());

            System.out.println("Weather " + weatherData);

            JSONObject mainData = new JSONObject(weatherData.get("main").toString());
            System.out.println("mainData " + mainData);

            saveTemperature((Double) mainData.get("temp"), id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getWeather(Long id, Double lon, Double lat) {

        System.out.println(String.format("Getting weather from %s", lon));

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();

        Request request = new Request.Builder().url("http://api.openweathermap.org/data/2.5/weather?lat=" + lat
                + "&lon=" + lon + "&units=metric&appid=4abd26b96c010f1493a74f9263f2f2b8").build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            JSONObject weatherData = new JSONObject(response.body().string());

            System.out.println("Weather " + weatherData);
            JSONObject mainData = new JSONObject(weatherData.get("main").toString());
            System.out.println("mainData " + mainData);

            saveTemperature((Double) mainData.get("temp"), id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTemperature(Double value, Long id) {
        dataRepository.findById(id).map(data -> {
            Temperature temperature = new Temperature();
            temperature.setData(data);
            temperature.setValue(value);
            temperature.setDate(new Date());

            System.out.println("Saving... " + temperature.toString() + "\n");
            return temperatureRepository.save(temperature);
        });

    }
}
