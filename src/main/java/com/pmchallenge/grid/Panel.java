package com.pmchallenge.grid;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Panel {

    private Set<Square> empty_squares = new HashSet<>();
    private Map<Integer, Panel> neighbouringPanels = new HashMap<>(4);

}
