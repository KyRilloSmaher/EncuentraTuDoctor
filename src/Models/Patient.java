package Models;

import java.util.List;

/**
 * Represents a patient in the healthcare system. Extends the base User class
 * and adds medical history information specific to patients.
 */
public class Patient extends User {
    /** Medical history and background information of the patient */
    private String medicalHistory;

    /**
     * Default constructor. Initializes the patient with default values.
     */
    public Patient() {
        super();
    }

    /**
     * Creates a new patient with the specified details.
     *
     * @param name           Full name of the patient
     * @param age            Age of the user in years
     * @param gender         Gender of the  User
     * @param email          Email address of the patient
     * @param password       Password for the patient account
     * @param phone          Contact phone number
     * @param role           Role of the user (will be set to PATIENT)
     * @param medicalHistory Medical history and background information
     */
    public Patient(String name, int age, String gender ,String email, String password, String phone, UserRole role, String medicalHistory) {
        super(name,age,gender,email, password, phone, role);
        this.medicalHistory = medicalHistory;
        super.setRole(UserRole.PATIENT);
    }

    /**
     * Creates a new patient with the specified details including ID.
     *
     * @param id             Unique identifier for the patient
     * @param age            Age of the user in years
     * @param gender         Gender of the USer
     * @param name           Full name of the patient
     * @param email          Email address of the patient
     * @param password       Password for the patient account
     * @param phone          Contact phone number
     * @param role           Role of the user (will be set to PATIENT)
     * @param medicalHistory Medical history and background information
     */
    public Patient(int id, int age ,String name, String gender, String email, String password, String phone, UserRole role, String medicalHistory) {
        super(id,name,age,gender,email, password, phone, role);
        this.medicalHistory = medicalHistory;
        super.setRole(UserRole.PATIENT);
    }
    /**
     * Creates a new patient with the specified details including ID.
     *
     * @param id             Unique identifier for the patient
     * @param age            Age of the user in years
     * @param gender         Gender of the USer
     * @param name           Full name of the patient
     * @param email          Email address of the patient
     * @param medicalHistory Medical history and background information
     */
    public Patient(int id, String name, int age ,String gender, String email,String phone, String medicalHistory) {
        super.setId(id);
        super.setName(name);
        super.setGender(gender);
        super.setEmail(email);
        super.setPhone(phone);
        this.medicalHistory = medicalHistory;
        super.setRole(UserRole.PATIENT);
    }
    /**
     * Gets the medical history of the patient.
     *
     * @return The patient's medical history
     */
    public String getMedicalHistory() {
        return medicalHistory;
    }

    /**
     * Sets the medical history of the patient.
     *
     * @param medicalHistory The new medical history information
     */
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                '}';
    }
}
