package com.adventofcode.aoc2017;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.Solution.getInput;

@RunWith(Parameterized.class)
public class AoC032017Test extends Generic {

    private static final Solution INSTANCE = new AoC032017();

    public AoC032017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "1", "0"},
                {FIRST, "2", "1"},
                {FIRST, "12", "3"},
                {FIRST, "23", "2"},
                {FIRST, "1024", "31"},
                {FIRST, "25", "4"},
                {FIRST, getInput(INSTANCE), "438"},
                {SECOND, "300", "304"},
                {SECOND, "100", "122"},
                {SECOND, "700", "747"},
                {SECOND, "747", "806"},
                {SECOND, getInput(INSTANCE), "266330"},
        });
    }
}