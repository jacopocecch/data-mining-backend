package com.unipi.data.mining.backend.entities.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Question {

    private String name;
    private int value;
    private double time;

    public Question() {
    }

    public Question(String name, int value, double time) {
        this.name = name;
        this.value = value;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Question{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", time=" + time +
                '}';
    }
}
