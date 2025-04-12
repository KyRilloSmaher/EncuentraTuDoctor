package Utiles;

import DAOs.*;
import DTOs.*;
import Models.*;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Helpers {
    
  public static class Mapper {

    public final static PatientDAO patientDAO = new PatientDAO();
    public final static PractitionerDAO practitionerrDAO = new PractitionerDAO();
    public final static ClinicDAO clinicDAO = new ClinicDAO();
    public final static ScheduleDAO scheduleDAO = new ScheduleDAO();
//****************** Patient***************
    public static PatientProfileDTO mapToPatientProfileDTO(Patient patient) {
      PatientProfileDTO patientProfileDTO = new PatientProfileDTO();
      // Mapping from Patient to PatientProfileDTO
      patientProfileDTO.setId(patient.getId());
      patientProfileDTO.setName(patient.getName());
      patientProfileDTO.setAge(patient.getAge());
      patientProfileDTO.setGender(patient.getGender());
      patientProfileDTO.setEmail(patient.getEmail());
      patientProfileDTO.setPhoneNumber(patient.getPhone());
      patientProfileDTO.setMedicalHistory(patient.getMedicalHistory());
      return patientProfileDTO;
    }

    public static Patient mapToPatient(PatientRegistrationDTO dto) {
      Patient patient = new Patient();
      patient.setName(dto.getName());
      patient.setAge(dto.getAge());
      patient.setGender(dto.getGender());
      patient.setEmail(dto.getEmail());
      patient.setPassword(dto.getPassword());
      patient.setPhone(dto.getPhoneNumber());
      patient.setMedicalHistory(dto.getMedicalHistory());
      return patient;
    }
//****************** Clinic ***************
    public static ClinicProfileDTO mapToClinicProfileDTO(Clinic clinic) throws SQLException {
      List<ScheduleDetailsDTO> scheduleDetailsList = new ArrayList<>();
      for (Schedule schedule : clinic.getSchedules())
      {
        ScheduleDetailsDTO scheduleDetailsDTO = mapToScheduleDetailsDTO(schedule);
        scheduleDetailsList.add(scheduleDetailsDTO);
      }

      ClinicProfileDTO clinicProfileDTO = new ClinicProfileDTO(
      clinic.getId(),
      clinic.getName(),
      clinic.getLocation(),
      clinic.getRating(),
      practitionerrDAO.getPractitionerById(clinic.getPractitionerId()).getName(),
      scheduleDetailsList 
      );
      return clinicProfileDTO;
    }
    
    public static Clinic mapToClinic(CreateClinicDTO dto) {
      Clinic clinic = new Clinic(
          dto.getName(), dto.getLocation(), dto.getPractitionerId());
      return clinic;
    }

//****************** Practitioner ***************
  
    public static PractitionerProfileDTO mapToPractitionerProfileDTO(Practitioner practitioner, List<Clinic> clinics) throws SQLException {
      PractitionerProfileDTO practitionerProfileDTO = new PractitionerProfileDTO();
      // Mapping from Practitioner to PractitionerProfileDTO
      practitionerProfileDTO.setId(practitioner.getId());
      practitionerProfileDTO.setName(practitioner.getName());
      practitionerProfileDTO.setAge(practitioner.getAge());
      practitionerProfileDTO.setGender(practitioner.getGender());
      practitionerProfileDTO.setEmail(practitioner.getEmail());
      practitionerProfileDTO.setPhoneNumber(practitioner.getPhone());
      practitionerProfileDTO.setSpecialization(practitioner.getSpecialization());
      practitionerProfileDTO.setAppointmentPrice(practitioner.getAppointmentPrice());
      practitionerProfileDTO.setRating(practitioner.getRating());
      // Mapping clinics list to clinics DTO list
      List<ClinicProfileDTO> ClinicList = new ArrayList<ClinicProfileDTO>();
      for (Clinic clinic : clinics) {
        ClinicList.add(mapToClinicProfileDTO(clinic));
      }
      practitionerProfileDTO.setClinics(ClinicList);
      return practitionerProfileDTO;
    }

//****************** Schedule ***************
    public static Schedule mapToSchedule(CreateScheduleDTO schedule){
     Schedule dto = new Schedule();
     dto.setClinicId(schedule.getClinicId());
     dto.setDayOfWeek(schedule.getDayOfWeek().toString());
     dto.setStartTime(schedule.getStartTime());
     dto.setEndTime(schedule.getEndTime());
     return dto;
  }
    
    public static ScheduleDetailsDTO mapToScheduleDetailsDTO(Schedule schedule){
     ScheduleDetailsDTO dto = new ScheduleDetailsDTO();
     dto.setClinicId(schedule.getClinicId());
     dto.setDayOfWeek(schedule.getDayOfWeek());
     dto.setStartTime(schedule.getStartTime());
     dto.setEndTime(schedule.getEndTime());
     return dto;
  }

//****************** Appointment ***************

    public static Appointment mapToAppointment(AppointmentRequestDTO dto) {
      Appointment appointment = new Appointment();
      // Mapping from AppointmentRequestDTO to Appointment
      appointment.setPatientId(dto.getPatientId());
      appointment.setPractitionerId(dto.getPractitionerId());
      appointment.setClinicId(dto.getClinicId());
      appointment.setNotes(dto.getNotes());
      appointment.setScheduleId(dto.getScheduleId());
      return appointment;
    }

    public static AppointmentDetailsDTO mapToAppointmentDetails(Appointment appointment) throws SQLException {
      AppointmentDetailsDTO appointmentDetailsDTO = new AppointmentDetailsDTO();
      // Mapping from Appointment to AppointmentDetailsDTO
      appointmentDetailsDTO.setAppointmentId(appointment.getId());
      appointmentDetailsDTO.setPatientName(patientDAO.getPatientById(appointment.getPatientId()).getName());
      appointmentDetailsDTO.setPractitionerName(practitionerrDAO.getPractitionerById(appointment.getPractitionerId()).getName());
      appointmentDetailsDTO.setClinicName(clinicDAO.getClinicById(appointment.getClinicId()).getName());
      appointmentDetailsDTO.setClinicAddress(clinicDAO.getClinicById(appointment.getClinicId()).getLocation());
      appointmentDetailsDTO.setDayOfWeek(scheduleDAO.getScheduleById(appointment.getScheduleId()).getDayOfWeek());
      appointmentDetailsDTO.setStartTime(scheduleDAO.getScheduleById(appointment.getScheduleId()).getStartTime());
      appointmentDetailsDTO.setEndTime(scheduleDAO.getScheduleById(appointment.getScheduleId()).getEndTime());
      return appointmentDetailsDTO;
    }


//*********** Rates ******************
    public static ClinicRating mapToRateClinicDisplayDTO(RateClinicDTO rating) throws SQLException {
      ClinicRating rate = new ClinicRating();
      rate.setClinicId(rating.getClinicId());
      rate.setPatientId(rating.getPatientId());
      rate.setRatingValue(rating.getRating());
      rate.setComment(rating.getComment());
      return rate;
    }

    public static PractitionerRatings mapToPractitionerRatings(RatePractitionerDTO rateDTO) {
      PractitionerRatings rate = new PractitionerRatings();
      rate.setPatientId(rateDTO.getPatientId());
      rate.setPractitionerId(rateDTO.getPractitionerId());
      rate.setRateValue(rateDTO.getRating());
      rate.setWaitingTime(rateDTO.getWaitingTime());
      rate.setComment(rateDTO.getComment());
      return rate;
    }

    public static  DisplayRateDTO mapToRateDisplay(PractitionerRatings rate) throws SQLException {
      DisplayRateDTO dto;
      var rateId =rate.getRateId();
      var name = practitionerrDAO.getPractitionerById(rate.getPractitionerId()).getName();
      var val = rate.getRateValue();
      var WaitingTime =rate.getWaitingTime();
      var comment = rate.getComment();
      dto = new DisplayRateDTO(rateId,name,val , WaitingTime,comment);
      return dto;
    }
    
    public static DisplayRateDTO mapToRateDisplayTO(ClinicRating rate) throws SQLException {
      DisplayRateDTO dto;
      var rateId =rate.getRatingId();
      var name = clinicDAO.getClinicById(rate.getClinicId()).getName();
      var val = rate.getRatingValue();
      var comment =rate.getComment();
      dto = new DisplayRateDTO(rateId,name,val ,0,comment);
      return dto;
   
    }
  }

  
  
  
  public class InputValidator {
     
    public static boolean RegisterationValidation(
        JTextField fullNameField, JTextField ageField, JTextField emailField,
        JTextField phoneField, JRadioButton maleButton, JRadioButton femaleButton,
        JTextField medicalHistoryField,
        JPasswordField passwordField, JPasswordField confirmPasswordField) {

      // Name field
      String fullname = fullNameField.getText().trim();
      if (!validateName(fullname)) {
        return false;
      }
      // Age Validation
      String age = ageField.getText().trim();
      if (!validateAge(age)) {
        return false;
      }
      // Email Validation
      String email = emailField.getText().trim();
      if (!isValidEmail(email)) {
        showMessage("Invalid email format!");
        return false;
      }

      // Phone Number Validation
      String phoneNumber = phoneField.getText().trim();
      if (!validatePhoneNumber(phoneNumber)) {
        return false;
      }

      // Gender Validation
      if (!maleButton.isSelected() && !femaleButton.isSelected()) {
        showMessage("Please select a gender!");
        return false;
      }

      // Password Validation
      String password = new String(passwordField.getPassword()).trim();
      String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
      if (!validatePassword(password, confirmPassword)) {
        return false;
      }
      // Medical History validation
      String medicalHistory = medicalHistoryField.getText();
       if(!validateMedicalHistory(medicalHistory))
       { return false;}

      // If all validations pass
      return true;
    }
    public static boolean SignInValidation(JTextField emailField,JPasswordField passwordField){
      String email = emailField.getText().trim();
      String password = new String(passwordField.getPassword()).trim();
      if (!validatePassword(password, password)){
        showMessage("password Format Not valid!");
        return false;
      }
      if(!isValidEmail(email) ) {
        showMessage("Email Format Not valid!");
        return false;
      }
      return true;
    }
    // Name Validation
    public static boolean validateName(String name) {
      if (name.isEmpty()) {
        showMessage("Name cannot be empty!");
        return false;
      }
      if (!name.matches("[a-zA-Z\\s]+")) {
        showMessage("Name can only contain letters and spaces!");
        return false;
      }
      return true;
    }

    // Age Validation
    public static boolean validateAge(String age) {
      try {
        int ageValue = Integer.parseInt(age);
        if (ageValue <= 0) {
          showMessage("Age must be a positive number!");
          return false;
        }
      } catch (NumberFormatException e) {
        showMessage("Invalid Age! Please enter a valid number.");
        return false;
      }
      return true;
    }

    // Phone Number Validation
    public static boolean validatePhoneNumber(String phoneNumber) {
      if (phoneNumber.isEmpty()) {
        showMessage("Phone number cannot be empty!");
        return false;
      }
      if (!phoneNumber.matches("\\d{10,15}")) {
        showMessage("Invalid phone number! It should contain only digits (10-15 characters).");
        return false;
      }
      return true;
    }

    // Address Validation
    public static boolean validateAddress(String address) {
      if (address.isEmpty()) {
        showMessage("Address cannot be empty!");
        return false;
      }
      return true;
    }

    // Email validation using regex
    public static boolean isValidEmail(String email) {
      String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
      Pattern pattern = Pattern.compile(emailRegex);
      Matcher matcher = pattern.matcher(email);
      return matcher.matches();
    }

    // Password validation
    public static boolean validatePassword(String password, String confirmPassword) {
      String passwordRegex = "^[A-Za-z0-9@#$%^&+=]{6,}$";
      Pattern pattern = Pattern.compile(passwordRegex);
      Matcher matcher = pattern.matcher(password);
      if (password.isEmpty() || confirmPassword.isEmpty()) {
        showMessage("Password fields cannot be empty!");
        return false;
      }
      if (password.length() < 6) {
        showMessage("Password must be at least 6 characters long!");
        return false;
      }
      if (!matcher.matches()) {
        showMessage(
            "Password must be at least 6 characters long and can contain letters, numbers, and special characters!");
        return false;
      }
      if (!password.equals(confirmPassword)) {
        showMessage("Passwords do not match!");
        return false;
      }
      return true;
    }

public static boolean validateMedicalHistory(String medicalHistory) {
    if(medicalHistory.isEmpty())
         return true;
    // Check if the input contains only words separated by commas (allow spaces)
    if (!medicalHistory.matches("^[a-zA-Z0-9\\s]+(,[a-zA-Z0-9\\s]+)*$")) {
        JOptionPane.showMessageDialog(null, "Medical History must be comma-separated words!", "Validation Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    return true;
}

    // Show error message
    private static void showMessage(String message) {
      JOptionPane.showMessageDialog(null, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
