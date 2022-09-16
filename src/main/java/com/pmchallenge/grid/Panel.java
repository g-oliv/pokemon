package com.pmchallenge.grid;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class represents a 2D map of size N * N, where N = 2 * Integer.MAX_VALUE
 */
@Getter
@Setter
public class Panel {

    private Set<Square> visitedSquares = new HashSet<>();
    private Map<Integer, Panel> neighbouringPanels = new HashMap<>(4);

}
