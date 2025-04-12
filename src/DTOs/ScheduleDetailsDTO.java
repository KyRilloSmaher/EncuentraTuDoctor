package DTOs;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Data Transfer Object for displaying detailed schedule information.
 * Contains comprehensive details about a clinic's schedule including
 * time slots and location information.
 */
public class ScheduleDetailsDTO {
    /** Unique identifier of the schedule */
    private int scheduleId;
    
    /** Id of the clinic */
    private int clinicId;
    
    /** Physical address of the clinic */
    private String clinicAddress;
    
    /** Day of the week for this schedule */
    private DayOfWeek dayOfWeek;
    
    /** Start time of the schedule */
    private LocalTime startTime;
    
    /** End time of the schedule */
    private LocalTime endTime;

    
    /**
     * Empty Constructor
     * Constructs a new ScheduleDetailsDTO .

     */
    public ScheduleDetailsDTO(){}
      
      
    /**
     * Constructs a new ScheduleDetailsDTO with the specified details.
     *
     * @param scheduleId Unique identifier of the schedule
     * @param dayOfWeek Day of the week for this schedule
     * @param startTime Start time of the schedule
     * @param endTime End time of the schedule
     * @param clinicId Id of the clinic
     * @param clinicAddress Physical address of the clinic
     */
    public ScheduleDetailsDTO(int scheduleId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, 
                            int clinicId, String clinicAddress) {
        this.scheduleId = scheduleId;
        this.clinicId = clinicId;
        this.clinicAddress = clinicAddress;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets the unique identifier of the schedule.
     *
     * @return The schedule's ID
     */
    public int getScheduleId() {
        return scheduleId;
    }

    /**
     * Sets the unique identifier of the schedule.
     *
     * @param scheduleId The schedule's ID
     */
    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * Gets the name of the clinic.
     *
     * @return The clinic's Id
     */
    public int getClinicId() {
        return clinicId;
    }

    /**
     * Sets the Id of the clinic.
     *
     * @param clinicId The clinic's Id
     */
    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    /**
     * Gets the day of the week for this schedule.
     *
     * @return The day of the week
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Sets the day of the week for this schedule.
     *
     * @param dayOfWeek The day of the week
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Gets the physical address of the clinic.
     *
     * @return The clinic's address
     */
    public String getClinicAddress() {
        return clinicAddress;
    }

    /**
     * Sets the physical address of the clinic.
     *
     * @param clinicAddress The clinic's address
     */
    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    /**
     * Gets the start time of the schedule.
     *
     * @return The start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the schedule.
     *
     * @param startTime The start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the schedule.
     *
     * @return The end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the schedule.
     *
     * @param endTime The end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ScheduleDetailsDTO{" +
                "scheduleId=" + scheduleId +
                ", clinicName='" + clinicId + '\'' +
                ", clinicAddress='" + clinicAddress + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
