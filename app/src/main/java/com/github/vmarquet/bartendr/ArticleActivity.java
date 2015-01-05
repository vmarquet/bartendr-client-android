package com.github.vmarquet.bartendr;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ArticleActivity extends Activity {
    // the article attributes
    private String articleName;
    private String articleDescription;
    private double articlePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        // we get the article ID
        Bundle bundle = getIntent().getExtras();
        int articleID = bundle.getInt("articleID");

        // we download the JSON file, and we update the Activity display
        new DownloadMenuTask().execute("http://v-marquet.bitbucket.org/bartendr/articles/"
                + Integer.toString(articleID) + ".json");
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
            try {
                // the JSON begins with an object (actually, it is only an object)
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("name"))
                        articleName = reader.nextString();
                    else if (name.equals("description"))
                        articleDescription = reader.nextString();
                    else if (name.equals("price"))
                        articlePrice = reader.nextDouble();
                    else
                        reader.skipValue();
                }
                reader.endObject();
            } finally {
                reader.close();
            }
            return;
        }

        // once we've downloaded the JSON file, we update the activity display
        @Override
        protected void onPostExecute(String message) {
            // we update the ListView display
            TextView textViewArticleName = (TextView)findViewById(R.id.textViewArticleName);
            textViewArticleName.setText(articleName);

            TextView textViewArticleDescription = (TextView)findViewById(R.id.textViewArticleDescription);
            textViewArticleDescription.setText(articleDescription);

            TextView textViewArticlePrice = (TextView)findViewById(R.id.textViewArticlePrice);
            textViewArticlePrice.setText("Prix: " + Double.toString(articlePrice) + " €");

            // debug message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            return;
        }
    }
}
