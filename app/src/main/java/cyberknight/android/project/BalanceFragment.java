package cyberknight.android.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Parth on 30-06-2016.
 * CyberKnight apps
 */
public class BalanceFragment extends Fragment {

    public BalanceFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        return view;
    }
}