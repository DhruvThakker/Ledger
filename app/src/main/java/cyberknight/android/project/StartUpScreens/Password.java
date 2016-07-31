package cyberknight.android.project.StartUpScreens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cyberknight.android.project.AccountManagement.SettingsActivity;
import cyberknight.android.project.HomeScreen.MainActivity;
import cyberknight.android.project.R;

public class Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        final EditText password = (EditText) findViewById(R.id.password);
        Button done = (Button) findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SettingsActivity.SETTINGS_FILE, Context.MODE_PRIVATE);
                if(sharedPreferences.getString("password","").equals(password.getText().toString())){
                    startActivity(new Intent(Password.this, MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            }
        });
    }
}
