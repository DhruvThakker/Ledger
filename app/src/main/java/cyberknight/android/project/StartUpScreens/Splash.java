package cyberknight.android.project.StartUpScreens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import cyberknight.android.project.AccountManagement.SettingsActivity;
import cyberknight.android.project.HomeScreen.MainActivity;
import cyberknight.android.project.R;

public class Splash extends Activity {

    private static int SPLASH_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(SettingsActivity.SETTINGS_FILE, Context.MODE_PRIVATE);

                if(sharedPreferences.getBoolean("passwordSet",false)) {
                    startActivity(new Intent(Splash.this,Password.class));
                    Log.d("SPLASH","password set");
                }
                else{
                    startActivity(new Intent(Splash.this,IntroActivity.class));
                    Log.d("SPLASH","Not Set");
                }
                finish();
            }
        },SPLASH_TIMEOUT);
    }
}
