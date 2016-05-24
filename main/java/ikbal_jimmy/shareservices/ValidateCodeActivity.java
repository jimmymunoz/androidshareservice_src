package ikbal_jimmy.shareservices;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import Config.ConstValue;


public class ValidateCodeActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Context myContext;
    private EditText codeET;
    private LinearLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        setTitle("Validation des Codes");
        setContentView(R.layout.activity_validate_code);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    private void initAccerometer(){
        Boolean supported = false;

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            List<Sensor> gravSensors = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
            for(int i=0; i < gravSensors.size(); i++) {
                ShowToast("Gravity sensor: " + gravSensors.get(i).getName());
                mSensor = gravSensors.get(i);
                break;
            }
        }
        else{
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                ShowToast("Accelerometré ok");
                supported = mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
            }
            else{
                ShowToast("Désolé il n'y pas des Accelerometres");
            }
        }


    }

    public  void  ShowToast( String  affichage )
    {
        Context context = getApplicationContext();
        CharSequence text = affichage;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //ShowToast("Test");
        rl = (LinearLayout)findViewById(R.id.linearLayout4);
        codeET = (EditText) findViewById(R.id.editText1);

        if (mSensor.getType() == Sensor.TYPE_ACCELEROMETER && codeET.getText().length() > 0 ) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            double total = Math.sqrt(x * x + y * y + z * z);

            if( total > 11 ){
                TextView textViewok = (TextView)findViewById(R.id.result_ok);
                textViewok.setText("");

                TextView textViewError = (TextView)findViewById(R.id.result_error);
                textViewError.setText("");

                rl.setBackgroundColor(Color.parseColor("#008080"));
                this.onPause();


                ShowToast("Envoyant le code :" + codeET.getText());
            }
            /*
            else if( total > 9 ){

                rl.setBackgroundColor(Color.WHITE);
            }
            */
            else{
                rl.setBackgroundColor(Color.WHITE);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    private class HttpValidateCodeTask extends AsyncTask<String, Void, String> {
        private HashMap<String,String> paramspost = new HashMap<String,String>();

        @Override
        protected String doInBackground(String... params) {
            String urladress = ConstValue.WEB_SERVICE_URL + "order_validate_code";
            paramspost.put("str_list_id_message", params[0]);

            Log.d("Post ", "Request params :" + params[0]);
            String responsePost = RestHelper.executePOST(urladress, paramspost);
            Log.d("Post", "Response  :" + responsePost);
            return responsePost;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {
            Toast.makeText(myContext, "Regsiter Response :" + responseUrl, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                if( jsonRootObject.optString("error").toString().equals("1") ){
                    Toast.makeText(myContext, "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                }
                else{

                    if( jsonRootObject.optString("error").toString().equals("1") ){
                        //Toast.makeText(myContext, "Error :" + jsonRootObject.optString("message").toString(), Toast.LENGTH_LONG).show();
                    }
                    else{

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


