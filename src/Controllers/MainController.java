package Controllers;

import View.User.RegisterPage_View;
import View.User.SignIn_View;



public class MainController {
    
    
 public void openSignInPage() {
        SignIn_View signinPage = new SignIn_View();
        signinPage.setVisible(true);
        signinPage.setLocationRelativeTo(null);
    }
 
   public void openRegisterPage() {
        RegisterPage_View registerPage = new RegisterPage_View();
        registerPage.setVisible(true);
        registerPage.setLocationRelativeTo(null);
    }
} 