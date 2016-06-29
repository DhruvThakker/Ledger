package cyberknight.android.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Parth on 29-06-2016.
 * CyberKnight apps
 */
public class MainActivityFragments extends Fragment {

    public MainActivityFragments(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_temp, container, false);
    }
}
