package com.bojan.animations;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void fade(View view){
        ImageView cloud = (ImageView) findViewById(R.id.cloud);

        cloud.animate().rotation(180f).setDuration(2000);           //rotacija za tooliko stupnjeva
        cloud.animate()
                .scaleX(0.5f)
                .scaleY(0.5f)
                .rotation(3600f)
                .translationYBy(500f)
                .setDuration(2000);

        /*cloud.animate().translationYBy(1000f).setDuration(2000);     //move by pixels along the vertical axis
        cloud.animate().translationXBy(1000f).setDuration(2000);     //move by pixels along the vertical axis
        //left right up down

        ImageView sephiroth = (ImageView) findViewById(R.id.sephiroth);
       cloud.animate().alpha(0f).setDuration(2000);    //animate the object so that you set the alpha(transparency) to 0(invisible)
                                                        // over the course of 2000 miliseconds
       sephiroth.animate().alpha(1f).setDuration(2000);        //animate the sephiroth image (object) so that you set the alpha to 1
                                                                // over the course of 2 seconds
        */

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ImageView cloud = (ImageView) findViewById(R.id.cloud);
        cloud.setTranslationX(-1000f);
        cloud.setTranslationY(-1000f);*/

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
