package edu.esprit.entities;

public class Ratings {
    
    private int id;
    private double value;
    
    public Ratings(int id, double value) {
        this.id = id;
        this.value = value;
    }
    
    // getters and setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
