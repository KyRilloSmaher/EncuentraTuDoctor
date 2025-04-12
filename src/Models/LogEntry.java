package Models;

import java.time.LocalDateTime;

public class LogEntry {

    private Long id;
    private LocalDateTime timestamp;
    private String operation;
    private String entityType;
    private String errorMessage;
    private String stackTrace;

    // Constructors
    public LogEntry() {
        this.timestamp = LocalDateTime.now();
    }

    public LogEntry(String operation, String entityType, String errorMessage, String stackTrace) {
        this();
        this.operation = operation;
        this.entityType = entityType;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
} 