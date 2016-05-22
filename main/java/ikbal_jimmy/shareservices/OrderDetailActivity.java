package ikbal_jimmy.shareservices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Config.ConstValue;

public class OrderDetailActivity extends Activity {
    String id_order;
    Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_result);
        myContext = this;

        Intent intentMain = getIntent();
        Bundle extras = intentMain.getExtras();
        id_order = extras.getString("id_order");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttGetOrderTask().execute(id_order);
        }
        else {
            Toast.makeText(myContext, "No network connection available.", Toast.LENGTH_LONG).show();
        }

    }

    private class HttGetOrderTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "orders/" + params[0];

            Log.d("Get", "request  :" + urladress);
            String responseServer = RestHelper.executeGET(urladress);
            Log.d("Get", "Response  :" + responseServer);
            return responseServer;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(myContext, "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    Order tmpObj = new Order(
                        jsonRootObject.optString("id_order").toString(),
                        jsonRootObject.optString("id_user").toString(),
                        jsonRootObject.optString("id_service").toString(),
                        jsonRootObject.optString("payment_code").toString(),
                        jsonRootObject.optString("code").toString(),
                        jsonRootObject.optString("code_valided").toString(),
                        jsonRootObject.optString("code_notified").toString(),
                        jsonRootObject.optString("created_at").toString(),
                        jsonRootObject.optString("client_pseudo").toString(),
                        jsonRootObject.optString("client_first_name").toString(),
                        jsonRootObject.optString("client_last_name").toString(),
                        jsonRootObject.optString("client_phone").toString(),
                        jsonRootObject.optString("client_email").toString(),
                        jsonRootObject.optString("titre").toString(),
                        jsonRootObject.optString("description").toString(),
                        jsonRootObject.optString("price").toString(),
                        jsonRootObject.optString("image").toString(),
                        jsonRootObject.optString("address").toString(),
                        jsonRootObject.optString("city").toString(),
                        jsonRootObject.optString("latitude").toString(),
                        jsonRootObject.optString("longituge").toString(),
                        jsonRootObject.optString("publication_date").toString(),
                        jsonRootObject.optString("id_category_service").toString(),
                        jsonRootObject.optString("active").toString(),
                        jsonRootObject.optString("id_provider").toString(),
                        jsonRootObject.optString("provider_pseudo").toString(),
                        jsonRootObject.optString("provider_first_name").toString(),
                        jsonRootObject.optString("provider_last_name").toString(),
                        jsonRootObject.optString("provider_phone").toString(),
                        jsonRootObject.optString("provider_email").toString()
                    );

                    TextView tmp1 = (TextView) findViewById(R.id.textViewTitre);
                    tmp1.setText("Code de Commande: " + tmpObj.id_order);


                    TextView textViewtitre = (TextView) findViewById(R.id.textViewtitre);
                    textViewtitre.setText("Service: " + tmpObj.titre);


                    TextView descriptionTmp = (TextView) findViewById(R.id.textViewdescription);
                    descriptionTmp.setText("" + tmpObj.description);

                    TextView payment_codeTmp = (TextView) findViewById(R.id.textViewpayment_code);
                    payment_codeTmp.setText(tmpObj.payment_code);

                    TextView codeTmp = (TextView) findViewById(R.id.textViewcode);
                    codeTmp.setText("Code de validation: (" + tmpObj.code + ")");

                    TextView created_atTmp = (TextView) findViewById(R.id.textViewcreated_at);
                    created_atTmp.setText("Date de commande: " + tmpObj.created_at);

                    TextView client_pseudoTmp = (TextView) findViewById(R.id.textViewclient_pseudo);
                    client_pseudoTmp.setText("Client pseudo: " + tmpObj.client_pseudo);

                    TextView client_first_nameTmp = (TextView) findViewById(R.id.textViewclient_first_name);
                    client_first_nameTmp.setText("Client prenom: " + tmpObj.client_first_name);

                    TextView client_last_nameTmp = (TextView) findViewById(R.id.textViewclient_last_name);
                    client_last_nameTmp.setText("Client nom: " + tmpObj.client_last_name);

                    TextView client_phoneTmp = (TextView) findViewById(R.id.textViewclient_phone);
                    client_phoneTmp.setText("Client phone: " + tmpObj.client_phone);

                    TextView client_emailTmp = (TextView) findViewById(R.id.textViewclient_email);
                    client_emailTmp.setText("Client email: " + tmpObj.client_email);

                    TextView priceTmp = (TextView) findViewById(R.id.textViewprice);
                    priceTmp.setText("Client price: " + tmpObj.price);

                    TextView addressTmp = (TextView) findViewById(R.id.textViewaddress);
                    addressTmp.setText("Client adress: " + tmpObj.address);

                    TextView cityTmp = (TextView) findViewById(R.id.textViewcity);
                    cityTmp.setText("Client cité: " + tmpObj.city);

                    TextView provider_pseudoTmp = (TextView) findViewById(R.id.textViewprovider_pseudo);
                    provider_pseudoTmp.setText("Fournisseur pseudo: " + tmpObj.provider_pseudo);

                    TextView provider_first_nameTmp = (TextView) findViewById(R.id.textViewprovider_first_name);
                    provider_first_nameTmp.setText("Fournisseur prenom: " + tmpObj.provider_first_name);

                    TextView provider_last_nameTmp = (TextView) findViewById(R.id.textViewprovider_last_name);
                    provider_last_nameTmp.setText("Fournisseur nom: " + tmpObj.provider_last_name);

                    TextView provider_phoneTmp = (TextView) findViewById(R.id.textViewprovider_phone);
                    provider_phoneTmp.setText("Fournisseur phone: " + tmpObj.provider_phone);

                    TextView provider_emaiTmp = (TextView) findViewById(R.id.textViewprovider_emai);
                    provider_emaiTmp.setText("Fournisseur email: " + tmpObj.provider_email);

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
