package com.unipi.data.mining.backend.dtos;

import com.unipi.data.mining.backend.entities.mongodb.Question;

import java.io.Serializable;

public class QuestionDto implements Serializable {

    private String name;
    private int value;
    private double time;

    public QuestionDto() {
    }

    public QuestionDto(String name, int value, double time) {
        this.name = name;
        this.value = value;
        this.time = time;
    }

    public QuestionDto(Question question) {
        this.name = question.getName();
        this.value = question.getValue();
        this.time = question.getTime();
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
}
