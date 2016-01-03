package com.bojan.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    AudioManager audioManager;

    //for playing with colors
    static final int[] COLORS = {0xff0000, 0x00ff00};
    int currentColor = 0;
    //RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

    public void playMusic(View view){
        mPlayer.start();
        //273 162 ms aka 4:33 mins
        //drop comes at 44 seconds aka 44 000 ms
        //int duration = mPlayer.getDuration();
        //System.out.println(mPlayer.getCurrentPosition());           //prints out milliseconds

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                ImageView image1 = (ImageView) findViewById(R.id.image1);
                ImageView image2 = (ImageView) findViewById(R.id.image2);

                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.VISIBLE);

                image1.animate().rotation(7200f).scaleX(0.2f).scaleY(-0.2f).translationYBy(100).translationXBy(100).setDuration(15000);
                image2.animate().rotation(7200f).scaleX(-0.2f).scaleY(0.2f).translationYBy(-100).translationXBy(-100).setDuration(15000);

                VideoView videoView = (VideoView) findViewById(R.id.videoView);
                videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.dance);
                videoView.setVisibility(View.VISIBLE);
                videoView.start();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                    }
                });

            }
        }, 8500); //time in millis

        /*new CountDownTimer(8500, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                //images part

            }
        }.start();*/
    }

    public void pauseMusic(View view){
        mPlayer.pause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer = MediaPlayer.create(this, R.raw.dnb);      //create a media player at the start of the app
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);       //use audiomanager to get information about the volume
        final int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  //generic stream for playing sound music in our app
        final int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);
        //set the max and current volume to the seekbar end
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curVolume);

        volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            //we need to implement methods for other two user use cases even though we may not be using them
            //when user touches, when user moves, and when user releases the seekbar
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //when the user stopped
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //when the user started
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //we are adding our own code to a method that already exists
                //thats what @Override is for, when you add a code to a method that already exists
                //because when the app runs some methods already exists and do some work, we just expand them

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);       //set the volume
            }
        });

        final SeekBar scrubber = (SeekBar) findViewById(R.id.scrubber);        //scrubber generally means something than you can scroll through
        int duration = mPlayer.getDuration();
        scrubber.setMax(duration);

        new Timer().scheduleAtFixedRate(new TimerTask() {               //schedule a certain taks to be done at a fixed schedule, in this case every second
            @Override
            public void run() {                                     //method runnable
                scrubber.setProgress(mPlayer.getCurrentPosition());
            }
        }, 0, 1000);                    //number of seconds before the timer runs for the first time, and number of ms between successive pause of timer
                                            //run the code in run() immediately and do that every second afterwards


        scrubber.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) mPlayer.seekTo(progress);           //seek to a particular position
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mPlayer.pause();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayer.start();
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
