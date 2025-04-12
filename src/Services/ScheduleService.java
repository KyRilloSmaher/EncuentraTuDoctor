package Services;

import DAOs.*;
import DTOs.*;
import Models.*;
import Utiles.Helpers;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ScheduleService {
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    /**
     * Retrieves a schedule by its ID.
     * @param scheduleId ID of the schedule.
     * @return Schedule object if found, null otherwise.
     * @throws SQLException 
     */
    public Schedule getScheduleById(int scheduleId) throws SQLException {
        return scheduleDAO.getScheduleById(scheduleId);
    }

    /**
     * Fetches all schedules for a specific practitioner.
     * @param practitionerId ID of the practitioner.
     * @return List of schedules for the practitioner.
     * @throws SQLException 
     */
     public List<ScheduleDetailsDTO> getSchedulesByPractitioner(int clinicId) throws SQLException {
        var schs = scheduleDAO.getSchedulesByClinicId(clinicId);
        List <ScheduleDetailsDTO>  x =  new ArrayList<ScheduleDetailsDTO>();
        for( var s :schs ){
          x.add(Helpers.Mapper.mapToScheduleDetailsDTO(s));
        }
        return x;
    }

    /**
     * Fetches all schedules for a specific clinic.
     * @param clinicId ID of the clinic.
     * @return List of schedules for the clinic.
     * @throws SQLException 
     */
         public List<Schedule> getSchedulesByClinic(int clinicId) throws SQLException {
        return scheduleDAO.getSchedulesByClinicId(clinicId);
    }

    /**
     * Creates a new schedule.
     * @param schedule Schedule object to be created.
     * @return true if schedule is successfully created, false otherwise.
     * @throws SQLException 
     */
         public boolean createSchedule(Schedule schedule) throws SQLException {
        return scheduleDAO.addSchedule(schedule);
    }

    /**
     * Updates an existing schedule.
     * @param scheduleId ID of the schedule to be updated.
     * @param updatedSchedule Updated schedule data.
     * @return true if update is successful, false otherwise.
     * @throws SQLException 
     */
         public boolean updateSchedule(int scheduleId, Schedule updatedSchedule) throws SQLException {
        Schedule existingSchedule = scheduleDAO.getScheduleById(scheduleId);
        if (existingSchedule == null) {
            return false;
        }
        return scheduleDAO.updateSchedule(updatedSchedule);
    }

    /**
     * Deletes a schedule by ID.
     * @param scheduleId ID of the schedule to be deleted.
     * @return true if deleted successfully, false otherwise.
     * @throws SQLException 
    */
         public boolean deleteSchedule(int scheduleId) throws SQLException {
        return scheduleDAO.deleteSchedule(scheduleId);
    }
}
