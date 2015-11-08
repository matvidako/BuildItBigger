package hr.matvidako.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import hr.matvidako.jokes.myApi.MyApi;
import hr.matvidako.jokes.myApi.model.Joke;
import hr.matvidako.jokesandroidlib.JokeActivity;

public abstract class BaseMainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String localhostUrlGenymotion = "http://10.0.3.2:8080";
    private static String localhostUrlStandardEmulator = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.joke_btn).setOnClickListener(this);
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
