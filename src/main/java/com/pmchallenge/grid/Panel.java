package com.pmchallenge.grid;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class represents a 2D map
 * Its size is N * N, where N = 2 * Integer.MAX_VALUE, given that {@code Square} has its x,y coordinates as Integers
 */
@Getter
@Setter
public class Panel {

    private Set<Square> visitedSquares = new HashSet<>();
    private Map<Integer, Panel> neighbouringPanels = new HashMap<>(4);

}
