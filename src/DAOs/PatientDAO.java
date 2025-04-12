/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Utiles.DatabaseConnection;
import Models.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author KyRilloS
 */



public class PatientDAO {
    private final Connection connection = DatabaseConnection.getConnection();
    private final LogDAO logDAO = new LogDAO(connection);

    // Create Patient
    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO users (age, name,email, password, phone,gender, role, active) " +
                     "VALUES (?, ?, ?,?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, patient.getAge());
            statement.setString(2, patient.getName());
            statement.setString(3, patient.getEmail());
            statement.setString(4, patient.getPassword());
            statement.setString(5, patient.getPhone());
            statement.setString(6, patient.getGender());
            statement.setString(7, "PATIENT");
            statement.setBoolean(8, patient.isActive());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logDAO.logError("PatientDAO", "addPatient", "Failed to create patient", null);
                return false;
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    patient.setId(generatedKeys.getInt(1));
                    
                    // Now add to patients table
                    String patientSql = "INSERT INTO patients (id, medical_history) VALUES (?, ?)";
                    try (PreparedStatement patientStmt = connection.prepareStatement(patientSql)) {
                        patientStmt.setInt(1, patient.getId());
                        patientStmt.setString(2, patient.getMedicalHistory());
                        patientStmt.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            logDAO.logError("PatientDAO", "addPatient", e.getMessage(), e);
            return false;
        }
    }

    // Get Patient by ID
    public Patient getPatientById(int id) {
        String sql = "SELECT u.*, p.medical_history FROM users u " +
                     "JOIN patients p ON u.id = p.id " +
                     "WHERE u.id = ? AND u.role = 'PATIENT'";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractPatientFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logDAO.logError("PatientDAO", "getPatientById", "Error fetching patient ID: " + id, e);
        }
        return null;
    }

    // Update Patient
    public boolean updatePatient(Patient patient) {
        String userSql = "UPDATE users SET age = ?, name = ?, email = ?"+
                         "phone = ?,updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        String patientSql = "UPDATE patients SET medical_history = ? WHERE id = ?";
        
        try {
            // Update users table
            try (PreparedStatement userStmt = connection.prepareStatement(userSql)) {
                userStmt.setInt(1, patient.getAge());
                userStmt.setString(2, patient.getName());
                userStmt.setString(3, patient.getEmail());
                userStmt.setString(4, patient.getPhone());
                userStmt.setInt(5, patient.getId());
                userStmt.executeUpdate();
            }
            
            // Update patients table
            try (PreparedStatement patientStmt = connection.prepareStatement(patientSql)) {
                patientStmt.setString(1, patient.getMedicalHistory());
                patientStmt.setInt(2, patient.getId());
                patientStmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            logDAO.logError("PatientDAO", "updatePatient", 
                          "Error updating patient ID: " + patient.getId(), e);
            return false;
        }
    }
    

    // Delete Patient
    public boolean deletePatient(int id) {
        try {
            // First delete from patients table
            String patientSql = "DELETE FROM patients WHERE id = ?";
            try (PreparedStatement patientStmt = connection.prepareStatement(patientSql)) {
                patientStmt.setInt(1, id);
                patientStmt.executeUpdate();
            }    
            // Then delete from users table
            String userSql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement userStmt = connection.prepareStatement(userSql)) {
                userStmt.setInt(1, id);
                return userStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            logDAO.logError("PatientDAO", "deletePatient", 
                          "Error deleting patient ID: " + id, e);
            return false;
        }
    }

    // List All Patients
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT u.*, p.medical_history FROM users u " +
                     "JOIN patients p ON u.id = p.id " +
                     "WHERE u.role = 'PATIENT'";
        
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                patients.add(extractPatientFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logDAO.logError("PatientDAO", "getAllPatients", "Error fetching all patients", e);
        }
        return patients;
    }
    // check if patientExist 
    public boolean isExist(int patientId){
             String sql = "SELECT id " +
                            "From patients " +
                            "WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            logDAO.logError("PatientDAO", "IS patient Exist", "Error fetching patient ID: " + patientId, e);
        }
        return false;
    }
    // Helper method to extract Patient from ResultSet
    private Patient extractPatientFromResultSet(ResultSet resultSet) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getInt("id"));
        patient.setAge(resultSet.getInt("age"));
        patient.setName(resultSet.getString("name"));
        patient.setGender(resultSet.getString("gender"));
        patient.setEmail(resultSet.getString("email"));
        patient.setPassword(resultSet.getString("password"));
        patient.setPhone(resultSet.getString("phone"));
        patient.setActive(resultSet.getBoolean("active"));
        patient.setMedicalHistory(resultSet.getString("medical_history"));
        
        // Handle timestamps
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        if (createdAt != null) {
            patient.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            patient.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return patient;
    }
}
