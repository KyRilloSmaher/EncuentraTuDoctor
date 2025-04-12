package Services;

import DAOs.*;
import DTOs.*;
import Models.*;
import Utiles.Helpers;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClinicRateService {
    private final ClinicRatingDAO clinicRatingDAO = new ClinicRatingDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final ClinicDAO clinicDAO = new ClinicDAO();

    /**
     * Allows a patient to rate a clinic.
     * @param rateDTO DTO containing rating details.
     * @return true if the rating was successfully added, false otherwise.
     * @throws SQLException 
     */
    public boolean rateClinic(RateClinicDTO rateDTO) throws SQLException {
        if (!patientDAO.isExist(rateDTO.getPatientId()) || !clinicDAO.isExist(rateDTO.getClinicId())) {
            return false;
        }
        ClinicRating rating = Helpers.Mapper.mapToRateClinicDisplayDTO(rateDTO);
        return clinicRatingDAO.addClinicRating(rating);
    }

    /**
     * Retrieves all ratings for a specific clinic.
     * @param clinicId ID of the clinic.
     * @return List of Clinic Rating Display DTO containing rating details.
     * @throws SQLException 
          */
 public List<DisplayRateDTO> getRatingsForClinic(int clinicId) throws SQLException {
    List<ClinicRating> clinicRatings = clinicRatingDAO.getAllRatingsForClinic(clinicId);
     List<DisplayRateDTO> displayRatings = new ArrayList<DisplayRateDTO>();
     for (ClinicRating rate : clinicRatings) {
         displayRatings.add(Helpers.Mapper.mapToRateDisplayTO(rate));
     }
     return displayRatings;
    }

    /**
     * Retrieves all ratings made for clinics by patient.
     * @param patientId ID of the patient.
     * @return List of Clinic Rating Display DTO containing rating details.
     * @throws SQLException 
          */
 public List<DisplayRateDTO> getRatingsClinicByPatient(int patientId) throws SQLException {
     List<ClinicRating> clinicRatings = clinicRatingDAO.getAllRatedClinicsByPatient(patientId);
  List<DisplayRateDTO> displayRatings = new ArrayList<DisplayRateDTO>();
     for (ClinicRating rate : clinicRatings) {
         displayRatings.add(Helpers.Mapper.mapToRateDisplayTO(rate));
     }
     return displayRatings;
    }
}
