package com.pmchallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PerformanceTests {

    private PokeGame pokeGame;

    @BeforeEach
    void setUp() {
        pokeGame = new PokeGame();
    }

    @Test
    public void testComputeTimeForInputSize () throws IOException {

        // verifies compute time based on input size - O(n) as expected
        ByteArrayInputStream stream = new ByteArrayInputStream("N".repeat(100000).getBytes());
        System.setIn(stream);

        StopWatch watch = new StopWatch();
        watch.start();
        pokeGame.run();
        watch.stop();

        double elapsed;
        String units;
        if ((watch.getTotalTimeSeconds() > 1)) {
            elapsed = watch.getTotalTimeSeconds();
            units = "sec";

        } else {
            elapsed = watch.getTotalTimeMillis();
            units = "millis";
        }

        System.out.println("Total execution time: " + elapsed + units);

    }
}
