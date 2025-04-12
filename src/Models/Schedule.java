package Models;

import java.time.DayOfWeek;
import java.time.LocalTime;
public class Schedule {
    private int id;
    private int clinicId;
    private Clinic Clinic;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    // Constructors
    public Schedule() {
    }
    
    public Schedule(int id, int clinicId,DayOfWeek dayOfWeek, LocalTime startTime,
            LocalTime endTime) {
        this.id = id;
        this.clinicId = clinicId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Schedule(int clinicId, Clinic clinic, DayOfWeek dayOfWeek, LocalTime startTime,
            LocalTime endTime) {
        this.clinicId = clinicId;
        this.Clinic = clinic;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clinic getClinic() {
        return Clinic;
    }

    public void setClinic(Clinic clinic) {
        this.Clinic = clinic;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }


    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ",ClinicId=" + clinicId +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int ClinicId) {
        this.clinicId = ClinicId;
    }
}