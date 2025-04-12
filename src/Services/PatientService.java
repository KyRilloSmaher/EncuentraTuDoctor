package Services;

import DAOs.*;
import DTOs.*;
import Models.*;
import Utiles.Helpers;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class PatientService {
    private final PatientDAO patientDAO = new PatientDAO();
    private final UserDAO usertDAO = new UserDAO();
    public PatientService() {}

    public boolean registerPatient(PatientRegistrationDTO dto) {
        try {
            validatePatientInput(dto);
            Patient patient = Helpers.Mapper.mapToPatient(dto) ;
            return patientDAO.addPatient(patient);
        } catch (IllegalArgumentException e) {
            System.err.println("Error registering patient: " + e.getMessage());
            return false;
        }
    }
    public PatientProfileDTO SignIn(String email, String Password) throws SQLException{
        if(email== null || email.isEmpty() ||Password== null || Password.isEmpty() ){
            return null;
        }
        User user = usertDAO.IsUserByEmailandPasswordExist(email, Password);
        if(user != null){
            return this.getPatientById(user.getId());
        }
        return null;
    }

    public PatientProfileDTO getPatientById(int id) {
        if (id < 0) {
            System.err.println("Invalid patient ID.");
            return null;
        }
        Patient patient = patientDAO.getPatientById(id);
        if (patient != null) {
            return Helpers.Mapper.mapToPatientProfileDTO(patient);
        }
        System.err.println("No patient found with this ID.");
        return null;
    }

    public boolean updatePatient(UpdatePatientDTO dto) {
        try {
            validatePatientUpdate(dto.getId(), dto.getName(), dto.getAge(), dto.getGender(), dto.getEmail(), dto.getPhone(), dto.getMedicalHistory());
            Patient patient = new Patient(dto.getId(), dto.getName(), dto.getAge(), dto.getGender(), dto.getEmail(), dto.getPhone(), dto.getMedicalHistory());
            return patientDAO.updatePatient(patient);
        } catch (IllegalArgumentException e) {
            System.err.println("Error updating patient: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePatient(int id) {
        if (id < 0) {
            System.err.println("Invalid patient ID.");
            return false;
        }
        return patientDAO.deletePatient(id);
    }

    public List<PatientProfileDTO> getAllPatients() {
        List<Patient> patients = patientDAO.getAllPatients();
        List<PatientProfileDTO> profiles = new ArrayList<>();
        if (patients != null) {
            for (Patient patient : patients) {
                profiles.add(Helpers.Mapper.mapToPatientProfileDTO(patient));
            }
        }
        return profiles;
    }

    private void validatePatientInput(PatientRegistrationDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (dto.getAge() <= 0) {
            throw new IllegalArgumentException("Age must be greater than 0.");
        }
        if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (dto.getPassword()== null || dto.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }
        if (dto.getPhoneNumber() == null || dto.getPhoneNumber().length() < 8) {
            throw new IllegalArgumentException("Invalid phone number.");
        }
    }

    private void validatePatientUpdate(int id, String name, int age, String gender,String email, String phoneNumber, String medicalHistory) {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid ID.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if(gender == null || gender.trim().isEmpty() ){
           throw new IllegalArgumentException("Gender Feilds  Not Valid.");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be greater than 0.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (phoneNumber == null || phoneNumber.length() < 8) {
            throw new IllegalArgumentException("Invalid phone number.");
        }
        if (medicalHistory == null || medicalHistory.trim().isEmpty()) {
            throw new IllegalArgumentException("Medical history cannot be empty.");
        }
    }
}
