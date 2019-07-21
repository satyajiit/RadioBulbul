package com.disha.radiobulbul.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.disha.radiobulbul.R;
import com.disha.radiobulbul.services.BgService;
import com.disha.radiobulbul.utils.PlayerStatus;

public class ErrorDialog  extends DialogFragment {

    private Button ok;


    public ErrorDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        setCancelable(false);

        View view = inflater.inflate(R.layout.radio_avail, container);  //Inflate the Layout

        initUI(view);

        setListeners();

        return view;
    }


    private void initUI(View v) {


                ok = v.findViewById(R.id.ok);


    }

    private void setListeners(){

                ok.setOnClickListener(v -> {



                    ((PlayerStatus) getActivity().getApplication()).setSTATUS(0);
                    ((PlayerStatus) getActivity().getApplication()).setFlag(0);
                    ((PlayerStatus) getActivity().getApplication()).setSTART_TIME(0);
                    Intent serviceIntent = new Intent(getActivity(), BgService.class);
                    getActivity().stopService(serviceIntent);

                    dismiss();
                });

    }

}
