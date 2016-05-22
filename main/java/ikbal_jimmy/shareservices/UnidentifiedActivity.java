package ikbal_jimmy.shareservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class UnidentifiedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noidentifi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button sign = (Button) findViewById(R.id.sign_in);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intentDisplayContacts.putExtra("display_from", "activity");
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });



        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intentDisplayContacts.putExtra("display_from", "activity");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        // l
    }

}
