/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import Utiles.DatabaseConnection;
import Models.Schedule;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author KyRilloS
 */

 // Retrieve startTime and endTime directly as LocalTime
               


public class ScheduleDAO {
  private Connection connection = DatabaseConnection.getConnection();
   private final LogDAO logDAO = new LogDAO(connection);

    // Create Schedule
    public boolean addSchedule(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO Schedules (clinic_id, day_of_week, start_Time , end_Time) VALUES (?, ?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, schedule.getClinicId());
            statement.setString(2, schedule.getDayOfWeek().toString());
            statement.setObject(3, schedule.getStartTime());
            statement.setObject(4, schedule.getEndTime());
               int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logDAO.logError("ScheduleDAO", "addSchedule", "Failed to create Schedule", null);
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    schedule.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            throw new SQLException("Error adding schedule: " + e.getMessage());
        }
    }

    // Read Schedule by ID
    public Schedule getScheduleById(int id) throws SQLException {
        String sql = "SELECT * FROM Schedules WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    LocalTime startTime = resultSet.getObject("start_Time", LocalTime.class);
                    LocalTime endTime = resultSet.getObject("end_Time", LocalTime.class);
                    return new Schedule(
                    resultSet.getInt("id"),
                     resultSet.getInt("clinic_id"),
                    DayOfWeek.valueOf(resultSet.getString("day_of_week")),
                    startTime,
                    endTime
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching schedule: " + e.getMessage());
        }
        return null;
    }

    // Update Schedule
    public boolean updateSchedule(Schedule schedule) throws SQLException {
        String sql = "UPDATE Schedules SET clinic_id = ?, day_of_week = ?, start_Time = ? , end_Time =? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, schedule.getClinicId());
            statement.setString(2, schedule.getDayOfWeek().toString());
            statement.setObject(3, schedule.getStartTime());
            statement.setObject(4, schedule.getEndTime());
            statement.setInt(5, schedule.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Error updating schedule: " + e.getMessage());
        }
    }

    // Delete Schedule
    public boolean deleteSchedule(int id) throws SQLException {
        String sql = "DELETE FROM Schedules WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("Error deleting schedule: " + e.getMessage());
        }
    }

    // List All Schedules
    public List<Schedule> getAllSchedules() throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM Schedules";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    LocalTime startTime = resultSet.getObject("start_Time", LocalTime.class);
                    LocalTime endTime = resultSet.getObject("end_Time", LocalTime.class);

                schedules.add(new Schedule(
                    resultSet.getInt("id"),
                    resultSet.getInt("clinic_id"),
                  DayOfWeek.valueOf(resultSet.getString("day_of_week")),
                    startTime,
                    endTime
                ));
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching schedules: " + e.getMessage());
        }
        return schedules;
    }

    // List Schedules by Clinic ID
    public List<Schedule> getSchedulesByClinicId(int clinicId) throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM Schedules WHERE clinic_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clinicId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LocalTime startTime = resultSet.getObject("start_Time", LocalTime.class);
                    LocalTime endTime = resultSet.getObject("end_Time", LocalTime.class);

                     schedules.add(new Schedule(
                    resultSet.getInt("id"),
                    resultSet.getInt("clinic_id"),
                  DayOfWeek.valueOf(resultSet.getString("day_of_week")),
                    startTime,
                    endTime
                ));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error fetching schedules by clinic ID: " + e.getMessage());
        }
        return schedules;
    }

       // List Schedules avaliable for practitonear
//       public List<ScheduleDetailsDTO> getSchedulesByPractitioner(int practitionerId) throws SQLException {
//        List<ScheduleDetailsDTO> schedules = new ArrayList<>();
//        String sql = "SELECT sc.ScheduleId, sc.DayOfWeek, sc.startTime, sc.endTime, cl.Name, cl.Location FROM Schedules sc JOIN Clinics cl ON sc.clinicId = cl.ClinicId WHERE cl.practitionerId = ?";
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, practitionerId);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    schedules.add(new ScheduleDetailsDTO(
//                    resultSet.getInt("ScheduleId"),
//                    resultSet.getString("DayOfWeek"),
//                    resultSet.getTime("startTime"),
//                    resultSet.getTime("endTime"),
//                    resultSet.getString("clinincName"),
//                    resultSet.getString("clinicAddress")
//                    ));
//                }
//            }
//        } catch (SQLException e) {
//            throw new SQLException("Error fetching schedules by practitionear ID: " + e.getMessage());
//        }
//        return schedules;
//    }
}
