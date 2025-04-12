package DTOs;

/**
 * Data Transfer Object for creating a new appointment.
 * Contains all necessary information to schedule an appointment between
 * a patient and a practitioner at a specific clinic and time.
 */
public class AppointmentRequestDTO {
    /** ID of the patient requesting the appointment */
    private int patientId;
    
    /** ID of the practitioner for the appointment */
    private int practitionerId;
    
    /** ID of the clinic where the appointment will take place */
    private int clinicId;
    
    /** ID of the schedule slot for the appointment */
    private int scheduleId;
    
    /** Additional notes or comments about the appointment */
    private String notes;

    /**
     * Gets the ID of the patient requesting the appointment.
     *
     * @return The patient's ID
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Sets the ID of the patient requesting the appointment.
     *
     * @param patientId The patient's ID
     */
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the ID of the practitioner for the appointment.
     *
     * @return The practitioner's ID
     */
    public int getPractitionerId() {
        return practitionerId;
    }

    /**
     * Sets the ID of the practitioner for the appointment.
     *
     * @param practitionerId The practitioner's ID
     */
    public void setPractitionerId(int practitionerId) {
        this.practitionerId = practitionerId;
    }

    /**
     * Gets the ID of the clinic where the appointment will take place.
     *
     * @return The clinic's ID
     */
    public int getClinicId() {
        return clinicId;
    }

    /**
     * Sets the ID of the clinic where the appointment will take place.
     *
     * @param clinicId The clinic's ID
     */
    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    /**
     * Gets the ID of the schedule slot for the appointment.
     *
     * @return The schedule slot's ID
     */
    public int getScheduleId() {
        return scheduleId;
    }

    /**
     * Sets the ID of the schedule slot for the appointment.
     *
     * @param scheduleId The schedule slot's ID
     */
    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * Gets any additional notes about the appointment.
     *
     * @return The appointment notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets additional notes about the appointment.
     *
     * @param notes The appointment notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "AppointmentRequestDTO{" +
                "patientId=" + patientId +
                ", practitionerId=" + practitionerId +
                ", clinicId=" + clinicId +
                ", scheduleId=" + scheduleId +
                ", notes='" + notes + '\'' +
                '}';
    }
}
