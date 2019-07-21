package com.disha.radiobulbul.fragments;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.disha.radiobulbul.R;
import com.disha.radiobulbul.services.BgService;
import com.disha.radiobulbul.utils.PlayerStatus;
import com.disha.radiobulbul.utils.ServiceManager;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.satyajit.thespotsdialog.SpotsDialog;


public class PlayFragment extends Fragment {


    private static final String CHANNEL_ID = "548";
    Typeface Cav;
    ImageView play;
    TextView level;
    int max_vol;
    int percent;
    AlertDialog dialog;
    ImageView art,vol;
    int flag = 0;
    View vw;




    //FAB4 IS FOR MORE

    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;



    FloatingActionButton floatingActionButton1,floatingActionButton2,floatingActionButton3,floatingActionButton4,floatingActionButton5,floatingActionButton6;
    FloatingActionMenu materialDesignFAM;




    public static PlayFragment newInstance() {
        return new PlayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.play_layout, container, false);


        vw = view;

        initUI(view);

        setCustomTitlebar();



        setupListeners();

        initControls();







        if (((PlayerStatus) getActivity().getApplication()).getSTATUS()==1){
            ((PlayerStatus) getActivity().getApplication()).play();
        }

        return view;

    }


    public void stopService() {
        Intent serviceIntent = new Intent(getActivity(), BgService.class);
        getActivity().stopService(serviceIntent);
    }
    void initUI(View v) {

        //initialise vars and UI elements


        Cav = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cav.ttf");
        play = v.findViewById(R.id.play_btn);
        volumeSeekbar = v.findViewById(R.id.volume);
        level = v.findViewById(R.id.progress);
        vol = v.findViewById(R.id.vol);

        dialog = new SpotsDialog.Builder()
                .setContext(getActivity())   //Need to send the Context , from fragments you can use getApplication()
                .setMessage("Connecting to Radio Service...") //Set Custom Text
                .setTypeface(Cav)  //Set the Typeface
                .setTheme(R.style.Custom)
                .setCancelable(false) //Set Option to cancel when clicked outside
                .build();


        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        assert audioManager != null;
        max_vol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //if (((PlayerStatus) getActivity().getApplication()).getSTATUS()==0) {
            //WebView to load the Radio channel
            //exoPlayerPlay();
       // }

        ((PlayerStatus) getActivity().getApplication()).iniAnim(v);


        art = v.findViewById(R.id.art);


        materialDesignFAM =  v.findViewById(R.id.social_floating_menu);
        floatingActionButton1 = v.findViewById(R.id.floating_facebook);
        floatingActionButton2 = v.findViewById(R.id.floating_twitter);
      floatingActionButton3 = v.findViewById(R.id.floating_linkdin);
        floatingActionButton4 =  v.findViewById(R.id.more); //more for
        floatingActionButton5 =  v.findViewById(R.id.floating_instagram);
        floatingActionButton6 = v.findViewById(R.id.floating_whatsapp);


    }


void stopRadio(){
    ((PlayerStatus) getActivity().getApplication()).getPlayer().setPlayWhenReady(false);
    ((PlayerStatus) getActivity().getApplication()).getPlayer().release();

    enablePlay();
}

    void setupListeners() {

        //Click listeners
        play.setOnClickListener(v -> {


            if (((PlayerStatus) getActivity().getApplication()).getSTATUS()==0) {
                //Play Options
                //new GetIp().execute();
                playRadio();
            }
            else {

                stopRadio();


            }

        });


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
              shareFunc("com.facebook.katana");


            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
          shareFunc("com.twitter.android");


            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                shareFunc("com.linkedin.android");

            }
        });

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = getString(R.string.share_msg);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Share Radio Bulbul on"));
            }
        });
        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                shareFunc("com.instagram.android");

            }
        });
        floatingActionButton6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                shareFunc("com.whatsapp");

            }
        });


    }

    void shareFunc(String pkg){

        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setPackage(pkg);
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_msg));
            startActivity(intent);
        }
        catch (Exception e) {

            Toast.makeText(getActivity(), "Sorry , App is not Installed!!", Toast.LENGTH_SHORT).show();

        }




    }

    void enablePlay() {

        //Show play controls which mean the player is currently stopped

            play.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_player));
           // if (animationDrawable != null && animationDrawable.isRunning()) {
                // stop the animation
        ((PlayerStatus) getActivity().getApplication()).getAnim().stop();
           // }
            ((PlayerStatus) getActivity().getApplication()).setSTATUS(0); //Set player as 0 , PAUSED
            art.clearAnimation();

        ((PlayerStatus) getActivity().getApplication()).setFlag(0);

        stopService();

    }


    void setCustomTitlebar() {
        //Set Title bar with Custom Typeface
        TextView tv = new TextView(getContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Radio Bulbul");
        tv.setTextSize(24);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTypeface(Cav);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(tv);

    }


    void playRadio() {

        ServiceManager sm = new ServiceManager(getActivity());
        if (sm.isConnectionAvailable()) {

            flag = 1;


            ((PlayerStatus) getActivity().getApplication()).setSTATUS(1); //Set player as 1 , PLAYING

            ((PlayerStatus) getActivity().getApplication()).setFlag(0);

            ((PlayerStatus) getActivity().getApplication()).setData(getActivity());


        }
    }








    private void initControls() {
        try {


            volumeSeekbar.setMax(max_vol);
            int temp = audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            volumeSeekbar.setProgress(temp);

            int p = temp * 100 / max_vol;

            level.setText( p + "%");

            setVolIcons(p);


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                    percent = (progress * 100 / max_vol);
                    level.setText(String.valueOf(percent) + "%");
                    setVolIcons(percent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void setVolIcons(int p){

        if ( p < 50 && p > 0 )
            vol.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_volume_down));
        else if ( p <= 0) vol.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_volume_off));
        else vol.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_volume_up));


    }

    @Override
    public void onResume() {

        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();

    }













}