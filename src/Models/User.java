package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user in the system. This class contains information about
 * both patients and practitioners, with their role determining their access
 * and capabilities within the system.
 */
public class User {
    /** Unique identifier for the user */
    private int id;
    
    /** Age of the user in years */
    private int age;
    
    /** Full name of the user */
    private String name;
/** Gender of the User*/
    private String gender;
    /** Email address of the user, used for communication and login */
    private String email;
    
    /** Hashed password for user authentication */
    private String password;
    
    /** Contact phone number of the user */
    private String phone;
    
    /** Role of the user in the system (e.g., PATIENT, PRACTITIONER, ADMIN) */
    private UserRole role;
    
    /** List of appointments associated with this user */
    private List<Appointment> appointments = new ArrayList<>();
    
    
    /** Indicates whether the user account is active */
    private boolean active = true;
    
    /** Timestamp when the user account was created */
    private java.time.LocalDateTime createdAt;
    
    /** Timestamp when the user account was last updated */
    private java.time.LocalDateTime updatedAt;

    /**
     * Default constructor. Initializes the creation timestamp.
     */
    public User() {
        this.createdAt = java.time.LocalDateTime.now();
    }

    /**
     * Creates a new user with the specified details.
     *
     * @param name     Full name of the user
     * @param age      Age of the user in years
     * @param gender  Gender of the User
     * @param email    Email address of the user
     * @param password Password for the user account
     * @param phone    Contact phone number
     * @param role     Role of the user in the system
     */
    public User(String name, int age,  String gender ,String email, String password, String phone, UserRole role) {
        this.name = name;
        this.age = age;
         this.gender = gender;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.createdAt = java.time.LocalDateTime.now();
    }

    /**
     * Creates a new user with the specified details including ID.
     *
     * @param id       Unique identifier for the user
     * @param name     Full name of the user
     * @param age      Age of the user in years
     * @param gender  Gender of the User
     * @param email    Email address of the user
     * @param password Password for the user account
     * @param phone    Contact phone number
     * @param role     Role of the user in the system
     */
    public User(int id, String name, int age, String gender, String email, String password, String phone, UserRole role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.createdAt = java.time.LocalDateTime.now();
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The new ID for the user
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    public int getAge() {
        return age;
    }
    
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public java.time.LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && 
               Objects.equals(email, user.email) && 
               Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", active=" + active +
                '}';
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

  
}