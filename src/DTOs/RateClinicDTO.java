/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author KyRilloS
 */

/**
 * Data Transfer Object for submitting clinic ratings.
 * Contains information about a patient's experience at a clinic,
 * including rating value, waiting time, and comments.
 */
public class RateClinicDTO {
    /** Unique identifier of the clinic being rated */
    private int clinicId;
    
    /** Unique identifier of the patient submitting the rating */
    private int patientId;
    
    /** Rating value (0.0 to 5.0) */
    private double rating;
    
    /** Waiting time in minutes */
    private int waitingTime;
    
    /** Patient's comment about their experience */
    private String comment;

    /**
     * Constructs a new RateClinicDTO with the specified details.
     *
     * @param clinicId Unique identifier of the clinic
     * @param patientId Unique identifier of the patient
     * @param rating Rating value (0.0 to 5.0)
     * @param waitingTime Waiting time in minutes
     * @param comment Patient's comment
     */
    public RateClinicDTO(int clinicId, int patientId, double rating, 
                        int waitingTime, String comment) {
        this.clinicId = clinicId;
        this.patientId = patientId;
        setRating(rating);
        setWaitingTime(waitingTime);
        this.comment = comment;
    }

    /**
     * Gets the unique identifier of the clinic.
     *
     * @return The clinic's ID
     */
    public int getClinicId() {
        return clinicId;
    }

    /**
     * Sets the unique identifier of the clinic.
     *
     * @param clinicId The clinic's ID
     */
    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    /**
     * Gets the unique identifier of the patient.
     *
     * @return The patient's ID
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Sets the unique identifier of the patient.
     *
     * @param patientId The patient's ID
     */
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the rating value.
     *
     * @return The rating value (0.0 to 5.0)
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the rating value.
     * Rating must be between 0.0 and 5.0 inclusive.
     *
     * @param rating The rating value
     * @throws IllegalArgumentException if rating is not between 0.0 and 5.0
     */
    public void setRating(double rating) {
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        this.rating = rating;
    }

    /**
     * Gets the waiting time in minutes.
     *
     * @return The waiting time in minutes
     */
    public int getWaitingTime() {
        return waitingTime;
    }

    /**
     * Sets the waiting time in minutes.
     * Waiting time must be non-negative.
     *
     * @param waitingTime The waiting time in minutes
     * @throws IllegalArgumentException if waiting time is negative
     */
    public void setWaitingTime(int waitingTime) {
        if (waitingTime < 0) {
            throw new IllegalArgumentException("Waiting time cannot be negative");
        }
        this.waitingTime = waitingTime;
    }

    /**
     * Gets the patient's comment about their experience.
     *
     * @return The patient's comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the patient's comment about their experience.
     *
     * @param comment The patient's comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "RateClinicDTO{" +
                "clinicId=" + clinicId +
                ", patientId=" + patientId +
                ", rating=" + rating +
                ", waitingTime=" + waitingTime +
                ", comment='" + comment + '\'' +
                '}';
    }
} 