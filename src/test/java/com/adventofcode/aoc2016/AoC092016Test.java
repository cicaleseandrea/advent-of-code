package com.adventofcode.aoc2016;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC092016Test extends Generic {

    private static final Solution INSTANCE = new AoC092016();

    public AoC092016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "ADVENT", "6" },
                { FIRST, "A(1x5)BC", "7" },
                { FIRST, "(3x3)XYZ", "9" },
                { FIRST, "(6x1)(1x3)A", "6" },
                { FIRST, "X(8x2)(3x3)ABCY", "18" },
                { FIRST, getInput( INSTANCE ), "112830" },
                { SECOND, "(3x3)XYZ", "9" },
                { SECOND, "X(8x2)(3x3)ABCY", "20" },
                { SECOND, "(27x12)(20x12)(13x14)(7x10)(1x12)A", "241920" },
                { SECOND, "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN", "445" },
                { SECOND, getInput( INSTANCE ), "10931789799" },
        });
    }
}
