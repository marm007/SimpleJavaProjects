package com.example.quartzshedulerweatherapp.repository;

import com.example.quartzshedulerweatherapp.entity.Temperature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Long> {

}
