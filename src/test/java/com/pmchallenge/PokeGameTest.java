package com.pmchallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PokeGameTest {

    private PokeGame pokeGame;

    @BeforeEach
    void setUp() {
        pokeGame = new PokeGame();
    }

    @Test
    public void shouldConvertInputToListOfChars() throws IOException {

        // given
        String testInput = "NnEesS";
        ByteArrayInputStream stream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(stream);

        // when
        List<Character> result = pokeGame.convertInput();
        System.out.println(result);

        // then
        List<Character> expected = List.of('N', 'N', 'E', 'E', 'S', 'S');

        assertThat(result).isEqualTo(expected);

    }

    @Test
    void shouldEvaluateInputAndUpdateNextPosition() throws IOException {

        // given
        String testInput = "NNES";
        ByteArrayInputStream stream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(stream);
        List<Character> commands = pokeGame.convertInput();

        // when
        pokeGame.evalInput(commands.get(0));
        pokeGame.evalInput(commands.get(1));
        pokeGame.evalInput(commands.get(2));
        pokeGame.evalInput(commands.get(3));

        // then
        assertThat(pokeGame.next_square_x).isEqualTo(1);
        assertThat(pokeGame.next_square_y).isEqualTo(1);

    }

    @Test
    void shouldVerifySquareHasPokemon() {

        // given
        BigInteger[] next_square_0 = new BigInteger[]{BigInteger.valueOf(0), BigInteger.valueOf(0)};
        BigInteger[] next_square_1 = new BigInteger[]{BigInteger.valueOf(1), BigInteger.valueOf(0)};

        // when
        boolean result_0 = pokeGame.hasPokemon(next_square_0);
        boolean result_1 = pokeGame.hasPokemon(next_square_1);

        // then
        assertThat(result_0).isFalse();
        assertThat(result_1).isTrue();

    }

    @Test
    void shouldReturnPokemonCount() throws IOException {

        // given
        String testInput = "NESO";
        ByteArrayInputStream stream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(stream);

        // when
        pokeGame.run();

        // then
        assertThat(pokeGame.pokemonCount).isEqualTo(4);
    }
}