package Config;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;

public class CommonFunction {
	Activity activity;
	public SharedPreferences settings;
	public CommonFunction(Activity act){
		activity = act; 
		settings = act.getSharedPreferences(ConstValue.MAIN_PREF, 0);
	}
    public void logOut(){
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
    	 
        // Setting Dialog Title
        alertDialog.setTitle("Log Out...");
 
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want Logut?");
 
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);
 
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
 
            // Write your code here to invoke YES event
            	/*
                settings.edit().clear().commit();
            	Intent intent = new Intent(activity,LoginActivity.class);
            	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	activity.startActivity(intent);
            	*/
            	//activity.finish();            	
            }
        });
 
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // Write your code here to invoke NO event
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }
}
