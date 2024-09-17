package com.running.tracker.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.support.JacksonLocalDateTimeDeserializer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "runs")
public class Runs {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "runs_seq")
    @Column(nullable=false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user_id;

	@Column(nullable=false)
    private Double start_lon;

    private Double start_lat;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = JacksonLocalDateTimeDeserializer.class)
	private LocalDateTime start_datetime;

    private Double finish_lon;

    private Double finish_lat;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = JacksonLocalDateTimeDeserializer.class)
	private LocalDateTime finish_datetime;

    private Double distance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Double getStart_lon() {
        return start_lon;
    }

    public void setStart_lon(Double start_lon) {
        this.start_lon = start_lon;
    }

    public Double getStart_lat() {
        return start_lat;
    }

    public void setStart_lat(Double start_lat) {
        this.start_lat = start_lat;
    }

    public LocalDateTime getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(LocalDateTime start_datetime) {
        this.start_datetime = start_datetime;
    }

    public Double getFinish_lon() {
        return finish_lon;
    }

    public void setFinish_lon(Double finish_lon) {
        this.finish_lon = finish_lon;
    }

    public Double getFinish_lat() {
        return finish_lat;
    }

    public void setFinish_lat(Double finish_lat) {
        this.finish_lat = finish_lat;
    }

    public LocalDateTime getFinish_datetime() {
        return finish_datetime;
    }

    public void setFinish_datetime(LocalDateTime finish_datetime) {
        this.finish_datetime = finish_datetime;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void calculateDistance() {
        var R = 6378.137;
        var dLat = finish_lat * Math.PI / 180 - start_lat * Math.PI / 180;
        var dLon = finish_lon * Math.PI / 180 - start_lon * Math.PI / 180;
        var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(start_lat * Math.PI / 180) * Math.cos(finish_lat * Math.PI / 180) *
        Math.sin(dLon/2) * Math.sin(dLon/2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var d = R * c;
        this.distance = d;
    }
    
    @Autowired
    public Runs() {
    }

    public Runs(Runs run1) {
        this.id = run1.id;
        this.user_id = run1.user_id;
        this.start_lon = run1.start_lon;
        this.start_lat = run1.start_lat;
        this.start_datetime = run1.start_datetime;
    }    
    
    

}
