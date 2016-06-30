package cyberknight.android.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Parth on 29-06-2016.
 * CyberKnight apps
 */
public class MainActivityFragments extends Fragment {

    public MainActivityFragments(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_temp, container, false);
        TextView text = (TextView) view.findViewById(R.id.category);
        TextView text2 = (TextView) view.findViewById(R.id.note);
        TextView text3 = (TextView) view.findViewById(R.id.money);
        TextView text4 = (TextView) view.findViewById(R.id.type);
        text.setText("Food");
        text2.setText("Cheese Burger");
        text3.setText("34");
        text4.setText("Cash");
        return view;
    }
}
