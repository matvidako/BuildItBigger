package hr.matvidako.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import hr.matvidako.jokes.myApi.MyApi;
import hr.matvidako.jokes.myApi.model.Joke;
import hr.matvidako.jokesandroidlib.JokeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String localhostUrlGenymotion = "http://10.0.3.2:8080";
    private static String localhostUrlStandardEmulator = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.joke_btn).setOnClickListener(this);

        boolean shouldShowAds = getResources().getBoolean(R.bool.show_ads);
        if(shouldShowAds) {
            setupAd();
        }
    }

    private void setupAd() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onClick(View view) {
        new RandomJokeAsyncTask().execute(this);
    }

    public static class RandomJokeAsyncTask extends AsyncTask<Context, Void, String> {

        private MyApi myApiService = null;
        private Context context;

        @Override
        protected void onPreExecute() {
            if (myApiService != null) {
                return;
            }

            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(localhostUrlGenymotion + "/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        @Override
        protected String doInBackground(Context... params) {
            context = params[0];
            try {
                Joke joke = myApiService.randomJoke().execute();
                return joke.getText();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (context != null) {
                Intent intent = JokeActivity.buildIntent(context, result);
                context.startActivity(intent);
            }
        }
    }

}
