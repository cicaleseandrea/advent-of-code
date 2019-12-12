package com.adventofcode.aoc2019;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC122019Test extends Generic {

    private static final Solution INSTANCE = new AoC122019();

    public AoC122019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "<x=-1, y=0, z=2>\n" + "<x=2, y=-10, z=-7>\n" + "<x=4, y=-8, z=8>\n" + "<x=3, y=5, z=-1>", "179" },
                { FIRST, "<x=-8, y=-10, z=0>\n" + "<x=5, y=5, z=10>\n" + "<x=2, y=-7, z=3>\n" + "<x=9, y=-8, z=-3>", "1940" },
                { FIRST, getInput( INSTANCE ), "14907" },
                { SECOND, "<x=-1, y=0, z=2>\n" + "<x=2, y=-10, z=-7>\n" + "<x=4, y=-8, z=8>\n" + "<x=3, y=5, z=-1>", "2772" },
                { SECOND, getInput( INSTANCE ), "467081194429464" }
        });
    }
}
