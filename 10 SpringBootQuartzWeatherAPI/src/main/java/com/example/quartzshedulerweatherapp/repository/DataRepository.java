package com.example.quartzshedulerweatherapp.repository;

import java.util.List;

import com.example.quartzshedulerweatherapp.entity.Data;
import com.example.quartzshedulerweatherapp.entity.DataInterface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {

    @Query(value = "select d.id, d.lat, d.lon, d.name, d.peroid from Data d", nativeQuery = true)
    List<DataInterface> getAll();
}
