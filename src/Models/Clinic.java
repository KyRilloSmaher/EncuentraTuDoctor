package Models;

import java.util.ArrayList;
import java.util.List;



public class Clinic {
    private int id;
    private String name;
    private String location;
    private double rating;
    private int practitionerId;
    private Practitioner practitioner = new Practitioner();
    private List<Schedule> Schedules = new ArrayList<>();


    // Constructors
    public Clinic() {}

    public Clinic(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Clinic(String name, String location, double rating) {
        this.name = name;
        this.location = location;
        this.rating = rating;
    }

    // Getters and Setters
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Schedule> getSchedules() {
        return Schedules;
    }
    public Practitioner getPractitioner() {
        return practitioner;
    }
    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }
    public void setSchedules(List<Schedule> schedules) {
        Schedules = schedules;
    }
    public int getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(int practitionerId) {
        this.practitionerId = practitionerId;
    }
    @Override
    public String toString() {
        return "Clinic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", rating=" + rating +
                '}';
    }
}