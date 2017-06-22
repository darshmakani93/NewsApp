package com.example.darshmakani.newsapp.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Drash Makani on 6/18/2017.
 */


// Created a new class called NetworkUtils & Defined the appropriate base_url and query_parameter constants.
public class NetworkUtils {

    private static final String BASE_URL = "https://newsapi.org/v1/articles";

    private final static String src = "the-next-web";
    private final static String sort = "latest";
    // put key here
    private final static String key = " ";


    final static String SOURCE_PARAM = "source";
    final static String SORT_PARAM = "sortBy";
    final static String APIKEY_PARAM = "apiKey";

   // Created a static method in NetworkUtils that uses Uri.Builder to build the appropriate url

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAM, src)
                .appendQueryParameter(SORT_PARAM, sort)
                .appendQueryParameter(APIKEY_PARAM, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }



    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }




}
