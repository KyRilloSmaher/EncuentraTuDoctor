/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Utiles.DatabaseConnection;
import Models.Patient;
import Models.User;
import Models.UserRole;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author KyRilloS
 */
public class UserDAO {

      private Connection connection = DatabaseConnection.getConnection();
      private final LogDAO logDAO = new LogDAO(connection);
    /**
     * Authenticates a user by email and password
     * @param email User's email
     * @param password User's password 
     * @return User object if authentication succeeds, null otherwise
     */
    public User IsUserByEmailandPasswordExist(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
             logDAO.logError("UserDAO", "IsUserByEmailandPasswordExist", "Error authenticating user", e);
        }
        return null;
    }

    /**
     * Updates a user's password after verifying old password
     * @param email User's email
     * @param oldPassword Current password (for verification)
     * @param newPassword New password to set
     */
    public void updateUserPassword(String email, String oldPassword, String newPassword) {
        // First verify old password
        User user = IsUserByEmailandPasswordExist(email, oldPassword);
        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,newPassword);
            statement.setString(2, email);
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Password update failed, no rows affected");
            }
        } catch (SQLException e) {
            logDAO.logError("UserDAO", "updateUserPassword", "Error Updating Password", e);
          
        }
    }

      // Helper methods
    
    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setAge(resultSet.getInt("age"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setPhone(resultSet.getString("phone"));
        user.setRole(UserRole.valueOf(resultSet.getString("role")));
        user.setActive(resultSet.getBoolean("active"));
        
        // Handle timestamps
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            user.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return user;
    }
}
