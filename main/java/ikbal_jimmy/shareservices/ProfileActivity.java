package ikbal_jimmy.shareservices;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myContext = this;
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Authenticate.loadUserData(myContext);
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView pseudo = (TextView) findViewById(R.id.editPseudo);
        pseudo.setText(Authenticate.pseudo);

        TextView edit_name = (TextView) findViewById(R.id.editFname);
        edit_name.setText(Authenticate.getUserfirst_name(myContext));

        TextView edit_lastname = (TextView) findViewById(R.id.editLname);
        edit_lastname.setText(Authenticate.last_name);

        TextView edit_phone = (TextView) findViewById(R.id.editPhone);
        edit_phone.setText(Authenticate.phone);

        TextView editemail = (TextView) findViewById(R.id.editEmail);
        editemail.setText(Authenticate.email);


        findViewById(R.id.button_logout).
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Authenticate.logout(myContext);
                    Toast.makeText(myContext, "Bye!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
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
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(myContext, ConversationsActivity.class);
            myContext.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
