package Config;

import org.json.JSONObject;

import java.util.HashMap;

public class ConstValue {

	public static String GOOGLE_API_KEY = "AIzaSyABopnV4i4hKftdEKfTaMgOWThp9uVx12s"; // Replce this KEY with yours
	
	public static String SITE_URL = "http://46.101.40.23/shareserviceserver"; //Replce this URL with your site URL
	public static String JSON_SERVICES=SITE_URL+"/v1/category_services";
	public static String JSON_ADD_COMMENT = SITE_URL + "/v1/category_services";
	public static String JSON_CANCLE_JOB = SITE_URL + "/v1/category_services";
	public static String JSON_COMMENTS = SITE_URL + "/v1/category_services";
	public static String JSON_JOBS = SITE_URL + "/v1/category_services";
	public static String JSON_LOGIN = SITE_URL + "/v1/category_services";
	public static String JSON_PAGE = SITE_URL + "/v1/category_services";
	public static String JSON_POST_JOB = SITE_URL + "/v1/category_services";
	public static String JSON_PRICES = SITE_URL + "/v1/category_services";
	public static String JSON_PROS = SITE_URL + "/v1/category_services";
	public static String JSON_SUB_SERVICES = SITE_URL + "/v1/category_services";
	public static String JSON_UPDATE_JOB = SITE_URL + "/v1/category_services";
	public static String JSON_VISITOR = SITE_URL + "/v1/category_services";
	
	
	public static String IMAGE_PATH = SITE_URL+"/static/userfiles/contents/big/";
	public static String WEB_SERVICE_URL = "http://46.101.40.23/shareserviceserver/v1/";

	public static String GCM_SENDER_ID = "720391900040";  // Replace this ID with Yours
	
	public static String MAIN_PREF = "ServPro";
	public static String PREFS_MAIN_CAT = "prefs_main_category";
	
	public static HashMap<String, String> selected_service; 
	public static HashMap<String, String> selected_city;
	
	public static HashMap<String, String> selected_job;
	public static JSONObject selected_clinic;

	public static final String IMAGE_DIRECTORY_NAME = "ServPro";
	

	
}
