package com.github.vmarquet.bartendr;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class OrderActivity extends Activity {
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // we get the Order instance (singleton pattern)
        this.order = Order.getInstance();

        // we set the ListView
        ListView lv = (ListView)findViewById(R.id.listViewOrder);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return order.getNumberOfArticles();
            }

            @Override
            public String getItem(int i) {
                return order.getArticleAt(i).getArticleName();
            }

            @Override
            public long getItemId(int i) {
                return i;
            }  // seriously ?

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                String string = getItem(i);

                // we use the row_category.xml layout file
                if(view == null) {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_category, viewGroup, false);
                }

                // we set the text for each options in the ListView
                TextView textEdit = (TextView)view.findViewById(R.id.category_option);
                textEdit.setText(string);

                return view;
            }
        });
    }

    public void pay(View view) {
        Toast.makeText(getApplicationContext(), "Not available yet  : (", Toast.LENGTH_LONG).show();
    }

}
