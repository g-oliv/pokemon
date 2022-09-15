package com.pmchallenge.grid;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Grid {

    private Set<Panel> gridMap = new HashSet<>();
}
