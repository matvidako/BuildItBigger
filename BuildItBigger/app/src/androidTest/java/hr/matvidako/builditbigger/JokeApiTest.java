package hr.matvidako.builditbigger;

import android.content.Context;
import android.test.AndroidTestCase;

import java.util.concurrent.TimeUnit;

public class JokeApiTest extends AndroidTestCase {

    private static final long TIMEOUT = 3;

    public void testRandomJoke() {
        Context context = getContext();
        assertNotNull(context);

        try {
            MainActivity.RandomJokeAsyncTask jokeTask = new MainActivity.RandomJokeAsyncTask();
            jokeTask.execute(context);
            String joke = jokeTask.get(TIMEOUT, TimeUnit.SECONDS);
            assertNotNull(joke);
        } catch (Exception e) {
            fail("Timeout");
        }
    }

}
