package com.pmchallenge;


import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PokeGame {

    private BigInteger next_square_x;
    private BigInteger next_square_y;
    private BigInteger[] current_square;
    private Set<BigInteger[]> empty_squares = new HashSet<>();
    private Integer pokemonCount;

    public PokeGame() {

        next_square_x = BigInteger.valueOf(0);
        next_square_y = BigInteger.valueOf(0);

        current_square = new BigInteger[]{next_square_x, next_square_y};
        empty_squares.add(current_square);

        pokemonCount = 1;
    }

    public void run() throws IOException {

        List<Character> commands = convertInput();

        commands.stream()
                .map(this::evalInput)
                .toList()
                .forEach(this::updateCount);

        System.out.println(pokemonCount);

    }

    List<Character> convertInput() throws IOException {

        System.out.println("Provide sequence of movements: ");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String input = inputReader.readLine().toUpperCase();

        return input.chars().mapToObj(ch -> (char) ch).toList();
    }

    BigInteger[] evalInput(Character command) {

        switch (command) {
            case 'N' -> next_square_y = next_square_y.add(BigInteger.valueOf(1));
            case 'S' -> next_square_y = next_square_y.subtract(BigInteger.valueOf(1));
            case 'E' -> next_square_x = next_square_x.add(BigInteger.valueOf(1));
            case 'O' -> next_square_x = next_square_x.subtract(BigInteger.valueOf(1));
        }

        return new BigInteger[]{next_square_x, next_square_y};
    }

    boolean hasPokemon(BigInteger[] square) {

        boolean squareIsEmpty = empty_squares.stream().anyMatch(empty_sq -> Arrays.equals(empty_sq, square));
        return !squareIsEmpty;
    }

    void updateCount(BigInteger[] square) {
        if (hasPokemon(square)) {
            pokemonCount++;
            empty_squares.add(square);
        }
        current_square = square;
    }
}
