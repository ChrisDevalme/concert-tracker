package com.pluralsight.concerttracker.models;

import jakarta.persistence.*;

@Entity
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String city;
    private int capacity;

    public Venue() {
    }

    public Venue(String name, String city, int capacity) {
        this.name = name;
        this.city = city;
        this.capacity = capacity;
    }


    public long getId() {
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
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Name: %s | City: %s | Capacity: %,d",
                id,
                name,
                city,
                capacity
        );
    }
}