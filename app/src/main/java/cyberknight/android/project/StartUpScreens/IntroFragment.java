package cyberknight.android.project.StartUpScreens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cyberknight.android.project.R;

/**
 * Created by Parth on 31-07-2016.
 * CyberKnight apps
 */
public class IntroFragment extends Fragment{
    private TextView head,description;
    private ImageView screenshot;
    String text1,text2;

    int imgsrc;
    public IntroFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_intro, container, false);
        Bundle bundle=this.getArguments();
        text1=bundle.getString("HEAD");
        text2=bundle.getString("DESCRIPTION");
        imgsrc=bundle.getInt("DRAWABLE");
        head=(TextView)rootView.findViewById(R.id.tvSlideIntro);
        description=(TextView)rootView.findViewById(R.id.tvSlideDescription);
        screenshot=(ImageView) rootView.findViewById(R.id.ivScreenshot);

        head.setText(text1);
        description.setText(text2);
        screenshot.setImageResource(imgsrc);
        // Inflate the layout for this fragment
        return rootView;
    }
}
