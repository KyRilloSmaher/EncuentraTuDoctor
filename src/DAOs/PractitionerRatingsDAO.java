/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.ClinicRating;
import Models.PractitionerRatings;
import Utiles.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KyRilloS
 */
public class PractitionerRatingsDAO {
     private final Connection connection = DatabaseConnection.getConnection();
    private final LogDAO logDAO = new LogDAO(connection);

    // Create Clinic Rating
    public boolean addRate(PractitionerRatings rate) {
        String sql = "INSERT INTO practitioner_ratings (patient_id,practitioner_id, rate_value,waiting_time ,comment) " +
                     "VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, rate.getPatientId());
            statement.setInt(2, rate.getPractitionerId());
            statement.setDouble(3,rate.getRateValue());
            statement.setString(5,rate.getComment());
            statement.setInt(4,rate.getWaitingTime());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logDAO.logError("PractitionerRatingsDAo", "addRate", "Failed to create practitionear rating", null);
                return false;
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rate.setRateId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            logDAO.logError("RatingDAO", "addRating", 
                           "Error adding  Practionearating for ID: " + rate.getRateId(), e);
            return false;
        }
    }

    // Read Clinic Rating by ID
    public PractitionerRatings getRatingById(int id) {
        String sql = "SELECT * FROM practitioner_ratings WHERE rate_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractRatingFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logDAO.logError("RatingDAO", "getRatingById", 
                          "Error fetching  rating ID: " + id, e);
        }
        return null;
    }

    // Update Clinic Rating
    public boolean updateRating(PractitionerRatings rate) {
        String sql = "UPDATE practitioner_ratings SET patient_id = ?, practitioner_id = ?, " +
                     "rating_value = ?, comment = ? WHERE rate_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rate.getPatientId());
            statement.setInt(2, rate.getPractitionerId());
            statement.setDouble(3, rate.getRateValue());
            statement.setString(4, rate.getComment());
            statement.setInt(5, rate.getRateId());
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logDAO.logError("RatingDAO", "updateRating", 
                          "Error updating  rating ID: " + rate.getRateId(), e);
            return false;
        }
    }

    // Delete Clinic Rating
    public boolean deleteClinicRating(int id) {
        String sql = "DELETE FROM practitioner_ratings WHERE rate_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logDAO.logError("RatingDAO", "deleteRating", 
                          "Error deleting  rating ID: " + id, e);
            return false;
        }
    }

    // List All Clinic Ratings
    public List<PractitionerRatings> getAllRatings() {
        List<PractitionerRatings> Ratings = new ArrayList<>();
        String sql = "SELECT * FROM practitioner_ratings";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
               Ratings.add(extractRatingFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logDAO.logError("RatingDAO", "getAllRatings", 
                          "Error fetching all  ratings", e);
        }
        return Ratings;
    }
    public List<PractitionerRatings> getAllRatesOfPractitionear(int id){
    List<PractitionerRatings> Ratings = new ArrayList<>();
        String sql = "SELECT * FROM practitioner_ratings WHERE practitioner_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Ratings.add(extractRatingFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logDAO.logError("RatingDAO", "getAllRatedOfPractitonear", 
                          "Error fetching rated for Practitonear ID: " + id, e);
        }
        return Ratings;
 
      }
    // List all Rated Practitonear By Specific patient
    public List<PractitionerRatings> getAllRatedPractitionerByPatient(int patientId) {
        List<PractitionerRatings> Ratings = new ArrayList<>();
        String sql = "SELECT * FROM practitioner_ratings WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Ratings.add(extractRatingFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logDAO.logError("RatingDAO", "getAllRatedByPatient", 
                          "Error fetching rated for patient ID: " + patientId, e);
        }
        return Ratings;
    }

    // Helper method to extract ClinicRating from ResultSet
    private PractitionerRatings extractRatingFromResultSet(ResultSet resultSet) throws SQLException {
        PractitionerRatings rating = new PractitionerRatings();
        rating.setPatientId(resultSet.getInt("patient_id"));
        rating.setPractitionerId(resultSet.getInt("practitioner_id"));
        rating.setWaitingTime(resultSet.getInt("waiting_time"));
        rating.setRateValue(resultSet.getDouble("rate_value"));
        rating.setComment(resultSet.getString("comment"));
        return rating;
    } 
}
