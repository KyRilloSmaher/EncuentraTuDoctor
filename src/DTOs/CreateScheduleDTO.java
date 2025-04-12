package DTOs;

import java.time.LocalTime;
import java.time.DayOfWeek;

/**
 * Data Transfer Object for creating a new schedule slot.
 * Contains information about when a practitioner is available at a clinic.
 */
public class CreateScheduleDTO {
    /** ID of the clinic where the schedule applies */
    private int clinicId;
    
    /** Day of the week for this schedule slot */
    private DayOfWeek dayOfWeek;
    
    /** Start time of the schedule slot */
    private LocalTime startTime;
    
    /** End time of the schedule slot */
    private LocalTime endTime;

    /**
     * Gets the ID of the clinic.
     *
     * @return The clinic's ID
     */
    public int getClinicId() {
        return clinicId;
    }

    /**
     * Sets the ID of the clinic.
     *
     * @param clinicId The clinic's ID
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
     * Gets the start time of the schedule slot.
     *
     * @return The start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the schedule slot.
     *
     * @param startTime The start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the schedule slot.
     *
     * @return The end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the schedule slot.
     *
     * @param endTime The end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CreateScheduleDTO{" +
                "clinicId=" + clinicId +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
