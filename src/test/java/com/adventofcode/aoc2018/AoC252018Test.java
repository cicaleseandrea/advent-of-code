package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.Solution.getInput;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;

@RunWith(Parameterized.class)
public class AoC252018Test extends Generic {

    private static final Solution INSTANCE = new AoC252018();

    public AoC252018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "0,0,0,0\n" +
                        "3,0,0,0\n" +
                        "0,3,0,0\n" +
                        "0,0,3,0\n" +
                        "0,0,0,3\n" +
                        "0,0,0,6\n" +
                        "9,0,0,0\n" +
                        "12,0,0,0", "2"},
                {FIRST, "-1,2,2,0\n" +
                        "0,0,2,-2\n" +
                        "0,0,0,-2\n" +
                        "-1,2,0,0\n" +
                        "-2,-2,-2,2\n" +
                        "3,0,2,-1\n" +
                        "-1,3,2,2\n" +
                        "-1,0,-1,0\n" +
                        "0,2,1,-2\n" +
                        "3,0,0,0", "4"},
                {FIRST, "1,-1,0,1\n" +
                        "2,0,-1,0\n" +
                        "3,2,-1,0\n" +
                        "0,0,3,1\n" +
                        "0,0,-1,-1\n" +
                        "2,3,-2,0\n" +
                        "-2,2,0,0\n" +
                        "2,-2,0,-1\n" +
                        "1,-1,0,-1\n" +
                        "3,2,0,2", "3"},
                {FIRST, "1,-1,-1,-2\n" +
                        "-2,-2,0,1\n" +
                        "0,2,1,3\n" +
                        "-2,3,-2,1\n" +
                        "0,2,3,-2\n" +
                        "-1,-1,1,-2\n" +
                        "0,-2,-1,0\n" +
                        "-2,2,3,-1\n" +
                        "1,2,2,0\n" +
                        "-1,-2,0,-2", "8"},
                {FIRST, getInput(INSTANCE), "305"},
                {SECOND, getInput(INSTANCE), MERRY_CHRISTMAS}
        });
    }
}