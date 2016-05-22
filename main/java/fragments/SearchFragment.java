package fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Config.ConstValue;
import adapters.SearcheAdapter;
import ikbal_jimmy.shareservices.R;
import ikbal_jimmy.shareservices.RestHelper;
import ikbal_jimmy.shareservices.ServiceShare;

//import com.servproapp.BookAppointmentActivity;
//import com.servproapp.R;

public class SearchFragment extends Fragment{
    Activity act;
    public SharedPreferences settings;

    private static  ArrayList<ServiceShare> listeServices;
    static ArrayAdapter<ServiceShare> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.activity_activity_search, container, false);
        act =getActivity();
        settings = act.getSharedPreferences(ConstValue.MAIN_PREF, 0);
        //setTitle("Recherche par nom" );

        rootView.findViewById(R.id.button1).
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                EditText DetailService = (EditText) rootView.findViewById(R.id.editDescription);
                String DetailServicesearch = DetailService.getText().toString();

                String task = "";
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new HttpSearchServiceRequestTask().execute(DetailServicesearch);
                } else {
                    Toast.makeText(getActivity(), "No network connection available.", Toast.LENGTH_LONG).show();
                    //textView.setText("No network connection available.");
                }
            }
        });

        rootView.findViewById(R.id.button_tmp_navigation).
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new ServiceDetailFragment();

                    Bundle args = new Bundle();
                    fragment.setArguments(args);
                    //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }
            });

        listeServices = new ArrayList<ServiceShare>();
        adapter = new SearcheAdapter(getActivity(), listeServices);
        //ListAdapter adapter = new ArrayAdapter(myContext, android.R.layout.simple_list_item_1, ListMessages);
        ListView vue = (ListView) rootView.findViewById(R.id.search_service_list);
        vue.setAdapter(adapter);
        return rootView;
    }
    public static void refreshServicesListViewData(ArrayList<ServiceShare> UpdatedListService){
        listeServices.clear();
        listeServices.addAll(UpdatedListService);
        adapter.notifyDataSetChanged();

    }

    private class HttpSearchServiceRequestTask extends AsyncTask<String, Void, String> {
        String id_user_logged = "";

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "services?titre_service="+params[0];

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
                    Toast.makeText(getActivity(), "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    JSONArray jsonArray = jsonRootObject.optJSONArray("services");
                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ServiceShare tmpObj = new ServiceShare(
                            jsonObject.optString("id_service").toString(),
                            jsonObject.optString("titre").toString(),
                            jsonObject.optString("active").toString(),
                            jsonObject.optString("description").toString(),
                            jsonObject.optString("address").toString(),
                            jsonObject.optString("id_category_service").toString(),
                            jsonObject.optString("price").toString()

                        );
                        updatedListAdapterData.add(tmpObj);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SearchFragment.refreshServicesListViewData(updatedListAdapterData);
        }
    }



}
