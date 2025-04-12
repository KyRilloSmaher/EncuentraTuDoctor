package Services;

import DAOs.*;
import DTOs.*;
import Models.*;
import Utiles.Helpers;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ClinicService {
    private final ClinicDAO clinicDAO = new ClinicDAO();
    private final PractitionerService pracService = new PractitionerService();

    public boolean registerClinic(CreateClinicDTO dto) {
        try {
            validateClinicInput(dto);
            Clinic clinic = Helpers.Mapper.mapToClinic(dto);
            return clinicDAO.addClinic(clinic);
        } catch (IllegalArgumentException e) {
            System.err.println("Error registering clinic: " + e.getMessage());
            return false;
        }
    }

    public ClinicProfileDTO getClinicById(int id) {
        try {
            if (id < 0) {
                throw new IllegalArgumentException("Invalid clinic ID.");
            }
            Clinic clinic = clinicDAO.getClinicById(id);
            return (clinic != null) ? Helpers.Mapper.mapToClinicProfileDTO(clinic) : null;
        } catch (IllegalArgumentException | SQLException e) {
            System.err.println("Error fetching clinic: " + e.getMessage());
            return null;
        }
    }

    public boolean updateClinic(UpdateClinicDTO dto) {
        try {
            validateClinicUpdate(dto.getClinicId(),dto.getName(),dto.getLocation() );
            Clinic clinic = new Clinic(dto.getName(),dto.getLocation());
            return clinicDAO.updateClinic(clinic);
        } catch (IllegalArgumentException e) {
            System.err.println("Error updating clinic: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteClinic(int id) {
        try {
            if (id < 0) {
                throw new IllegalArgumentException("Invalid clinic ID.");
            }
            return clinicDAO.deleteClinic(id);
        } catch (IllegalArgumentException e) {
            System.err.println("Error deleting clinic: " + e.getMessage());
            return false;
        }
    }
   
    public List<ClinicProfileDTO> getAllClinics() {
        try {
            List<Clinic> clinics = clinicDAO.getAllClinics();
            List<ClinicProfileDTO> clinicDTOs = new ArrayList<>();
            for (Clinic clinic : clinics) {
                clinicDTOs.add(Helpers.Mapper.mapToClinicProfileDTO(clinic));
            }
            return clinicDTOs;
        } catch (SQLException e) {
            System.err.println("Error fetching clinics: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private void validateClinicInput(CreateClinicDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Clinic name cannot be empty.");
        }
        if (dto.getLocation() == null || dto.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty.");
        }
        if (dto.getPractitionerId() < 0 && pracService.isPractitionerExist(dto.getPractitionerId())) {
            throw new IllegalArgumentException("Invalid practitioner ID.");
        }
    }

    private void validateClinicUpdate(int id, String name, String location) {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid clinic ID.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Clinic name cannot be empty.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty.");
        }
    }
}
