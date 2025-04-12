/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Utiles.DatabaseConnection;
import Models.Practitioner;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author KyRilloS
 */

public class PractitionerDAO {
    private final Connection connection = DatabaseConnection.getConnection();
    private final LogDAO logDAO = new LogDAO(connection);

    // Create Practitioner
    public boolean addPractitioner(Practitioner practitioner) {
        String sql = "INSERT INTO users (age, name,gender, email, password, phone, role, active) " +
                     "VALUES (?, ?, ?,?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, practitioner.getAge());
            statement.setString(2, practitioner.getName());
            statement.setString(3, practitioner.getGender());
            statement.setString(4, practitioner.getEmail());
            statement.setString(5, practitioner.getPassword());
            statement.setString(6, practitioner.getPhone());
            statement.setString(7, "PRACTITIONER");
            statement.setBoolean(8, practitioner.isActive());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logDAO.logError("PractitionerDAO", "addPractitioner", "Failed to create practitioner", null);
                return false;
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    practitioner.setId(generatedKeys.getInt(1));
                    
                    // Now add to practitioners table
                    String practitionerSql = "INSERT INTO practitioners (id, specialization, appointment_price, rating) " +
                                           "VALUES (?, ?, ?, ?)";
                    try (PreparedStatement practitionerStmt = connection.prepareStatement(practitionerSql)) {
                        practitionerStmt.setInt(1, practitioner.getId());
                        practitionerStmt.setString(2, practitioner.getSpecialization());
                        practitionerStmt.setDouble(3, practitioner.getAppointmentPrice());
                        practitionerStmt.setDouble(4, practitioner.getRating());
                        practitionerStmt.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            logDAO.logError("PractitionerDAO", "addPractitioner", "Error adding practitioner", e);
            return false;
        }
    }

    // Get Practitioner by ID
    public Practitioner getPractitionerById(int id) {
        String sql = "SELECT u.*, p.specialization, p.appointment_price, p.rating " +
                     "FROM users u JOIN practitioners p ON u.id = p.id " +
                     "WHERE u.id = ? AND u.role = 'PRACTITIONER'";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractPractitionerFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logDAO.logError("PractitionerDAO", "getPractitionerById", 
                          "Error fetching practitioner ID: " + id, e);
        }
        return null;
    }

    // Update Practitioner
    public boolean updatePractitioner(Practitioner practitioner) {
        String userSql = "UPDATE users SET age = ?, name = ?,gender =?, email = ?, password = ?, " +
                         "phone = ?, active = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        String practitionerSql = "UPDATE practitioners SET specialization = ?, " +
                                "appointment_price = ?, rating = ? WHERE id = ?";
        
        try {
            // Update users table
            try (PreparedStatement userStmt = connection.prepareStatement(userSql)) {
                userStmt.setInt(1, practitioner.getAge());
                userStmt.setString(2, practitioner.getName());
                userStmt.setString(3, practitioner.getGender());
                userStmt.setString(4, practitioner.getEmail());
                userStmt.setString(5, practitioner.getPassword());
                userStmt.setString(6, practitioner.getPhone());
                userStmt.setBoolean(7, practitioner.isActive());
                userStmt.setInt(8, practitioner.getId());
                userStmt.executeUpdate();
            }
            
            // Update practitioners table
            try (PreparedStatement practitionerStmt = connection.prepareStatement(practitionerSql)) {
                practitionerStmt.setString(1, practitioner.getSpecialization());
                practitionerStmt.setDouble(2, practitioner.getAppointmentPrice());
                practitionerStmt.setDouble(3, practitioner.getRating());
                practitionerStmt.setInt(4, practitioner.getId());
                practitionerStmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            logDAO.logError("PractitionerDAO", "updatePractitioner", 
                          "Error updating practitioner ID: " + practitioner.getId(), e);
            return false;
        }
    }

    // Delete Practitioner
    public boolean deletePractitioner(int id) {
        try {
            // First delete from practitioners table
            String practitionerSql = "DELETE FROM practitioners WHERE id = ?";
            try (PreparedStatement practitionerStmt = connection.prepareStatement(practitionerSql)) {
                practitionerStmt.setInt(1, id);
                practitionerStmt.executeUpdate();
            }
            
            // Then delete from users table
            String userSql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement userStmt = connection.prepareStatement(userSql)) {
                userStmt.setInt(1, id);
                return userStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            logDAO.logError("PractitionerDAO", "deletePractitioner", 
                          "Error deleting practitioner ID: " + id, e);
            return false;
        }
    }

    // List All Practitioners
    public List<Practitioner> getAllPractitioners() {
        List<Practitioner> practitioners = new ArrayList<>();
        String sql = "SELECT u.*, p.specialization, p.appointment_price, p.rating " +
                     "FROM users u JOIN practitioners p ON u.id = p.id " +
                     "WHERE u.role = 'PRACTITIONER'";
        
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                practitioners.add(extractPractitionerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logDAO.logError("PractitionerDAO", "getAllPractitioners", "Error fetching all practitioners", e);
        }
        return practitioners;
    }
    public boolean isExist(int Id){
             String sql = "SELECT id " +
                            "From practitioners " +
                            "WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,Id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            logDAO.logError("PatientDAO", "IS patient Exist", "Error fetching patient ID: " +Id, e);
        }
        return false;
    }
    // Helper method to extract Practitioner from ResultSet
    private Practitioner extractPractitionerFromResultSet(ResultSet resultSet) throws SQLException {
        Practitioner practitioner = new Practitioner();
        practitioner.setId(resultSet.getInt("id"));
        practitioner.setAge(resultSet.getInt("age"));
        practitioner.setName(resultSet.getString("name"));
        practitioner.setGender(resultSet.getString("gender"));
        practitioner.setEmail(resultSet.getString("email"));
        practitioner.setPassword(resultSet.getString("password"));
        practitioner.setPhone(resultSet.getString("phone"));
        practitioner.setActive(resultSet.getBoolean("active"));
        practitioner.setSpecialization(resultSet.getString("specialization"));
        practitioner.setAppointmentPrice(resultSet.getDouble("appointment_price"));
        practitioner.setRating(resultSet.getDouble("rating"));
        
        // Handle timestamps
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        if (createdAt != null) {
            practitioner.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            practitioner.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return practitioner;
    }
}
