package DTOs;

import java.util.ArrayList;
import java.util.List;
import Models.Schedule;

/**
 * Data Transfer Object for creating a new clinic.
 * Contains all necessary information to establish a new medical clinic
 * and its associated schedules.
 */
public class CreateClinicDTO {
    /** Name of the clinic */
    private String name;
    
    /** Physical location/address of the clinic */
    private String location;
    
    /** ID of the practitioner who owns/manages the clinic */
    private int practitionerId;
    
    /** List of schedule slots for the clinic */
    private List<Schedule> schedules = new ArrayList<>();

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
     * Gets the ID of the practitioner associated with the clinic.
     *
     * @return The practitioner's ID
     */
    public int getPractitionerId() {
        return practitionerId;
    }

    /**
     * Sets the ID of the practitioner associated with the clinic.
     *
     * @param practitionerId The practitioner's ID
     */
    public void setPractitionerId(int practitionerId) {
        this.practitionerId = practitionerId;
    }

    /**
     * Gets the list of schedule slots for the clinic.
     *
     * @return List of schedule slots
     */
    public List<Schedule> getSchedules() {
        return schedules;
    }

    /**
     * Sets the list of schedule slots for the clinic.
     *
     * @param schedules The new list of schedule slots
     */
    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return "CreateClinicDTO{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", practitionerId=" + practitionerId +
                ", schedules=" + schedules +
                '}';
    }
}
