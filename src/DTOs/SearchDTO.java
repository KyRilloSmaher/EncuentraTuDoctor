package DTOs;

public class SearchDTO {
    // Attributes
    private String specialization = null;
    private String location = null;
    private String Name = null;
   private String dayOfWeek = null;
   public SearchDTO() {
       
    }
    // Constructor
    public SearchDTO(String specialization, String location, String dayOfWeek, String name) {
        this.specialization = specialization;
        this.location = location;
        this.dayOfWeek = dayOfWeek;
        this.Name = name;
    }
    // Getters and Setters
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public String getName() {
        return Name;
    }
    public void setname(String name) {
        this.Name = name;
    }
    @Override
    public String toString() {
        return "SearchDTO [specialization=" + specialization + ", location=" + location + ", dayOfWeek=" + dayOfWeek
                + ", Name=" + Name + "]";
    }

    
}
