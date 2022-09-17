package com.pmchallenge;


import com.pmchallenge.exception.InvalidInputException;
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

/**
 * Class responsible for the computation
 */
@Getter
@Setter
public class PokeGame {

    private static final List<String> OPPOSITE_DIR_Y = List.of("N", "S");
    private static final List<String> OPPOSITE_DIR_X = List.of("E", "O");
    private int nextSquareX;
    private int nextSquareY;
    private Grid grid;
    private Panel currentPanel;
    private Square currentSquare;


    public PokeGame() {

        nextSquareX = 0;
        nextSquareY = 0;

        grid = new Grid();
        currentPanel = new Panel();
        currentSquare = new Square(nextSquareX, nextSquareY);

        currentPanel.getVisitedSquares().add(currentSquare);
        grid.getGridMap().add(currentPanel);

    }

    /**
     * Main method. Prints and returns final result
     * <p>
     * Calls input handler method {@link #convertInput()} to obtain the input from user in a consumable format. That input is consumed
     * by {@link #evaluateMove(String)}. The number of visited squares from all panels is summed, printed and returned.
     * </p>
     *
     * @return Number of squares visited
     * @throws IOException
     */
    public long run() throws IOException {

        List<String> movementsSequence = convertInput();

        movementsSequence.forEach(this::evaluateMove);
        long result = grid.getGridMap()
                          .stream()
                          .mapToLong(panel -> panel.getVisitedSquares().size())
                          .sum();

        System.out.println(result);
        return result;
    }

    /**
     * Asks input from user and converts it to List of Strings
     *
     * @return List with sequence of movements
     * @throws IOException
     */
    List<String> convertInput() throws IOException {

        System.out.println("Provide sequence of movements: ");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String input = inputReader.readLine().toUpperCase();

        return Arrays.asList(input.split(""));
    }

    /**
     * Consumes movements, evaluates them, updates the current position and updates the {@code visitedSquares}
     * <p>
     * Checks for cases at the edge of the current panel and calls {@link #updateGrid(String)} in case {@code move}
     * will require extending the grid
     * </p>
     *
     * @param move Element from the sequence of movements
     */
    void evaluateMove(String move) {

        List<Moves> edgeCaseMoves = edgeCases();

        switch (move) {
            case "N" -> nextSquareY++;
            case "S" -> nextSquareY--;
            case "E" -> nextSquareX++;
            case "O" -> nextSquareX--;
            default -> throw new InvalidInputException("Movement not recognized");
        }

        currentSquare = new Square(nextSquareX, nextSquareY);

        if (edgeCaseMoves.contains(Moves.valueOf(move))) {
            updateGrid(move);
        }

        currentPanel.getVisitedSquares().add(currentSquare);
    }


    /**
     * Updates the grid.
     * <p>
     * The method is called when the limit of a panel has been reached. If the current panel does not have an adjacent neighbour
     * {@code Panel} in the direction of {@code move}, a new one is created, the {@code neighbouringPanels} are updated, and the current
     * panel is updated.
     * </p>
     *
     * @param move
     */
    private void updateGrid(String move) {

        Panel neighbourPanel;
        if (currentPanel.getNeighbouringPanels().get(Moves.valueOf(move).ordinal()) == null) {
            neighbourPanel = new Panel();
            grid.getGridMap().add(neighbourPanel);

            // compute index of current panel in new panel's neighbours
            List<String> remainingElementOfOppositeDir;

            if (OPPOSITE_DIR_Y.contains(move)) {
                remainingElementOfOppositeDir = new ArrayList<>(List.copyOf(OPPOSITE_DIR_Y));
            } else {
                remainingElementOfOppositeDir = new ArrayList<>(List.copyOf(OPPOSITE_DIR_X));
            }

            remainingElementOfOppositeDir.remove(move);
            int index = Moves.valueOf(remainingElementOfOppositeDir.get(0)).ordinal();

            neighbourPanel.getNeighbouringPanels().put(index, currentPanel);
            currentPanel.getNeighbouringPanels().put(Moves.valueOf(move).ordinal(), neighbourPanel);
        } else {
            neighbourPanel = currentPanel.getNeighbouringPanels().get(Moves.valueOf(move).ordinal());
        }

        currentPanel = neighbourPanel;

    }


    /**
     * Method evaluates if there is the possibility of reaching the limit of the {@code currentPanel} based on the {@code currentSquare}
     *
     * @return List with moves that will trigger the need to update the grid
     */
    List<Moves> edgeCases() {
        List<Moves> edge = new ArrayList<>();

        if (currentSquare.getY() == Integer.MAX_VALUE) {
            edge.add(Moves.N);
        } else if (currentSquare.getY() == Integer.MIN_VALUE) {
            edge.add(Moves.S);
        }

        if (currentSquare.getX() == Integer.MAX_VALUE) {
            edge.add(Moves.E);
        } else if (currentSquare.getX() == Integer.MIN_VALUE) {
            edge.add(Moves.O);
        }

        return edge;
    }

}
