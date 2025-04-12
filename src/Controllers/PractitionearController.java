package Controllers;

import DTOs.*;
import Services.*;
import View.Practitionear.DoctorDetailsPage;

import java.sql.SQLException;

public class PractitionearController {
    AppointmentService service = new AppointmentService();
     PatientService patientservice = new PatientService();
    public void openPractitionearPage(PractitionerProfileDTO practitioner, PatientProfileDTO Patient) throws SQLException{
        var page = new DoctorDetailsPage(practitioner,Patient);
        page.setVisible(true);
    }
    public void AddAppointment(AppointmentRequestDTO dto) throws SQLException{
     if(service.bookAppointment(dto)){
      var patient = patientservice.getPatientById(dto.getPatientId());
      PatientController controller = new PatientController();
      controller.OpenPatientHomePage(patient);
     }
     
    }
}
