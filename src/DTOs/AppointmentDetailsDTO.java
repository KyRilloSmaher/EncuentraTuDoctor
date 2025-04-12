package DTOs;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Data Transfer Object for displaying detailed appointment information.
 * Contains comprehensive details about an appointment including patient,
 * practitioner, clinic, and schedule information.
 */
public class AppointmentDetailsDTO {
    /** Unique identifier of the appointment */
    private int appointmentId;
    
    /** Name of the patient */
    private String patientName;
    
    /** Name of the practitioner */
    private String practitionerName;
    
    /** Name of the clinic */
    private String clinicName;
    
    /** Address of the clinic */
    private String clinicAddress;
    
    /** Day of the week for the appointment */
    private DayOfWeek dayOfWeek;
    
    /** Start time of the appointment */
    private LocalTime startTime;
    
    /** End time of the appointment */
    private LocalTime endTime;

    /**
     * Gets the unique identifier of the appointment.
     *
     * @return The appointment's ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the unique identifier of the appointment.
     *
     * @param appointmentId The appointment's ID
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the name of the patient.
     *
     * @return The patient's name
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Sets the name of the patient.
     *
     * @param patientName The patient's name
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * Gets the name of the practitioner.
     *
     * @return The practitioner's name
     */
    public String getPractitionerName() {
        return practitionerName;
    }

    /**
     * Sets the name of the practitioner.
     *
     * @param practitionerName The practitioner's name
     */
    public void setPractitionerName(String practitionerName) {
        this.practitionerName = practitionerName;
    }

    /**
     * Gets the name of the clinic.
     *
     * @return The clinic's name
     */
    public String getClinicName() {
        return clinicName;
    }

    /**
     * Sets the name of the clinic.
     *
     * @param clinicName The clinic's name
     */
    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    /**
     * Gets the address of the clinic.
     *
     * @return The clinic's address
     */
    public String getClinicAddress() {
        return clinicAddress;
    }

    /**
     * Sets the address of the clinic.
     *
     * @param clinicAddress The clinic's address
     */
    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    /**
     * Gets the day of the week for the appointment.
     *
     * @return The day of the week
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Sets the day of the week for the appointment.
     *
     * @param dayOfWeek The day of the week
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Gets the start time of the appointment.
     *
     * @return The start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the appointment.
     *
     * @param startTime The start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the appointment.
     *
     * @return The end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the appointment.
     *
     * @param endTime The end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "AppointmentDetailsDTO{" +
                "appointmentId=" + appointmentId +
                ", patientName='" + patientName + '\'' +
                ", practitionerName='" + practitionerName + '\'' +
                ", clinicName='" + clinicName + '\'' +
                ", clinicAddress='" + clinicAddress + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}