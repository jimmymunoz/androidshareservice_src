package ikbal_jimmy.shareservices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddSerciceActivity extends AppCompatActivity {
    private Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        setContentView(R.layout.activity_add__service);


        findViewById(R.id.buttonPublish).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edittitre = (EditText) findViewById(R.id.title);
                        String titre = edittitre.getText().toString();

                        EditText editdescription = (EditText) findViewById(R.id.description);
                        String description = editdescription.getText().toString();

                        EditText editprice = (EditText) findViewById(R.id.price);
                        String prix = editprice.getText().toString();

                        EditText editadress = (EditText) findViewById(R.id.Adress);
                        String adress = editadress.getText().toString();

                        EditText editcity = (EditText) findViewById(R.id.City);
                        String city = editcity.getText().toString();
                        // String passe= "pasword ";

                        EditText editcatergory = (EditText) findViewById(R.id.catergory);
                        String catergory = editcatergory.getText().toString();

                        EditText editatitude = (EditText) findViewById(R.id.Atitude);
                        String atitude = editatitude.getText().toString();


                        EditText editlongituge = (EditText) findViewById(R.id.longituge);
                        String longituge = editlongituge.getText().toString();


                        String task = "";
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            new HttpRequestTask().execute(titre, description, prix, adress, city, catergory, atitude, longituge);
                        } else {
                            Toast.makeText(myContext, "No network connection available.", Toast.LENGTH_LONG).show();
                            //textView.setText("No network connection available.");
                        }
                    }
                });
    }


    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        private HashMap<String,String> paramspos = new HashMap<String,String>();
        @Override
        protected String doInBackground(String... params) {
            String urladress ="http://46.101.40.23/shareserviceserver/v1/services";
            paramspos.put("titre",params[0]);
            paramspos.put("description",params[1]);
            paramspos.put("price",params[2]);

            paramspos.put("address",params[3]);
            paramspos.put("city", params[4]);
            paramspos.put("id_category_service", params[5]);
            paramspos.put("latitude", params[6]);
            paramspos.put("longituge", params[7]);

            Log.d("Post ", "Request params :" + params[0] + " - " + params[1] + " - " + params[2] + " - " + " - " + params[3] + " - " + " - " + params[4] + " - ");
            String responsePost = RestHelper.executePOST(urladress, paramspos);
            Log.d("Post", "Response  :" + responsePost);
            return responsePost;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {
            Toast.makeText(myContext, "Regsiter Response :" + responseUrl, Toast.LENGTH_LONG).show();


            ArrayList<ServiceShare> ListService = new ArrayList<ServiceShare>();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.getJSONObject("error").toString().equals("true") ){
                    Toast.makeText(myContext, "Error :" + jsonRootObject.getJSONObject("message").toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(myContext, "" + jsonRootObject.getJSONObject("message").toString(), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}