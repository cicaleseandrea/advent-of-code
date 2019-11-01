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
public class AoC102017Test extends Generic {

    private static final Solution INSTANCE = new AoC102017();

    public AoC102017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "3,4,1,5", "12"},
                {FIRST, getInput(INSTANCE), "4114"},
                {SECOND, "", "a2582a3a0e66e6e86e3812dcb672a272"},
                {SECOND, "AoC 2017", "33efeb34ea91902bb2f59c9920caa6cd"},
                {SECOND, "1,2,3", "3efbe78a8d82f29979031a4aa0b16a9d"},
                {SECOND, "1,2,4", "63960835bcdc130f0b66d7ff4f6a5a8e"},
                {SECOND, getInput(INSTANCE), "2f8c3d2100fdd57cec130d928b0fd2dd"}
        });
    }
}
