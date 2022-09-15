package com.pmchallenge;

import com.pmchallenge.grid.Moves;
import com.pmchallenge.grid.Panel;
import com.pmchallenge.grid.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class PokeGameTest {

    private PokeGame pokeGame;

    @BeforeEach
    void setUp() {
        pokeGame = new PokeGame();
    }

    @Test
    public void shouldConvertInputToList() throws IOException {

        // given
        String testInput = "NnEesS";
        ByteArrayInputStream stream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(stream);

        // when
        List<String> result = pokeGame.convertInput();
        System.out.println(result);

        // then
        List<String> expected = List.of("N", "N", "E", "E", "S", "S");

        assertThat(result).isEqualTo(expected);

    }

    @Test
    void shouldEvaluateMoveAndUpdateNextPosition() throws IOException {

        // given
        String testInput = "NNOO";
        ByteArrayInputStream stream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(stream);
        List<String> commands = pokeGame.convertInput();

        // when
        pokeGame.evaluateMove(commands.get(0));
        pokeGame.evaluateMove(commands.get(1));
        pokeGame.evaluateMove(commands.get(2));
        pokeGame.evaluateMove(commands.get(3));

        // then
        assertThat(pokeGame.getNext_square_x()).isEqualTo((byte) -2);
        assertThat(pokeGame.getNext_square_y()).isEqualTo((byte) 2);

    }

    @Test
    void shouldEvaluateEdgeCases() {

        // given
        String move = "N";
        byte x = (byte) 127;
        byte y = (byte) 127;
        pokeGame.setNext_square_x(x);
        pokeGame.setNext_square_y(y);
        pokeGame.setCurrent_square(new Square(x, y));

        // when
        pokeGame.evaluateMove(move);

        // then
        assertThat(pokeGame.edgeCases().size()).isEqualTo(2);
    }

    @Test
    void testUpdateGridWhenNeighbourNotExists() {

        // given
        byte x = (byte) 0;
        byte y = (byte) 127;
        String move = "N";
        Square csqr = new Square(x, y);
        Panel pan = new Panel();
        pokeGame.setCurrent_panel(pan);
        pokeGame.setNext_square_x(x);
        pokeGame.setNext_square_y(y);
        pokeGame.setCurrent_square(csqr);


        // when
        pokeGame.evaluateMove(move);

        // then
        assertThat(pokeGame.getCurrent_panel().getNeighbouringPanels().size()).isEqualTo(1);
        assertThat(pokeGame.getGrid().getGridMap().size()).isEqualTo(2);
        assertThat(pokeGame.getCurrent_panel().getNeighbouringPanels()).contains(entry(Moves.S.ordinal(), pan));
        assertThat(pokeGame.getCurrent_square()).isEqualTo(new Square((byte) 0, (byte) -128));
        assertThat(pokeGame.getCurrent_panel().getEmpty_squares()).contains(new Square((byte) 0, (byte) -128));

    }

    @Test
    void testUpdateGridWhenNeighbourExists() {

        // given
        byte x = (byte) 0;
        byte y = (byte) 127;
        String move = "N";
        Square csqr = new Square(x, y);
        Panel pan1 = new Panel();
        Panel pan2 = new Panel();
        pan1.getNeighbouringPanels().put(0, pan2);
        pan2.getNeighbouringPanels().put(1, pan1);
        pokeGame.getGrid().getGridMap().add(pan2);
        pokeGame.setCurrent_panel(pan1);
        pokeGame.setNext_square_x(x);
        pokeGame.setNext_square_y(y);
        pokeGame.setCurrent_square(csqr);


        // when
        pokeGame.evaluateMove(move);

        // then
        assertThat(pokeGame.getCurrent_panel().getNeighbouringPanels().size()).isEqualTo(1);
        assertThat(pokeGame.getGrid().getGridMap().size()).isEqualTo(2);
        assertThat(pokeGame.getCurrent_panel().getNeighbouringPanels()).contains(entry(Moves.S.ordinal(), pan1));
        assertThat(pokeGame.getCurrent_square()).isEqualTo(new Square((byte) 0, (byte) -128));
        assertThat(pokeGame.getCurrent_panel().getEmpty_squares()).contains(new Square((byte) 0, (byte) -128));

    }

    @Test
    void shouldReturnPokemonCount() throws IOException {

        // given
        String testInput = "NNESOOSE";
        ByteArrayInputStream stream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(stream);

        // when
        pokeGame.run();

        // then
        assertThat(pokeGame.getCurrent_panel().getEmpty_squares().size()).isEqualTo(7);
    }

    @Test
    void shouldReturnPokemonCountForMultiplePanels() throws IOException {

        // given
        String testInput = "N".repeat(200) + "E".repeat(200) + "S".repeat(200) + "O".repeat(200);
        ByteArrayInputStream stream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(stream);

        // when
        int result = pokeGame.run();

        // then
        assertThat(result).isEqualTo(801);
    }



}