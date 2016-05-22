package adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fragments.ServiceDetailFragment;
import ikbal_jimmy.shareservices.Conversation;
import ikbal_jimmy.shareservices.MessagesActivity;
import ikbal_jimmy.shareservices.R;
import ikbal_jimmy.shareservices.ServiceDetailFragmentActivity;
import ikbal_jimmy.shareservices.ServiceShare;

/**
 * Created by jimmymunoz on 11/05/16.
 */
public class SearcheAdapter extends ArrayAdapter<ServiceShare>  {
    Context myContext;

    public SearcheAdapter(Context context, ArrayList<ServiceShare> arrayListData) {
        super(context, 0, arrayListData);
        myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ServiceShare serviceobj = getItem(position);

        ViewHolder holder = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_search_service, parent, false);
        }
        //holder.textView.setText(mData.get(position));

        TextView textview_user = (TextView) convertView.findViewById(R.id.titreservice);
        textview_user.setText(serviceobj.getTitre());
        TextView textview_message = (TextView) convertView.findViewById(R.id.titrecategory);
        textview_message.setText(serviceobj.getCategory());
        TextView textview_status = (TextView) convertView.findViewById(R.id.prixservice);
        textview_status.setText(serviceobj.getPrix());
        TextView textview_messages = (TextView) convertView.findViewById(R.id.adresseservice);
        textview_messages.setText(serviceobj.getAdress());

/*
*   public void onClick(View v) {
                //send conversation id
                Intent intent = new Intent(myContext, MessagesActivity.class);
                intent.putExtra("id_conversation", conversation.id_conversation);
                intent.putExtra("id_reciver", conversation.id_reciver);
                myContext.startActivity(intent);
                //Toast.makeText(myContext, "Load Messages, id_conversation: " + conversation.id_conversation, Toast.LENGTH_LONG).show();
            }
        });

*
* */
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override


            public void onClick(View v) {
                //send conversation id
                Intent intent = new Intent(myContext, ServiceDetailFragmentActivity.class);
                intent.putExtra("id_service",serviceobj.getId_service());
                myContext.startActivity(intent);
                //Toast.makeText(myContext, "Load Messages, id_conversation: " + conversation.id_conversation, Toast.LENGTH_LONG).show();
            }
        });
         /*   public void onClick(View v) {
            Fragment fragment = new ServiceDetailFragment();
                //send conversation id
             Bundle args = new Bundle();
					args.putString("id_service", serviceobj.getId_service());
					fragment.setArguments(args);
					//args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
					FragmentManager fragmentManager = ((Activity) myContext).getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });*/

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}
