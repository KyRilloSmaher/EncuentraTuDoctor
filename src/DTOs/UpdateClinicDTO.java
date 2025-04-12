package DTOs;

public class UpdateClinicDTO {
    private int clinicId;

    public int getPractitionearId() {
        return practitionearId;
    }

    public void setPractitionearId(int practitionearId) {
        this.practitionearId = practitionearId;
    }
    private int practitionearId;
    private String name;
    private String location;
    public int getClinicId() {
        return clinicId;
    }
    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    @Override
    public String toString() {
        return "UpdateClinicDTO [clinicId=" + clinicId + ", name=" + name + ", location=" + location + "]";
    }
    
}
