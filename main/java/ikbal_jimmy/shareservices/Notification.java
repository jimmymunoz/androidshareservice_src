package ikbal_jimmy.shareservices;

/**
 * Created by jimmymunoz on 21/05/16.
 */
public class Notification {

    public String id_notification;
    public String id_user;
    public String titre;
    public String activity;
    public String activity_data;
    public String message;
    public String notified;
    public String created_at;

    public Notification (String id_notification_, String id_user_, String titre_, String activity_, String activity_data_, String message_, String notified_, String created_at_){
        id_notification = id_notification_;
        id_user = id_user_;
        titre = titre_;
        activity = activity_;
        activity_data = activity_data_;
        message = message_;
        notified = notified_;
        created_at = created_at_;
    }
}
