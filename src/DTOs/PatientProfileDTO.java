package DTOs;

/**
 * Data Transfer Object for displaying patient profile information.
 * Contains comprehensive details about a patient including personal
 * information and medical history.
 */
public class PatientProfileDTO {
    /** Unique identifier of the patient */
    private int id;
    
    /** Full name of the patient */
    private String name;
    
    /** Email address for communication */
    private String email;
    
    /** Physical address of the patient */
    private String address;
    
    /** Medical history and background information */
    private String medicalHistory;
    
    /** Age of the patient in years */
    private int age;
    
    /** Gender of the patient */
    private String gender;
    
    /** Contact phone number */
    private String phoneNumber;

    /**
     * Gets the unique identifier of the patient.
     *
     * @return The patient's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the patient.
     *
     * @param id The patient's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the full name of the patient.
     *
     * @return The patient's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the patient.
     *
     * @param name The patient's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the patient.
     *
     * @return The patient's age in years
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the patient.
     *
     * @param age The patient's age in years
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the gender of the patient.
     *
     * @return The patient's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the patient.
     *
     * @param gender The patient's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the physical address of the patient.
     *
     * @return The patient's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the physical address of the patient.
     *
     * @param address The patient's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the email address of the patient.
     *
     * @return The patient's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the patient.
     *
     * @param email The patient's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the contact phone number of the patient.
     *
     * @return The patient's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the contact phone number of the patient.
     *
     * @param phoneNumber The patient's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
     * @param medicalHistory The patient's medical history
     */
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "PatientProfileDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
