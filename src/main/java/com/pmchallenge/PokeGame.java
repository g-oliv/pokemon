package com.pmchallenge;


import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PokeGame {

    BigInteger current_square_x;
    BigInteger current_square_y;

    BigInteger next_square_x;
    BigInteger next_square_y;

    BigInteger[] current_square;
    Set<BigInteger[]> empty_squares = new HashSet<>();

    Integer pokemonCount;

    public PokeGame() {
        current_square_x = BigInteger.valueOf(0);
        current_square_y = BigInteger.valueOf(0);
        next_square_x = BigInteger.valueOf(0);
        next_square_y = BigInteger.valueOf(0);

        current_square = new BigInteger[]{current_square_x, current_square_y};
        empty_squares.add(current_square);

        pokemonCount = 1;
    }

    public void run() throws IOException {

        // Handle input
        List<Character> commands = convertInput();

        for (char ch: commands) {
            evalInput(ch);
            BigInteger[] next_square = new BigInteger[]{next_square_x, next_square_y};

        }


    }

    List<Character> convertInput() throws IOException {
        System.out.println("Provide sequence of movements: \n");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String input = inputReader.readLine();
        List<Character> letters = input.chars().mapToObj(ch -> (char) ch).toList();
        return letters;
    }

    public void evalInput(Character command) {

        switch (command) {
            case 'N' -> next_square_y = next_square_y.add(BigInteger.valueOf(1));
            case 'S' -> next_square_y = next_square_y.subtract(BigInteger.valueOf(1));
            case 'E' -> next_square_x = next_square_x.add(BigInteger.valueOf(1));
            case 'O' -> next_square_x = next_square_x.subtract(BigInteger.valueOf(1));
        }
    }


}
