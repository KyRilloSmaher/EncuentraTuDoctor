package Models;


public class PractitionerRatings {
    private int rateId;
    private double rateValue;
    private int waitingTime;
    private String comment;
    private int patientId;
    private int practitionerId;
    private Patient patient;
    private Practitioner practitioner;

    // Constructors
    public PractitionerRatings() {
     
    }

    public PractitionerRatings(int id ,int patientId, int practitionerId, double rateValue, int waitingTime, String comment) {
        this.rateId = id;
        this.patientId = patientId;
        this.practitionerId = practitionerId;
        this.rateValue = rateValue;
        this.waitingTime = waitingTime;
        this.comment = comment;
    }

    public PractitionerRatings(int patientId, int practitionerId,double rateValue, int waitingTime, String comment) {
        this.patientId = patientId;
        this.practitionerId = practitionerId;
        this.rateValue = rateValue;
        this.waitingTime = waitingTime;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "rateId=" + rateId +
                ", rateValue=" + rateValue +
                ", waitingTime=" + waitingTime +
                ", comment='" + comment + '\'' +
                '}';
    }





    public int getRateId() {
        return rateId;
    }
    public void setRateId(int rateId) {
        this.rateId = rateId;
    }
    public double getRateValue() {
        return rateValue;
    }
    public void setRateValue(double rateValue) {
        this.rateValue = rateValue;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(int practitionerId) {
        this.practitionerId = practitionerId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }
}