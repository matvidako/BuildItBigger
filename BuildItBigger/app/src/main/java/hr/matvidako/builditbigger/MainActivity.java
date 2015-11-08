package hr.matvidako.builditbigger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import hr.matvidako.jokes.Jokes;
import hr.matvidako.jokesandroidlib.JokeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Jokes jokes = new Jokes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jokes = new Jokes();
        findViewById(R.id.joke_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = JokeActivity.buildIntent(this, jokes.getRandomJoke().text);
        startActivity(intent);
    }

}
