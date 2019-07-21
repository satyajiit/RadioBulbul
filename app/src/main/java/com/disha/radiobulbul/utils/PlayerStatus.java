package com.disha.radiobulbul.utils;

import android.app.AlertDialog;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.disha.radiobulbul.R;
import com.disha.radiobulbul.fragments.ErrorDialog;
import com.disha.radiobulbul.services.BgService;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.satyajit.thespotsdialog.SpotsDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PlayerStatus extends Application {

    private int STATUS;

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setSTART_TIME(int START_TIME) {
        this.START_TIME = START_TIME;
    }

    public static final String CHANNEL_ID = "RadioBulbul";

    int START_TIME = 0;

    private SimpleExoPlayer player;
    private ExtractorMediaSource mediaSource;
    private DefaultDataSourceFactory dataSourceFactory;
    DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    private TrackSelection.Factory trackSelectionFactory  = new AdaptiveTrackSelection.Factory();
    private TrackSelector trackSelector= new DefaultTrackSelector(trackSelectionFactory);

    AlertDialog dialog;

    ImageView art;

    private RelativeLayout relativeLayout;
    private AnimationDrawable animationDrawable;

    ImageView play;

    int flag =0;




    Context con;

    SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy", Locale.US);
    String currentDate = sdf.format(new Date());

    String FileA = "http://radiobulbul.org/RADIO/"+currentDate+"/A.mp3";
    String FileB = "http://radiobulbul.org/RADIO/"+currentDate+"/B.mp3"; //b

    public void setData(Context c){

       Typeface Cav = Typeface.createFromAsset(c.getAssets(), "fonts/cav.ttf");

       dialog = new SpotsDialog.Builder()
                .setContext(c)   //Need to send the Context , from fragments you can use getApplication()
                .setMessage("Buffering...") //Set Custom Text
                .setTypeface(Cav)  //Set the Typeface
                .setTheme(R.style.Custom)
                .setCancelable(false) //Set Option to cancel when clicked outside
                .build();


        dataSourceFactory = new DefaultDataSourceFactory(c, Util.getUserAgent(c, "mediaPlayerSample"), bandwidthMeter);

        con = c;


        int hr = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        int sec = Calendar.getInstance().get(Calendar.SECOND);

        String temp = FileB;

        int time = hr*10000+min*100+sec;

        if (time>=70000 && time < 140000){
            //7AM to 12 PM
            temp = FileA;
            START_TIME = 7;

        }

        else if (time>=140000 && time<170000)
        {
            temp = FileB;
            START_TIME = 14;

        }
        else if (time>=172000 && time<213000)
        {
            temp = FileA;
            START_TIME = 17;

        }
        else if (time>=213000)
        {
           temp = FileB;
          START_TIME = 21;

        }

        else offline();
        //Break 12pm - 14pm - break

        //lets start from 2pm to 5pm

        //Break 17pm - 17:20pm - break or 5pm to 5:20pm

        if (START_TIME!=0) {

            player = ExoPlayerFactory.newSimpleInstance(c, trackSelector);

            mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(temp));

        Log.d("INSIDE PLAY",temp);

            player.addListener(new PlayerEventListener());

            player.prepare(mediaSource);



        }

    }



    public SimpleExoPlayer getPlayer(){
        return player;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Radio Bulbul",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void offline(){


        ErrorDialog fragError = new ErrorDialog();
        fragError.show(((AppCompatActivity) con).getSupportFragmentManager(), "NO NET");
        if (player!=null)
        player.release();
    }

    public class PlayerEventListener implements Player.EventListener {



        @Override
        public void onTracksChanged(TrackGroupArray trackGroups,
                                    TrackSelectionArray trackSelections) {}
        @Override
        public void onLoadingChanged(boolean isLoading) {}

        @Override
        public void onPlayerStateChanged(boolean playWhenReady,int playbackState) {

            if (playbackState == Player.STATE_BUFFERING)
                if (!dialog.isShowing()) dialog.show();


                if (playbackState == Player.STATE_ENDED)
                        offline();

            if (playbackState == Player.DISCONTINUITY_REASON_SEEK){
                if (!dialog.isShowing()) dialog.show();
            }




            if (playbackState == Player.STATE_READY) {

                if (dialog.isShowing()) dialog.dismiss();

                if (flag==0)
                seeker();


            }
        }

        void seeker(){

            long songDur = player.getDuration();

            int hr = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int min = Calendar.getInstance().get(Calendar.MINUTE);
            int sec = Calendar.getInstance().get(Calendar.SECOND);

            long tempMIN = min*60000;
            long tempSEC = sec*1000;

            long temp;


            if (START_TIME!=0&&START_TIME!=17&&START_TIME!=21){

                long tempHR = (hr-START_TIME)*3600000;

                 temp = tempHR+tempMIN+tempSEC;

                if (temp > songDur) offline();
                else {

                    player.seekTo(temp);
                    player.setPlayWhenReady(true);
                    play();

                }

            }

            else if (START_TIME==17){

                long tempHR = ((hr-START_TIME)*3600000);

                temp = tempHR+tempMIN+tempSEC-(20*60*1000)+(3600*1000); //Seek 1hr

                if (temp > songDur) offline();
                else {
                    player.seekTo(temp);
                    player.setPlayWhenReady(true);
                    play();
                }


            }

            else if (START_TIME==21){

                long tempHR = ((hr-START_TIME)*3600000);

                temp = tempHR+tempMIN+tempSEC-(30*60*1000);

                if (temp > songDur) offline();
                else {
                    player.seekTo(temp);
                    player.setPlayWhenReady(true);
                    play();
                }


            }

            flag = 1;

        }



        @Override
        public void onPlayerError(ExoPlaybackException error) {

            Log.d("TAG",String.valueOf(error.getSourceException()));
        if (error.type == ExoPlaybackException.TYPE_SOURCE){

            if (dialog.isShowing()) dialog.dismiss();
             FileA = "http://radiobulbul.org/RADIO/30-06-2019/A.mp3";
             FileB = "http://radiobulbul.org/RADIO/30-06-2019/B.mp3"; //b
            setData(con);

        }


        }








        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
    }

    public void iniAnim(View v){


        // init constraintLayout
        relativeLayout = v.findViewById(R.id.relativeLayout);

        // initializing animation drawable by getting background from constraint layout
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

        // setting enter fade animation duration to 5 seconds
        animationDrawable.setEnterFadeDuration(5000);

        // setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(2000);

        art = v.findViewById(R.id.art);

         play = v.findViewById(R.id.play_btn);

    }

    public AnimationDrawable getAnim(){
        return animationDrawable;
    }



    public void play() {






        //Start background animation
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }

    startService();


        //Rotate the Image
        RotateAnimation rotate = new RotateAnimation(0, 358, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setFillAfter(true);
        art.startAnimation(rotate);



        play.setImageDrawable(ContextCompat.getDrawable(con, R.mipmap.ic_pause));


    }


    public void startService() {


        Intent serviceIntent = new Intent(con, BgService.class);


        ContextCompat.startForegroundService(con, serviceIntent);
    }


}