package Services;

import DAOs.*;
import DTOs.*;
import Models.*;
import Utiles.Helpers;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class PractitionerService {
    private final PractitionerDAO practitionerDAO= new PractitionerDAO();
    private final ClinicDAO clinicDAO= new ClinicDAO();

    public PractitionerProfileDTO getPractitionerWithClinics(int id) {
        try {
            Practitioner practitioner = practitionerDAO.getPractitionerById(id);
            if (practitioner == null) {
                return null;
            }
        List<Clinic> clinics = clinicDAO.getAllClinicsOfPractitioner(id);
        PractitionerProfileDTO result = Helpers.Mapper.mapToPractitionerProfileDTO(practitioner, clinics);
          return result;
        } catch (SQLException e) {
            System.err.println("Error retrieving practitioner: " + e.getMessage());
            return null;
        }
    }
    public List<PractitionerProfileDTO> getAllPractitioner() {
        try {
            List<Practitioner>practitioners = practitionerDAO.getAllPractitioners();
            if (practitioners == null) {
                return null;
            }
            List<PractitionerProfileDTO> result = new ArrayList<PractitionerProfileDTO>();
        for(var doctor : practitioners ){
             int docID= doctor.getId();
            var clinics = clinicDAO.getAllClinicsOfPractitioner(docID);
            var profile =Helpers.Mapper.mapToPractitionerProfileDTO(doctor,clinics);
            result.add(profile);
        }
          return result;
        } catch (SQLException e) {
            System.err.println("Error retrieving practitioner: " + e.getMessage());
            return null;
        }
    }
    public boolean updatePractitioner(Practitioner practitioner) {
        return practitionerDAO.updatePractitioner(practitioner);
    }
    public boolean deletePractitioner(int id) {
        return practitionerDAO.deletePractitioner(id);
    }
    public boolean isPractitionerExist(int id) {
        return practitionerDAO.getPractitionerById(id)!= null;
    }
     /**
     * Get practitioners ordered by rating, optionally filtered by specialization
     * @param specialization (optional) filter by this specialization
     * @return List of practitioners ordered by rating (descending)
     */
    public List<Practitioner> getPractitionersOrderedByRating(String specialization) {
        List<Practitioner> practitioners = practitionerDAO.getAllPractitioners(); // Handle exception appropriately (log it, throw custom exception, etc.)
        // Return empty list on error
        // Filter by specialization if provided
        if (specialization != null && !specialization.isEmpty()) {
            practitioners = practitioners.stream()
                    .filter(p -> p.getSpecialization().equalsIgnoreCase(specialization))
                    .collect(Collectors.toList());
        }
        // Sort by rating descending
        practitioners.sort(Comparator.comparingDouble(Practitioner::getRating).reversed());
        return practitioners;
    }

    /**
     * Get all practitioners of a specific specialization
     * @param specialization required specialization filter
     * @return List of practitioners with the given specialization
     */
    public List<Practitioner> getPractitionersBySpecialization(String specialization) {
        if (specialization == null || specialization.isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be null or empty");
        }
        
        return practitionerDAO.getAllPractitioners().stream()
                .filter(p -> p.getSpecialization().equalsIgnoreCase(specialization))
                .collect(Collectors.toList()); // Return empty list on error
    }
    
    /**
     * Get all practitioners of a specific specialization ordered by rating (descending)
     * @param specialization required specialization filter
     * @return List of practitioners with the given specialization ordered by rating
     */
    public List<Practitioner> getPractitionersBySpecializationOrderedByRating(String specialization) {
        if (specialization == null || specialization.isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be null or empty");
        }
        
        return practitionerDAO.getAllPractitioners().stream()
                .filter(p -> p.getSpecialization().equalsIgnoreCase(specialization))
                .sorted(Comparator.comparingDouble(Practitioner::getRating).reversed())
                .collect(Collectors.toList()); // Return empty list on error
    }
}