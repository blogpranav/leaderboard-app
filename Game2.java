package com.example.scoreit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Game2 extends AppCompatActivity {
    int key=1;
    String J1;
    String f1;
    JSONObject j1;
    JSONArray ja;
    TextView t1,t2,t3,k1,k2,k3,bbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        t1 = findViewById(R.id.teama);
        t2 = findViewById(R.id.teamb);
        t3 = findViewById(R.id.teamc);
        k1 = findViewById(R.id.killsA);
        k2 = findViewById(R.id.killsB);
        k3 =findViewById(R.id.killsC);
        bbtn = findViewById(R.id.button);
        bbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJSON(v);
            }
        });
    }
    public void getJSON(View view)
    {
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String url1;
        @Override
        protected void onPostExecute(String aVoid) {
            f1 = aVoid;
            try{
                j1 = new JSONObject(f1);
                ja = j1.getJSONArray("server_response");
                JSONObject j2 = ja.getJSONObject(1);
                t1.setText(j2.getString("TeamAname"));
                k1.setText(j2.getString("TeamAkills"));
                t2.setText(j2.getString("TeamBname"));
                k2.setText(j2.getString("TeamBkills"));
                t3.setText(j2.getString("TeamCname"));
                k3.setText(j2.getString("TeamCkills"));

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPreExecute() {
            url1 = "https://projectpegasus.000webhostapp.com/getdata.php";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(url1);
                HttpURLConnection h1 = (HttpURLConnection)url.openConnection();
                InputStream i1 =h1.getInputStream();
                BufferedReader b1 = new BufferedReader(new InputStreamReader(i1));
                StringBuilder s1 = new StringBuilder();
                while((J1 = b1.readLine()) != null)
                {
                    s1.append(J1 + "\n");
                }
                b1.close();
                i1.close();
                h1.disconnect();
                return s1.toString().trim();
            }
            catch(MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}

