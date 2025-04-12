package Controllers;

import DTOs.*;

import Services.*;
import Utiles.Helpers;

import View.Appointment.PatientAppointmentsView;
import View.Patient.PatientHomePage_View;
import View.Patient.PatientProfile_View;
import View.Patient.EditProfile_view;
import View.Practitionear.AllPractitionearPage_View;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PatientController {
     UserController usercontroller = new UserController();
     MainController controller = new MainController();
     PatientService service = new PatientService();
     PractitionerService doctorService = new PractitionerService();
    // Sign In button Action
    public void PatientSignIn(JTextField Email_TextField ,JPasswordField Password_jPasswordField){
      var isValidInputs = Helpers.InputValidator.SignInValidation(Email_TextField, Password_jPasswordField);
        if(isValidInputs){
            var service = new PatientService();
            try {
                var patient = service.SignIn(Email_TextField.getText(), new String(Password_jPasswordField.getPassword()));
                if(patient == null ){
                     JOptionPane.showMessageDialog(null, "Email or Password may be Incorrect", "Sign In Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    for (java.awt.Window window : java.awt.Window.getWindows()){
                      if(window instanceof javax.swing.JFrame){
                          window.dispose();
                      }
                    }
                  OpenPatientHomePage(patient);
                }
            } catch (SQLException ex) {
                System.out.println("Error In SignIn_jButtonActionPerformed !");
            }
    }
  }
  
    // Open Patient Landing page  
    public void OpenPatientHomePage( PatientProfileDTO patient){
       var page = new PatientHomePage_View(patient);
                      page.setVisible(true);
                      page.setLocationRelativeTo(null);
    }
    
    // Register patient
     public void PatientRegistration(JTextField FullName_TextField,JTextField Age_TextField ,JTextField Email_TextField ,JTextField PhoneNumber_TextField ,JRadioButton MaleButton, JRadioButton FemaleButton,JTextField MedicalHistory_TextField ,JPasswordField Password_jPasswordField ,JPasswordField ConfirmPassword_jPasswordField){
           if(Helpers.InputValidator.RegisterationValidation( FullName_TextField, Age_TextField, Email_TextField,PhoneNumber_TextField, MaleButton, FemaleButton, MedicalHistory_TextField, Password_jPasswordField, ConfirmPassword_jPasswordField))
        {   String name = FullName_TextField.getText();
            int age =Integer.parseInt(Age_TextField.getText());
            String gender = MaleButton.isSelected()? "Male" :"Female";
            String email = Email_TextField.getText();
            String passwordHash = new String (Password_jPasswordField.getPassword());
            String confrimpassword = new String (ConfirmPassword_jPasswordField.getPassword());
            String phoneNumber = PhoneNumber_TextField.getText();
            String medicalHistory = MedicalHistory_TextField.getText().isEmpty()?"no signifcant medical history":MedicalHistory_TextField.getText() ;
            PatientRegistrationDTO dto = new PatientRegistrationDTO(name , age , gender,email,passwordHash,confrimpassword,phoneNumber,medicalHistory);
            PatientService service = new PatientService();
            
            if(service.registerPatient(dto)){
                  JOptionPane.showMessageDialog(null, "Registeration Succesfully", "Sign In Now", JOptionPane.OK_OPTION);
               for (java.awt.Window window : java.awt.Window.getWindows()){
                      if(window instanceof javax.swing.JFrame){
                          window.dispose();
                      }
                    }
                    controller.openSignInPage();
            }
        }
     }

    // Patient Profile Page
     
     public void PatientProfilePage(PatientProfile_View PatientProfile_View){    
         PatientProfile_View.setVisible(true);
     }
     
     // Open Page Conatins All Practitionear 
     
     public void DiscoverDoctorsPage( PatientProfileDTO patient){
         var docs=  doctorService.getAllPractitioner();
       var AllPractitionearView = new AllPractitionearPage_View(patient,docs);
       AllPractitionearView.setVisible(true);
     }
     // Open Edit Profile Page 
     public void OpenEditPatientProfile(PatientProfileDTO patient)
     {
        EditProfile_view x = new EditProfile_view(patient);
        x.setVisible(true);
     }
     // Edit PatientProfile
    public void EditPatientProfile(int Id ,String gender,JTextField FullName_TextField ,JTextField Age_TextField  ,JTextField Email_TextField,JTextField PhoneNumber_TextField,JTextField MedicalHistory_TextField) throws SQLException{
      String name = FullName_TextField.getText();
      String age = Age_TextField.getText();
      String email = Email_TextField.getText();
      String phone =PhoneNumber_TextField.getText();
      String med = MedicalHistory_TextField.getText() == "no significt medical History" ? "":MedicalHistory_TextField.getText();
      boolean validaName = Helpers.InputValidator.validateName(name);
      boolean ValidAge = Helpers.InputValidator.validateAge(age);
      boolean ValidEmail = Helpers.InputValidator.isValidEmail(email);
      boolean ValidPhone = Helpers.InputValidator.validatePhoneNumber(phone);
      boolean Validmedical = Helpers.InputValidator.validateMedicalHistory(med);
      if(validaName &&ValidAge  && ValidEmail&& ValidPhone&&Validmedical)
      {    
          UpdatePatientDTO dto = new UpdatePatientDTO(Id,name,Integer.parseInt( age),gender, email, phone,med);
          service.updatePatient(dto);
      }
      var newpatient = service.getPatientById(Id);
     PatientProfilePage(new PatientProfile_View(newpatient));
    }
    
     // Open Patient Appointments page  
    public void OpenPatientAppointmentspage( PatientProfileDTO patient) throws SQLException{
        AppointmentService service = new AppointmentService();
        var apppointments = service.GetPatientAllAppointments(patient.getId());
       var page = new PatientAppointmentsView(patient,apppointments);
                      page.setVisible(true);
                      page.setLocationRelativeTo(null);
    }
}
