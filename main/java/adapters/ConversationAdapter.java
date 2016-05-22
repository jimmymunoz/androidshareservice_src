package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ikbal_jimmy.shareservices.Conversation;
import ikbal_jimmy.shareservices.MessagesActivity;
import ikbal_jimmy.shareservices.R;

/**
 * Created by jimmymunoz on 11/05/16.
 */
public class ConversationAdapter extends ArrayAdapter<Conversation> {
    String id_user_logged;
    Context myContext;

    public ConversationAdapter(Context context, ArrayList<Conversation> arrayListData, String id_user_logged) {
        super(context, 0, arrayListData);
        myContext = context;
        this.id_user_logged = id_user_logged;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Conversation conversation = getItem(position);

        ViewHolder holder = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_conversation, parent, false);

            //convertView.setTag(holder);
        } else {
            //holder = (ViewHolder)convertView.getTag();
        }
        //holder.textView.setText(mData.get(position));

        TextView textview_user = (TextView) convertView.findViewById(R.id.textView1);
        textview_user.setText(conversation.reciver_pseudo);
        TextView textview_message = (TextView) convertView.findViewById(R.id.textView2);
        textview_message.setText(conversation.text);
        TextView textview_status = (TextView) convertView.findViewById(R.id.textView3);
        textview_status.setText(conversation.send_date);
        TextView textview_messages = (TextView) convertView.findViewById(R.id.textView4);
        textview_messages.setText(conversation.total_readed + " / " + conversation.total_messages);

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //send conversation id
                Intent intent = new Intent(myContext, MessagesActivity.class);
                intent.putExtra("id_conversation", conversation.id_conversation);
                intent.putExtra("id_reciver", conversation.id_reciver);
                myContext.startActivity(intent);
                //Toast.makeText(myContext, "Load Messages, id_conversation: " + conversation.id_conversation, Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}
