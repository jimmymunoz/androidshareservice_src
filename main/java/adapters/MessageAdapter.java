package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ikbal_jimmy.shareservices.Authenticate;
import ikbal_jimmy.shareservices.Message;
import ikbal_jimmy.shareservices.R;

/**
 * Created by jimmymunoz on 10/05/16.
 */
public class MessageAdapter extends ArrayAdapter<Message> {
    String id_user_logged;
    String user_pseudo;

    public MessageAdapter(Context context, ArrayList<Message> messages, String id_user_logged) {
        super(context, 0, messages);
        user_pseudo = Authenticate.getPseudo(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);

        ViewHolder holder = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            //Log.d("message.sender_pseudo ", message.sender_pseudo + " - " + user_pseudo + " - " + message.text);
            if( message.sender_pseudo.equals(user_pseudo) ){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_message, parent, false);
            }
            else{
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_message_right, parent, false);
                //holder.textView = (TextView)convertView.findViewById(R.id.list_item_type2_button);
            }
            convertView.setTag(holder);
        }

        TextView textview_user = (TextView) convertView.findViewById(R.id.textView1);
        textview_user.setText(message.text);
        TextView textview_message = (TextView) convertView.findViewById(R.id.textView2);
        textview_message.setText(message.sender_pseudo);
        TextView textview_status = (TextView) convertView.findViewById(R.id.textView3);
        textview_status.setText(message.send_date);

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}
