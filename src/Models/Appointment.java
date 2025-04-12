package Models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a medical appointment between a patient and a practitioner
 * at a specific clinic and scheduled time.
 */
public class Appointment {
    /** Unique identifier for the appointment */
    private int id;
    
    /** ID of the practitioner handling the appointment */
    private int practitionerId;
    
    /** ID of the patient attending the appointment */
    private int patientId;
    
    /** ID of the clinic where the appointment takes place */
    private int clinicId;
    
    /** ID of the schedule slot for this appointment */
    private int scheduleId;
    
    /** Current status of the appointment (e.g., SCHEDULED, COMPLETED, CANCELLED) */
    private AppointmentStatus status;
    
    /** Additional notes or comments about the appointment */
    private String notes;
    
    /** Timestamp when the appointment was created */
    private LocalDateTime createdAt;
    
    /** Timestamp when the appointment was last updated */
    private LocalDateTime updatedAt;
    
    /** The schedule slot details for this appointment */
    private Schedule schedule;
    
    /** The patient attending this appointment */
    private Patient patient;
    
    /** The practitioner handling this appointment */
    private Practitioner practitioner;
    
    /** The clinic where this appointment takes place */
    private Clinic clinic;

    /**
     * Default constructor. Initializes the creation timestamp and sets default status.
     */
    public Appointment() {
        this.createdAt = LocalDateTime.now();
        this.status = AppointmentStatus.SCHEDULED;
    }

    /**
     * Creates a new appointment with the specified details.
     *
     * @param id            Unique identifier for the appointment
     * @param practitionerId ID of the practitioner
     * @param patientId     ID of the patient
     * @param clinicId      ID of the clinic
     * @param scheduleId    ID of the schedule slot
     * @param notes         Additional notes about the appointment
     */
    public Appointment(int id, int practitionerId, int patientId, int clinicId, int scheduleId, String notes) {
        this.id = id;
        this.practitionerId = practitionerId;
        this.patientId = patientId;
        this.clinicId = clinicId;
        this.scheduleId = scheduleId;
        this.status = AppointmentStatus.SCHEDULED;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(schedule, that.schedule) &&
               Objects.equals(patient, that.patient) &&
               Objects.equals(practitioner, that.practitioner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, schedule, patient, practitioner);
    }

    /**
     * Gets the unique identifier of the appointment.
     *
     * @return The appointment's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the appointment.
     *
     * @param id The new ID for the appointment
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the practitioner handling this appointment.
     *
     * @return The practitioner's ID
     */
    public int getPractitionerId() {
        return practitionerId;
    }

    /**
     * Sets the ID of the practitioner handling this appointment.
     *
     * @param practitionerId The new practitioner ID
     */
    public void setPractitionerId(int practitionerId) {
        this.practitionerId = practitionerId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}