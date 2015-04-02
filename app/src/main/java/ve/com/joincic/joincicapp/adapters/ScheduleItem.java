package ve.com.joincic.joincicapp.adapters;

/**
 * Created by carla on 02/04/15.
 */
public class ScheduleItem {
    int id;
    int sponsorId;
    int presenterId;
    String subject;
    String title;
    String description;
    String startDate;
    String endDate;

    public ScheduleItem(int id, int sponsorId, int presenterId, String subject, String title,
                        String description, String startDate, String endDate) {
        this.id = id;
        this.sponsorId = sponsorId;
        this.presenterId = presenterId;
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
