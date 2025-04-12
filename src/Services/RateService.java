package Services;

import DAOs.*;
import DTOs.*;
import Models.*;
import Utiles.Helpers;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RateService {
    private final PractitionerRatingsDAO rateDAO = new PractitionerRatingsDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final PractitionerDAO practitionerDAO = new PractitionerDAO();

    /**
     * Allows a patient to rate a practitioner.
     * @param rateDTO DTO containing rating details.
     * @return true if the rating was successfully added, false otherwise.
     * @throws SQLException 
     */
    public boolean ratePractitioner(RatePractitionerDTO rateDTO) throws SQLException {
        if (!patientDAO.isExist(rateDTO.getPatientId()) || !practitionerDAO.isExist(rateDTO.getPractitionerId())) {
            return false;
        }
        PractitionerRatings rate = Helpers.Mapper.mapToPractitionerRatings(rateDTO);
        return rateDAO.addRate(rate);
    }

    /**
     * Retrieves all ratings for a specific practitioner.
     * @param practitionerId ID of the practitioner.
     * @return List of DisplayRateDTO containing rating details.
          * @throws SQLException 
          */
         public List<DisplayRateDTO> getRatingsForPractitioner(int practitionerId) throws SQLException {
        List<PractitionerRatings> rates = rateDAO.getAllRatesOfPractitionear(practitionerId);
        List<DisplayRateDTO> ratesList = new ArrayList<DisplayRateDTO>();
        for (PractitionerRatings rate : rates) {
            ratesList.add(Helpers.Mapper.mapToRateDisplay(rate));
        }
        return ratesList;
    }
  public List<DisplayRateDTO> getRatingsByPatient(int patientId) throws SQLException {
        List<PractitionerRatings> rates = rateDAO.getAllRatedPractitionerByPatient(patientId);
        List<DisplayRateDTO> ratesList = new ArrayList<DisplayRateDTO>();
        for (PractitionerRatings rate : rates) {
            var r = Helpers.Mapper.mapToRateDisplay(rate);
            ratesList.add(r);
        }
        return ratesList;
    }
}
