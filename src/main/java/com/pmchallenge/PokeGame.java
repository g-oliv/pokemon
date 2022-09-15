package com.pmchallenge;


import com.pmchallenge.grid.Grid;
import com.pmchallenge.grid.Moves;
import com.pmchallenge.grid.Panel;
import com.pmchallenge.grid.Square;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class PokeGame {

    private static final List<String> OPPOSITE_DIR_Y = List.of("N", "S");
    private static final List<String> OPPOSITE_DIR_X = List.of("E", "O");
    private byte next_square_x;
    private byte next_square_y;
    private Grid grid;
    private Panel current_panel;
    private Square current_square;


    public PokeGame() {

        next_square_x = 0;
        next_square_y = 0;

        grid = new Grid();
        current_panel = new Panel();
        current_square = new Square(next_square_x, next_square_y);

        current_panel.getEmpty_squares().add(current_square);
        grid.getGridMap().add(current_panel);

    }

    public int run() throws IOException {

        List<String> movementsSequence = convertInput();

        movementsSequence.forEach(this::evaluateMove);
        int result = grid.getGridMap()
                         .stream()
                         .mapToInt(panel -> panel.getEmpty_squares().size())
                         .sum();

        System.out.println(result);
        return result;
    }

    List<String> convertInput() throws IOException {

        System.out.println("Provide sequence of movements: ");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String input = inputReader.readLine().toUpperCase();

        return Arrays.asList(input.split(""));
    }

    void evaluateMove(String move) {

        List<Moves> edgeCaseMoves = edgeCases();

        switch (move) {
            case "N" -> next_square_y++;
            case "S" -> next_square_y--;
            case "E" -> next_square_x++;
            case "O" -> next_square_x--;
        }

        current_square = new Square(next_square_x, next_square_y);

        if (edgeCaseMoves.contains(Moves.valueOf(move))) {
            updateGrid(move);
        }

        current_panel.getEmpty_squares().add(current_square);
    }

    private void updateGrid(String move) {

        Panel neighbourPanel;
        if (current_panel.getNeighbouringPanels().get(Moves.valueOf(move).ordinal()) == null) {
            neighbourPanel = new Panel();
            grid.getGridMap().add(neighbourPanel);

            // compute index of current panel in new panel's neighbours
            int index;
            List<String> remainingElementOfOppositeDir;

            if (OPPOSITE_DIR_Y.contains(move)) {
                remainingElementOfOppositeDir = new ArrayList<>(List.copyOf(OPPOSITE_DIR_Y));
            } else {
                remainingElementOfOppositeDir = new ArrayList<>(List.copyOf(OPPOSITE_DIR_X));
            }

            remainingElementOfOppositeDir.remove(move);
            index = Moves.valueOf(remainingElementOfOppositeDir.stream()
                                                               .findFirst()
                                                               .get()).ordinal();

            neighbourPanel.getNeighbouringPanels().put(index, current_panel);
            current_panel.getNeighbouringPanels().put(Moves.valueOf(move).ordinal(), neighbourPanel);
        } else {
            neighbourPanel = current_panel.getNeighbouringPanels().get(Moves.valueOf(move).ordinal());
        }

        current_panel = neighbourPanel;

    }

    List<Moves> edgeCases() {
        List<Moves> edge = new ArrayList<>();

        if (current_square.getY() == Byte.MAX_VALUE) {
            edge.add(Moves.N);
        } else if (current_square.getY() == Byte.MIN_VALUE) {
            edge.add(Moves.S);
        }

        if (current_square.getX() == Byte.MAX_VALUE) {
            edge.add(Moves.E);
        } else if (current_square.getX() == Byte.MIN_VALUE) {
            edge.add(Moves.O);
        }

        return edge;
    }

}
