package com.disha.radiobulbul.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.disha.radiobulbul.R;

public class NoInternet  extends DialogFragment {

    private Button ok;


    public NoInternet() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        setCancelable(false);

        View view = inflater.inflate(R.layout.no_internet_pop, container);  //Inflate the Layout

        initUI(view);

        setListeners();

        return view;
    }


    private void initUI(View v) {


        ok = v.findViewById(R.id.ok);


    }

    private void setListeners(){

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dismiss();
            }
        });

    }

}
