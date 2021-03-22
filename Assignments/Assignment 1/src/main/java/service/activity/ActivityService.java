package service.activity;

import model.Activity;
import model.validation.Notification;
import repository.activity.ActivityRepository;
import service.FileService;

import java.sql.Date;
import java.util.List;

public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Boolean save(String name, String operation){
        return activityRepository.save(name, operation);
    }

    public Notification<Boolean> report(String name, Date date1, Date date2){
        List<Activity> activities = activityRepository.report(name);
        Notification<Boolean> reportNotification = new Notification<>();
        if (activities == null){
            reportNotification.addError("There was an error generating the report");
            reportNotification.setResult(false);
        }
        else{
            reportNotification.setResult(true);
            FileService.setOutputFile(name + "_" + date1.toString() + "_" + date2.toString() + ".txt");
            FileService.write("Operations between " + date1.toString() + " and " + date2.toString() + "\n\n");
            for (Activity activity: activities){
                Date date = new Date(activity.getDate().getTime());
                if ((date.equals(date1) || date.after(date1)) && (date.equals(date2) || date.before(date2))) {
                    FileService.write("Name: " + name + "\n");
                    FileService.write("Date: " + activity.getDate().toString() + "\n");
                    FileService.write("Operation: " + activity.getOperation() + "\n\n");
                }
            }
            FileService.closeFile();
        }
        return reportNotification;
    }

}
