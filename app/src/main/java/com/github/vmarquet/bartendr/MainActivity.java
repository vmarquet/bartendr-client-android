package com.github.vmarquet.bartendr;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.AsyncTask;
import java.lang.Thread;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    // when button "Choisir sa boisson" is clicked, we switch to a new activity to display menu
    public void seeMenu(View view) {
        Intent i = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(i);
    }

    // we display a message when button "Payer" is clicked
	public void pay(View view) {
		Toast.makeText(getApplicationContext(), "Not available yet : (", 3).show();
        new AsyncTaskTest().execute();
	}

	// AsyncTask test
	private class AsyncTaskTest extends AsyncTask<Integer, Integer, Long> {
		@Override
		protected Long doInBackground(Integer... a) {
			int count = 3;
			for (int i = 0; i < count; i++) {
				publishProgress(i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.interrupted();
				}
				// Escape early if cancel() is called
				if (isCancelled()) break;
			}
			return 0L;
		}

		@Override
		protected void onProgressUpdate(Integer... i) {
			Toast.makeText(getApplicationContext(), "test AsyncTask " + Integer.toString(i[0]), 1).show();
		}

		@Override
		protected void onPostExecute(Long result) {
			Toast.makeText(getApplicationContext(), "test AsyncTask finished", 1).show();
		}
	}
	// end AsyncTask test
}
