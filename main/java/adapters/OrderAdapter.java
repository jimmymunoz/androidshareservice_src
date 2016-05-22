package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ikbal_jimmy.shareservices.Order;
import ikbal_jimmy.shareservices.OrderDetailActivity;
import ikbal_jimmy.shareservices.R;

/**
 * Created by jimmymunoz on 22/05/16.
 */

public class OrderAdapter extends ArrayAdapter<Order> {
    Context myContext;

    public OrderAdapter(Context context, ArrayList<Order> arrayListData) {
        super(context, 0, arrayListData);
        myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Order order = getItem(position);

        ViewHolder holder = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_order, parent, false);
        }
        TextView textview_user = (TextView) convertView.findViewById(R.id.textView1);
        textview_user.setText("Service code: " + order.code);
        TextView textview_message = (TextView) convertView.findViewById(R.id.textView2);
        textview_message.setText(order.titre);
        TextView textview_status = (TextView) convertView.findViewById(R.id.TextView01);
        textview_status.setText(order.price + " € ");
        TextView textview_messages = (TextView) convertView.findViewById(R.id.textView4);
        textview_messages.setText(order.client_pseudo + " / " + order.provider_pseudo);
        TextView textview_3 = (TextView) convertView.findViewById(R.id.textView3);
        if( order.code_valided.equals("1") ){
            textview_3.setText( "Code Validated" );
        }
        else{
            textview_3.setText( "Non Validated" );
        }

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //send conversation id
                Intent intent = new Intent(myContext, OrderDetailActivity.class);
                intent.putExtra("id_order", order.id_order);
                myContext.startActivity(intent);
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }
}
