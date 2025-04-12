CREATE DATABASE EncuentraTuDoctor;
GO

USE EncuentraTuDoctor;
GO

-- Create users table with gender field
CREATE TABLE users (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    age INT,
    name NVARCHAR(255),
    email NVARCHAR(255) UNIQUE,
    password NVARCHAR(255),
    phone NVARCHAR(50),
    gender NVARCHAR(10),  
    role NVARCHAR(50),
    active BIT DEFAULT 1,
    created_at DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET(),
    updated_at DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET()
);
GO

-- Create patients table
CREATE TABLE patients (
    id BIGINT PRIMARY KEY,
    medical_history NVARCHAR(MAX),
    FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);
GO

-- Create practitioners table
CREATE TABLE practitioners (
    id BIGINT PRIMARY KEY,
    specialization NVARCHAR(255),
    appointment_price DECIMAL(10, 2),
    rating DECIMAL(3, 2) DEFAULT 0.0,
    FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);
GO

-- Create clinics table
CREATE TABLE clinics (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(255),
    location NVARCHAR(MAX),
    rating DECIMAL(3, 2) DEFAULT 0.0,
    practitioner_id BIGINT,
    FOREIGN KEY (practitioner_id) REFERENCES practitioners (id) ON DELETE SET NULL
);
GO

-- Create schedules table
CREATE TABLE schedules (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    clinic_id BIGINT,
    day_of_week NVARCHAR(20),
    start_time TIME,
    end_time TIME,
    FOREIGN KEY (clinic_id) REFERENCES clinics (id) ON DELETE CASCADE
);
GO

-- Create appointments table
CREATE TABLE appointments (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    practitioner_id BIGINT,
    patient_id BIGINT,
    clinic_id BIGINT,
    schedule_id BIGINT,
    status NVARCHAR(50) DEFAULT 'Scheduled',
    notes NVARCHAR(MAX),
    created_at DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET(),
    updated_at DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET(),
    FOREIGN KEY (practitioner_id) REFERENCES practitioners (id),
    FOREIGN KEY (patient_id) REFERENCES patients (id),
    FOREIGN KEY (clinic_id) REFERENCES clinics (id),
    FOREIGN KEY (schedule_id) REFERENCES schedules (id)
);
GO

-- Create clinic_ratings table
CREATE TABLE clinic_ratings (
    rating_id BIGINT PRIMARY KEY IDENTITY(1,1),
    rating_value DECIMAL(3, 2),
    comment NVARCHAR(MAX),
    clinic_id BIGINT,
    patient_id BIGINT,
    FOREIGN KEY (clinic_id) REFERENCES clinics (id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patients (id)
);
GO

-- Create practitioner_ratings table
CREATE TABLE practitioner_ratings (
    rate_id BIGINT PRIMARY KEY IDENTITY(1,1),
    rate_value DECIMAL(3, 2),
    waiting_time INT,
    comment NVARCHAR(MAX),
    patient_id BIGINT,
    practitioner_id BIGINT,
    FOREIGN KEY (patient_id) REFERENCES patients (id),
    FOREIGN KEY (practitioner_id) REFERENCES practitioners (id) ON DELETE CASCADE
);
GO

-- Create log_entries table
CREATE TABLE log_entries (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    [timestamp] DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET(),
    operation NVARCHAR(50),
    entity_type NVARCHAR(50),
    error_message NVARCHAR(MAX),
    stack_trace NVARCHAR(MAX)
);
GO

-- *********************************************************************** --
-- **************************** TRIGGERS ********************************* --
-- *********************************************************************** --

-- Create trigger to update updated_at timestamp on users table
CREATE TRIGGER tr_users_updated_at
ON users
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE users 
    SET updated_at = SYSDATETIMEOFFSET()
    FROM users u
    INNER JOIN inserted i ON u.id = i.id
END;
GO

-- Create trigger to update updated_at timestamp on appointments table
CREATE TRIGGER tr_appointments_updated_at
ON appointments
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE appointments 
    SET updated_at = SYSDATETIMEOFFSET()
    FROM appointments a
    INNER JOIN inserted i ON a.id = i.id
END;
GO

-- Create trigger to update practitioner rating when new rating is added
CREATE TRIGGER tr_update_practitioner_rating
ON practitioner_ratings
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @practitioner_id BIGINT;
    
    -- Handle INSERT and UPDATE
    IF EXISTS (SELECT * FROM inserted)
    BEGIN
        SELECT @practitioner_id = practitioner_id FROM inserted;
        
        UPDATE practitioners
        SET rating = (
            SELECT AVG(rate_value)
            FROM practitioner_ratings
            WHERE practitioner_id = @practitioner_id
        )
        WHERE id = @practitioner_id;
    END
    
    -- Handle DELETE
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted)
    BEGIN
        SELECT @practitioner_id = practitioner_id FROM deleted;
        
        UPDATE practitioners
        SET rating = (
            SELECT AVG(rate_value)
            FROM practitioner_ratings
            WHERE practitioner_id = @practitioner_id
        )
        WHERE id = @practitioner_id;
    END
END;
GO

-- Create trigger to update clinic rating when new rating is added
CREATE TRIGGER tr_update_clinic_rating
ON clinic_ratings
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @clinic_id BIGINT;
    
    -- Handle INSERT and UPDATE
    IF EXISTS (SELECT * FROM inserted)
    BEGIN
        SELECT @clinic_id = clinic_id FROM inserted;
        
        UPDATE clinics
        SET rating = (
            SELECT AVG(rating_value)
            FROM clinic_ratings
            WHERE clinic_id = @clinic_id
        )
        WHERE id = @clinic_id;
    END
    
    -- Handle DELETE
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted)
    BEGIN
        SELECT @clinic_id = clinic_id FROM deleted;
        
        UPDATE clinics
        SET rating = (
            SELECT AVG(rating_value)
            FROM clinic_ratings
            WHERE clinic_id = @clinic_id
        )
        WHERE id = @clinic_id;
    END
END;
GO

-- Create trigger for logging user changes
CREATE TRIGGER tr_log_user_changes
ON users
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @operation NVARCHAR(50);
    DECLARE @entity_type NVARCHAR(50) = 'User';
    
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)
        SET @operation = 'UPDATE';
    ELSE IF EXISTS (SELECT * FROM inserted)
        SET @operation = 'INSERT';
    ELSE
        SET @operation = 'DELETE';
    
    INSERT INTO log_entries (operation, entity_type)
    VALUES (@operation, @entity_type);
END;
GO

-- *********************************************************************** --
-- **************************** Functions ******************************** --
-- *********************************************************************** --

CREATE FUNCTION dbo.CalculateMonthlyRevenue(@year INT, @month INT)
RETURNS DECIMAL(10, 2)
AS
BEGIN
    DECLARE @revenue DECIMAL(10, 2);
    
    SELECT @revenue = SUM(p.appointment_price)
    FROM appointments a
    JOIN practitioners p ON a.practitioner_id = p.id
    WHERE YEAR(a.created_at) = @year
      AND MONTH(a.created_at) = @month
      AND a.status = 'Completed';
    
    RETURN ISNULL(@revenue, 0);
END;
GO

CREATE FUNCTION dbo.GetPractitionerAppointmentStats(@practitionerId BIGINT, @startDate DATE, @endDate DATE)
RETURNS TABLE
AS
RETURN (
    SELECT 
        COUNT(CASE WHEN status = 'Completed' THEN 1 END) AS completed_appointments,
        COUNT(CASE WHEN status = 'Cancelled' THEN 1 END) AS cancelled_appointments,
        COUNT(CASE WHEN status = 'Scheduled' THEN 1 END) AS scheduled_appointments,
        AVG(DATEDIFF(MINUTE, a.created_at, a.updated_at)) AS avg_appointment_duration_minutes
    FROM appointments a
    WHERE practitioner_id = @practitionerId
      AND CAST(a.created_at AS DATE) BETWEEN @startDate AND @endDate
);
GO

CREATE FUNCTION dbo.CalculatePatientRetentionRate(@months INT)
RETURNS DECIMAL(5, 2)
AS
BEGIN
    DECLARE @retentionRate DECIMAL(5, 2);
    DECLARE @totalPatients INT;
    DECLARE @returningPatients INT;
    
    -- Get total patients in the period
    SELECT @totalPatients = COUNT(DISTINCT patient_id)
    FROM appointments
    WHERE created_at >= DATEADD(MONTH, -@months, GETDATE());
    
    -- Get returning patients (those with more than one appointment)
    SELECT @returningPatients = COUNT(*)
    FROM (
        SELECT patient_id
        FROM appointments
        WHERE created_at >= DATEADD(MONTH, -@months, GETDATE())
        GROUP BY patient_id
        HAVING COUNT(*) > 1
    ) AS ReturningPatients;
    
    -- Calculate retention rate
    IF @totalPatients > 0
        SET @retentionRate = (@returningPatients * 100.0) / @totalPatients;
    ELSE
        SET @retentionRate = 0;
    
    RETURN @retentionRate;
END;
GO

CREATE FUNCTION dbo.GetPractitionerRatingDetails(@practitionerId BIGINT)
RETURNS TABLE
AS
RETURN (
    SELECT 
        AVG(rate_value) AS average_rating,
        COUNT(rate_id) AS total_ratings,
        AVG(waiting_time) AS avg_waiting_time_minutes,
        SUM(CASE WHEN rate_value >= 4 THEN 1 ELSE 0 END) AS positive_ratings,
        SUM(CASE WHEN rate_value <= 2 THEN 1 ELSE 0 END) AS negative_ratings
    FROM practitioner_ratings
    WHERE practitioner_id = @practitionerId
);
GO

CREATE FUNCTION dbo.GetPatientVisitHistory(@patientId BIGINT)
RETURNS TABLE
AS
RETURN (
    SELECT 
        a.id AS appointment_id,
        u.name AS practitioner_name,
        p.specialization,
        c.name AS clinic_name,
        a.status,
        a.created_at AS appointment_date,
        a.notes
    FROM appointments a
    JOIN practitioners p ON a.practitioner_id = p.id
    JOIN users u ON p.id = u.id
    LEFT JOIN clinics c ON a.clinic_id = c.id
    WHERE a.patient_id = @patientId
);
GO

CREATE FUNCTION dbo.CalculateNoShowRate(@startDate DATE, @endDate DATE)
RETURNS DECIMAL(5, 2)
AS
BEGIN
    DECLARE @totalAppointments INT;
    DECLARE @noShows INT;
    DECLARE @noShowRate DECIMAL(5, 2);
    
    -- Get total appointments in period
    SELECT @totalAppointments = COUNT(*)
    FROM appointments
    WHERE CAST(created_at AS DATE) BETWEEN @startDate AND @endDate
      AND status IN ('Completed', 'No-Show');
    
    -- Get no-shows
    SELECT @noShows = COUNT(*)
    FROM appointments
    WHERE CAST(created_at AS DATE) BETWEEN @startDate AND @endDate
      AND status = 'No-Show';
    
    -- Calculate no-show rate
    IF @totalAppointments > 0
        SET @noShowRate = (@noShows * 100.0) / @totalAppointments;
    ELSE
        SET @noShowRate = 0;
    
    RETURN @noShowRate;
END;
GO

CREATE FUNCTION dbo.GetMostCommonSpecializations(@topN INT)
RETURNS TABLE
AS
RETURN (
    SELECT TOP (@topN)
        p.specialization,
        COUNT(a.id) AS appointment_count,
        AVG(pr.rate_value) AS avg_rating
    FROM practitioners p
    JOIN appointments a ON p.id = a.practitioner_id
    LEFT JOIN practitioner_ratings pr ON p.id = pr.practitioner_id
    GROUP BY p.specialization
    ORDER BY appointment_count DESC
);
GO

-- *********************************************************************** --
-- **************************** Data Seed ******************************** --
-- *********************************************************************** --

-- Insert data into users table (20 records - 10 patients, 8 practitioners, 2 admins)
INSERT INTO users (age, name, email, password, phone, gender, role, active)
VALUES
-- Patients
(25, 'Emma Johnson', 'emma.j@example.com', 'hashed123', '5550101', 'Female', 'patient', 1),
(32, 'Liam Smith', 'liam.s@example.com', 'hashed123', '5550102', 'Male', 'patient', 1),
(45, 'Olivia Williams', 'olivia.w@example.com', 'hashed123', '5550103', 'Female', 'patient', 1),
(28, 'Noah Brown', 'noah.b@example.com', 'hashed123', '5550104', 'Male', 'patient', 1),
(60, 'Ava Jones', 'ava.j@example.com', 'hashed123', '5550105', 'Female', 'patient', 1),
(35, 'William Garcia', 'william.g@example.com', 'hashed123', '5550106', 'Male', 'patient', 1),
(42, 'Sophia Miller', 'sophia.m@example.com', 'hashed123', '5550107', 'Female', 'patient', 1),
(19, 'James Davis', 'james.d@example.com', 'hashed123', '5550108', 'Male', 'patient', 1),
(50, 'Isabella Rodriguez', 'isabella.r@example.com', 'hashed123', '5550109', 'Female', 'patient', 1),
(27, 'Benjamin Wilson', 'benjamin.w@example.com', 'hashed123', '5550110', 'Male', 'patient', 1),

-- Practitioners
(40, 'Dr. Michael Chen', 'michael.c@example.com', 'hashed123', '5550201', 'Male', 'practitioner', 1),
(38, 'Dr. Sarah Johnson', 'sarah.j@example.com', 'hashed123', '5550202', 'Female', 'practitioner', 1),
(52, 'Dr. Robert Kim', 'robert.k@example.com', 'hashed123', '5550203', 'Male', 'practitioner', 1),
(45, 'Dr. Jennifer Lee', 'jennifer.l@example.com', 'hashed123', '5550204', 'Female', 'practitioner', 1),
(48, 'Dr. David Patel', 'david.p@example.com', 'hashed123', '5550205', 'Male', 'practitioner', 1),
(36, 'Dr. Lisa Wong', 'lisa.w@example.com', 'hashed123', '5550206', 'Female', 'practitioner', 1),
(43, 'Dr. Thomas Brown', 'thomas.b@example.com', 'hashed123', '5550207', 'Male', 'practitioner', 1),
(50, 'Dr. Emily Davis', 'emily.d@example.com', 'hashed123', '5550208', 'Female', 'practitioner', 1),

-- Admins
(35, 'Admin Alex', 'admin.alex@example.com', 'hashed123', '5550301', 'Male', 'admin', 1),
(40, 'Admin Taylor', 'admin.taylor@example.com', 'hashed123', '5550302', 'Male', 'admin', 1);
GO

-- Insert data into patients table (10 records)
INSERT INTO patients (id, medical_history)
VALUES
(1, 'Allergic to penicillin, asthma'),
(2, 'Hypertension, controlled with medication'),
(3, 'Type 2 diabetes'),
(4, 'No known allergies or conditions'),
(5, 'History of migraines'),
(6, 'High cholesterol'),
(7, 'Seasonal allergies'),
(8, 'Broken leg in 2018'),
(9, 'Hypothyroidism'),
(10, 'No significant medical history');
GO

-- Insert data into practitioners table (8 records)
INSERT INTO practitioners (id, specialization, appointment_price, rating)
VALUES
(11, 'Cardiology', 180.00, 4.7),
(12, 'Pediatrics', 150.00, 4.9),
(13, 'Neurology', 200.00, 4.5),
(14, 'Dermatology', 160.00, 4.8),
(15, 'Orthopedics', 190.00, 4.6),
(16, 'General Practice', 120.00, 4.7),
(17, 'Endocrinology', 175.00, 4.4),
(18, 'Psychiatry', 165.00, 4.9);
GO

-- Insert data into clinics table (10 records)
INSERT INTO clinics (name, location, practitioner_id, rating)
VALUES
('City Heart Center', '123 Main St, Cityville', 11, 4.8),
('Children''s Wellness Clinic', '456 Oak Ave, Townsville', 12, 4.9),
('NeuroCare Institute', '789 Pine Rd, Metropolis', 13, 4.7),
('Skin Health Dermatology', '321 Elm Blvd, Springfield', 14, 4.8),
('Bone & Joint Specialists', '654 Maple Dr, Lakeside', 15, 4.6),
('Primary Care Associates', '987 Cedar Ln, Hill Valley', 16, 4.7),
('Endocrine Health Center', '135 Birch St, Riverside', 17, 4.5),
('Mind & Balance Psychiatry', '246 Walnut Ave, Mountain View', 18, 4.9),
('Westside Medical Center', '579 Spruce Rd, Westwood', 11, 4.7),
('Downtown Health Clinic', '864 Palm Blvd, Central City', 12, 4.8);
GO

-- Insert data into schedules table (20 records - 2 per practitioner)
INSERT INTO schedules (clinic_id, day_of_week, start_time, end_time)
VALUES
-- Dr. Michael Chen (Cardiology)
(1, 'MONDAY', '08:00', '16:00'),
(1, 'WEDNESDAY', '09:00', '17:00'),

-- Dr. Sarah Johnson (Pediatrics)
(2, 'TUESDAY', '08:30', '16:30'),
(2, 'THURSDAY', '09:00', '17:00'),

-- Dr. Robert Kim (Neurology)
(3, 'MONDAY', '09:00', '17:00'),
(3, 'FRIDAY', '08:00', '16:00'),

-- Dr. Jennifer Lee (Dermatology)
(4, 'TUESDAY', '08:00', '16:00'),
(4, 'THURSDAY', '09:00', '17:00'),

-- Dr. David Patel (Orthopedics)
(5, 'WEDNESDAY', '08:30', '16:30'),
(5, 'FRIDAY', '09:00', '17:00'),

-- Dr. Lisa Wong (General Practice)
(6, 'MONDAY', '08:00', '16:00'),
(6, 'THURSDAY', '08:00', '16:00'),

-- Dr. Thomas Brown (Endocrinology)
(7, 'TUESDAY', '09:00', '17:00'),
(7, 'FRIDAY', '09:00', '17:00'),

-- Dr. Emily Davis (Psychiatry)
(8, 'MONDAY', '10:00', '18:00'),
(8, 'WEDNESDAY', '10:00', '18:00'),
(8, 'FRIDAY', '09:00', '15:00'),
(8, 'SATURDAY', '09:00', '13:00');
GO

-- Insert data into appointments table (30 records)
INSERT INTO appointments (practitioner_id, patient_id, clinic_id, schedule_id, status, notes, created_at)
VALUES
-- Completed appointments
(11, 1, 1, 1, 'Completed', 'Annual heart checkup', '2023-01-10 09:00'),
(12, 2, 2, 3, 'Completed', 'Child vaccination', '2023-01-11 10:30'),
(13, 3, 3, 5, 'Completed', 'Migraine consultation', '2023-01-12 11:00'),
(14, 4, 4, 7, 'Completed', 'Skin allergy check', '2023-01-13 14:00'),
(15, 5, 5, 9, 'Completed', 'Knee pain evaluation', '2023-01-16 15:30'),
(16, 6, 6, 11, 'Completed', 'Annual physical', '2023-01-17 10:00'),
(17, 7, 7, 13, 'Completed', 'Thyroid follow-up', '2023-01-18 11:30'),
(18, 8, 8, 15, 'Completed', 'Depression management', '2023-01-19 14:00'),
(11, 9, 9, 2, 'Completed', 'Cardiac stress test', '2023-01-20 09:30'),
(12, 10, 10, 4, 'Completed', 'Newborn checkup', '2023-01-23 10:00'),

-- Scheduled appointments
(13, 1, 3, 6, 'Scheduled', 'Follow-up for MRI results', '2023-02-01 10:00'),
(14, 2, 4, 8, 'Scheduled', 'Acne treatment', '2023-02-02 11:00'),
(15, 3, 5, 10, 'Scheduled', 'Physical therapy', '2023-02-03 14:00'),
(16, 4, 6, 12, 'Scheduled', 'Blood work review', '2023-02-06 15:30'),
(17, 5, 7, 14, 'Scheduled', 'Diabetes management', '2023-02-07 10:00'),
(18, 6, 8, 16, 'Scheduled', 'Anxiety consultation', '2023-02-08 11:30'),
(11, 7, 1, 1, 'Scheduled', 'EKG test', '2023-02-09 14:00'),
(12, 8, 2, 3, 'Scheduled', 'Child wellness exam', '2023-02-10 09:30'),
(13, 9, 3, 5, 'Scheduled', 'Neurological exam', '2023-02-13 10:00'),
(14, 10, 4, 7, 'Scheduled', 'Annual skin check', '2023-02-14 11:00'),

-- Cancelled appointments
(15, 1, 5, 9, 'Cancelled', 'Patient rescheduled', '2023-01-25 15:30'),
(16, 2, 6, 11, 'Cancelled', 'No longer needed', '2023-01-26 10:00'),
(17, 3, 7, 13, 'Cancelled', 'Doctor unavailable', '2023-01-27 11:30'),
(18, 4, 8, 15, 'Cancelled', 'Patient request', '2023-01-30 14:00'),
(11, 5, 9, 2, 'Cancelled', 'Insurance issues', '2023-01-31 09:30'),
(12, 6, 10, 4, 'Cancelled', 'Found other provider', '2023-02-01 10:00'),
(13, 7, 3, 6, 'Cancelled', 'Emergency closure', '2023-02-02 10:00'),
(14, 8, 4, 8, 'Cancelled', 'Duplicate appointment', '2023-02-03 11:00'),
(15, 9, 5, 10, 'Cancelled', 'Weather closure', '2023-02-06 14:00'),
(16, 10, 6, 12, 'Cancelled', 'Patient moved', '2023-02-07 15:30');
GO

-- Insert data into clinic_ratings table (20 records)
INSERT INTO clinic_ratings (rating_value, comment, clinic_id, patient_id)
VALUES
(5, 'Excellent service and clean facilities', 1, 1),
(4, 'Friendly staff but long wait times', 2, 2),
(5, 'Very professional and efficient', 3, 3),
(4, 'Good experience overall', 4, 4),
(3, 'Average experience, could improve', 5, 5),
(5, 'Best clinic in town!', 6, 6),
(4, 'Clean and modern facility', 7, 7),
(5, 'Highly recommend this clinic', 8, 8),
(4, 'Good doctors but parking is difficult', 9, 9),
(5, 'Exceptional care and attention', 10, 10),
(4, 'Would come back again', 1, 2),
(5, 'Great pediatric care', 2, 3),
(4, 'Knowledgeable specialists', 3, 4),
(3, 'Waiting area could be more comfortable', 4, 5),
(5, 'Very satisfied with my treatment', 5, 6),
(4, 'Professional and caring staff', 6, 7),
(5, 'Made me feel very comfortable', 7, 8),
(4, 'Efficient check-in process', 8, 9),
(5, 'Excellent psychiatric care', 8, 1),
(4, 'Good follow-up after appointment', 10, 2);
GO

-- Insert data into practitioner_ratings table (20 records)
INSERT INTO practitioner_ratings (rate_value, waiting_time, comment, patient_id, practitioner_id)
VALUES
(5, 5, 'Dr. Chen is very thorough', 1, 11),
(4, 15, 'Good with children', 2, 12),
(5, 10, 'Excellent neurologist', 3, 13),
(4, 20, 'Helped clear my skin', 4, 14),
(3, 30, 'Long wait but good care', 5, 15),
(5, 5, 'Very knowledgeable GP', 6, 16),
(4, 10, 'Great with diabetes management', 7, 17),
(5, 0, 'Best psychiatrist Ive seen', 8, 18),
(4, 15, 'Explains things clearly', 9, 11),
(5, 5, 'Very patient with questions', 10, 12),
(4, 10, 'Professional and caring', 1, 13),
(5, 5, 'Fixed my back pain', 2, 15),
(4, 15, 'Good listener', 3, 16),
(5, 0, 'On time and efficient', 4, 17),
(4, 10, 'Very thorough examination', 5, 18),
(5, 5, 'Excellent bedside manner', 6, 11),
(4, 20, 'Knowledgeable and friendly', 7, 12),
(3, 25, 'Long wait but worth it', 8, 13),
(5, 5, 'Solved my chronic issue', 9, 14),
(4, 10, 'Good follow-up care', 10, 15);
GO