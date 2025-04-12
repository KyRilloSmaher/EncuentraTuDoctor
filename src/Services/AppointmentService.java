package Services;

import DAOs.*;
import DTOs.*;
import Models.*;
import Utiles.Helpers;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final PractitionerDAO practitionerDAO = new PractitionerDAO();
    private final ClinicDAO clinicDAO = new ClinicDAO();
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    /**
     * Books a new appointment based on the request data.
     * 
     * @param requestDTO AppointmentRequestDTO containing appointment details.
     * @return true if appointment is successfully created, false otherwise.
     * @throws SQLException
     */
    public boolean bookAppointment(AppointmentRequestDTO requestDTO) throws SQLException {
        Patient patient = patientDAO.getPatientById(requestDTO.getPatientId());
        Practitioner practitioner = practitionerDAO.getPractitionerById(requestDTO.getPractitionerId());
        Clinic clinic = clinicDAO.getClinicById(requestDTO.getClinicId());
        Schedule schedule = scheduleDAO.getScheduleById(requestDTO.getScheduleId());

        if (patient == null || practitioner == null || clinic == null || schedule == null) {
            return false; // One or more required entities not found
        }

        Appointment appointment = Helpers.Mapper.mapToAppointment(requestDTO);
        return appointmentDAO.addAppointment(appointment);
    }

    
    /**
     * Fetches appointment details by ID.
     * 
     * @param appointmentId Appointment ID.
     * @return AppointmentDetailsDTO containing details, or null if not found.
     * @throws SQLException
     */
    public AppointmentDetailsDTO getAppointmentDetails(int appointmentId) throws SQLException {
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
        if (appointment == null) {
            return null; // Appointment not found
        }

        AppointmentDetailsDTO appointmentDetails = Helpers.Mapper.mapToAppointmentDetails(appointment);

        return appointmentDetails;
    }

    
    /**
     * Fetches appointments Of Patients by its ID.
     * 
     * @param patientId Appointment ID.
     * @return AppointmentDetailsDTO containing details, or null if not found.
     * @throws SQLException
     */
    
    public List<AppointmentDetailsDTO> GetPatientAllAppointments(int patientId) throws SQLException {
        var appointments = appointmentDAO.getAppointmentsByPatientId(patientId);
        if (appointments == null) {
            return null; // Appointment not found
        }

        List<AppointmentDetailsDTO> appointmentDetails = new ArrayList<>() ;
        for(Appointment appointment : appointments ){
            var e = Helpers.Mapper.mapToAppointmentDetails(appointment);
                appointmentDetails.add(e);
        }
        return appointmentDetails;
    }
    
    
    
    /**
     * Cancels an appointment by ID.
     * 
     * @param appointmentId Appointment ID.
     * @return true if canceled successfully, false otherwise.
     * @throws SQLException
     */
    public boolean cancelAppointment(int appointmentId) throws SQLException {
        return appointmentDAO.deleteAppointment(appointmentId);
    }
    public boolean isExist(int id) {
        return appointmentDAO.getAppointmentById(id)!= null;
    }
}
