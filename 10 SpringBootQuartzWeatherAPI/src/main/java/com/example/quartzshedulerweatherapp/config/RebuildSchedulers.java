package com.example.quartzshedulerweatherapp.config;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import com.example.quartzshedulerweatherapp.controller.DataJob;
import com.example.quartzshedulerweatherapp.entity.Data;
import com.example.quartzshedulerweatherapp.repository.DataRepository;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RebuildSchedulers {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init() {

        List<Data> dataList = dataRepository.findAll();

        for (Data data : dataList) {
            JobDetail jobDetail = buildJobDetail(data);
            Trigger trigger = buildJobTrigger(jobDetail, data.getPeriod());
            try {
                scheduler.scheduleJob(jobDetail, trigger);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

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
