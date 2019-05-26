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
public class AoC232018Test extends Generic {

    static private Solution INSTANCE = new AoC232018();

    public AoC232018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "pos=<0,0,0>, r=4\n" +
                        "pos=<1,0,0>, r=1\n" +
                        "pos=<4,0,0>, r=3\n" +
                        "pos=<0,2,0>, r=1\n" +
                        "pos=<0,5,0>, r=3\n" +
                        "pos=<0,0,3>, r=1\n" +
                        "pos=<1,1,1>, r=1\n" +
                        "pos=<1,1,2>, r=1\n" +
                        "pos=<1,3,1>, r=1", "7"},
                {FIRST, getInput(INSTANCE), "410"},
                {SECOND, "pos=<10,12,12>, r=2\n" +
                        "pos=<12,14,12>, r=2\n" +
                        "pos=<16,12,12>, r=4\n" +
                        "pos=<14,14,14>, r=6\n" +
                        "pos=<50,50,50>, r=200\n" +
                        "pos=<10,10,10>, r=5", "36"},
                {SECOND, getInput(INSTANCE), "119188816"}
        });
    }
}