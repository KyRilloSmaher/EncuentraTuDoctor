package Models;
public class ClinicRating {
    private int ratingId;
    private double ratingValue;
    private String comment;
    private int clinicId;
    private Patient patient;
    private Clinic clinic;


    // Constructors
    public ClinicRating() {}

    public ClinicRating(Patient patient, Clinic clinic, double ratingValue, String comment) {
        this.patient = patient;
        this.clinic = clinic;
        this.ratingValue = ratingValue;
        this.comment = comment;
    }

    public ClinicRating(int ratingId, double ratingValue, String comment, int clinicId ,int patientId) {
        this.ratingId = ratingId;
        this.ratingValue = ratingValue;
        this.comment = comment;
        this.clinicId = clinicId;
        this.patientId = patientId;
    }
    // Getters and Setters
    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    private int patientId;
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ClinicRating{" +
                "ratingId=" + ratingId +
                ", ratingValue=" + ratingValue +
                ", comment='" + comment + '\'' +
                '}';
    }
}