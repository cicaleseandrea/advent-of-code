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
public class AoC052018Test extends Generic {

    private static final Solution INSTANCE = new AoC052018();

    public AoC052018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "aA", "0"},
                {FIRST, "abBA", "0"},
                {FIRST, "abAB", "4"},
                {FIRST, "aBaAAB", "4"},
                {FIRST, "aabAAB", "6"},
                {FIRST, getInput(INSTANCE), "9370"},
                {SECOND, "dabAcCaCBAcCcaDA", "4"},
                {SECOND, getInput(INSTANCE), "6390"},
        });
    }
}