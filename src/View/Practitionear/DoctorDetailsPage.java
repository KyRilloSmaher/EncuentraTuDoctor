package View.Practitionear;

import DTOs.*;
import DAOs.PractitionerDAO;
import Models.*;
import Services.*;
import View.Appointment.AppointmentBookingView;
import View.Patient.profileImage;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DoctorDetailsPage extends JFrame {
    // Color scheme
    private static final Color PRIMARY_PURPLE = new Color(102, 51, 153); // Dark purple
    private static final Color SECONDARY_PURPLE = new Color(147, 112, 219); // Medium purple
    private static final Color LIGHT_PURPLE = new Color(230, 230, 250); // Lavender
    private static final Color DARK_TEXT = new Color(30, 30, 30); // Almost black
    private static final Color LIGHT_TEXT = new Color(240, 240, 240); // Off-white
    
    private PractitionerProfileDTO doctor;
    private PatientProfileDTO patient;
    private RateService rateService;
    private List<DisplayRateDTO> rates;
    
    public DoctorDetailsPage(PractitionerProfileDTO doctor, PatientProfileDTO patient) throws SQLException {
        this.doctor = doctor;
        this.patient = patient;
        this.rateService = new RateService();
        rates = rateService.getRatingsForPractitioner(doctor.getId());
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Encuentra Tu Doctor");
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Doctor Profile - " + doctor.getName());
        setSize(900, 700);
        setMinimumSize(new Dimension(800, 600));
        getContentPane().setBackground(Color.WHITE);

        // Main container with tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(DARK_TEXT);

        // Doctor Information Tab
        tabbedPane.addTab("Profile", createDoctorProfilePanel());
        
        // Ratings Tab
        tabbedPane.addTab("Ratings", createRatingsPanel());
        
        // Clinics Tab
        tabbedPane.addTab("Clinics", createClinicsPanel());

        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // Bottom panel with action buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new MatteBorder(1, 0, 0, 0, LIGHT_PURPLE));
        
        JButton bookButton = new JButton("Book Appointment");
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookButton.setBackground(PRIMARY_PURPLE);
        bookButton.setForeground(LIGHT_TEXT);
        bookButton.setFocusPainted(false);
        bookButton.setBorder(new EmptyBorder(10, 20, 10, 20));
        bookButton.addActionListener(e -> bookAppointment());
        
        bottomPanel.add(bookButton);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createDoctorProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Doctor header with image and basic info
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new MatteBorder(0, 0, 2, 0, LIGHT_PURPLE));

        // Doctor image placeholder
        JPanel imagePanel = new profileImage();
        imagePanel.setPreferredSize(new Dimension(120, 120));
        imagePanel.setBorder(new LineBorder(SECONDARY_PURPLE, 2));

        // Basic info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(doctor.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setForeground(PRIMARY_PURPLE);
        nameLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel specLabel = new JLabel(doctor.getSpecialization());
        specLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        specLabel.setForeground(DARK_TEXT);

        // Rating stars
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        ratingPanel.setBackground(Color.WHITE);
        
        JLabel ratingValue = new JLabel(String.format("%.1f", doctor.getRating()));
        ratingValue.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ratingValue.setForeground(PRIMARY_PURPLE);
        ratingPanel.add(ratingValue);
        
        // Add star icons
        for (int i = 0; i < 5; i++) {
            JLabel star = new JLabel(i < (int)doctor.getRating() ? "★" : "☆");
            star.setForeground(SECONDARY_PURPLE);
            star.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            ratingPanel.add(star);
        }
        
        JLabel reviewCount = new JLabel("(" + rates.size() + " reviews)");
        reviewCount.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        reviewCount.setForeground(DARK_TEXT);
        ratingPanel.add(reviewCount);

        infoPanel.add(nameLabel);
        infoPanel.add(specLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(ratingPanel);

        headerPanel.add(imagePanel, BorderLayout.WEST);
        headerPanel.add(infoPanel, BorderLayout.CENTER);

        // Details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        detailsPanel.setBackground(Color.WHITE);

        addDetailRow(detailsPanel, "Specialization:", doctor.getSpecialization());
        addDetailRow(detailsPanel, "Email:", doctor.getEmail());
        addDetailRow(detailsPanel, "Consultation Fee:", "$" + doctor.getAppointmentPrice());

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(detailsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRatingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Overall rating summary
        JPanel summaryPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Average rating card
        JPanel avgPanel = new JPanel();
        avgPanel.setLayout(new BoxLayout(avgPanel, BoxLayout.Y_AXIS));
        avgPanel.setBackground(LIGHT_PURPLE);
        avgPanel.setBorder(new CompoundBorder(
            new LineBorder(SECONDARY_PURPLE, 1), 
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel avgLabel = new JLabel("Average Rating");
        avgLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        avgLabel.setForeground(DARK_TEXT);
        avgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel avgValue = new JLabel(String.format("%.1f", doctor.getRating()));
        avgValue.setFont(new Font("Segoe UI", Font.BOLD, 36));
        avgValue.setForeground(PRIMARY_PURPLE);
        avgValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel countLabel = new JLabel("Based on " + rates.size() + " reviews");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(DARK_TEXT);
        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        avgPanel.add(avgLabel);
        avgPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        avgPanel.add(avgValue);
        avgPanel.add(countLabel);

        summaryPanel.add(avgPanel);

        // Reviews list
        JPanel reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        reviewsPanel.setBackground(Color.WHITE);

        if (rates.isEmpty()) {
            JLabel noReviews = new JLabel("No reviews yet");
            noReviews.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            noReviews.setForeground(DARK_TEXT);
            noReviews.setAlignmentX(Component.CENTER_ALIGNMENT);
            reviewsPanel.add(noReviews);
        } else {
            for (DisplayRateDTO review : rates) {
                reviewsPanel.add(createReviewCard(review));
                reviewsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(reviewsPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(summaryPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createClinicsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        if (doctor.getClinics().isEmpty()) {
            JLabel noClinics = new JLabel("No clinics available");
            noClinics.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            noClinics.setForeground(DARK_TEXT);
            noClinics.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(noClinics, BorderLayout.CENTER);
            return panel;
        }

        JPanel clinicsPanel = new JPanel();
        clinicsPanel.setLayout(new BoxLayout(clinicsPanel, BoxLayout.Y_AXIS));
        clinicsPanel.setBackground(Color.WHITE);

        for (ClinicProfileDTO clinic : doctor.getClinics()) {
            clinicsPanel.add(createClinicCard(clinic));
            clinicsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        JScrollPane scrollPane = new JScrollPane(clinicsPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void addDetailRow(JPanel panel, String label, String value) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelComp.setForeground(PRIMARY_PURPLE);
        labelComp.setPreferredSize(new Dimension(120, 20));

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valueComp.setForeground(DARK_TEXT);

        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.CENTER);
        panel.add(row);
    }

    private JPanel createReviewCard(DisplayRateDTO review) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new CompoundBorder(
            new LineBorder(LIGHT_PURPLE, 1), 
            new EmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(review.getPatientName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(PRIMARY_PURPLE);

        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        ratingPanel.setBackground(Color.WHITE);
        for (int i = 0; i < 5; i++) {
            JLabel star = new JLabel(i < review.getRateValue() ? "★" : "☆");
            star.setForeground(SECONDARY_PURPLE);
            ratingPanel.add(star);
        }

        header.add(nameLabel, BorderLayout.WEST);
        header.add(ratingPanel, BorderLayout.EAST);

        JTextArea comment = new JTextArea(review.getComment());
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        comment.setEditable(false);
        comment.setBackground(Color.WHITE);
        comment.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comment.setForeground(DARK_TEXT);
        comment.setBorder(new EmptyBorder(10, 0, 0, 0));

        card.add(header);
        card.add(comment);

        return card;
    }

    private JPanel createClinicCard(ClinicProfileDTO clinic) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new CompoundBorder(
            new LineBorder(LIGHT_PURPLE, 1), 
            new EmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(clinic.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(PRIMARY_PURPLE);
        nameLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        addClinicDetail(card, "📍", clinic.getLocation());

        // Schedules
        JPanel schedulesPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 10, 5));
        schedulesPanel.setBackground(Color.WHITE);
        
        TitledBorder scheduleBorder = new TitledBorder("Available Days");
        scheduleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
        scheduleBorder.setTitleColor(PRIMARY_PURPLE);
        schedulesPanel.setBorder(scheduleBorder);

        for (ScheduleDetailsDTO schedule : clinic.getAvailableSchedules()) {
            JLabel dayLabel = new JLabel(schedule.getDayOfWeek() + ": " + 
                schedule.getStartTime() + " - " + schedule.getEndTime());
            dayLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
            dayLabel.setBackground(LIGHT_PURPLE);
            dayLabel.setForeground(DARK_TEXT);
            dayLabel.setOpaque(true);
            schedulesPanel.add(dayLabel);
        }

        card.add(nameLabel);
        card.add(schedulesPanel);

        return card;
    }

    private void addClinicDetail(JPanel panel, String icon, String text) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row.setBackground(Color.WHITE);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setForeground(PRIMARY_PURPLE);
        row.add(iconLabel);
        
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(DARK_TEXT);
        row.add(textLabel);
        
        panel.add(row);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void bookAppointment() {
        var page = new AppointmentBookingView(doctor,patient);
        page.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                PractitionerService practitionerService = new PractitionerService();
                PractitionerProfileDTO doctor = practitionerService.getPractitionerWithClinics(11);
                DoctorDetailsPage frame = new DoctorDetailsPage(doctor, new PatientProfileDTO());
                frame.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
// Helper class for wrapping clinic schedules
class WrapLayout extends FlowLayout {
    public WrapLayout() {
        super();
    }

    public WrapLayout(int align) {
        super(align);
    }

    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        return layoutSize(target, false);
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getSize().width;
            if (targetWidth == 0) {
                targetWidth = Integer.MAX_VALUE;
            }

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);

            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            for (Component comp : target.getComponents()) {
                if (comp.isVisible()) {
                    Dimension d = preferred ? comp.getPreferredSize() : comp.getMinimumSize();
                    if (rowWidth + d.width > maxWidth) {
                        dim.width = Math.max(dim.width, rowWidth);
                        dim.height += rowHeight + vgap;
                        rowWidth = 0;
                        rowHeight = 0;
                    }
                    rowWidth += d.width + hgap;
                    rowHeight = Math.max(rowHeight, d.height);
                }
            }

            dim.width = Math.max(dim.width, rowWidth);
            dim.height += rowHeight;

            dim.width += insets.left + insets.right;
            dim.height += insets.top + insets.bottom + vgap * 2;

            return dim;
        }
    }
}