package com.adventofcode.aoc2019;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC062019Test extends Generic {

    private static final Solution INSTANCE = new AoC062019();

    public AoC062019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST,
                  "COM)B\n" + "B)C\n" + "C)D\n" + "D)E\n" + "E)F\n" + "B)G\n" + "G)H\n" + "D)I\n" + "E)J\n" + "J)K\n" + "K)L",
                  "42" },
                { FIRST, getInput( INSTANCE ), "117672" },
                { SECOND,
                  "COM)B\n" + "B)C\n" + "C)D\n" + "D)E\n" + "E)F\n" + "B)G\n" + "G)H\n" + "D)I\n" + "E)J\n" + "J)K\n" + "K)L\n" + "K)YOU\n" + "I)SAN",
                  "4" },
                { SECOND, getInput(INSTANCE), "277" }
        });
    }
}
