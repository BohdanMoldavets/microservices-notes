package com.moldavets.microservices.limits_service.model;

public class Limit {

    private int minimum;
    private int maximum;

    public Limit() {
    }

    public Limit(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    @Override
    public String toString() {
        return "Limit{" +
                "minimum=" + minimum +
                ", maximum=" + maximum +
                '}';
    }
}
