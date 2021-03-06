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
import android.widget.ArrayAdapter;
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

public class ServiceCategoryFragment extends Fragment {
    Activity act;
    public SharedPreferences settings;
    String id_category;
    String id_reciver;
    String id_user_logged;
    static ArrayAdapter<ServiceShare> adapter;
    static ArrayList<ServiceShare> arrayListData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arg = this.getArguments();
        String id_category = arg.getString("id_category");

        final View rootView = inflater.inflate(R.layout.activity_service_category, container, false);

        act = getActivity();
        settings = act.getSharedPreferences(ConstValue.MAIN_PREF, 0);

        //getActionBar().setIcon(R.drawable.app_icons_13);

        arrayListData = new ArrayList<ServiceShare>();
        adapter = new SearcheAdapter(act, arrayListData);
        ListView vue = (ListView) rootView.findViewById(R.id.service_category_list);
        vue.setAdapter(adapter);

        //Toast.makeText(act, "id_category."+id_category, Toast.LENGTH_LONG).show();


        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttGetConversationMessagesTask().execute(id_category);
        } else {
            Toast.makeText(getActivity(), "No network connection available.", Toast.LENGTH_LONG).show();
        }
        return rootView;

    }


    public static void refreshArrayListViewData(ArrayList<ServiceShare> UpdatedArrayListData){
        arrayListData.clear();
        arrayListData.addAll(UpdatedArrayListData);
        adapter.notifyDataSetChanged();
    }


    private class HttGetConversationMessagesTask extends AsyncTask<String, Void, String> {
        String id_user_logged = "";

        @Override
        protected String doInBackground(String... params) {
           String urladress = ConstValue.WEB_SERVICE_URL + "services?id_category="+params[0];

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
                            jsonObject.optString("id_category_service").toString(),
                            jsonObject.optString("address").toString(),
                            jsonObject.optString("price").toString()
                            );

                        updatedListAdapterData.add(tmpObj);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
           ServiceCategoryFragment.refreshArrayListViewData(updatedListAdapterData);

        }
    }
}
