package com.disha.radiobulbul.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.disha.radiobulbul.R;
import com.disha.radiobulbul.fragments.AboutFragment;
import com.disha.radiobulbul.fragments.ExitFragmentPop;
import com.disha.radiobulbul.fragments.PlayFragment;
import com.disha.radiobulbul.fragments.ContactFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    Fragment fragment = null;

   // ServiceManager sm = new ServiceManager(this); //Connectivity checker class
    int startPos = 2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            //Actions on Nav item is selected
            int pos = 0;

            switch (item.getItemId()) {
                case R.id.navigation_play:
                    fragment = PlayFragment.newInstance();
                    pos = 2;
                    break;
                case R.id.navigation_programs:
                    fragment = ContactFragment.newInstance();
                    pos = 1;
                    break;
                case R.id.navigation_about:
                    fragment = AboutFragment.newInstance();
                    pos = 3;
                    break;

            }

            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (startPos>pos)
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right );
                else if (startPos<pos)
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                startPos = pos;
            }


            return true;

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);




        Bundle extras = getIntent().getExtras();
        int extraStr=0;
        if (extras != null) {
            extraStr = extras.getInt("key");
        }


        if(extraStr==1) {
            finish();
        }

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_play);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener); //Set the Click Listener for Nav Bar




        //Set the main fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, PlayFragment.newInstance()); //Set fragment as default
        fragmentTransaction.commit();


       // sm.isConnectionAvailable();  //Checks for Internet Connection


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Build.VERSION.SDK_INT > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            ExitFragmentPop fragExit = new ExitFragmentPop();
            fragExit.show((getSupportFragmentManager()), "NO NET");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

