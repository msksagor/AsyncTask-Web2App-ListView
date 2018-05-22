package com.msksagor.appjsonbasic;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    NetworkInfo info = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textViewID);

        button = findViewById(R.id.buttonID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "https://api.myjson.com/bins/16rw42";
                new JsonTask().execute(link);

            }
        });

    }

    public class JsonTask extends AsyncTask<String, String, StringBuffer> {

        @Override
        protected StringBuffer doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            String nameKey = null;
            String postionKey = null;
            try {
                URL url = new URL(strings[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                // httpURLConnection.connect();
                //httpURLConnection.setRequestMethod("GET");

                InputStream stream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                StringBuffer lastbufferString = new StringBuffer();

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }
                String file = stringBuffer.toString();
                JSONObject fileObject = new JSONObject(file);
                JSONArray array = fileObject.getJSONArray("Student");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject arrayObject = array.getJSONObject(i);

                    nameKey = arrayObject.getString("name");
                    postionKey = arrayObject.getString("postion");

                    lastbufferString.append(nameKey + " \n" + postionKey + "\n\n");
                }
                return lastbufferString;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(StringBuffer s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }
}
