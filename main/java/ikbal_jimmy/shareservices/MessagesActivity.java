package ikbal_jimmy.shareservices;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Config.ConstValue;
import adapters.MessageAdapter;

public class MessagesActivity extends AppCompatActivity {
    Context myContext;
    String id_conversation;
    String id_reciver;
    String id_user_logged;
    static ArrayAdapter<Message> adapter;
    static ArrayList<Message> listMessages;
    private int refreshTime = 3000;//milliseconds 3 segs
    private boolean activity_running = false;//Jimmy: To validate u¡if is running
    static ArrayList<String> listMessageIds = new ArrayList<String>();//Jimmy: Update ids

    private android.os.Handler customHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        customHandler = new android.os.Handler();
        activity_running = true;

        setContentView(R.layout.activity_messages);

        Intent intentMain = getIntent();
        Bundle extras = intentMain.getExtras();
        id_conversation = extras.getString("id_conversation");
        id_reciver = extras.getString("id_reciver");
        setTitle("Conversation :" + id_conversation);
        id_user_logged = Authenticate.getIdUserLogged(myContext);//TODO: Get from Session


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            customHandler.postDelayed(updateTimerThread, 0);
        }
        else {
            Toast.makeText(myContext, "No network connection available.", Toast.LENGTH_LONG).show();
        }

        findViewById(R.id.button_send).
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editTextMessage = (EditText) findViewById(R.id.edit_text_out);
                    String messageText = editTextMessage.getText().toString();

                    if (messageText.length() > 0) {//Validation Message

                        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            editTextMessage.setText("");//Clear message
                            new HttpSendMessageTask().execute(messageText, id_reciver, id_conversation, id_user_logged);
                        } else {
                            Toast.makeText(myContext, "No network connection available.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(myContext, "Le message ne peut pas être vide", Toast.LENGTH_LONG).show();
                    }
                }
            });

        listMessages = new ArrayList<Message>();
        adapter = new MessageAdapter(myContext, listMessages, id_user_logged);
        //ListAdapter adapter = new ArrayAdapter(myContext, android.R.layout.simple_list_item_1, ListMessages);
        ListView vue = (ListView) findViewById(R.id.messages_list);
        vue.setAdapter(adapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        activity_running = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        activity_running = true;
        listMessageIds.clear();
    }


    /**
     * Automatic Process
     */
    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            //write here whaterver you want to repeat
            if( Authenticate.getApiKey() != null ){
                new HttGetConversationMessagesTask().execute(id_conversation, id_user_logged);
            }
            else{
                //Log.d("Handlers", "No Api key");
            }
            if( activity_running ){
                customHandler.postDelayed(this, refreshTime);
            }

        }
    };


    public static void refreshMessagesListViewData(ArrayList<Message> UpdatedListMessages){

        ArrayList<String> tmplistMessageIds = new ArrayList<String>();
        for (Message msgObj : UpdatedListMessages){
            tmplistMessageIds.add(msgObj.id_message);
        }
        //Jimmy: Displays only if there are updated messages
        if(! listMessageIds.containsAll(tmplistMessageIds) ){
            listMessages.clear();
            listMessages.addAll(UpdatedListMessages);
            adapter.notifyDataSetChanged();
            listMessageIds = tmplistMessageIds;
        }
    }


    private class HttGetConversationMessagesTask extends AsyncTask<String, Void, String> {
        String id_user_logged = "";

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "messages?id_conversation=" + params[0];
            id_user_logged = params[1];

            Log.d("Get", "request  :" + urladress);
            String responseServer = RestHelper.executeGET(urladress);
            Log.d("Get", "Response  :" + responseServer);
            return responseServer;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {
            ArrayList<Message> updatedListMessages = new ArrayList<Message>();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(myContext, "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    JSONArray jsonArray = jsonRootObject.optJSONArray("messages");
                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                         Message tmpMsg = new Message(
                            jsonObject.optString("id_message").toString(),
                            jsonObject.optString("id_conversation").toString(),
                            jsonObject.optString("text").toString(),
                            jsonObject.optString("readed").toString() ,
                            jsonObject.optString("send_date").toString(),
                            jsonObject.optString("read_date").toString(),
                            jsonObject.optString("sender_id_user").toString(),
                            jsonObject.optString("reciver_id_user").toString(),
                            jsonObject.optString("sender_pseudo").toString(),
                            jsonObject.optString("reciver_pseudo").toString()
                        );

                        //Log.d("Messages--> ",  jsonObject.optString("readed").toString() + " - " + jsonObject.optString("id_message").toString());
                        updatedListMessages.add(tmpMsg);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MessagesActivity.refreshMessagesListViewData(updatedListMessages);
            ArrayList<String> list_unread_messages = new ArrayList<String>();
            for(Message msgObj : updatedListMessages){
                if ( ( ! msgObj.readed.equals("1") )  && msgObj.reciver_id_user.equals(id_user_logged)  ){
                    list_unread_messages.add(msgObj.id_message);
                }
            }
            String str_list_id_message =  TextUtils.join(", ", list_unread_messages);
            //Log.d("Messages--> ",  str_list_id_message);

            if( str_list_id_message.length() > 0 ){
                //Update Readed Messages.
                new HttpUpdateMessagesTask().execute(str_list_id_message);
            }
        }
    }

    private class HttpSendMessageTask extends AsyncTask<String, Void, String> {
        private HashMap<String,String> paramspost = new HashMap<String,String>();
        String id_conversation;

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "messages";
            //id_user_logged = params[3];
            id_conversation = params[2];
            paramspost.put("text", params[0]);
            paramspost.put("id_reciver",params[1]);
            //paramspost.put("id_user_logged",params[3]);
            paramspost.put("id_conversation", params[2]);

            Log.d("Post ", "Request params :" + params[0] + " - " + params[1] + " - " + params[2] + " - ");
            String responsePost = RestHelper.executePOST(urladress, paramspost);
            Log.d("Post", "Response  :" + responsePost);
            return responsePost;
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

                    //new HttGetConversationMessagesTask().execute(id_conversation, id_user_logged);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class HttpUpdateMessagesTask extends AsyncTask<String, Void, String> {
        private HashMap<String,String> paramspost = new HashMap<String,String>();

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "messages_sedListReaded";
            paramspost.put("str_list_id_message", params[0]);

            Log.d("Post ", "Request params :" + params[0]);
            String responsePost = RestHelper.executePOST(urladress, paramspost);
            Log.d("Post", "Response  :" + responsePost);
            return responsePost;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {
            //Toast.makeText(myContext, "Regsiter Response :" + responseUrl, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(myContext, "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
