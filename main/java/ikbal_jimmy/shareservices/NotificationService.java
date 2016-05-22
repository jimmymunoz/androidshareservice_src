package ikbal_jimmy.shareservices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Config.ConstValue;

/**
 * Created by jimmymunoz on 21/05/16.
 */
public class NotificationService extends Service {

    private Context myContext;
    private android.os.Handler customHandler;


    @Override
    public void onCreate(){
        myContext = this;
        customHandler = new android.os.Handler();
        Toast.makeText(myContext, "Init Service", Toast.LENGTH_LONG).show();

        customHandler.postDelayed(updateTimerThread, 0);

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
                //Log.d("Handlers", "HttGetUserPendingNoftificationsTask");
                new HttGetUserPendingNoftificationsTask().execute("");
            }
            else{
                //Log.d("Handlers", "No Api key");
            }

            customHandler.postDelayed(this, 60000);
        }
    };


    @Override
    public void onDestroy(){
        Toast.makeText(myContext, "Destroy Service", Toast.LENGTH_LONG).show();

        super.onDestroy();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void notifyMessage(Notification objNotification)
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_icon)
                        .setContentTitle("" + objNotification.titre.toString())
                        .setContentText("" + objNotification.message.toString());
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent;
        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        switch (objNotification.activity){
            case "Conversations":
                resultIntent = new Intent(this, ConversationsActivity.class);
                stackBuilder.addParentStack(ConversationsActivity.class);
                break;
            default:
                resultIntent = new Intent(this, MainActivity.class);
                stackBuilder.addParentStack(MainActivity.class);
                break;
        }

        // Adds the back stack for the Intent (but not the Intent itself)

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify( Integer.parseInt( objNotification.id_notification ), mBuilder.build());

    }


    private class HttGetUserPendingNoftificationsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "notifications";
            Log.d("Get", "request  :" + urladress);
            String responseServer = RestHelper.executeGET(urladress);
            Log.d("Get", "Response  :" + responseServer);
            return responseServer;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {

            ArrayList<Notification> updatedListNotifications = new ArrayList<Notification>();
            ArrayList<String> UpdateNotifications = new ArrayList<String>();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(myContext, "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    JSONArray jsonArray = jsonRootObject.optJSONArray("notifications");
                    //Iterate the jsonArray and print the info of JSONObjects
                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Notification tmpMsg = new Notification(
                            jsonObject.optString("id_notification").toString(),
                            jsonObject.optString("id_user").toString(),
                            jsonObject.optString("titre").toString(),
                            jsonObject.optString("activity").toString(),
                            jsonObject.optString("activity_data").toString() ,
                            jsonObject.optString("message").toString(),
                            jsonObject.optString("notified").toString(),
                            jsonObject.optString("created_a").toString()
                        );
                        String id_notification = jsonObject.optString("id_notification").toString();
                        String titre = jsonObject.optString("titre").toString();
                        String message = jsonObject.optString("message").toString();

                        UpdateNotifications.add(id_notification);
                        Log.d("Not item::: ", " " + id_notification + " - " + titre + " - " + message );
                        updatedListNotifications.add(tmpMsg);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String ids_notification =  TextUtils.join(", ", UpdateNotifications);
            for (Notification objNotification : updatedListNotifications){
                notifyMessage(objNotification);
            }
            if( ids_notification.length() > 0 ){
                Log.d("UpdateNotifications ", "ids_notification: " + ids_notification);
                new HttpUpdateNotificationsTask().execute(ids_notification);
            }

        }
    }

    private class HttpUpdateNotificationsTask extends AsyncTask<String, Void, String> {
        private HashMap<String,String> paramspost = new HashMap<String,String>();

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "update_notifications";
            paramspost.put("str_ids_notification", params[0]);

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
