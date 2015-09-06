package hr.matvidako.jokesandroidlib;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    private static final String EXTRA_JOKE = "joke";
    String joke;

    public static Intent buildIntent(Context context, String joke) {
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(EXTRA_JOKE, joke);
        return intent;
    }

    private void loadFromIntent(Intent intent) {
        this.joke = intent.getStringExtra(EXTRA_JOKE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        loadFromIntent(getIntent());
        TextView jokeTv = (TextView) findViewById(R.id.joke);
        jokeTv.setText(joke);
    }

}
