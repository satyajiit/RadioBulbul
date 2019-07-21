package com.disha.radiobulbul.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.disha.radiobulbul.R;

public class UpdateFoundPop  extends DialogFragment {

    private Button update;
    TextView content;
    String logs;


    public UpdateFoundPop() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        setCancelable(false);

        String temp = "";

        if (getArguments() != null) {
            temp = getArguments().getString("log");
        }

        View view = inflater.inflate(R.layout.update_found, container);  //Inflate the Layout

        initUI(view);

        setListeners();

        giveLine(temp);

        content.setText(logs.substring(logs.indexOf('*')));



        return view;
    }


    private void initUI(View v) {


        update = v.findViewById(R.id.ok);
        content = v.findViewById(R.id.content);


    }

    void giveLine(String str){

        for(int i = 0; i < str.length(); i++){

            if (str.charAt(i) == '*') logs = logs+"\n*";
            else logs = logs + str.charAt(i);

        }

    }

    private void setListeners(){

        update.setOnClickListener(v -> {


            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.disha.radiobulbul"));
            startActivity(browserIntent);
            dismiss();
            getActivity().finish();
        });

    }

}
