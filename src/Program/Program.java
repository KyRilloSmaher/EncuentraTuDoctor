/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Program;

import Controllers.MainController;
import View.User.Startpage_View;
import java.sql.SQLException;


/**
 *
 * @author KyRilloS
 */
public class Program {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        MainController controller = new MainController();
        Startpage_View LandingPage = new Startpage_View(controller);
        LandingPage.setVisible(true);
    }
}
