package com.lifeline;

import android.os.AsyncTask;

import com.lifeline.helpers.LifeLine;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by cory on 8/16/2014.
 */
public class DAL {
    private String rootUrl = "http://lifeline.corywestropp.com/data/";

    public boolean PostToAPI(String objectName, String jsonData) {
        return PostToAPI(objectName, jsonData, false);
    }

    public boolean PostToAPI(String objectName, String jsonData, boolean counter) {
        String url = rootUrl + objectName;

        if (counter) {
            url = url + "/counter";
        }

        String postData = jsonData;
        AsyncTask task = new PostTask().execute(url, postData);
        try {
            task.get(10000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public JSONObject GetFromAPI(String objectName) {
        String url = rootUrl + objectName + "/single/today";
        AsyncTask task = new GetTask().execute(url);
        try {
            Object result = task.get(100000, TimeUnit.MILLISECONDS);
                return new JSONObject(result.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

//    public JSONArray GetCollectionFromAPI(String objectName) {
//        String url = rootUrl + objectName + "/single/today";
//        AsyncTask task = new GetTask().execute(url);
//        try {
//            Object result = task.get(100000, TimeUnit.MILLISECONDS);
//            return new JSONArray(result.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

        public class GetTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try {
                HttpGet httpGet = new HttpGet(params[0]);
                httpGet.setHeader("Accept", "application/json");
                httpGet.setHeader("Content-type", "application/json");
                HttpResponse response = new DefaultHttpClient().execute(httpGet);
                result = LifeLine.convertStreamToString(response.getEntity().getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // do something
            } else {
                // error occured
            }
        }
    }

    public class PostTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                HttpPost httpPost = new HttpPost(params[0]);
                httpPost.setEntity(new StringEntity(params[1]));
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                HttpResponse response = new DefaultHttpClient().execute(httpPost);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (result != null) {
                // do something
            } else {
                // error occured
            }
        }
    }
}