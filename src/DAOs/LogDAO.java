/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

/**
 *
 * @author KyRilloS
 */
import java.sql.*;

public class LogDAO {
    private  Connection connection;
   
    public LogDAO(Connection connection) {
        this.connection = connection;
    }

    public void logError(String className, String methodName, String errorMessage, Exception exception) {
        String sql = "INSERT INTO log_entries (operation, entity_type, error_message, stack_trace) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "ERROR");
            statement.setString(2, className + "." + methodName);
            statement.setString(3, errorMessage);
            
            if (exception != null) {
                statement.setString(4, getStackTraceAsString(exception));
            } else {
                statement.setNull(4, Types.VARCHAR);
            }
            
            statement.executeUpdate();
        } catch (SQLException e) {
            // Fallback to console if logging fails
            System.err.println("Failed to log error: " + e.getMessage());
            if (exception != null) {
                exception.printStackTrace();
            }
        }
    }

    private String getStackTraceAsString(Exception exception) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : exception.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}



