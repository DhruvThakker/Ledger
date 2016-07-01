package cyberknight.android.project.AccountManagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cyberknight.android.project.R;

/**
 * Created by umang on 1/7/16.
 */
public class AccountFragment extends Fragment {

    public AccountFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);

        return view;
    }
}
