package ikbal_jimmy.shareservices;

import android.app.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jimmymunoz on 17/04/16.
 */
public class ServiceModel extends RestModel{

    //http://www.tutorialspoint.com/android/android_json_parser.htm
    public ServiceModel()
    {
        super();//Jimmy -> RestModel -> Instance UrlServer
    }


    public String getServiceList()
    {
        String responseUrl = RestHelper.executeGET(this.urlServer + "services");
        return responseUrl;
        //return responseUrl;

    }

}
