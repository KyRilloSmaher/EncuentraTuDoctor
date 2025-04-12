package View.Practitionear;

import DTOs.*;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.*;
import javax.swing.border.*;

public class AllPractitionearPage_View extends javax.swing.JFrame {
    private PatientProfileDTO patient;   
    private List<PractitionerProfileDTO> doctors;
    private JTextField nameField;
    private JTextField locationField;
    private JTextField specializationField;
    private JComboBox<String> dayCombo;
    
    public AllPractitionearPage_View(PatientProfileDTO patient, List<PractitionerProfileDTO> doctors) {
        this.patient = patient;
        this.doctors = doctors;
        initComponents();
        setLocationRelativeTo(null);
        loadDoctorCards(doctors);
    }

    private void loadDoctorCards(List<PractitionerProfileDTO> doctorsToDisplay) {
        JPanel cardsPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        cardsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        cardsPanel.setBackground(new Color(245, 248, 250));
        
        for (PractitionerProfileDTO doctor : doctorsToDisplay) {
            cardsPanel.add(new DoctorCard(
                doctor,patient
            ));
        }
        
        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainContentPanel.removeAll();
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Find Your Doctor");
        setMinimumSize(new Dimension(900, 600));
        
        // Main container with border layout
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(0, 0));
        
        // Search panel on the right
        JPanel searchPanel = createSearchPanel();
        contentPane.add(searchPanel, BorderLayout.EAST);
        
        // Main content area for doctor cards
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(new Color(245, 248, 250));
        contentPane.add(mainContentPanel, BorderLayout.CENTER);
        
        pack();
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(225, 600));
        panel.setBackground(new Color(153, 153, 255)); // Set the requested background color
        panel.setBorder(new CompoundBorder(
            new MatteBorder(0, 1, 0, 0, new Color(120, 120, 200)),
            new EmptyBorder(20, 15, 20, 15)
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Title
        JLabel titleLabel = new JLabel("Find Your Doctor");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE); // White text for better contrast
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(titleLabel);
        
        // Search fields
        nameField = createSearchField(panel, "Name", "Dr. Smith");
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        locationField = createSearchField(panel, "Location", "New York");
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        specializationField = createSearchField(panel, "Specialization", "Cardiology");
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Day of week combo box
        JLabel dayLabel = new JLabel("Day of Week");
        dayLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(dayLabel);
        
        dayCombo = new JComboBox<>(new String[] { 
            "Any Day", "Monday", "Tuesday", "Wednesday", 
            "Thursday", "Friday", "Saturday", "Sunday"
        });
        dayCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dayCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        dayCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(dayCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Search button
        JButton searchButton = new JButton("Search Doctors");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(new EmptyBorder(10, 15, 10, 15));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setMaximumSize(new Dimension(200, 40));
        searchButton.addActionListener(e -> performSearch());
        panel.add(searchButton);
        
        // Add flexible space to push content up
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

    private JTextField createSearchField(JPanel parent, String label, String placeholder) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBackground(new Color(153, 153, 255)); // Match parent background
        fieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fieldLabel.setForeground(Color.WHITE);
        fieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPanel.add(fieldLabel);
        
        JTextField textField = new JTextField(placeholder);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        textField.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, Color.WHITE),
            new EmptyBorder(5, 5, 5, 5)
        ));
        textField.setBackground(new Color(200, 200, 255)); // Light purple background
        fieldPanel.add(textField);
        
        parent.add(fieldPanel);
        return textField;
    }

    private void performSearch() {
        // Create search DTO with the current search criteria
    SearchDTO searchCriteria = new SearchDTO();
    searchCriteria.setname(nameField.getText().trim());
    searchCriteria.setLocation(locationField.getText().trim());
    searchCriteria.setSpecialization(specializationField.getText().trim());
    
    String selectedDay = (String) dayCombo.getSelectedItem();
    if (!"Any Day".equals(selectedDay)) {
        searchCriteria.setDayOfWeek(selectedDay);
    }

    // Filter doctors based on search criteria
    List<PractitionerProfileDTO> filteredDoctors = doctors.stream()
        .filter(doctor -> {
            // Name filter
            boolean nameMatch = searchCriteria.getName().isEmpty() || 
                doctor.getName().toLowerCase().contains(searchCriteria.getName().toLowerCase());
            
            // Specialization filter
            boolean specializationMatch = searchCriteria.getSpecialization() == null || 
                searchCriteria.getSpecialization().isEmpty() || 
                doctor.getSpecialization().toLowerCase()
                    .contains(searchCriteria.getSpecialization().toLowerCase());
            
            // Location filter - check all clinics' addresses
            boolean locationMatch = searchCriteria.getLocation() == null || 
                searchCriteria.getLocation().isEmpty() || 
                doctor.getClinics().stream()
                    .anyMatch(clinic -> clinic.getLocation().toLowerCase()
                        .contains(searchCriteria.getLocation().toLowerCase()));
            
        boolean dayMatch = searchCriteria.getDayOfWeek() == null 
                                            || searchCriteria.getDayOfWeek().isEmpty()
                                            || "Any Day".equalsIgnoreCase(searchCriteria.getDayOfWeek())
                                            || Optional.ofNullable(doctor.getClinics())
                                                .orElse(Collections.emptyList())
                                                .stream()
                                                .flatMap(clinic -> 
                                                    Optional.ofNullable(clinic.getAvailableSchedules())
                                                        .orElse(Collections.emptyList())
                                                        .stream()
                                                )
                                                .anyMatch(schedule -> 
                                                    schedule != null 
                                                    && schedule.getDayOfWeek() != null
                                                    && schedule.getDayOfWeek().toString().equalsIgnoreCase(searchCriteria.getDayOfWeek())
                                                );
            
            return nameMatch && specializationMatch && locationMatch && dayMatch;
        })
        .toList();
    
    // Reload the doctor cards with filtered results
    loadDoctorCards(filteredDoctors);
    }
    // Main method remains the same
public static void main(String args[]) {
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    
    java.awt.EventQueue.invokeLater(() -> {
        new AllPractitionearPage_View(null, null).setVisible(true);

    });
}
private JPanel mainContentPanel;
}

