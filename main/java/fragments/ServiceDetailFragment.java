package fragments;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Config.ConstValue;
import ikbal_jimmy.shareservices.Conversation;
import ikbal_jimmy.shareservices.R;
import ikbal_jimmy.shareservices.RestHelper;
import ikbal_jimmy.shareservices.ServiceShare;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceDetailFragment extends android.app.Fragment {
    String id_service;
    static Activity act;
    public static View rootview2;
    public ServiceDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        act = getActivity();
        Bundle arg = this.getArguments();
        id_service = arg.getString("id_service");
        rootview2 = inflater.inflate(R.layout.fragment_service_detail, container, false);
        final View rootView = inflater.inflate(R.layout.fragment_service_detail, container, false);
        //rootview2 = rootView;
        rootView.findViewById(R.id.button_tmp_navigation).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        android.app.Fragment fragment = new PaymentFragment();

                        Bundle args = new Bundle();
                        fragment.setArguments(args);
                        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    }
                });


        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttGetDetailService().execute(id_service);
        } else {

        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_detail, container, false);
    }
     public static  void refreshArrayListViewData( String id_service,String titre ,String active,String description,String address,String id_category_service,String price)
     {
         Toast.makeText(act,"lisyee."+address, Toast.LENGTH_LONG).show();
        /*
         TextView textview_messages = (TextView)rootview2.findViewById(R.id.adresseservice);
         textview_messages.setText(address);

         TextView titre_view= (TextView)rootview2.findViewById(R.id.titreservice);
         titre_view.setText(titre);

         TextView textview_prix= (TextView)rootview2.findViewById(R.id.price);
         textview_prix.setText(price);
           */
         TextView texteview_description = (TextView)rootview2.findViewById(R.id.adresseservice);
         texteview_description.setText(address);


         TextView texteview_titre = (TextView)rootview2.findViewById(R.id.titrecategory);
         texteview_titre.setText(titre);


     }


    private class HttGetDetailService extends AsyncTask<String, Void, String> {
        String id_user_logged = "";

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "services/" + id_service;

            Log.d("Get", "request  :" + urladress);
            String responseServer = RestHelper.executeGET(urladress);
            Log.d("Get", "Response  :" + responseServer);
            return responseServer;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {
            ServiceShare objetresult = new ServiceShare();
            String id_service = "";
            String titre = "";
            String active = "";
            String description = "";
            String address = "";
            String id_category_service = "";
            String price = "";

            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if (jsonRootObject.optString("error").toString().equals("1")) {
                    Toast.makeText(getActivity(), "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                } else {
                    jsonRootObject.optString("error").toString();
                    id_service = jsonRootObject.optString("id_service").toString();
                    titre = jsonRootObject.optString("titre").toString();
                    active = jsonRootObject.optString("active").toString();
                    description = jsonRootObject.optString("description").toString();
                    address = jsonRootObject.optString("address").toString();
                    id_category_service = jsonRootObject.optString("id_category_service").toString();
                    price = jsonRootObject.optString("price").toString();
                    Log.d("Debug service", "id_service: " + id_service + " titre: " + titre + " active: " + active+ " description: " + description+ " address: " + address);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Debug responseUrl: ",responseUrl );
            ServiceDetailFragment.refreshArrayListViewData(id_service, titre, active, description, address, id_category_service, price);



        }
    }
}