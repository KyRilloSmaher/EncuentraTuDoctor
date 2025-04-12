/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author KyRilloS
 */
package DAOs;
import Utiles.DatabaseConnection;
import Models.Appointment;
import Models.AppointmentStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private final Connection connection = DatabaseConnection.getConnection();
    private final LogDAO logDAO = new LogDAO(connection);

    // Create Appointment with full parameters
    public boolean addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (practitioner_id, patient_id, clinic_id, schedule_id, status, notes) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, appointment.getPractitionerId());
            statement.setInt(2, appointment.getPatientId());
            statement.setInt(3, appointment.getClinicId());
            statement.setInt(4, appointment.getScheduleId());
            statement.setString(5, appointment.getStatus().toString());
            statement.setString(6, appointment.getNotes());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                logDAO.logError("AppointmentDAO", "addAppointment", "Failed to create appointment", null);
                return false;
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    appointment.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            logDAO.logError("AppointmentDAO", "addAppointment", "Error adding appointment", e);
            return false;
        }
    }

    // Read Appointment by ID
    public Appointment getAppointmentById(int id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAppointmentFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logDAO.logError("AppointmentDAO", "getAppointmentById", "Error fetching appointment ID: " + id, e);
        }
        return null;
    }

    // Update Appointment
    public boolean updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET practitioner_id = ?, patient_id = ?, clinic_id = ?, " +
                     "schedule_id = ?, status = ?, notes = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, appointment.getPractitionerId());
            statement.setInt(2, appointment.getPatientId());
            statement.setInt(3, appointment.getClinicId());
            statement.setInt(4, appointment.getScheduleId());
            statement.setString(5, appointment.getStatus().toString());
            statement.setString(6, appointment.getNotes());
            statement.setInt(7, appointment.getId());
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logDAO.logError("AppointmentDAO", "updateAppointment", 
                           "Error updating appointment ID: " + appointment.getId(), e);
            return false;
        }
    }

    // Delete Appointment
    public boolean deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logDAO.logError("AppointmentDAO", "deleteAppointment", 
                          "Error deleting appointment ID: " + id, e);
            return false;
        }
    }

    // List All Appointments
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                appointments.add(extractAppointmentFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logDAO.logError("AppointmentDAO", "getAllAppointments", "Error fetching all appointments", e);
        }
        return appointments;
    }

    // List Appointments by Patient ID
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    appointments.add(extractAppointmentFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logDAO.logError("AppointmentDAO", "getAppointmentsByPatientId", 
                          "Error fetching appointments for patient ID: " + patientId, e);
        }
        return appointments;
    }

    // List Appointments by Practitioner ID
    public List<Appointment> getAppointmentsByPractitionerId(int practitionerId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE practitioner_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, practitionerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    appointments.add(extractAppointmentFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            logDAO.logError("AppointmentDAO", "getAppointmentsByPractitionerId", 
                          "Error fetching appointments for practitioner ID: " + practitionerId, e);
        }
        return appointments;
    }

    // Helper method to extract Appointment object from ResultSet
    private Appointment extractAppointmentFromResultSet(ResultSet resultSet) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(resultSet.getInt("id"));
        appointment.setPractitionerId(resultSet.getInt("practitioner_id"));
        appointment.setPatientId(resultSet.getInt("patient_id"));
        appointment.setClinicId(resultSet.getInt("clinic_id"));
        appointment.setScheduleId(resultSet.getInt("schedule_id"));
        appointment.setStatus(AppointmentStatus.valueOf(resultSet.getString("status")));
        appointment.setNotes(resultSet.getString("notes"));
        
        // Handle timestamps
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        if (createdAt != null) {
            appointment.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            appointment.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return appointment;
    }
}
