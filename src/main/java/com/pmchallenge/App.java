package com.pmchallenge;

import java.io.IOException;

/**
 * App runner
 */
public class App {
    public static void main(String[] args) throws IOException {
        PokeGame game = new PokeGame();
        game.run();
    }
}
