package com.sunbeaminfo.app.Entity;

import java.io.Serializable;

public class Event {
    private int id;
    private String name;
    private String details;
    private String image;
    private String createdTimestamp;

    public Event(int id, String name, String details, String image, String createdTimestamp) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.image = image;
        this.createdTimestamp = createdTimestamp;
    }

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", image='" + image + '\'' +
                ", createdTimestamp='" + createdTimestamp + '\'' +
                '}';
    }
}