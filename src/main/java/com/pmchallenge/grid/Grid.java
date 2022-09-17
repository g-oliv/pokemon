package com.pmchallenge.grid;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that clusters panels to represent a virtual infinite 2D grid
 */
@Getter
public class Grid {

    private final Set<Panel> gridMap = new HashSet<>();
}
