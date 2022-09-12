package com.pmchallenge;

import org.assertj.core.api.BigIntegerAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class PokeGameTest {

    private PokeGame pokeGame;

    @BeforeEach
    void setUp() {
        pokeGame = new PokeGame();
    }

    @Test
    public void shouldConvertInputToListOfChars() throws IOException {

        // given
        String testInput = "NNEESS";
        ByteArrayInputStream bais = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(bais);

        // when
        List<Character> result = pokeGame.convertInput();
        System.out.println(result);

        // then
        assertThat(result).isEqualTo(List.of('N', 'N', 'E', 'E', 'S', 'S'));

    }

    @Test
    void shouldEvaluateInputAndUpdateNextPosition() throws IOException {

        // given
        String testInput = "NNES";
        ByteArrayInputStream bais = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(bais);
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


}