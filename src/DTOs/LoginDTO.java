package DTOs;

/**
 * Data Transfer Object for handling user login information.
 * Contains the user's email and password for authentication.
 */
public class LoginDTO {
    /** User's email address */
    private String email;
    
    /** User's password */
    private String password;

    /**
     * Constructs a new LoginDTO with the specified credentials.
     *
     * @param email User's email address
     * @param password User's password
     */
    public LoginDTO(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    /**
     * Gets the user's email address.
     *
     * @return The user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     * Email must not be null or empty.
     *
     * @param email The user's email
     * @throws IllegalArgumentException if email is null or empty
     */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = email.trim();
    }

    /**
     * Gets the user's password.
     *
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * Password must not be null or empty.
     *
     * @param password The user's password
     * @throws IllegalArgumentException if password is null or empty
     */
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}
