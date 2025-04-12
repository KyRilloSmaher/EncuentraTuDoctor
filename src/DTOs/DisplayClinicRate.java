package DTOs;

public class DisplayClinicRate {
    private int rateId;
    private String patientName;
    private double rateValue;
    private String clinicName;
    private String comment;
    public int getRateId() {
        return rateId;
    }
    public void setRateId(int rateId) {
        this.rateId = rateId;
    }
    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public double getRateValue() {
        return rateValue;
    }
    public void setRateValue(double rateValue) {
        this.rateValue = rateValue;
    }
    public String getClinicName() {
        return clinicName;
    }
    public void setClinicName(String name) {
        this.clinicName = name;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    @Override
    public String toString() {
        return "DisplayClinicRate [rateId=" + rateId + ", patientName=" + patientName + ", rateValue=" + rateValue
                + ", clinicName=" + clinicName + ", comment=" + comment + "]";
    }

    
    
}
