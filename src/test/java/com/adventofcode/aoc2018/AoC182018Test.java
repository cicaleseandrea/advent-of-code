package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

@RunWith(Parameterized.class)
public class AoC182018Test extends Generic {

    private static final Solution INSTANCE = new AoC182018();

    public AoC182018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, ".#.#...|#.\n" +
                        ".....#|##|\n" +
                        ".|..|...#.\n" +
                        "..|#.....#\n" +
                        "#.#|||#|#|\n" +
                        "...#.||...\n" +
                        ".|....|...\n" +
                        "||...#|.#|\n" +
                        "|.||||..|.\n" +
                        "...#.|..|.", "1147"},
                {FIRST, getInput(INSTANCE), "589931"},
                {SECOND, getInput(INSTANCE), "222332"}
        });
    }
}