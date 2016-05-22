package fragments;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Config.ConstValue;
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
    static TextView titre_texte;
    static TextView description_texte;
    static TextView texteview_address;
    static TextView texteview_category;
    static TextView text_prix;
    Context myContext;

    public ServiceDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        act = getActivity();
        myContext = act;
        Bundle arg = this.getArguments();
        id_service = arg.getString("id_service");
        rootview2 = inflater.inflate(R.layout.fragment_service_detail, container, false);
        final View rootView = inflater.inflate(R.layout.fragment_service_detail, container, false);
        //rootview2 = rootView;
        rootView.findViewById(R.id.Button_paiment).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*
                        android.app.Fragment fragment = new PaymentFragment();

                        Bundle args = new Bundle();
                        fragment.setArguments(args);
                        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                        */
                    }
                });


        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttGetDetailService().execute(id_service);
        } else {

        }
        titre_texte = (TextView) rootView.findViewById(R.id.titreservice);
        description_texte = (TextView) rootView.findViewById(R.id.descriptionservice);
        texteview_address = (TextView) rootView.findViewById(R.id.adresseservice);
        texteview_category = (TextView)rootView.findViewById(R.id.titrecategory);
        text_prix = (TextView) rootView.findViewById(R.id.priceservice);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_detail, container, false);
    }
     public static void refreshArrayListViewData( String id_service,String titre ,String active,String description,String address,String id_category_service,String price)
     {
         Toast.makeText(act,"lisyee."+address, Toast.LENGTH_LONG).show();
         titre_texte.setText(titre);
         description_texte.setText("description");
         texteview_address.setText(address);
         texteview_category.setText(id_category_service);
         text_prix.setText(price);
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

    private class HttpPayOrderTask extends AsyncTask<String, Void, String> {
        private HashMap<String,String> paramspost = new HashMap<String,String>();

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "orders";
            paramspost.put("id_service", params[0]);

            Log.d("Post ", "Request params :" + params[0]);
            String responsePost = RestHelper.executePOST(urladress, paramspost);
            Log.d("Post", "Response  :" + responsePost);
            return responsePost;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {
            //Toast.makeText(myContext, "Regsiter Response :" + responseUrl, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(myContext, "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(myContext, jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}