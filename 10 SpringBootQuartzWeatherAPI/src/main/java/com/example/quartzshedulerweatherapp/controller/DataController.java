package com.example.quartzshedulerweatherapp.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.example.quartzshedulerweatherapp.entity.Data;
import com.example.quartzshedulerweatherapp.entity.DataInterface;
import com.example.quartzshedulerweatherapp.entity.Temperature;
import com.example.quartzshedulerweatherapp.repository.DataRepository;

import org.json.JSONObject;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    DataRepository dataRepository;

    @PostMapping("/register")
    public Data registerDataScheduler(@Valid @RequestParam("period") int period,
            @RequestParam(name = "cityName", required = false) String name,
            @RequestParam(name = "lon", required = false) Double lon,
            @RequestParam(name = "lat", required = false) Double lat) throws Exception {

        if ((lat == null || lon == null) && name.isEmpty())
            throw new NotFoundException("Exception");

        Data data = new Data();

        data.setPeriod(period);

        if (!name.isEmpty()) {
            data.setName(name);
        } else {
            data.setLat(lat);
            data.setLon(lon);
        }

        Data d = dataRepository.save(data);
        JobDetail jobDetail = buildJobDetail(d);
        Trigger trigger = buildJobTrigger(jobDetail, period);
        scheduler.scheduleJob(jobDetail, trigger);

        return d;

    }

    @GetMapping("/stat/{id}/{n}")
    public Map<String, Object> getLastNMeasurementsAverage(@Valid @NotNull @PathVariable("n") Integer n,
            @Valid @NotNull @PathVariable("id") Long id) throws Exception {
        Data data = dataRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        List<Temperature> temperatures = data.getTemperatureList();

        if (n > temperatures.size())
            throw new IndexOutOfBoundsException(
                    "n: " + n + " is greater then list size: " + temperatures.size() + " !");

        double average = 0;

        for (int i = temperatures.size() - 1; i > temperatures.size() - 1 - n; i--) {
            average += temperatures.get(i).getValue();
        }

        average = average / n;

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("count", n);
        jsonObject.put("average", average);

        return jsonObject.toMap();
    }

    @GetMapping("/stat")
    public List<DataInterface> getAllJobs() {
        return dataRepository.getAll();
    }

    private JobDetail buildJobDetail(Data data) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("id", data.getId());

        if (data.getLat() != null) {

            jobDataMap.put("lat", data.getLat());
            jobDataMap.put("lon", data.getLon());
        } else {
            jobDataMap.put("name", data.getName());
        }

        return JobBuilder.newJob(DataJob.class).withIdentity(UUID.randomUUID().toString(), "data-jobs")
                .withDescription("Get Data Job").usingJobData(jobDataMap).storeDurably().build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, int period) {
        return TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobDetail.getKey().getName(), "data-triggers")
                .withDescription("Get Data Job Trigger").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(period).repeatForever())
                .build();
    }

}
