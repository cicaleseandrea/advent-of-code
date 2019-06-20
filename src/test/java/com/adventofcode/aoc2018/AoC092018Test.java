package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.Solution.getInput;

@RunWith(Parameterized.class)
public class AoC092018Test extends Generic {

    private static final Solution INSTANCE = new AoC092018();

    public AoC092018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "9 players; last marble is worth 25 points", "32"},
                {FIRST, "10 players; last marble is worth 1618 points", "8317"},
                {FIRST, "13 players; last marble is worth 7999 points", "146373"},
                {FIRST, "17 players; last marble is worth 1104 points", "2764"},
                {FIRST, "21 players; last marble is worth 6111 points", "54718"},
                {FIRST, "30 players; last marble is worth 5807 points", "37305"},
                {FIRST, getInput(INSTANCE), "361466"},
                {SECOND, getInput(INSTANCE), "2945918550"}
        });
    }
}