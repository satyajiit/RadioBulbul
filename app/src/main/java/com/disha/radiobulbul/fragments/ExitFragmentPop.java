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

public class ExitFragmentPop  extends DialogFragment {

    private Button yes,no;


    public ExitFragmentPop() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        setCancelable(false);

        View view = inflater.inflate(R.layout.exit_layout, container);  //Inflate the Layout

        initUI(view);

        setListeners();

        return view;
    }


    private void initUI(View v) {


        yes = v.findViewById(R.id.yes);
        no = v.findViewById(R.id.no);

    }

    private void setListeners(){

        yes.setOnClickListener(v -> {


            Intent serviceIntent = new Intent(getActivity(), BgService.class);
            getActivity().stopService(serviceIntent);


            if (((PlayerStatus) getActivity().getApplication()).getPlayer()!=null){
                ((PlayerStatus) getActivity().getApplication()).getPlayer().setPlayWhenReady(false);
                ((PlayerStatus) getActivity().getApplication()).getPlayer().release();
            }

            ((PlayerStatus) getActivity().getApplication()).setSTATUS(0);
            ((PlayerStatus) getActivity().getApplication()).setSTART_TIME(0);
            ((PlayerStatus) getActivity().getApplication()).setFlag(0);
            ((PlayerStatus) getActivity().getApplication()).getAnim().stop();
            dismiss();
            getActivity().finish();
        });

        no.setOnClickListener(v -> dismiss());


    }

}
