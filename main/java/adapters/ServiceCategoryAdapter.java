package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ikbal_jimmy.shareservices.R;
import ikbal_jimmy.shareservices.ServiceCategory;

/**
 * Created by jimmymunoz on 10/05/16.
 */
public class ServiceCategoryAdapter extends ArrayAdapter<ServiceCategory> {

    public ServiceCategoryAdapter(Context context, ArrayList<ServiceCategory> services) {
        super(context, 0, services);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String sender_id_user = "1";

        MessageAdapter.ViewHolder holder = null;
        //int type = getItemViewType(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_service_category, parent, false);

        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}
