package ikbal_jimmy.shareservices;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Config.ConstValue;
import fragments.PaymentFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceDetailFragmentActivity extends AppCompatActivity {
    String id_service;
    static Context myConntext;
    static TextView titre_texte;
    static TextView description_texte;
    static TextView texteview_address;
    static TextView texteview_category;
    static TextView text_prix;
    public ServiceDetailFragmentActivity() {
        // Required empty public constructor
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myConntext= this;


        Button paymement ;

        setContentView(R.layout.fragment_service_detail);
        /*
        * Intent intentMain = getIntent();
        Bundle extras = intentMain.getExtras();
        id_conversation = extras.getString("id_conversation");
        id_reciver = extras.getString("id_reciver");
        *
        * */
        Intent intentMain = this.getIntent();
        Bundle extras = intentMain.getExtras();
        id_service = extras.getString("id_service");


        /**Recuperation  du  service  */


        Button payment = (Button)findViewById(R.id.Button_paiment);
               payment.setOnClickListener(new View.OnClickListener() {
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


        ConnectivityManager connMgr = (ConnectivityManager) myConntext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttGetDetailService().execute(id_service);
        } else {

        }
        titre_texte = (TextView) findViewById(R.id.titreservice);
        description_texte = (TextView) findViewById(R.id.descriptionservice);
        texteview_address = (TextView) findViewById(R.id.adresseservice);
        texteview_category = (TextView)findViewById(R.id.titrecategory);
        text_prix = (TextView) findViewById(R.id.priceservice);
        // Inflate the layout for this fragment
    }
    public static void refreshArrayListViewData( String id_service,String titre ,String active,String description,String address,String id_category_service,String price)
    {
        Toast.makeText(myConntext,"listee."+address, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(myConntext, "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
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
            ServiceDetailFragmentActivity.refreshArrayListViewData(id_service, titre, active, description, address, id_category_service, price);



        }
    }
}