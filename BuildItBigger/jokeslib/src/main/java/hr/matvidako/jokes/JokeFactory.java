package hr.matvidako.jokes;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JokeFactory {

    private Random random = new Random();
    private List<String> jokes =
            Arrays.asList("There are 2 hard problems in computer science: caching, naming, and off-by-1 errors",
                    "How do functions break up? They stop calling each other!",
                    "When is a function a bad investment? When there's no return",
                    "When do two functions fight? When they have arguments",
                    "What happened to all the illegal exceptions? They were all caught!",
                    "A  SQL query goes into a bar, walks up to two tables and asks, \"Can I join  you?\""
            );

    public Joke createRandomJoke() {
        int index = random.nextInt(jokes.size());
        return new Joke(jokes.get(index));
    }

}
