package com.disha.radiobulbul.fragments;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.disha.radiobulbul.R;

public class AboutFragment extends Fragment {


Typeface Cav;
ImageView logo;

LinearLayout click;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about_layout, container, false);

        logo = view.findViewById(R.id.icn);


        //Rotate the Image
        RotateAnimation rotate = new RotateAnimation(0, 358, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setFillAfter(true);
        logo.startAnimation(rotate);


                initUI();

        setCustomTitlebar();


    click = view.findViewById(R.id.clicker);

    click.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


                try {
                    getActivity().getPackageManager()
                            .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://profile/100002076197696"))); //Trys to make intent with FB's URI
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/satyajiit"))); //catches and opens a url to the desired page
                }

        }
    });



        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }

    void initUI(){

        Cav = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cav.ttf");







    }

    void setCustomTitlebar() {
        //Set Title bar with Custom Typeface
        TextView tv = new TextView(getContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("About Radio Bulbul");
        tv.setTextSize(24);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTypeface(Cav);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(tv);

    }


}
