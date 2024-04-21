package com.sunbeaminfo.app.Entity;

import java.io.Serializable;

public class Venue  implements Serializable {
    private int id;
    private String name;
    private String capacity;
    private String price;
    private String location;
    private String image;

    // Constructor
    public Venue(int id, String name, String capacity, String price, String location, String image) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.location = location;
        this.image = image;
    }

    public Venue() {
    }

    // Getters and setters
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Venue{" +

                " name= " + name + ' ' +
                " capacity= " + capacity + ' ' +
                " price= " + price;
    }

    // Example usage
    public static void main(String[] args) {
        Venue venue = new Venue(1, "Example Venue", "100", "50.00", "Example Location", "example.jpg");
        System.out.println(venue);
    }
}
