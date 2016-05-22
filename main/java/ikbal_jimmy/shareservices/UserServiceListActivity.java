package ikbal_jimmy.shareservices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Config.ConstValue;
import adapters.SearcheAdapter;


public class UserServiceListActivity extends AppCompatActivity {
    Context myContext;

    static ArrayAdapter<ServiceShare> adapter;
    static ArrayList<ServiceShare> arrayListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        setContentView(R.layout.activity_user_service_list);
        setTitle("Services - " + Authenticate.pseudo);

        //getActionBar().setIcon(R.drawable.app_icons_13);

        arrayListData = new ArrayList<ServiceShare>();
        adapter = new SearcheAdapter(myContext, arrayListData);
        ListView vue = (ListView) findViewById(R.id.orders_list);
        vue.setAdapter(adapter);


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttGetServiceShareListTask().execute("");
        }
        else {
            Toast.makeText(myContext, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }


    public static void refreshArrayListViewData(ArrayList<ServiceShare> UpdatedArrayListData){
        arrayListData.clear();
        arrayListData.addAll(UpdatedArrayListData);
        adapter.notifyDataSetChanged();

    }


    private class HttGetServiceShareListTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "services";

            Log.d("Get", "request  :" + urladress);
            String responseServer = RestHelper.executeGET(urladress);
            Log.d("Get", "Response  :" + responseServer);
            return responseServer;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {

            ArrayList<ServiceShare> updatedListAdapterData = new ArrayList<ServiceShare>();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(myContext, "Error :" + jsonRootObject.optString("services").toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    JSONArray jsonArray = jsonRootObject.optJSONArray("orders");
                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //public ServiceShare(String id_order, String id_user, String id_service, String payment_code, String code, String code_valided, String code_notified, String created_at, String client_pseudo, String client_first_name, String client_last_name, String client_phone, String client_email, String titre, String description, String price, String image, String address, String city, String latitude, String longituge, String publication_date, String id_category_service, String active, String id_provider, String provider_pseudo, String provider_first_name, String provider_last_name, String provider_phone, String provider_email) {

                        ServiceShare tmpObj = new ServiceShare(
                            jsonObject.optString("id_service").toString(),
                            jsonObject.optString("titre").toString(),
                            jsonObject.optString("active").toString(),
                            jsonObject.optString("adress").toString(),
                            jsonObject.optString("description").toString(),
                            jsonObject.optString("category").toString(),
                            jsonObject.optString("prix").toString()
                        );
                        updatedListAdapterData.add(tmpObj);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            UserServiceListActivity.refreshArrayListViewData(updatedListAdapterData);

        }
    }
}
