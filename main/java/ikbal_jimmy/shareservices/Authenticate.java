package ikbal_jimmy.shareservices;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by H.ikbal on 04/05/2016.
 */
public class Authenticate {
    private static String api_key = null;
    private static String id_user_logged = "";
    private static String pseudo = "";

    private static String first_name = "";
    private static String last_name = "";
    private static String email = "";
    private static String id_user = "";
    private static String phone = "";



    public static void loadApiKey(Context context){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        api_key = settings.getString("api_key", null);
    }

    public static void loadUserData(Context context){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        api_key = settings.getString("api_key", null);
        id_user_logged = settings.getString("id_user_logged", null);
        pseudo = settings.getString("pseudo", null);
        first_name = settings.getString("first_name", null);
        last_name = settings.getString("last_name", null);
        email = settings.getString("email", null);
        phone = settings.getString("phone", null);

    }



    public static String getApiKey(){
        return api_key;
    }

    public static void setApiKey(String api_key_var, Context context){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("api_key", api_key_var);
        editor.commit();
        api_key = api_key_var;
    }

    public static void setUserLoginData(String apikey, String first_name_, String last_name, String pseudo_var, String email, String id_user, String phone, Context context){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("api_key", apikey);
        editor.putString("first_name", first_name_);
        editor.putString("last_name", last_name);
        editor.putString("pseudo", pseudo_var);
        editor.putString("email", email);
        editor.putString("id_user", id_user);
        editor.putString("phone", phone);

        editor.commit();
        api_key = apikey;
        first_name = first_name_;
        last_name = last_name;
        id_user_logged = id_user;
        pseudo = pseudo_var;
        email = email;
        phone = phone;
    }

    public static String getIdUserLogged(){
        return id_user_logged;
    }

    public static String getIdUserLogged(Context context){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        id_user_logged = settings.getString("id_user_logged", null);
        return id_user_logged;
    }

    public static String getPseudo(Context context){
        /*
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        api_key = settings.getString("api_key", null);

       */
        return pseudo;
    }
}
