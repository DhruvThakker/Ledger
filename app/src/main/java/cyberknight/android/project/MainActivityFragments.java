package cyberknight.android.project;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Parth on 29-06-2016.
 * CyberKnight apps
 */
public class MainActivityFragments extends Fragment {

    public MainActivityFragments(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ListView records = (ListView) view.findViewById(R.id.recordList);
        ArrayList<RecordItem> list = new ArrayList<>();
        list.add(new RecordItem(0,"Food","Cheese Burger","34.00","cash"));
        list.add(new RecordItem(0,"Food","Veg Pulav","42.00","cash"));
        list.add(new RecordItem(0,"Transport","Gandhinagar to Baroada","110.00","cash"));
        list.add(new RecordItem(0,"Clothing","Jeans pair","500.00","card"));
        list.add(new RecordItem(0,"Food","Cheese Burger","34.00","cash"));
        list.add(new RecordItem(0,"Food","Veg Pulav","42.00","cash"));
        list.add(new RecordItem(0,"Transport","Gandhinagar to Baroada","110.00","cash"));
        list.add(new RecordItem(0,"Clothing","Jeans pair","500.00","card"));
        list.add(new RecordItem(0,"Food","Cheese Burger","34.00","cash"));
        list.add(new RecordItem(0,"Food","Veg Pulav","42.00","cash"));
        list.add(new RecordItem(0,"Transport","Gandhinagar to Baroada","110.00","cash"));
        list.add(new RecordItem(0,"Clothing","Jeans pair","500.00","card"));
        list.add(new RecordItem(0,"Food","Cheese Burger","34.00","cash"));
        list.add(new RecordItem(0,"Food","Veg Pulav","42.00","cash"));
        list.add(new RecordItem(0,"Transport","Gandhinagar to Baroada","110.00","cash"));
        list.add(new RecordItem(0,"Clothing","Jeans pair","500.00","card"));
        list.add(new RecordItem(0,"Food","Cheese Burger","34.00","cash"));
        list.add(new RecordItem(0,"Food","Veg Pulav","42.00","cash"));
        list.add(new RecordItem(0,"Transport","Gandhinagar to Baroada","110.00","cash"));
        list.add(new RecordItem(0,"","","",""));
        RecordAdapter adapter = new RecordAdapter(getContext(),list);
        records.setAdapter(adapter);
        return view;
    }

    class RecordItem{
        int icon;
        String category, note, money, type;

        public RecordItem(int icon, String category, String note, String money, String type){
            this.icon = icon;
            this.category = category;
            this.note = note;
            this.money = money;
            this.type = type;
        }
    }

    class RecordAdapter extends BaseAdapter {

        Context fContext;
        ArrayList<RecordItem> fRecordItems;

        public RecordAdapter(Context fContext, ArrayList fRecordItems){
            this.fContext = fContext;
            this.fRecordItems = fRecordItems;
        }

        @Override
        public int getCount() {
            return fRecordItems.size();
        }

        @Override
        public Object getItem(int position) {
            return fRecordItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater) fContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.record_bar,null);
            }
            else{
                view = convertView;
            }
            TextView text = (TextView) view.findViewById(R.id.category);
            TextView text2 = (TextView) view.findViewById(R.id.note);
            TextView text3 = (TextView) view.findViewById(R.id.money);
            TextView text4 = (TextView) view.findViewById(R.id.type);
            ImageView iconView = (ImageView) view.findViewById(R.id.recIcon);
            text.setText(fRecordItems.get(position).category);
            text2.setText(fRecordItems.get(position).note);
            text3.setText(fRecordItems.get(position).money);
            text4.setText(fRecordItems.get(position).type);
            iconView.setImageResource(fRecordItems.get(position).icon);

            return view;
        }
    }
}
