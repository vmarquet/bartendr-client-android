package com.github.vmarquet.bartendr;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MenuActivity extends Activity {
    private ArrayList<String> menuCategories = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // we download the JSON file, and we update the Activity display
        new DownloadMenuTask().execute("http://v-marquet.bitbucket.org/bartendr/menu.json");

        // we set the ListView
        ListView lv = (ListView)findViewById(R.id.listViewMenu);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return menuCategories.size();
            }

            @Override
            public String getItem(int i) {
                return menuCategories.get(i);
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

        // when a category is selected, we switch to a new activity
        // to display the articles in that category
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parentView, View childView, int position, long id)
            {
                Intent intent = new Intent(MenuActivity.this, CategoryActivity.class);
                // we use a Bundle to give a parameter to the CategoryActivity
                // cf http://stackoverflow.com/questions/3913592
                Bundle bundle = new Bundle();
                bundle.putInt("categoryID", position);  // not safe if categories doesn't start at 0, etc...
                bundle.putString("categoryName", menuCategories.get(position));  // not safe, same as above
                // TODO: use the real category ID instead of "position" which is the index in the menuCategories ArrayList
                intent.putExtras(bundle);
                startActivity(intent);
            }

            public void onNothingSelected(AdapterView parentView) {}
        });

    }


    private class DownloadMenuTask extends AsyncTask<String, Void, String> {
        // we launch the download process of the JSON file
        @Override
        protected String doInBackground(String... urls) {
            try {
                downloadMenuJSON(new URL(urls[0]));
            } catch (IOException ex) {
                return "Unable to download JSON file or invalid JSON file.";
            }

            return "JSON file downloaded.";
        }

        // the function that actually downloads the file
        private void downloadMenuJSON(URL url) throws IOException {
            InputStream inputStream = null;

            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int status = connection.getResponseCode();
                inputStream = connection.getInputStream();

                // we convert input stream (JSON file) to an array of strings
                readJSON(inputStream);
            }
            finally {
                if (inputStream != null)
                    inputStream.close();
            }
        }

        // we read the JSON file and convert it to an array of Strings
        private void readJSON(InputStream inputStream) throws IOException {
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            menuCategories.clear();
            try {
                // the JSON begins with an array
                reader.beginArray();
                while (reader.hasNext()) {
                    // we analyse the objects in the array
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String name = reader.nextName();
                        if (name.equals("name"))
                            menuCategories.add(reader.nextString());
                        else
                            reader.skipValue();
                    }
                    reader.endObject();
                }
                reader.endArray();
            } finally {
                reader.close();
            }
            return;
        }

        // once we've downloaded the JSON file, we update the activity display
        @Override
        protected void onPostExecute(String message) {
            // we update the ListView display
            ListView lv = (ListView)findViewById(R.id.listViewMenu);
            BaseAdapter adapter = (BaseAdapter) lv.getAdapter();
            adapter.notifyDataSetChanged();

            // debug message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            return;
        }
    }
}
