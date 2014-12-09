package com.github.vmarquet.bartendr;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ListView lv = (ListView)findViewById(R.id.listViewMenu);

        final String[] liste = {"Bière", "Cocktail", "Vin"};
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return liste.length;
            }

            @Override
            public String getItem(int i) {
                return liste[i];
            }

            @Override
            public long getItemId(int i) {
                return i;
            }  // seriously ?

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                String string = getItem(i);

                // we use the row_menu.xml layout file
                if(view == null) {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_menu, viewGroup, false);
                }

                // we set the text for each options in the ListView
                TextView textEdit = (TextView)view.findViewById(R.id.menu_option);
                textEdit.setText(string);

                return view;
            }
        });
    }
}
