package ikbal_jimmy.shareservices;

/**
 * Created by jimmymunoz on 11/05/16.
 */
public class Conversation {
    public String id_conversation;
    public String id_reciver;
    public String id_sender;
    public String sender_pseudo;
    public String reciver_pseudo;
    public String text;
    public String readed;
    public String read_date;
    public String send_date;
    public String total_messages;
    public String total_readed;

    Conversation(String id_conversation, String id_reciver, String id_sender, String sender_pseudo, String reciver_pseudo, String text, String readed, String read_date, String send_date, String total_messages, String total_readed){
        this.id_conversation = id_conversation;
        this.id_reciver = id_reciver;
        this.id_sender = id_sender;
        this.sender_pseudo = sender_pseudo;
        this.reciver_pseudo = reciver_pseudo;
        this.text = text;
        this.readed = readed;
        this.read_date = read_date;
        this.send_date = send_date;
        this.total_messages = total_messages;
        this.total_readed = total_readed;
    }
}
