package com.github.vmarquet.bartendr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ArticleActivity extends CustomActionBarActivity {
    // the article attributes
    private Article article;

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

        // we create an Article object to store the article's data
        this.article = new Article(articleID);
    }

    // when the "Ajouter à la commande" button is clicked, we add it to the Order class
    public void orderArticle(View view) {
        if (this.article == null) {
            Toast.makeText(getApplicationContext(), "Attendez que les données soient chargées SVP.", Toast.LENGTH_LONG).show();
            return;
        }
        Order order = Order.getInstance();
        order.add(this.article);
        Toast.makeText(getApplicationContext(), "Article ajouté à la commande.", Toast.LENGTH_LONG).show();
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
            return null;
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
                        article.setArticleName(reader.nextString());
                    else if (name.equals("description"))
                        article.setDescription(reader.nextString());
                    else if (name.equals("price"))
                        article.setPrice(reader.nextDouble());
                    else if (name.equals("picture")) {
                        article.setPicture(reader.nextString());
                        // we launch the download process of the image file
                        new DownloadImageTask().execute(article.getPicture());
                    }
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
            textViewArticleName.setText(article.getArticleName());

            TextView textViewArticleDescription = (TextView)findViewById(R.id.textViewArticleDescription);
            textViewArticleDescription.setText(article.getDescription());

            TextView textViewArticlePrice = (TextView)findViewById(R.id.textViewArticlePrice);
            textViewArticlePrice.setText(String.format("Prix: %.2f €", article.getPrice()));

            // possible error message
            if (message != null)
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            return;
        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, String> {
        private Bitmap image = null;

        // we launch the download process of the image file
        @Override
        protected String doInBackground(String... urls) {
            try {
                downloadImage(new URL(urls[0]));
            } catch (IOException ex) {
                return "Unable to download image or invalid image.";
            }
            return null;
        }

        // the function that actually downloads the image
        private void downloadImage(URL url) throws IOException {
            InputStream inputStream = null;
            try {
                inputStream = url.openConnection().getInputStream();
                this.image = BitmapFactory.decodeStream(inputStream);
            }
            finally {
                if (inputStream != null)
                    inputStream.close();
            }
        }

        // once we've downloaded the image, we update the activity display
        @Override
        protected void onPostExecute(String message) {
            // we display the image
            ImageView imageView = (ImageView)findViewById(R.id.imageViewArticle);
            imageView.setImageBitmap(this.image);

            // possible error message
            if (message != null)
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            return;
        }
    }
}


