package com.example.quartzshedulerweatherapp.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Data {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    String name;

    @Basic
    @Column(name = "lon")
    Double lon;

    @Basic
    @Column(name = "lat")
    Double lat;

    @Basic
    @Column(name = "peroid")
    @NotNull
    int period;

    @OneToMany(mappedBy = "data", cascade = CascadeType.ALL)
    List<Temperature> temperatureList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lan) {
        this.lat = lan;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public List<Temperature> getTemperatureList() {
        return temperatureList;
    }

    public void setTemperatureList(List<Temperature> temperatureList) {
        this.temperatureList = temperatureList;
    }

    @Override
    public String toString() {
        return "Data{" + "id=" + id + ", name='" + name + '\'' + ", lon=" + lon + ", lat=" + lat + ", period=" + period
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Data data = (Data) o;
        return Objects.equals(id, data.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
