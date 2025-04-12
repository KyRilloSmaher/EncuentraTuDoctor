/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Utiles.DatabaseConnection;
import Models.Clinic;
import Models.Schedule;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the Clinic entity.
 * Provides CRUD operations and other database interactions for clinics.
 * @author KyRilloS
 */
public class ClinicDAO {
    private final Connection connection = DatabaseConnection.getConnection();
    private final LogDAO logDAO = new LogDAO(connection);

    /**
     * Adds a new clinic to the database.
     *
     * @param clinic The Clinic object to be added.
     * @return true if the clinic was successfully added, false otherwise.
     */
    public boolean addClinic(Clinic clinic) {
        String sql = "INSERT INTO clinics (name, location, rating, practitioner_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, clinic.getName());
            statement.setString(2, clinic.getLocation());
            statement.setDouble(3, clinic.getRating());
            statement.setInt(4, clinic.getPractitionerId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logDAO.logError("ClinicDAO", "addClinic", "Failed to create clinic", null);
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    clinic.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            logDAO.logError("ClinicDAO", "addClinic", "Error adding clinic", e);
            return false;
        }
    }

    /**
     * Retrieves a clinic from the database based on its ID.
     * Also fetches the schedules associated with the clinic.
     *
     * @param id The ID of the clinic to retrieve.
     * @return The Clinic object if found, null otherwise.
     */
    public Clinic getClinicById(int id) {
        String clinicSql = "SELECT * FROM clinics WHERE id = ?";
        String scheduleSql = "SELECT * FROM schedules WHERE clinic_id = ?";

        Clinic clinic = null;
        try (PreparedStatement clinicStmt = connection.prepareStatement(clinicSql)) {
            clinicStmt.setInt(1, id);
            try (ResultSet clinicRs = clinicStmt.executeQuery()) {
                if (clinicRs.next()) {
                    clinic = new Clinic();
                    clinic.setId(clinicRs.getInt("id"));
                    clinic.setName(clinicRs.getString("name"));
                    clinic.setLocation(clinicRs.getString("location"));
                    clinic.setRating(clinicRs.getDouble("rating"));
                    clinic.setPractitionerId(clinicRs.getInt("practitioner_id"));

                    // Load schedules
                    List<Schedule> schedules = new ArrayList<>();
                    try (PreparedStatement scheduleStmt = connection.prepareStatement(scheduleSql)) {
                        scheduleStmt.setInt(1, id);
                        try (ResultSet scheduleRs = scheduleStmt.executeQuery()) {
                            while (scheduleRs.next()) {
                                Schedule schedule = new Schedule();
                                schedule.setId(scheduleRs.getInt("id"));
                                schedule.setDayOfWeek(scheduleRs.getString("day_of_week"));
                                schedule.setStartTime(scheduleRs.getTime("start_time").toLocalTime());
                                schedule.setEndTime(scheduleRs.getTime("end_time").toLocalTime());
                                schedules.add(schedule);
                            }
                        }
                    }
                    clinic.setSchedules(schedules);
                }
            }
        } catch (SQLException e) {
            logDAO.logError("ClinicDAO", "getClinicById", "Error fetching clinic ID: " + id, e);
        }
        return clinic;
    }

    /**
     * Updates an existing clinic in the database.
     *
     * @param clinic The Clinic object with updated information.
     * @return true if the clinic was successfully updated, false otherwise.
     */
    public boolean updateClinic(Clinic clinic) {
        String sql = "UPDATE clinics SET name = ?, location = ?, rating = ?, practitioner_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, clinic.getName());
            statement.setString(2, clinic.getLocation());
            statement.setDouble(3, clinic.getRating());
            statement.setInt(4, clinic.getPractitionerId());
            statement.setInt(5, clinic.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logDAO.logError("ClinicDAO", "updateClinic",
                    "Error updating clinic ID: " + clinic.getId(), e);
            return false;
        }
    }

    /**
     * Deletes a clinic from the database based on its ID.
     *
     * @param id The ID of the clinic to delete.
     * @return true if the clinic was successfully deleted, false otherwise.
     */
    public boolean deleteClinic(int id) {
        String sql = "DELETE FROM clinics WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logDAO.logError("ClinicDAO", "deleteClinic",
                    "Error deleting clinic ID: " + id, e);
            return false;
        }
    }

    /**
     * Retrieves all clinics from the database along with their schedules.
     *
     * @return A List of Clinic objects.
     */
    public List<Clinic> getAllClinics() {
        List<Clinic> clinics = new ArrayList<>();
        String clinicSql = "SELECT * FROM clinics";
        String scheduleSql = "SELECT * FROM schedules WHERE clinic_id = ?";

        try (PreparedStatement clinicStmt = connection.prepareStatement(clinicSql);
             ResultSet clinicRs = clinicStmt.executeQuery()) {

            while (clinicRs.next()) {
                Clinic clinic = new Clinic();
                clinic.setId(clinicRs.getInt("id"));
                clinic.setName(clinicRs.getString("name"));
                clinic.setLocation(clinicRs.getString("location"));
                clinic.setRating(clinicRs.getDouble("rating"));
                clinic.setPractitionerId(clinicRs.getInt("practitioner_id"));

                // Load schedules for each clinic
                List<Schedule> schedules = new ArrayList<>();
                try (PreparedStatement scheduleStmt = connection.prepareStatement(scheduleSql)) {
                    scheduleStmt.setInt(1, clinic.getId());
                    try (ResultSet scheduleRs = scheduleStmt.executeQuery()) {
                        while (scheduleRs.next()) {
                            Schedule schedule = new Schedule();
                            schedule.setId(scheduleRs.getInt("id"));
                            schedule.setDayOfWeek(scheduleRs.getString("day_of_week"));
                            schedule.setStartTime(scheduleRs.getTime("start_time").toLocalTime());
                            schedule.setEndTime(scheduleRs.getTime("end_time").toLocalTime());
                            schedules.add(schedule);
                        }
                    }
                }
                clinic.setSchedules(schedules);
                clinics.add(clinic);
            }
        } catch (SQLException e) {
            logDAO.logError("ClinicDAO", "getAllClinics", "Error fetching all clinics", e);
        }
        return clinics;
    }

    /**
     * Retrieves all clinics associated with a specific practitioner ID, along with their schedules.
     *
     * @param practitionerId The ID of the practitioner.
     * @return A List of Clinic objects associated with the practitioner.
     */
    public List<Clinic> getAllClinicsOfPractitioner(int practitionerId) {
        List<Clinic> clinics = new ArrayList<>();
        String clinicSql = "SELECT * FROM clinics WHERE practitioner_id = ?";
        String scheduleSql = "SELECT * FROM schedules WHERE clinic_id = ?";

        try (PreparedStatement clinicStmt = connection.prepareStatement(clinicSql)) {
            clinicStmt.setInt(1, practitionerId);
            try (ResultSet clinicRs = clinicStmt.executeQuery()) {
                while (clinicRs.next()) {
                    Clinic clinic = new Clinic();
                    clinic.setId(clinicRs.getInt("id"));
                    clinic.setName(clinicRs.getString("name"));
                    clinic.setLocation(clinicRs.getString("location"));
                    clinic.setRating(clinicRs.getDouble("rating"));
                    clinic.setPractitionerId(clinicRs.getInt("practitioner_id"));

                    // Load schedules for each clinic
                    List<Schedule> schedules = new ArrayList<>();
                    try (PreparedStatement scheduleStmt = connection.prepareStatement(scheduleSql)) {
                        scheduleStmt.setInt(1, clinic.getId());
                        try (ResultSet scheduleRs = scheduleStmt.executeQuery()) {
                            while (scheduleRs.next()) {
                                Schedule schedule = new Schedule();
                                schedule.setId(scheduleRs.getInt("id"));
                                schedule.setDayOfWeek(scheduleRs.getString("day_of_week"));
                                schedule.setStartTime(scheduleRs.getTime("start_time").toLocalTime());
                                schedule.setEndTime(scheduleRs.getTime("end_time").toLocalTime());
                                schedules.add(schedule);
                            }
                        }
                    }
                    clinic.setSchedules(schedules);
                    clinics.add(clinic);
                }
            }
        } catch (SQLException e) {
            logDAO.logError("ClinicDAO", "getAllClinicsOfPractitioner",
                    "Error fetching clinics for practitioner ID: " + practitionerId, e);
        }
        return clinics;
    }

    /**
     * Checks if a clinic with the given ID exists in the database.
     *
     * @param id The ID to check.
     * @return true if a clinic with the ID exists, false otherwise.
     */
    public boolean isExist(int id) {
        String sql = "SELECT 1 FROM clinics WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logDAO.logError("ClinicDAO", "isExist",
                    "Error checking existence of clinic ID: " + id, e);
            return false;
        }
    }
}