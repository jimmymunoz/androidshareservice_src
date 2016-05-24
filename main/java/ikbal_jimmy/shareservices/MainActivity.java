package ikbal_jimmy.shareservices;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fragments.ConversationFragment;
import fragments.SearchFragment;
import fragments.ServicesFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String DEBUG_TAG = "HttpExample";//http://46.101.40.23/shareserviceserver/v1/register
    private Context myContext;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        myContext = this;
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setTitle("R");

        Authenticate.loadUserData(myContext);
        updateUserData();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



      /*  String api_key = Authenticate.getApiKey();
        if (api_key == null) {
            LinearLayout message= (LinearLayout) findViewById(R.id.tab1);
            message.();}

        }*/
        /*
        LinearLayout tab1 = (LinearLayout)findViewById(R.id.tab1);
        LinearLayout tab2 = (LinearLayout)findViewById(R.id.tab2);
        LinearLayout tab3 = (LinearLayout)findViewById(R.id.tab3);
        LinearLayout tab4 = (LinearLayout)findViewById(R.id.tab4);
        LinearLayout tab5 = (LinearLayout)findViewById(R.id.tab5);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);
        */

        if (getIntent().hasExtra("position")) {
            selectItem(getIntent().getExtras().getInt("position"));
        } else {
            selectItem(2);
        }




        //textView = (TextView) findViewById(R.id.myText);

        //Button btnOk = (Button) findViewById(R.id.btn_test_rest);
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickHandler();

            }
        };
        //btnOk.setOnClickListener(oclBtnOk);
        //((LinearLayout) findViewById(R.id.tab1))
        LinearLayout Message = (LinearLayout) findViewById(R.id.tab1);

        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ConversationFragment();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });

        /*
        *  TestFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new RegisterFragment();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        *
        * */
        LinearLayout Acount = (LinearLayout) findViewById(R.id.tab2);
        Acount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //intentDisplayContacts.putExtra("display_from", "activity");

            String api_key = Authenticate.getApiKey();
            if (api_key != null) {
                /*si l'utilisateur  est connecté */
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

            } else {
                /*sinon il dois  se  connecté */
                startActivity(new Intent(getApplicationContext(), UnidentifiedActivity.class));

            }
            }
        });

        // layout.setOnClickListener(new View.OnClickListener()

        LinearLayout addService = (LinearLayout) findViewById(R.id.tab3);
        // layout.setOnClickListener(new View.OnClickListener()
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //intentDisplayContacts.putExtra("display_from", "activity");
            String api_key = Authenticate.getApiKey();
            if (api_key != null) {

                startActivity(new Intent(getApplicationContext(), AddSerciceActivity.class));

            } else {
                startActivity(new Intent(getApplicationContext(), UnidentifiedActivity.class));
            }
            }
        });

        LinearLayout searcheservice = (LinearLayout) findViewById(R.id.searcheservice);
        searcheservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment fragment = new SearchFragment();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });


        startService(new Intent(myContext, NotificationService.class));
        
        //test
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        new HttpRequestTask().execute();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ikbal_jimmy.shareservices/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    public void updateUserData(){
        /*
        if (Authenticate.getApiKey() != null) {
            TextView textView = (TextView) findViewById(R.id.user_name_account);
            textView.setText(Authenticate.pseudo);

        } else {
            TextView textView = (TextView) findViewById(R.id.user_name_account);
            textView.setText("Non Conecté");
        }
        */
    }


    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    public void myClickHandler() {
        // Gets the URL from the UI's text field.
        String task = "";
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttpRequestTask().execute(task);
        } else {
            Toast.makeText(myContext, "No network connection available.", Toast.LENGTH_LONG).show();
            //textView.setText("No network connection available.");
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ikbal_jimmy.shareservices/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            ServiceModel serviceModel = new ServiceModel();
            return serviceModel.getServiceList();
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String responseUrl) {
            //Toast.makeText(myContext, "Response Server OK :)", Toast.LENGTH_LONG).show();
            /*
            textView = (TextView) findViewById(R.id.myText);
            textView.setText(responseUrl);
            */

            ArrayList<ServiceShare> ListService = new ArrayList<ServiceShare>();
            try {
                JSONObject jsonRootObject = new JSONObject(responseUrl);
                JSONArray jsonArray = jsonRootObject.optJSONArray("services");
                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String id_service = jsonObject.optString("id_service").toString();
                    String active = jsonObject.optString("active").toString();
                    String titre = jsonObject.optString("titre").toString();
                    String description = jsonObject.optString("description").toString();
                    String category = jsonObject.optString("id_category").toString();
                    String adresse = jsonObject.optString("adress").toString();
                    String prix = jsonObject.optString("price").toString();

                    ServiceShare objService = new ServiceShare(id_service, titre, active,adresse, description,category,prix);
                    ListService.add(objService);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<String> serviceList = new ArrayList<String>();
            for (ServiceShare objServiceShare : ListService) {
                serviceList.add(objServiceShare.getTitre());
            }
            /*
            ListAdapter adapter = new ArrayAdapter(myContext, android.R.layout.simple_list_item_1, serviceList);
            ListView vue = (ListView) findViewById(R.id.listViewServices);
            vue.setAdapter(adapter);
            */
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Authenticate.logout(myContext);
            Toast.makeText(myContext, "Bye", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    private void selectItem(int position) {

        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                //fragment = new WebviewFragment();
                break;
            case 1:
                //fragment = new AccountFragment();
                break;
            case 2:
                fragment = new ServicesFragment();
                break;
            case 3:
                //fragment = new MyJobsFragment();
                break;
            case 4:
                //fragment = new WebviewFragment();

                break;

            default:
                break;
        }
        Bundle args = new Bundle();
        fragment.setArguments(args);
        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        /*
        switch (v.getId()) {
            case R.id.tab1:
                selectItem(0);
                break;
            case R.id.tab2:
                selectItem(1);
                break;
            case R.id.tab3:
                selectItem(2);
                break;
            case R.id.tab4:
                selectItem(3);
                break;
            case R.id.tab5:
                selectItem(4);
                break;
            default:
                break;
        }
        */
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Authenticate.logout(myContext);
            Toast.makeText(myContext, "Bye", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(myContext, ConversationsActivity.class);
            myContext.startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(myContext, UserOrdersActivity.class);
            myContext.startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(myContext, UserServiceListActivity.class);
            myContext.startActivity(intent);
        } else if (id == R.id.nav_validate_codes) {
            Intent intent = new Intent(myContext, ValidateCodeActivity.class);
            myContext.startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Authenticate.logout(myContext);
            Toast.makeText(myContext, "Bye!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
