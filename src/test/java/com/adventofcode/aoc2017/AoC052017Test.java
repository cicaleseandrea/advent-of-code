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
public class AoC052017Test extends Generic {

    static private Solution INSTANCE = new AoC052017();

    public AoC052017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "0\n" +
                        "3\n" +
                        "0\n" +
                        "1\n" +
                        "-3\n", "5"},
                {FIRST, getInput(INSTANCE), "355965"},
                {SECOND, "0\n" +
                        "3\n" +
                        "0\n" +
                        "1\n" +
                        "-3\n", "10"},
                {SECOND, getInput(INSTANCE), "26948068"}
        });
    }
}