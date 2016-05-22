package ikbal_jimmy.shareservices;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by H.ikbal on 04/05/2016.
 */
public class Authenticate {
    public static String api_key = null;
    public static String id_user_logged = "";
    public static String pseudo = "";
    public static String first_name = "";
    public static String last_name = "";
    public static String email = "";
    public static String id_user = "";
    public static String phone = "";



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

    public static void logout(Context context){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();

        api_key = null;
        id_user_logged = "";
        pseudo = "";
        first_name = "";
        last_name = "";
        email = "";
        id_user = "";
        phone = "";
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

    public static void setUserLoginData(String apikey_, String first_name_, String last_name_, String pseudo_, String email_, String id_user_, String phone_, Context context){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("api_key", apikey_);
        editor.putString("first_name", first_name_);
        editor.putString("last_name", last_name_);
        editor.putString("pseudo", pseudo_);
        editor.putString("email", email_);
        editor.putString("id_user", id_user_);
        editor.putString("phone", phone_);

        editor.commit();
        api_key = apikey_;
        first_name = first_name_;
        last_name = last_name_;
        id_user_logged = id_user;
        pseudo = pseudo_;
        email = email_;
        phone = phone_;
    }

    public static String getIdUserLogged(){
        return id_user_logged;
    }

    public static String getUserfirst_name(Context context){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        first_name = settings.getString("first_name", null);
        return first_name;
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
