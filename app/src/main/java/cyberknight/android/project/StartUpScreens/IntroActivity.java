package cyberknight.android.project.StartUpScreens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;

import cyberknight.android.project.HomeScreen.MainActivity;
import cyberknight.android.project.R;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add your slide's fragments here.
        // MyAppIntro will automatically generate the dots indicator and buttons.
        Bundle bundle1=new Bundle();
        bundle1.putString("HEAD","FIRST");
        bundle1.putString("DESCRIPTION","MY name is Dhruv");
        bundle1.putInt("DRAWABLE",R.drawable.screenshot20160729172401);
        Bundle bundle2=new Bundle();
        bundle2.putString("HEAD","SECOND");
        bundle2.putString("DESCRIPTION","MY name is DhruvT");
        bundle2.putInt("DRAWABLE", R.drawable.screenshot20160729172406);
        Bundle bundle3=new Bundle();
        bundle3.putString("HEAD","THIRD");
        bundle3.putString("DESCRIPTION","MY name is DhruvV");
        bundle3.putInt("DRAWABLE",R.drawable.screenshot20160729194105);
        Bundle bundle4=new Bundle();
        bundle4.putString("HEAD","FOURTH");
        bundle4.putString("DESCRIPTION","MY name is DhruvX");
        bundle4.putInt("DRAWABLE",R.drawable.screenshot20160729194110);

        IntroFragment alpha=new IntroFragment();
        alpha.setArguments(bundle1);
        IntroFragment beta=new IntroFragment();
        beta.setArguments(bundle2);
        IntroFragment gamma=new IntroFragment();
        gamma.setArguments(bundle3);
        IntroFragment delta=new IntroFragment();
        delta.setArguments(bundle4);
        addSlide(alpha);
        addSlide(beta);
        addSlide(gamma);
        addSlide(delta);

      /*  // Ask Camera permission in the second slide
        askForPermissions(new String[]{android.Manifest.permission.CAMERA}, 2);

        // Ask Storage permission in the third slide
        askForPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);

        // Ask Location permission in the fifth slide
        askForPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 5);*/
        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. MyAppIntro will do the rest.
        //addSlide(AppIntroFragment.newInstance("Hello", "World",R.drawable.screenshot20160729172401 , getResources().getColor(R.color.colorPrimaryDark1)));

        //addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));
        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressIndicator();
        setProgressButtonEnabled(true);
        showDoneButton(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
//        setVibrate(true);
//.        setVibrateIntensity(50);
    }
    private void loadMainActivity(){
        Log.d("Done","MainActivity");
        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        loadMainActivity();

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        loadMainActivity();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
        //setFadeAnimation();
        setFlowAnimation();
        //setDepthAnimation();
    }
}
