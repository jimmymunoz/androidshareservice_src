package ikbal_jimmy.shareservices;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jimmymunoz on 17/04/16.
 */
public class UserModel extends RestModel{

    public UserModel()
    {
        super();//Jimmy -> RestModel -> Instance UrlServer
    }

    //http://www.tutorialspoint.com/android/android_json_parser.htm

    public String getUserData()
    {
        String responseUrl = RestHelper.executeGET(this.urlServer + "");
        String data = "--";
        try {
            JSONObject jsonRootObject = new JSONObject(responseUrl);

            data += jsonRootObject.getString("response");
            /*

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("Employee");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = Integer.parseInt(jsonObject.optString("id").toString());
                String name = jsonObject.optString("name").toString();
                float salary = Float.parseFloat(jsonObject.optString("salary").toString());

                data += "Node"+i+" : \n id= "+ id +" \n Name= "+ name +" \n Salary= "+ salary +" \n ";
            }
            */
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
