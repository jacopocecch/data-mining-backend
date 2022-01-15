package com.unipi.data.mining.backend.data;

public class ClusterValues {

    private double extraversion;
    private double agreeableness;
    private double conscientiousness;
    private double neuroticism;
    private double openness;
    private double timeSpent;

    public ClusterValues() {
    }

    public double getExtraversion() {
        return extraversion;
    }

    public void setExtraversion(double extraversion) {
        this.extraversion = extraversion;
    }

    public double getAgreeableness() {
        return agreeableness;
    }

    public void setAgreeableness(double agreeableness) {
        this.agreeableness = agreeableness;
    }

    public double getConscientiousness() {
        return conscientiousness;
    }

    public void setConscientiousness(double conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public double getNeuroticism() {
        return neuroticism;
    }

    public void setNeuroticism(double neuroticism) {
        this.neuroticism = neuroticism;
    }

    public double getOpenness() {
        return openness;
    }

    public void setOpenness(double openness) {
        this.openness = openness;
    }

    public double getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(double timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public String toString() {
        return "ClusterValues{" +
                "extraversion=" + extraversion +
                ", agreeableness=" + agreeableness +
                ", conscientiousness=" + conscientiousness +
                ", neuroticism=" + neuroticism +
                ", openness=" + openness +
                ", timeSpent=" + timeSpent +
                '}';
    }
}
