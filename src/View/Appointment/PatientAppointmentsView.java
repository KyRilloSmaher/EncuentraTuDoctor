/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Appointment;

import Controllers.*;
import DTOs.*;
import Services.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KyRilloS
 */


public class PatientAppointmentsView extends JFrame {
    // Color palette
    private static final Color PRIMARY_PURPLE = new Color(102, 51, 153);
    private static final Color LIGHT_PURPLE = new Color(204, 153, 255);
    private static final Color DARK_PURPLE = new Color(51, 0, 102);
    private static final Color BACKGROUND = new Color(245, 245, 245);
    private static final Color CARD_BG = Color.WHITE;
    private static PatientProfileDTO patient;
    
    private List<AppointmentDetailsDTO> appointments;

    public PatientAppointmentsView(PatientProfileDTO patient, List<AppointmentDetailsDTO> appointments) {
        this.appointments = appointments;
        this.patient =patient;
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("My Appointments");
        setSize(900, 650);
        setMinimumSize(new Dimension(800, 500));
        getContentPane().setBackground(BACKGROUND);

        // Main panel with card-like appearance
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND);
        getContentPane().add(mainPanel);

        // Header panel with gradient
        JPanel headerPanel = new GradientPanel(PRIMARY_PURPLE, DARK_PURPLE);
        headerPanel.setLayout(new BorderLayout(10, 10));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("MY APPOINTMENTS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel countLabel = new JLabel(appointments.size() + " upcoming appointments");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        countLabel.setForeground(new Color(230, 230, 230));
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(countLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Appointments list panel
        JPanel appointmentsPanel = new JPanel();
        appointmentsPanel.setLayout(new BoxLayout(appointmentsPanel, BoxLayout.Y_AXIS));
        appointmentsPanel.setBackground(BACKGROUND);
        
        if (appointments.isEmpty()) {
            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setBackground(BACKGROUND);
            
            JLabel emptyLabel = new JLabel("No appointments scheduled", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            emptyLabel.setForeground(new Color(150, 150, 150));
            
            emptyPanel.add(emptyLabel, BorderLayout.CENTER);
            appointmentsPanel.add(emptyPanel);
        } else {
            for (AppointmentDetailsDTO appointment : appointments) {
                appointmentsPanel.add(createAppointmentCard(appointment));
                appointmentsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(appointmentsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with action buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomPanel.setBorder(new MatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        bottomPanel.setBackground(BACKGROUND);
        
        JButton closeButton = createActionButton("CLOSE", PRIMARY_PURPLE);
        closeButton.addActionListener(e -> dispose());
        
        bottomPanel.add(closeButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createAppointmentCard(AppointmentDetailsDTO appointment) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 0, 0, new Color(220, 220, 220)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        // Header with appointment ID and status
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CARD_BG);
        
        JLabel idLabel = new JLabel("Appointment #" + appointment.getAppointmentId());
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        idLabel.setForeground(DARK_PURPLE);
        
        headerPanel.add(idLabel, BorderLayout.WEST);
        card.add(headerPanel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        // Doctor and clinic info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(CARD_BG);
        
        addInfoRow(infoPanel, "👨‍⚕️ Doctor:", appointment.getPractitionerName());
        addInfoRow(infoPanel, "🏥 Clinic:", appointment.getClinicName());
        addInfoRow(infoPanel, "📍 Address:", appointment.getClinicAddress());
        
        // Time information
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        timePanel.setBackground(CARD_BG);
        
        JLabel dayLabel = new JLabel("🗓️ " + appointment.getDayOfWeek());
        dayLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel timeLabel = new JLabel("⏰ " + formatTime(appointment.getStartTime()) + 
                                    " - " + formatTime(appointment.getEndTime()));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        timePanel.add(dayLabel);
        timePanel.add(timeLabel);
        
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(timePanel);
        card.add(infoPanel);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setBackground(CARD_BG);
        actionPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        JButton rescheduleButton = createActionButton("RESCHEDULE", LIGHT_PURPLE);
        rescheduleButton.setForeground(Color.BLACK);
        rescheduleButton.addActionListener(e -> rescheduleAppointment(appointment));
        
        JButton cancelButton = createActionButton("CANCEL", new Color(220, 80, 80));
        cancelButton.addActionListener(e -> cancelAppointment(appointment));
        
        actionPanel.add(rescheduleButton);
        actionPanel.add(cancelButton);
        card.add(actionPanel);

        return card;
    }

    private void addInfoRow(JPanel panel, String label, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row.setBackground(CARD_BG);
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelComp.setPreferredSize(new Dimension(100, 20));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        row.add(labelComp);
        row.add(valueComp);
        panel.add(row);
    }

    private String formatTime(LocalTime time) {
        return time.toString().substring(0, 5); // Format as HH:mm
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
            new MatteBorder(1, 1, 1, 1, new Color(200, 200, 200)),
            new EmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void rescheduleAppointment(AppointmentDetailsDTO appointment) {
        // Implementation for rescheduling
        JOptionPane.showMessageDialog(this, 
            "Reschedule functionality for appointment #" + appointment.getAppointmentId(),
            "Reschedule Appointment",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelAppointment(AppointmentDetailsDTO appointment) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to cancel appointment with " + appointment.getPractitionerName() + "?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Implementation for cancellation
            JOptionPane.showMessageDialog(this,
                "Appointment #" + appointment.getAppointmentId() + " has been cancelled",
                "Appointment Cancelled",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Gradient panel for header (same as previous implementation)
    private static class GradientPanel extends JPanel {
        private final Color color1;
        private final Color color2;
        
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
        // Sample data for testing
        java.util.List<AppointmentDetailsDTO> testAppointments = List.of(
            createTestAppointment(1, "Dr. Sarah Johnson", "City Heart Center", "123 Medical Drive", 
                                "Monday", LocalTime.of(9,0,0), LocalTime.of(10,00,00)),
            createTestAppointment(2, "Dr. Michael Smith", "Downtown Clinic", "456 Health Ave", 
                                "Wednesday", LocalTime.of(14,30,00), LocalTime.of(15,30,00))
        );
        
        SwingUtilities.invokeLater(() -> {
            PatientAppointmentsView frame = new PatientAppointmentsView(patient, testAppointments);
            frame.setVisible(true);
        });
    }
    
    private static AppointmentDetailsDTO createTestAppointment(int id, String doctor, String clinic, 
            String address, String day, LocalTime start, LocalTime end) {
        AppointmentDetailsDTO appt = new AppointmentDetailsDTO();
        appt.setAppointmentId(id);
        appt.setPatientName("John Doe");
        appt.setPractitionerName(doctor);
        appt.setClinicName(clinic);
        appt.setClinicAddress(address);
        appt.setDayOfWeek(DayOfWeek.valueOf(day));
        appt.setStartTime(start);
        appt.setEndTime(end);
        return appt;
    }
}
