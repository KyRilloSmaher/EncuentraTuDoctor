package DTOs;

import java.util.List;

/**
 * Data Transfer Object for displaying clinic profile information.
 * Contains comprehensive details about a clinic including its ratings,
 * practitioners, and available schedules.
 */
public class ClinicProfileDTO {
    /** Unique identifier of the clinic */
    private int clinicId;
    
    /** Name of the clinic */
    private String name;
    
    /** Physical location/address of the clinic */
    private String location;
    
    /** Average rating of the clinic (0.0 to 5.0) */
    private double rating;
    
    /** Name of practitioner(Owner) Of clinic */
    private String practitionerName;
    
    /** List of available schedule slots for appointments */
    private List<ScheduleDetailsDTO> availableSchedules;

    /**
     * Constructs a new ClinicProfileDTO with the specified details.
     *
     * @param clinicId Unique identifier of the clinic
     * @param name Name of the clinic
     * @param location Physical location/address of the clinic
     * @param rating Average rating of the clinic (0.0 to 5.0)
     * @param practitionerName practitioners working at this clinic
     * @param availableSchedules List of available schedule slots
     */
    public ClinicProfileDTO(int clinicId, String name, String location, double rating,
                           String practitionerName, List<ScheduleDetailsDTO> availableSchedules) {
        this.clinicId = clinicId;
        this.name = name;
        this.location = location;
        setRating(rating);
        this.practitionerName = practitionerName;
        this.availableSchedules = availableSchedules;
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
     * Gets the name of the clinic.
     *
     * @return The clinic's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the clinic.
     *
     * @param name The new clinic name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the location of the clinic.
     *
     * @return The clinic's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the clinic.
     *
     * @param location The new clinic location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the average rating of the clinic.
     *
     * @return The clinic's rating (0.0 to 5.0)
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the average rating of the clinic.
     * Rating must be between 0.0 and 5.0 inclusive.
     *
     * @param rating The new clinic rating
     * @throws IllegalArgumentException if rating is not between 0.0 and 5.0
     */
    public void setRating(double rating) {
        if (rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        this.rating = rating;
    }

    /**
     * Gets the list of practitioners working at the clinic.
     *
     * @return  practitioner name
     */
    public String getPractitionerName() {
        return practitionerName;
    }

    /**
     * Sets the list of practitioners working at the clinic.
     *
     * @param practitionerName The new list of practitioner name
     */
    public void setPractitionerName(String practitionerName) {
        this.practitionerName = practitionerName;
    }

    /**
     * Gets the list of available schedule slots.
     *
     * @return List of available schedules
     */
    public List<ScheduleDetailsDTO> getAvailableSchedules() {
        return availableSchedules;
    }

    /**
     * Sets the list of available schedule slots.
     *
     * @param availableSchedules The new list of available schedules
     */
    public void setAvailableSchedules(List<ScheduleDetailsDTO> availableSchedules) {
        this.availableSchedules = availableSchedules;
    }

    @Override
    public String toString() {
        return "ClinicProfileDTO{" +
                "clinicId=" + clinicId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", rating=" + rating +
                ", practitionerName=" + practitionerName +
                ", availableSchedules=" + availableSchedules +
                '}';
    }
}
