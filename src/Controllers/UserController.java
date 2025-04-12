package Controllers;

import DTOs.*;
import Services.*;
import View.User.ChangePassword_View;
import View.User.Startpage_View;
import java.sql.SQLException;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserController {

   UserService service = new UserService();
   MainController controller =new MainController();

  
    public void OpenChangePasswordPage(PatientProfileDTO patient ){
     var ChangePasswordPage = new ChangePassword_View( patient);
     ChangePasswordPage.setVisible(true);
    }
    public void SignOut(){
      
       for (java.awt.Window window : java.awt.Window.getWindows())
       {
           if(window instanceof javax.swing.JFrame)
           {
             window.dispose();
           }           
        }
       MainController controller = null;
       Startpage_View startpage = new Startpage_View(controller);
      startpage.setVisible(true);
    }
    
    public void ChangePassword(PatientProfileDTO patient ,String Email,String oldPassword ,String newPassword) throws SQLException{
        if(patient.getEmail() == null ? Email == null : patient.getEmail().equals(Email) )
             service.changePaasword(Email, newPassword, oldPassword);
        controller.openSignInPage();
    }
}
