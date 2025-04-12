package DTOs;

import java.util.List;

/**
 * Data Transfer Object for displaying practitioner profile information.
 * Contains comprehensive details about a medical practitioner including
 * their specialization, ratings, and associated clinics.
 */
public class PractitionerProfileDTO {
    /** Unique identifier of the practitioner */
    private int id;
    
    /** Full name of the practitioner */
    private String name;
    
    /** Age of the practitioner in years */
    private int age;
    
    /** Gender of the practitioner */
    private String gender;
    
    /** Email address for communication */
    private String email;
    
    /** Contact phone number */
    private String phoneNumber;
    
    /** Medical specialization of the practitioner */
    private String specialization;
    
    /** Price for a standard appointment */
    private double appointmentPrice;
    
    /** Average rating from patients */
    private double rating;
    
    /** List of clinics where the practitioner works */
    private List<ClinicProfileDTO> clinics;

    /**
     * Gets the unique identifier of the practitioner.
     *
     * @return The practitioner's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the practitioner.
     *
     * @param id The practitioner's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the full name of the practitioner.
     *
     * @return The practitioner's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the practitioner.
     *
     * @param name The practitioner's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the practitioner.
     *
     * @return The practitioner's age in years
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the practitioner.
     *
     * @param age The practitioner's age in years
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the gender of the practitioner.
     *
     * @return The practitioner's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the practitioner.
     *
     * @param gender The practitioner's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the email address of the practitioner.
     *
     * @return The practitioner's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the practitioner.
     *
     * @param email The practitioner's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the contact phone number of the practitioner.
     *
     * @return The practitioner's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the contact phone number of the practitioner.
     *
     * @param phoneNumber The practitioner's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the medical specialization of the practitioner.
     *
     * @return The practitioner's specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Sets the medical specialization of the practitioner.
     *
     * @param specialization The practitioner's specialization
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Gets the price for a standard appointment.
     *
     * @return The appointment price
     */
    public double getAppointmentPrice() {
        return appointmentPrice;
    }

    /**
     * Sets the price for a standard appointment.
     *
     * @param appointmentPrice The new appointment price
     */
    public void setAppointmentPrice(double appointmentPrice) {
        this.appointmentPrice = appointmentPrice;
    }

    /**
     * Gets the average rating of the practitioner.
     *
     * @return The practitioner's rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the average rating of the practitioner.
     *
     * @param rating The new rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Gets the list of clinics where the practitioner works.
     *
     * @return List of associated clinics
     */
    public List<ClinicProfileDTO> getClinics() {
        return clinics;
    }

    /**
     * Sets the list of clinics where the practitioner works.
     *
     * @param clinics The new list of associated clinics
     */
    public void setClinics(List<ClinicProfileDTO> clinics) {
        this.clinics = clinics;
    }

    @Override
    public String toString() {
        return "PractitionerProfileDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", specialization='" + specialization + '\'' +
                ", appointmentPrice=" + appointmentPrice +
                ", rating=" + rating +
                ", clinics=" + clinics +
                '}';
    }
}
