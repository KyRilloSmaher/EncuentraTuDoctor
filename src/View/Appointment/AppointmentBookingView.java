package View.Appointment;

import Controllers.*;
import DTOs.*;
import Services.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentBookingView extends JFrame {
    // Color palette
    private static final Color PRIMARY_PURPLE = new Color(102, 51, 153);
    private static final Color LIGHT_PURPLE = new Color(204, 153, 255);
    private static final Color DARK_PURPLE = new Color(51, 0, 102);
    private static final Color BACKGROUND = new Color(245, 245, 245);
    private static final Color CARD_BG = Color.WHITE;
    
    private PractitionerProfileDTO doctor;
    private PatientProfileDTO patient;
    private List<ClinicProfileDTO> clinics;
    private JComboBox<ClinicProfileDTO> clinicComboBox;
    private JComboBox<ScheduleDetailsDTO> scheduleComboBox;
    private JTextArea notesArea;
    private JButton submitButton;
    private JLabel priceLabel;

    public AppointmentBookingView(PractitionerProfileDTO doctor, PatientProfileDTO patient) {
        this.doctor = doctor;
        this.patient = patient;
        this.clinics = doctor.getClinics();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Book Appointment with " + doctor.getName());
        setSize(650, 550);
        setMinimumSize(new Dimension(550, 450));
        getContentPane().setBackground(BACKGROUND);

        // Main panel with card-like appearance
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new CompoundBorder(
            new EmptyBorder(20, 20, 20, 20),
            new MatteBorder(0, 0, 0, 0, PRIMARY_PURPLE)
        ));
        mainPanel.setBackground(BACKGROUND);
        getContentPane().add(mainPanel);

        // Header panel with gradient
        JPanel headerPanel = new GradientPanel(PRIMARY_PURPLE, DARK_PURPLE);
        headerPanel.setLayout(new BorderLayout(10, 10));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("NEW APPOINTMENT");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel doctorLabel = new JLabel("With Dr. " + doctor.getName() + " • " + doctor.getSpecialization());
        doctorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        doctorLabel.setForeground(new Color(230, 230, 230));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(doctorLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel with card appearance
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(CARD_BG);
        formPanel.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 0, 0, new Color(230, 230, 230)),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Current date display
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        datePanel.setBackground(CARD_BG);
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
        JLabel dateLabel = new JLabel("📅 " + today);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        datePanel.add(dateLabel);
        formPanel.add(datePanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Clinic selection with enhanced handling
        setupClinicSelection(formPanel);

        // Schedule selection with enhanced handling
        setupScheduleSelection(formPanel);

        // Notes field
        formPanel.add(createFormLabel("ADDITIONAL NOTES"));
        notesArea = new JTextArea(3, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        notesArea.setBorder(new CompoundBorder(
            new MatteBorder(1, 1, 1, 1, new Color(220, 220, 220)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane notesScroll = new JScrollPane(notesArea);
        notesScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        formPanel.add(notesScroll);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Price display
        priceLabel = new JLabel("💰 Consultation Fee: $" + doctor.getAppointmentPrice());
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        priceLabel.setForeground(DARK_PURPLE);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(priceLabel);

        JScrollPane formScroll = new JScrollPane(formPanel);
        formScroll.setBorder(null);
        formScroll.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(formScroll, BorderLayout.CENTER);

        // Submit button with enhanced action
        setupSubmitButton(mainPanel);
    }

    private void setupClinicSelection(JPanel formPanel) {
        formPanel.add(createFormLabel("SELECT CLINIC LOCATION"));
        clinicComboBox = createStyledComboBox();
        
        // Initialize with clinics
        DefaultComboBoxModel<ClinicProfileDTO> clinicModel = new DefaultComboBoxModel<>();
        if (clinics != null && !clinics.isEmpty()) {
            clinics.forEach(clinicModel::addElement);
        } else {
           // clinicModel.addElement();
            clinicComboBox.setEnabled(false);
        }
        clinicComboBox.setModel(clinicModel);
        
        clinicComboBox.setRenderer(new ClinicListRenderer());
        clinicComboBox.addActionListener(e -> {
            ClinicProfileDTO selected = (ClinicProfileDTO) clinicComboBox.getSelectedItem();
            if (selected != null && selected.getClinicId() != -1) {
                updateSchedulesForClinic(selected);
                updateFormState();
            }
        });
        
        formPanel.add(clinicComboBox);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void setupScheduleSelection(JPanel formPanel) {
        formPanel.add(createFormLabel("AVAILABLE TIME SLOTS"));
        scheduleComboBox = createStyledComboBox();
        scheduleComboBox.setRenderer(new ScheduleListRenderer());
        scheduleComboBox.addActionListener(e -> updateFormState());
        formPanel.add(scheduleComboBox);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void setupSubmitButton(JPanel mainPanel) {
        submitButton = new JButton("CONFIRM APPOINTMENT");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitButton.setBackground(PRIMARY_PURPLE);
        submitButton.setForeground(Color.BLACK);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(new EmptyBorder(12, 25, 12, 25));
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitButton.setEnabled(false); // Disabled until valid selection
        
        // Hover effects
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (submitButton.isEnabled()) {
                    submitButton.setBackground(LIGHT_PURPLE);
                }
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                if (submitButton.isEnabled()) {
                    submitButton.setBackground(PRIMARY_PURPLE);
                }
            }
        });
        
        submitButton.addActionListener(e -> {
            if (validateForm()) {
                try {
                    submitAppointment();
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentBookingView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        buttonPanel.add(submitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateSchedulesForClinic(ClinicProfileDTO clinic) {
        scheduleComboBox.removeAllItems();
        DefaultComboBoxModel<ScheduleDetailsDTO> model = new DefaultComboBoxModel<>();
        
        List<ScheduleDetailsDTO> schedules = clinic.getAvailableSchedules();
        if (schedules != null && !schedules.isEmpty()) {
            schedules.forEach(model::addElement);
        } else {
           // model.addElement(new ScheduleDetailsDTO(-1, "No available slots", "", ""));
            scheduleComboBox.setEnabled(false);
        }
        
        scheduleComboBox.setModel(model);
         scheduleComboBox.setEnabled(true);
    }

    private void updateFormState() {
        boolean validClinic = clinicComboBox.getSelectedItem() != null && 
                            ((ClinicProfileDTO) clinicComboBox.getSelectedItem()).getClinicId() != -1;
        
        boolean validSchedule = scheduleComboBox.getSelectedItem() != null && 
                              ((ScheduleDetailsDTO) scheduleComboBox.getSelectedItem()).getScheduleId() != -1;
        
        submitButton.setEnabled(validClinic && validSchedule);
    }

    private boolean validateForm() {
        ClinicProfileDTO clinic = (ClinicProfileDTO) clinicComboBox.getSelectedItem();
        ScheduleDetailsDTO schedule = (ScheduleDetailsDTO) scheduleComboBox.getSelectedItem();
        
        if (clinic == null || clinic.getClinicId() == -1 || 
            schedule == null || schedule.getScheduleId() == -1) {
            
            JOptionPane.showMessageDialog(this, 
                "Please select both a valid clinic and time slot", 
                "Incomplete Information", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void submitAppointment() throws SQLException {
        ClinicProfileDTO clinic = (ClinicProfileDTO) clinicComboBox.getSelectedItem();
        ScheduleDetailsDTO schedule = (ScheduleDetailsDTO) scheduleComboBox.getSelectedItem();
        
        AppointmentRequestDTO request = new AppointmentRequestDTO();
        request.setPatientId(patient.getId());
        request.setPractitionerId(doctor.getId());
        request.setClinicId(clinic.getClinicId());
        request.setScheduleId(schedule.getScheduleId());
        // Here you would typically send to your service layer
        System.out.println("Submitting appointment: " + request);
         PractitionearController controller = new PractitionearController();
         controller.AddAppointment(request);
        showConfirmationDialog(clinic, schedule);
    }
   private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(new Color(100, 100, 100));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, 0, 8, 0));
        return label;
    }

    private <T> JComboBox<T> createStyledComboBox() {
        JComboBox<T> combo = new JComboBox<>();
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        combo.setBorder(new CompoundBorder(
            new MatteBorder(1, 1, 1, 1, new Color(220, 220, 220)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return combo;
    }
    private void showConfirmationDialog(ClinicProfileDTO clinic, ScheduleDetailsDTO schedule) {
        JDialog dialog = new JDialog(this, "Appointment Confirmed", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(BACKGROUND);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(25, 25, 25, 25));
        content.setBackground(CARD_BG);
        
        JLabel icon = new JLabel("✓", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI", Font.BOLD, 48));
        icon.setForeground(PRIMARY_PURPLE);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("APPOINTMENT BOOKED!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(new EmptyBorder(10, 0, 20, 0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea details = new JTextArea(
            "Doctor: Dr. " + doctor.getName() + "\n" +
            "Date: " + schedule.getDayOfWeek() + "\n" +
            "Time: " + schedule.getStartTime() + " - " + schedule.getEndTime() + "\n" +
            "Location: " + clinic.getName()
        );
        details.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        details.setEditable(false);
        details.setBackground(CARD_BG);
        details.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton closeBtn = new JButton("DONE");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        closeBtn.setBackground(PRIMARY_PURPLE);
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBorder(new EmptyBorder(8, 25, 8, 25));
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.addActionListener(ev -> {
            dialog.dispose();
            this.dispose();
        });
        
        content.add(icon);
        content.add(title);
        content.add(details);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(closeBtn);
        
        dialog.add(content, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    // Custom renderers with modern styling
    private class ClinicListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                     boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof ClinicProfileDTO) {
                ClinicProfileDTO clinic = (ClinicProfileDTO) value;
                setText("🏥 " + clinic.getName() + " • " + clinic.getLocation());
            }
            if (isSelected) {
                setBackground(LIGHT_PURPLE);
                setForeground(Color.BLACK);
            }
            return this;
        }
    }

    private class ScheduleListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                     boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof ScheduleDetailsDTO) {
                ScheduleDetailsDTO schedule = (ScheduleDetailsDTO) value;
                setText("⏰ " + schedule.getDayOfWeek() + " • " + 
                       schedule.getStartTime() + " - " + schedule.getEndTime());
            }
            if (isSelected) {
                setBackground(LIGHT_PURPLE);
                setForeground(Color.BLACK);
            }
            return this;
        }
    }

    // Gradient panel for header
    private class GradientPanel extends JPanel {
        private Color color1;
        private Color color2;
        
        public GradientPanel(Color color1, Color color2) {
            this.color1 = color1;
            this.color2 = color2;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), 0, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // Test data
        PractitionerService practitionerService = new PractitionerService();
        PractitionerProfileDTO doctor = practitionerService.getPractitionerWithClinics(11);
        
        PatientProfileDTO patient = new PatientProfileDTO();
        patient.setId(1001);
        patient.setName("John Smith");
        
        SwingUtilities.invokeLater(() -> {
            AppointmentBookingView frame = new AppointmentBookingView(doctor, patient);
            frame.setVisible(true);
        });
    }
}