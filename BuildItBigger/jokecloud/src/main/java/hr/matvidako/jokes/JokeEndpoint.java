/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package hr.matvidako.jokes;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "jokes.matvidako.hr",
                ownerName = "jokes.matvidako.hr",
                packagePath = ""
        )
)
public class JokeEndpoint {

    JokeFactory jokeFactory = new JokeFactory();

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "randomJoke")
    public Joke getRandomJoke() {
        return jokeFactory.createRandomJoke();
    }

}
