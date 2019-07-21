package com.disha.radiobulbul.fragments;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.disha.radiobulbul.R;

public class ContactFragment extends Fragment {


Typeface Cav;


    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.programs_layout, container, false);


        initUI();

        setCustomTitlebar();

        CardView call = view.findViewById(R.id.card_view2);
        CardView map = view.findViewById(R.id.card_view);

        call.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:07894912912"));
            startActivity(callIntent);
        });


        map.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/maps/gS8LbfgLTimWSNa17"));
            startActivity(browserIntent);
        });

//

        ImageView fb = view.findViewById(R.id.facebook);
        ImageView youtube = view.findViewById(R.id.yt);
        ImageView whats = view.findViewById(R.id.whats);
        ImageView twit = view.findViewById(R.id.twit);
        ImageView insta = view.findViewById(R.id.insta);

        fb.setOnClickListener(v -> {
            Intent intent = getOpenFacebookIntent(getActivity());
            startActivity(intent);
        });

        youtube.setOnClickListener(v -> {
            Intent intent = getOpenYouTubeIntent(getActivity());
            startActivity(intent);
        });

        twit.setOnClickListener(v -> {
            Intent intent = getOpenTwitterIntent(getActivity());
            startActivity(intent);
        });

        whats.setOnClickListener(v -> {
            Intent intent = getOpenWhatsAppIntent();
            startActivity(intent);
        });

        insta.setOnClickListener(v -> {
            Intent intent = getShareInstagramIntent(getActivity());
            startActivity(intent);
        });


        return view;

    }
    void initUI(){

        Cav = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cav.ttf");

    }

    void setCustomTitlebar() {
        //Set Title bar with Custom Typeface
        TextView tv = new TextView(getContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("Contact");
        tv.setTextSize(24);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTypeface(Cav);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(tv);

    }

    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/507365995960052")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/radiobulbul91.2")); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenTwitterIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.twitter.android", 0); //Checks if Twitter is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/SD")); //Trys to make intent with Twitter's's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/SD")); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenWhatsAppIntent() {

        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + "917894912912" + "&text=You can request for custom wishes here!!");

        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);

        return  sendIntent;
    }



    public static Intent getShareInstagramIntent(Context context) {

      try {
            context.getPackageManager()
                    .getPackageInfo("com.instagram.android", 0); //Checks if Instagram is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/accounts/login/")); //Trys to make intent with Instagram's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/accounts/login/")); //catches and opens a url to the desired page
        }







    }

    public static Intent getOpenYouTubeIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.google.android.youtube", 0); //Checks if YT is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/RadioBulbul")); //Trys to make intent with YT's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/RadioBulbul")); //catches and opens a url to the desired page
        }
    }


}
