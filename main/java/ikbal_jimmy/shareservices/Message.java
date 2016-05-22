package ikbal_jimmy.shareservices;

/**
 * Created by jimmymunoz on 10/05/16.
 */
public class Message {
    public String id_message;
    public String id_conversation;
    public String text;
    public String readed;
    public String send_date;
    public String read_date;
    public String sender_id_user;
    public String reciver_id_user;
    public String sender_pseudo;
    public String reciver_pseudo;

    public Message(String id_message, String id_conversation, String text, String readed, String send_date, String read_date, String sender_id_user, String reciver_id_user, String sender_pseudo, String reciver_pseudo) {
        this.id_message = id_message;
        this.id_conversation = id_conversation;
        this.text = text;
        this.readed = readed;
        this.send_date = send_date;
        this.read_date = read_date;
        this.sender_id_user = sender_id_user;
        this.reciver_id_user = reciver_id_user;
        this.sender_pseudo = sender_pseudo;
        this.reciver_pseudo = reciver_pseudo;
    }
}
