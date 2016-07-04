package cyberknight.android.project.AccountManagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cyberknight.android.project.DatabaseAndReaders.DbHelper;
import cyberknight.android.project.DatabaseAndReaders.JsonReader;
import cyberknight.android.project.HomeScreen.MainActivity;
import cyberknight.android.project.R;

/**
 * Created by umang on 1/7/16.
 */
public class AccountFragment extends Fragment {

    DbHelper dbHelper;
    private double credits;
    private double debt;

    public AccountFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);

        TextView textCredits = (TextView) view.findViewById(R.id.tvCredits);
        TextView textDebt = (TextView) view.findViewById(R.id.tvDebt);
        TextView textBalance = (TextView) view.findViewById(R.id.tvBalance);
        TextView textBank = (TextView) view.findViewById(R.id.tvBankAccount);
        TextView textCard = (TextView) view.findViewById(R.id.tvCard);
        TextView textCash = (TextView) view.findViewById(R.id.tvCash);
        LinearLayout balanceBack = (LinearLayout) view.findViewById(R.id.balanceBack);

        dbHelper = new DbHelper(MainActivity.applicationContext);

        double cash = dbHelper.getAmountByName("Cash");
        double card = dbHelper.getAmountByName("Card");
        double bank = dbHelper.getAmountByName("Bank");

        textCash.setText(cash +"");
        textCard.setText(card +"");
        textBank.setText(bank +"");

        if(cash >0)
            credits+= cash;
        else
            debt+= cash;

        if(card >0)
            credits+= card;
        else
            debt+= card;

        if(bank >0)
            credits+= bank;
        else
            debt+= bank;

        double balance = credits + debt;

        textCredits.setText(credits+"");
        textDebt.setText(debt+"");
        textBalance.setText(balance +"");

        if(balance>0)   balanceBack.setBackgroundColor(getResources().getColor(R.color.green_400));
        else if(balance<0)  balanceBack.setBackgroundColor(getResources().getColor(R.color.red_400));

        return view;
    }
}
