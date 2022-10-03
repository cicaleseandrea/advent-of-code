package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC192018Test extends Generic {

    private static final Solution INSTANCE = new AoC192018();

    public AoC192018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        #ip 0
                        seti 5 0 1
                        seti 6 0 2
                        addi 0 1 0
                        addr 1 2 3
                        setr 1 0 0
                        seti 8 0 4
                        seti 9 0 5""", "7"},
                {FIRST, getInput(INSTANCE), "888"},
                {SECOND, getInput(INSTANCE), "10708992"}
        });
    }
}