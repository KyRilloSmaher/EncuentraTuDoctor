package Controllers;

import DTOs.*;

import Services.*;
import Utiles.Helpers;
import View.Rate.RateDisplay_View;
import View.Rate.RatePractitionear_View;
import View.User.Startpage_View;
import java.awt.FlowLayout;
import java.sql.SQLException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RateController {
    private RateService rateService = new RateService();
    
    public void allRates(int Id, JScrollPane pane) throws SQLException {
      var rates = rateService.getRatingsByPatient(Id);
      
      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Layout for proper spacing
  
      for (var x : rates) {
          var p = new RateDisplay_View(x.getPatientName(), x.getRateValue() + "", x.getComment());
          panel.add(p);
      }
      
      // Set the panel inside the JScrollPane
      pane.setViewportView(panel);
  
      // Refresh UI
      panel.revalidate();
      panel.repaint();
  }
    
    public void OpenRatePage(int patientId ,int PractitonearId,String practitionearName){
       var x = new  RatePractitionear_View(patientId,PractitonearId,practitionearName);
       x.setVisible(true);
    }

    public void RatePractitonear(RatePractitionerDTO dto) throws SQLException {
       rateService.ratePractitioner(dto);
    }
}
