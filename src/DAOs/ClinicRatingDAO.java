/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import  Utiles.DatabaseConnection;
import  Models.ClinicRating;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author KyRilloS
 */

public class ClinicRatingDAO {
    private final Connection connection = DatabaseConnection.getConnection();
    private final LogDAO logDAO = new LogDAO(connection);

    // Create Clinic Rating
    public boolean addClinicRating(ClinicRating clinicRating) {
        String sql = "INSERT INTO clinic_ratings (patient_id, clinic_id, rating_value, comment) " +
                     "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, clinicRating.getPatientId());
            statement.setInt(2, clinicRating.getClinicId());
            statement.setDouble(3, clinicRating.getRatingValue());
            statement.setString(4, clinicRating.getComment());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logDAO.logError("ClinicRatingDAO", "addClinicRating", "Failed to create clinic rating", null);
                return false;
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    clinicRating.setRatingId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            logDAO.logError("ClinicRatingDAO", "addClinicRating", 
                           "Error adding clinic rating for clinic ID: " + clinicRating.getClinicId(), e);
            return false;
        }
    }

    // Read Clinic Rating by ID
    public ClinicRating getClinicRatingById(int id) {
        String sql = "SELECT * FROM clinic_ratings WHERE rating_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractClinicRatingFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logDAO.logError("ClinicRatingDAO", "getClinicRatingById", 
                          "Error fetching clinic rating ID: " + id, e);
        }
        return null;
    }

    // Update Clinic Rating
    public boolean updateClinicRating(ClinicRating clinicRating) {
        String sql = "UPDATE clinic_ratings SET patient_id = ?, clinic_id = ?, " +
                     "rating_value = ?, comment = ? WHERE rating_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clinicRating.getPatientId());
            statement.setInt(2, clinicRating.getClinicId());
            statement.setDouble(3, clinicRating.getRatingValue());
            statement.setString(4, clinicRating.getComment());
            statement.setInt(5, clinicRating.getRatingId());
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logDAO.logError("ClinicRatingDAO", "updateClinicRating", 
                          "Error updating clinic rating ID: " + clinicRating.getRatingId(), e);
            return false;
        }
    }

    // Delete Clinic Rating
    public boolean deleteClinicRating(int id) {
        String sql = "DELETE FROM clinic_ratings WHERE rating_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logDAO.logError("ClinicRatingDAO", "deleteClinicRating", 
                          "Error deleting clinic rating ID: " + id, e);
            return false;
        }
    }

    // List All Clinic Ratings
    public List<ClinicRating> getAllClinicRatings() {
        List<ClinicRating> clinicRatings = new ArrayList<>();
        String sql = "SELECT * FROM clinic_ratings";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                clinicRatings.add(extractClinicRatingFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logDAO.logError("ClinicRatingDAO", "getAllClinicRatings", 
                          "Error fetching all clinic ratings", e);
        }
        return clinicRatings;
    }

    // List all Ratings for a Specific Clinic
    public List<ClinicRating> getAllRatingsForClinic(int clinicId) {
        List<ClinicRating> clinicRatings = new ArrayList<>();
        String sql = "SELECT * FROM clinic_ratings WHERE clinic_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clinicId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    clinicRatings.add(extractClinicRatingFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logDAO.logError("ClinicRatingDAO", "getAllRatingsForClinic", 
                          "Error fetching ratings for clinic ID: " + clinicId, e);
        }
        return clinicRatings;
    }

    // List all Rated Clinics By Specific patient
    public List<ClinicRating> getAllRatedClinicsByPatient(int patientId) {
        List<ClinicRating> clinicRatings = new ArrayList<>();
        String sql = "SELECT * FROM clinic_ratings WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    clinicRatings.add(extractClinicRatingFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logDAO.logError("ClinicRatingDAO", "getAllRatedClinicsByPatient", 
                          "Error fetching rated clinics for patient ID: " + patientId, e);
        }
        return clinicRatings;
    }

    // Helper method to extract ClinicRating from ResultSet
    private ClinicRating extractClinicRatingFromResultSet(ResultSet resultSet) throws SQLException {
        ClinicRating rating = new ClinicRating();
        rating.setRatingId(resultSet.getInt("rating_id"));
        rating.setPatientId(resultSet.getInt("patient_id"));
        rating.setClinicId(resultSet.getInt("clinic_id"));
        rating.setRatingValue(resultSet.getDouble("rating_value"));
        rating.setComment(resultSet.getString("comment"));
        return rating;
    }
}
