package Models;

import java.util.ArrayList;
import java.util.List;


public class Practitioner extends User {

    private String specialization;
    private double appointmentPrice;
    private double rating;
    private List<Clinic> clinics = new ArrayList<>();

    // Constructors
    public Practitioner() {
        super();
    }

    public Practitioner(String name, int age, String gender,String email, String password, String phone, UserRole role,
                        String specialization, double appointmentPrice, double rating) {
        super(name, age,gender,email, password, phone, role);
        this.specialization = specialization;
        this.appointmentPrice = appointmentPrice;
        this.rating = rating;
        super.setRole(UserRole.PRACTITIONER);
    }
    public Practitioner(int id ,String name,int age ,String gender,String email, String password, String phone, UserRole role,
    String specialization, double appointmentPrice, double rating) {
super(id ,name,age,gender, email, password, phone, role);
this.specialization = specialization;
this.appointmentPrice = appointmentPrice;
this.rating = rating;
super.setRole(UserRole.PRACTITIONER);
    }

    public Practitioner(String name, int age,String gender,String email, String password, String phone, UserRole role,
                        String specialization, double appointmentPrice) {
        super(name,age, gender, email, password, phone, role);
        this.specialization = specialization;
        this.appointmentPrice = appointmentPrice;
        super.setRole(UserRole.PRACTITIONER);
}
    // Getters and Setters
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public double getAppointmentPrice() {
        return appointmentPrice;
    }

    public void setAppointmentPrice(double appointmentPrice) {
        this.appointmentPrice = appointmentPrice;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Clinic> getClinics() {
        return clinics;
    }

    public void setClinics(List<Clinic> clinics) {
        this.clinics = clinics;
    }

    @Override
    public String toString() {
        return "Practitioner{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", specialization='" + specialization + '\'' +
                ", appointmentPrice=" + appointmentPrice +
                ", rating=" + rating +
                '}';
    }
}
