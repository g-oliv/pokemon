package com.pmchallenge;

import com.pmchallenge.exception.InvalidInputException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

class PokeGameTest {

    private PokeGame pokeGame;

    @BeforeEach
    void setUp() {
        pokeGame = new PokeGame();
    }

    @Test
    void shouldConvertInputToList() throws IOException {

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
    void shouldThrowExceptionWhenIncorrectInput() throws IOException {

        // given
        String testInput = "NP";
        ByteArrayInputStream stream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(stream);

        // when
        List<String> result = pokeGame.convertInput();
        System.out.println(result);
        Exception exception = assertThrows(InvalidInputException.class, () -> result.forEach(s -> pokeGame.evaluateMove(s)));

        // then
        String expectedMessage = "Movement not recognized";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).contains(expectedMessage);
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
        assertThat(pokeGame.getNextSquareX()).isEqualTo(-2);
        assertThat(pokeGame.getNextSquareY()).isEqualTo(2);

    }

    @Test
    void shouldEvaluateEdgeCases() {

        // given
        String move = "N";
        int x = Integer.MAX_VALUE;
        int y = Integer.MAX_VALUE;
        pokeGame.setNextSquareX(x);
        pokeGame.setNextSquareY(y);
        pokeGame.setCurrentSquare(new Square(x, y));

        // when
        pokeGame.evaluateMove(move);

        // then
        assertThat(pokeGame.edgeCases()).hasSize(2);
    }

    @Test
    void testUpdateGridWhenNeighbourNotExists() {

        // given
        int x = 0;
        int y = Integer.MAX_VALUE;
        String move = "N";
        Square cSqr = new Square(x, y);
        Panel pan = new Panel();
        pokeGame.setCurrentPanel(pan);
        pokeGame.setNextSquareX(x);
        pokeGame.setNextSquareY(y);
        pokeGame.setCurrentSquare(cSqr);


        // when
        pokeGame.evaluateMove(move);

        // then
        assertThat(pokeGame.getCurrentPanel().getNeighbouringPanels()).hasSize(1);
        assertThat(pokeGame.getGrid().getGridMap()).hasSize(2);
        assertThat(pokeGame.getCurrentPanel().getNeighbouringPanels()).contains(entry(Moves.S.ordinal(), pan));
        assertThat(pokeGame.getCurrentSquare()).isEqualTo(new Square(0, Integer.MIN_VALUE));
        assertThat(pokeGame.getCurrentPanel().getVisitedSquares()).contains(new Square(0, Integer.MIN_VALUE));

    }

    @Test
    void testUpdateGridWhenNeighbourExists() {

        // given
        int x = 0;
        int y = Integer.MAX_VALUE;
        String move = "N";
        Square cSqr = new Square(x, y);
        Panel pan1 = new Panel();
        Panel pan2 = new Panel();
        pan1.getNeighbouringPanels().put(0, pan2);
        pan2.getNeighbouringPanels().put(1, pan1);
        pokeGame.getGrid().getGridMap().add(pan2);
        pokeGame.setCurrentPanel(pan1);
        pokeGame.setNextSquareX(x);
        pokeGame.setNextSquareY(y);
        pokeGame.setCurrentSquare(cSqr);


        // when
        pokeGame.evaluateMove(move);

        // then
        assertThat(pokeGame.getCurrentPanel().getNeighbouringPanels()).hasSize(1);
        assertThat(pokeGame.getGrid().getGridMap()).hasSize(2);
        assertThat(pokeGame.getCurrentPanel().getNeighbouringPanels()).contains(entry(Moves.S.ordinal(), pan1));
        assertThat(pokeGame.getCurrentSquare()).isEqualTo(new Square(0, Integer.MIN_VALUE));
        assertThat(pokeGame.getCurrentPanel().getVisitedSquares()).contains(new Square(0, Integer.MIN_VALUE));

    }

    @Test
    void shouldReturnPokemonCount() throws IOException {

        // given
        String testInput = "NNSSOOEEE";
        ByteArrayInputStream stream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(stream);

        // when
        pokeGame.run();

        // then
        assertThat(pokeGame.getCurrentPanel().getVisitedSquares()).hasSize(6);
    }

    @Test
    void shouldReturnPokemonCountForMultiplePanels() throws IOException {

        // given
        String testInput = "N".repeat(200) + "E".repeat(200);
        ByteArrayInputStream stream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(stream);
        pokeGame.setCurrentSquare(new Square(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // when
        long result = pokeGame.run();

        // then
        assertThat(result).isEqualTo(401);
    }


}