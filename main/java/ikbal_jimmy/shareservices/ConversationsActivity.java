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
import adapters.ConversationAdapter;

public class ConversationsActivity extends AppCompatActivity {
    Context myContext;
    String id_conversation;
    String id_reciver;
    String id_user_logged;
    static ArrayAdapter<Conversation> adapter;
    static ArrayList<Conversation> arrayListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        setContentView(R.layout.activity_conversations);
        id_user_logged = "1";//TODO: Get from Session
        setTitle("Conversations");

        //getActionBar().setIcon(R.drawable.app_icons_13);

        arrayListData = new ArrayList<Conversation>();
        adapter = new ConversationAdapter(myContext, arrayListData, id_user_logged);
        ListView vue = (ListView) findViewById(R.id.conversation_list);
        vue.setAdapter(adapter);


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttGetConversationMessagesTask().execute(id_user_logged);
        }
        else {
            Toast.makeText(myContext, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }


    public static void refreshArrayListViewData(ArrayList<Conversation> UpdatedArrayListData){
        arrayListData.clear();
        arrayListData.addAll(UpdatedArrayListData);
        adapter.notifyDataSetChanged();

    }


    private class HttGetConversationMessagesTask extends AsyncTask<String, Void, String> {
        String id_user_logged = "";

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "conversations";
            id_user_logged = params[0];

            Log.d("Get", "request  :" + urladress);
            String responseServer = RestHelper.executeGET(urladress);
            Log.d("Get", "Response  :" + responseServer);
            return responseServer;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {

            ArrayList<Conversation> updatedListAdapterData = new ArrayList<Conversation>();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(myContext, "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    JSONArray jsonArray = jsonRootObject.optJSONArray("conversations");
                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Conversation tmpObj = new Conversation(
                            jsonObject.optString("id_conversation").toString(),
                            jsonObject.optString("id_reciver").toString(),
                            jsonObject.optString("id_sender").toString(),
                            jsonObject.optString("sender_pseudo").toString(),
                            jsonObject.optString("reciver_pseudo").toString(),
                            jsonObject.optString("text").toString(),
                            jsonObject.optString("readed").toString(),
                            jsonObject.optString("read_date").toString(),
                            jsonObject.optString("send_date").toString(),
                            jsonObject.optString("total_messages").toString(),
                            jsonObject.optString("total_readed").toString()
                        );
                        updatedListAdapterData.add(tmpObj);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ConversationsActivity.refreshArrayListViewData(updatedListAdapterData);

        }
    }
}
