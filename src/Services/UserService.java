package Services;


import DAOs.UserDAO;
import Utiles.Helpers;
import java.sql.SQLException;



 public class UserService {

   private final UserDAO userDAO = new UserDAO() ;




  public void changePaasword(String email , String newPassword , String Oldpassword) throws SQLException{
     boolean isValidEmailInput = Helpers.InputValidator.isValidEmail(email);
     boolean isValidoldPassword = Helpers.InputValidator.validatePassword(newPassword, newPassword);
     boolean isValidnewPassword = Helpers.InputValidator.validatePassword(Oldpassword, Oldpassword);
     if(isValidEmailInput&&isValidoldPassword &&isValidnewPassword ){
       userDAO.updateUserPassword(email, Oldpassword, newPassword);
     }
  }

 }