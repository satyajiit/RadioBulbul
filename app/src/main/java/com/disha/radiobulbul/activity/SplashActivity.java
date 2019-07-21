package com.disha.radiobulbul.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.disha.radiobulbul.R;
import com.disha.radiobulbul.fragments.UpdateFoundPop;
import com.disha.radiobulbul.utils.ServiceManager;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    String VERSION_NAME = "#GENERAL";
    ServiceManager sm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_splash);
         sm = new ServiceManager(this);

         if (sm.isNetworkAvailable()) {
             new GetData().execute();
         }
         else
             startAct();

    }



    class GetData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            String result = "";
            try {
                URL url = new URL("https://songs-quiz-97335.web.app/radio/radio.xxx");
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();

                if (code == 200) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    if (in != null) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                        String line = "";

                        while ((line = bufferedReader.readLine()) != null)
                            result += line;
                    }
                    in.close();
                }

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result;

        }

        @Override
        protected void onPostExecute(String result) {


            if (!result.contains(VERSION_NAME)){

                Bundle bundle = new Bundle();
                bundle.putString("log", result.substring(VERSION_NAME.length()-1));
                UpdateFoundPop fragUpd = new UpdateFoundPop();
                fragUpd.setArguments(bundle);
                fragUpd.show(getSupportFragmentManager(), "NO NET");
                //will redirect or start the update pop

            }
            else
                startAct();

            super.onPostExecute(result);
        }
    }

void startAct(){


    new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

        @Override

        public void run() {


            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();

        }

    }, 2*1000);
}

}
