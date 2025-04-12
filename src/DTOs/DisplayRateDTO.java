package DTOs;

/**
 * Data Transfer Object for displaying rating information.
 * Contains details about a patient's rating including their experience,
 * waiting time, and comments.
 */
public class DisplayRateDTO {
    /** Unique identifier of the rating */
    private int rateId;
    
    /** Name of the patient who provided the rating */
    private String patientName;
    
    /** Rating value (0.0 to 5.0) */
    private double rateValue;
    
    /** Waiting time in minutes */
    private int waitingTime;
    
    /** Patient's comment about their experience */
    private String comment;

    /**
     * Constructs a new DisplayRateDTO with the specified details.
     *
     * @param rateId Unique identifier of the rating
     * @param patientName Name of the patient
     * @param rateValue Rating value (0.0 to 5.0)
     * @param waitingTime Waiting time in minutes
     * @param comment Patient's comment
     */
    public DisplayRateDTO(int rateId, String patientName, double rateValue, 
                         int waitingTime, String comment) {
        this.rateId = rateId;
        this.patientName = patientName;
        setRateValue(rateValue);
        setWaitingTime(waitingTime);
        this.comment = comment;
    }

    /**
     * Gets the unique identifier of the rating.
     *
     * @return The rating's ID
     */
    public int getRateId() {
        return rateId;
    }

    /**
     * Sets the unique identifier of the rating.
     *
     * @param rateId The rating's ID
     */
    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    /**
     * Gets the name of the patient who provided the rating.
     *
     * @return The patient's name
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Sets the name of the patient who provided the rating.
     *
     * @param patientName The patient's name
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * Gets the rating value.
     *
     * @return The rating value (0.0 to 5.0)
     */
    public double getRateValue() {
        return rateValue;
    }

    /**
     * Sets the rating value.
     * Rating must be between 0.0 and 5.0 inclusive.
     *
     * @param rateValue The rating value
     * @throws IllegalArgumentException if rating is not between 0.0 and 5.0
     */
    public void setRateValue(double rateValue) {
        if (rateValue < 0.0 || rateValue > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        this.rateValue = rateValue;
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
        return "DisplayRateDTO{" +
                "rateId=" + rateId +
                ", patientName='" + patientName + '\'' +
                ", rateValue=" + rateValue +
                ", waitingTime=" + waitingTime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
