package ve.com.joincic.joincicapp.adapters;

/**
 * Created by carla on 02/04/15.
 */
public class ScheduleItem implements Item {
    int id;
    boolean isPresentation;
    int sponsorId;
    int presenterId;
    String subject;
    String title;
    String description;
    String startDate;
    String endDate;
    String day;

    public String getDay() {
        return day;
    }

    public ScheduleItem(int id, boolean isPresentation, int sponsorId, int presenterId, String subject, String title,
                        String description, String startDate, String endDate, String day) {
        this.id = id;
        this.sponsorId = sponsorId;
        this.presenterId = presenterId;
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPresentation = isPresentation;
        this.day = day;

    }

    public int getId() {
        return id;
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public int getPresenterId() {
        return presenterId;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isPresentation() {
        return isPresentation;
    }

    @Override
    public boolean isScheduleItem() {
        return true;
    }
}
