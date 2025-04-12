package DTOs;

/**
 * Data Transfer Object for patient registration.
 * Contains all necessary information to create a new patient account.
 */
public class PatientRegistrationDTO {
    /** Full name of the patient */
    private String name;
    
    /** Age of the patient in years */
    private int age;
    
    /** Gender of the patient */
    private String gender;
    
    
    /** Email address for communication and login */
    private String email;
    
    /** Hashed password for authentication */
    private String password;
    
    /** Confirmation of the password for validation */
    private String confirmPassword;
    
    /** Contact phone number */
    private String phoneNumber;
    
    /** Type of user (always "Patient" for this DTO) */
    private String userType = "Patient";
    
    /** Medical history and background information */
    private String medicalHistory;

    /**
     * Creates a new patient registration DTO with all required information.
     *
     * @param name           Full name of the patient
     * @param age            Age of the patient in years
     * @param gender         Gender of the patient
     * @param email          Email address
     * @param password       Hashed password
     * @param confirmPassword Confirmation of the password
     * @param phoneNumber    Contact phone number
     * @param medicalHistory Medical history information
     */
    public PatientRegistrationDTO(String name, int age, String gender,
                                String email, String password, String confirmPassword, 
                                String phoneNumber, String medicalHistory) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        this.medicalHistory = medicalHistory;
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
     * @param name The new name
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
     * @param age The new age in years
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
     * @param gender The new gender
     */
    public void setGender(String gender) {
        this.gender = gender;
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
     * @param email The new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the hashed password.
     *
     * @return The hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the hashed password.
     *
     * @param password The new hashed password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the password confirmation.
     *
     * @return The confirmed password
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets the password confirmation.
     *
     * @param confirmPassword The new password confirmation
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Gets the contact phone number.
     *
     * @return The phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the contact phone number.
     *
     * @param phoneNumber The new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the user type.
     *
     * @return The user type (always "Patient")
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Gets the medical history.
     *
     * @return The medical history information
     */
    public String getMedicalHistory() {
        return medicalHistory;
    }

    /**
     * Sets the medical history.
     *
     * @param medicalHistory The new medical history
     */
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "PatientRegistrationDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
             
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userType='" + userType + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                '}';
    }
}
