package com.example.darshmakani.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;


import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.EditText;

import android.widget.ProgressBar;

import com.example.darshmakani.newsapp.model.NewsItem;


import com.example.darshmakani.newsapp.utilities.NetworkUtils;
import com.example.darshmakani.newsapp.utilities.NewsAdapter;

import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

// Code by Darsh Makani


public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";

    /* private TextView newsTextView;
    private TextView newsUrlView;
    private ProgressBar progressBar;*/


    private RecyclerView mRecyclerView;

    private EditText mSearchBoxEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mSearchBoxEditText = (EditText) findViewById(R.id.search);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        loadNewsData();
    }
    private void loadNewsData() {
        new NewsData("").execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.newsmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id = item.getItemId();

        if (menu_id == R.id.search) {

            String s = mSearchBoxEditText.getText().toString();

            NewsData task = new NewsData(s);

            task.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class NewsData extends AsyncTask<URL, Void, ArrayList<NewsItem>> implements NewsAdapter.ItemClickListener {
        String query;
        ArrayList<NewsItem> data;

        NewsData(String s) {

            query = s;
        }
        //  set visibility of spinning progress bars
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {

            ArrayList<NewsItem> result = null;

            URL newsRequestURL = NetworkUtils.buildUrl();

            try {
                String json = NetworkUtils.getResponseFromHttpUrl(newsRequestURL);
                result = NetworkUtils.parseJSON(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> newsData) {
            this.data = newsData;
            super.onPostExecute(data);

            progressBar.setVisibility(View.INVISIBLE);

            if (data != null) {
                NewsAdapter adapter = new NewsAdapter(data, this);
                mRecyclerView.setAdapter(adapter);
            }
        }
        @Override
        public void onListItemClick(int clickedItemIndex) {

            openWebPage(data.get(clickedItemIndex).getUrl());
        }

        public void openWebPage(String url) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
