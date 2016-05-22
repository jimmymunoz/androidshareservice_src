package fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Config.ConstValue;
import ikbal_jimmy.shareservices.Authenticate;
import ikbal_jimmy.shareservices.R;
import ikbal_jimmy.shareservices.RestHelper;

//import com.servproapp.BookAppointmentActivity;
//import com.servproapp.R;

public class RegisterFragment extends Fragment{
    Activity act;
    public SharedPreferences settings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.activity_register, container, false);
        act =getActivity();
        settings = act.getSharedPreferences(ConstValue.MAIN_PREF, 0);



        rootView.findViewById(R.id.button1).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText pseudo = (EditText) rootView.findViewById(R.id.editPseudo);
                        String psseudo = pseudo.getText().toString();

                        EditText edit_name = (EditText) rootView.findViewById(R.id.editFname);
                        String name = edit_name.getText().toString();

                        EditText edit_lastname = (EditText) rootView.findViewById(R.id.editLname);
                        String last_name = edit_lastname.getText().toString();

                        EditText edit_phone = (EditText) rootView.findViewById(R.id.editPhone);
                        String phone = edit_phone.getText().toString();

                        EditText passeword = (EditText) rootView.findViewById(R.id.editPassword);
                        String passe = passeword.getText().toString();
                        // String passe= "pasword ";
                        EditText editemail = (EditText) rootView.findViewById(R.id.editEmail);
                        String email = editemail.getText().toString();

                        String task = "";
                        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            new HttpRequestTask().execute(name, last_name, phone, passe, email, psseudo);
                        } else {
                            Toast.makeText(getActivity(), "No network connection available.", Toast.LENGTH_LONG).show();
                            //textView.setText("No network connection available.");
                        }
                    }
                });

        return rootView;
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        private HashMap<String,String> paramspos = new HashMap<String,String>();

        @Override
        protected String doInBackground(String... params) {
            String urladress ="http://46.101.40.23/shareserviceserver/v1/register";
            paramspos.put("first_name",params[0]);
            paramspos.put("last_name",params[1]);
            paramspos.put("phone",params[2]);
            paramspos.put("password", params[3]);
            paramspos.put("email", params[4]);
            paramspos.put("pseudo", params[5]);

            Log.d("Post ", "Request params :" + params[0] + " - " + params[1] + " - " + params[2] + " - " + " - " + params[3] + " - " + " - " + params[4] + " - ");
            String responsePost = RestHelper.executePOST(urladress, paramspos);
            Log.d("Post", "Response  :" + responsePost);
            return responsePost;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {
            Toast.makeText(getContext(), "Regsiter Response :" + responseUrl, Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(getContext(), "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    String apikey = jsonRootObject.optString("api_key").toString();
                    Toast.makeText(getContext(), "api key :" + apikey, Toast.LENGTH_LONG).show();
                    Authenticate.setApiKey(apikey, getContext());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}
