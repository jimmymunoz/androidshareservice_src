package ikbal_jimmy.shareservices;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myContext =this;
        String apikey = Authenticate.getApiKey();;
        Toast.makeText(myContext, "api key session:" + apikey, Toast.LENGTH_LONG).show();
        ((Button) findViewById(R.id.buttonlogin)).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        EditText passeword = (EditText) findViewById(R.id.passewordLogin);
                        String passe= passeword.getText().toString();
                        // String passe= "pasword ";
                        EditText editemail = (EditText) findViewById(R.id.Emaillogin);
                        String email= editemail.getText().toString();

                        String task = "";
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            new HttpRequestTask().execute(email,passe);
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
            String urladress ="http://46.101.40.23/shareserviceserver/v1/login";
            paramspos.put("email",params[0]);
            paramspos.put("password", params[1]);
            String responsePost = RestHelper.executePOST(urladress, paramspos);
            Log.d("Post", "Response  :" + responsePost);
            return responsePost;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {
            //Toast.makeText(myContext, "Regsiter Response :" + responseUrl, Toast.LENGTH_LONG).show();


            ArrayList<ServiceShare> ListService = new ArrayList<ServiceShare>();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(myContext, "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    String apikey = jsonRootObject.optString("api_key").toString();
                    String first_name = jsonRootObject.optString("first_name").toString();
                    String last_name = jsonRootObject.optString("last_name").toString();
                    String pseudo = jsonRootObject.optString("pseudo").toString();
                    String email = jsonRootObject.optString("email").toString();
                    String id_user = jsonRootObject.optString("id_user").toString();
                    String phone = jsonRootObject.optString("phone").toString();

                    Toast.makeText(myContext, "api key :" + apikey, Toast.LENGTH_LONG).show();
                    //Authenticate.setApiKey(apikey, myContext);
                    Authenticate.setUserLoginData(apikey, first_name, last_name, pseudo, email, id_user, phone, myContext);
                    Intent intent = new Intent(myContext,MainActivity.class);
                    myContext.startActivity(intent);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

