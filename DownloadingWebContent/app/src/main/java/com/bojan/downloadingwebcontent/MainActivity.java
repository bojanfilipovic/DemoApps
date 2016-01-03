package com.bojan.downloadingwebcontent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {
        //any code that takes time should be run on a different thread than the onCreate one
        //3 = type of var that is returned by DownloadTask
        //2 = name of method we may or may not use to show the progress of this task
        //1 = type of var we send to class to instruct, in this case URL
        //Async allows us to run that code on another thread
        @Override
        protected String doInBackground(String... urls) {     //protected = access from anywhere in the package/app
                                                                //String... = similar to array, it will contain a bunch of strings; var args
            String result = "";
            URL url;

            HttpURLConnection urlConnection = null;         //kinda opening up a browser here and we will use it to fetch the content

            try{

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();        //stream to hold the input of data as it comes in
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1){
                    char current = (char)data;          //get the current character from url stream
                    result += current;
                    data = reader.read();               //loop in the contents of the url
                }

                return result;

            }catch(Exception e){
                e.printStackTrace();
                return "Failed";
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {        //main thread, UI thread
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        try {           //because its happening on a different thread
            result = task.execute("https://www.ecowebhosting.co.uk/").get();
        } catch (InterruptedException e) {
            e.printStackTrace();    //java method, prints info about the error/exception that ocurred
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i("Contents of URL:", result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
