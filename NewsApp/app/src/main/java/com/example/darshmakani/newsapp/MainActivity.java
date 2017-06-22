package com.example.darshmakani.newsapp;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.darshmakani.newsapp.utilities.NetworkUtils;

import java.net.URL;
// Code by Darsh Makani

public class MainActivity extends AppCompatActivity {

    private TextView newsTextView;
    private TextView newsUrlView;
    private ProgressBar progressBar;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* mSearchButton = (Button) findViewById(R.id.news_button);
       */
        newsTextView = (TextView) findViewById(R.id.news_api_data);
        newsUrlView = (TextView) findViewById(R.id.news_url);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //loadNewsData();
    }

   // Implemented a search menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.newsmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id = item.getItemId();

        if (menu_id == R.id.search) {
            newsUrlView.setText("");
            loadNewsData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadNewsData() {
        URL NewsSearchUrl = NetworkUtils.buildUrl();
        newsUrlView.setText(NewsSearchUrl.toString());
        new NewsData().execute();
    }


   // Extended and implemented a subclass naming NewsData of AsyncTask to handle the http request

    public class NewsData extends AsyncTask<Void, Void, String> {


        //  set visibility of spinning progress bars
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            URL newsRequestURL = NetworkUtils.buildUrl();
            try {
                return NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String newsData) {
            progressBar.setVisibility(View.INVISIBLE);
            if (newsData != null) {
                newsTextView.setText(newsData);
            }
        }
    }

}
