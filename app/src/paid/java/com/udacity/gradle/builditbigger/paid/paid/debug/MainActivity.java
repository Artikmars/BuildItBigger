package com.udacity.gradle.builditbigger.paid.paid.debug;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public static final String JOKE_TAG = "JOKE";
    public static String resultFromTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btn_tellJoke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GoogleEndpointsAsyncTask().execute(MainActivity.this);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GoogleEndpointsAsyncTask extends AsyncTask<Context, Void, String> {
        private MyApi myApiService = null;
        private Context context;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(MainActivity.this, "", "Please Wait",
                    false);
        }

        @Override
        protected String doInBackground(Context... params) {
            if (myApiService == null) {
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                myApiService = builder.build();
            }

            context = params[0];

            try {
                return myApiService.sayHi("Hi").execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            resultFromTask = result;
            Intent intent = new Intent();
            ComponentName cn = new ComponentName(context, "com.artamonov.joke.MainActivity");
            intent.putExtra(JOKE_TAG, result);
            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }


}
