package ikbal_jimmy.shareservices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Config.ConstValue;

public class ServiceCategoryActivity extends AppCompatActivity {
    Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_category);
        myContext = this;
        setTitle("Services par categorie");

        String id_category = getIntent().getStringExtra("id_category");
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttpRequestTask().execute(id_category);
        } else {
            Toast.makeText(myContext, "No network connection available.", Toast.LENGTH_LONG).show();
            //textView.setText("No network connection available.");
        }
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.JSON_SERVICES + "?id_category_service=" + params[0];

            Log.d("Get", "request  :" + urladress);
            String responseServer = RestHelper.executeGET(urladress);
            Log.d("Get", "Response  :" + responseServer);
            return responseServer;
            // ServiceModel serviceModel = new ServiceModel();
            //return serviceModel.getServiceList();
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {

            if(false){
                Log.d("Get", "Response  :" + responseUrl);
                return;
            }


           // ArrayList<HashMap<String, String>> serviceDictionary = new ArrayList<HashMap<String, String>>();
            ArrayList<ServiceCategory> ListService = new ArrayList<ServiceCategory>();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(myContext, "Error :", Toast.LENGTH_LONG).show();
                }
                else{
                    JSONArray jsonArray = jsonRootObject.optJSONArray("services");
                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();

                        /*
                        "id_service": 3,
                      "titre": "demenagement",
                      "active": 1,
                      "description": "aide  audemagemen",
                      "id_category_service": 2
                        map.put("reciver_id_user", jsonObject.optString("reciver_id_user").toString());
                        */
                        ServiceCategory tmpService = new ServiceCategory(
                                jsonObject.optString("id_service").toString(),
                                jsonObject.optString("titre").toString(),
                                jsonObject.optString("active").toString(),
                                jsonObject.optString("description").toString(),
                                jsonObject.optString("id_category_service").toString()
                        );
                        ListService.add(tmpService);
                       // serviceDictionary.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            /*
            for(ServiceShare objServiceShare : ListService ){
                serviceList.add(objServiceShare.getTitre());
                TextView txttitle = (TextView)convertView.findViewById(R.id.textView1);
                txttitle.setText(map.get("comment"));

                TextView txtby = (TextView)convertView.findViewById(R.id.textView2);
                txtby.setText(map.get("visitor_name"));

                TextView txtdate = (TextView)convertView.findViewById(R.id.textView3);
                txtdate.setText(map.get("date_time"));


            }
            */
            /*
            ListAdapter adapter = new ServiceCategoryAdapter(myContext, ListService);

            //ListAdapter adapter = new ArrayAdapter(myContext, android.R.layout.simple_list_item_1, ListMessages);
            ListView vue = (ListView) findViewById(R.id.service_category_list);
            vue.setAdapter(adapter);
            */
        }
    }
}
