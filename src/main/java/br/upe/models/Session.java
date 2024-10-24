package br.upe.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Session {
    private Long eventId;
    private Long sessionNumber;
    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(Long sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
