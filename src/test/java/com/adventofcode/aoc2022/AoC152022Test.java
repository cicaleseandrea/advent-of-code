package com.adventofcode.aoc2022;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC152022Test extends Generic {

    private static final Solution INSTANCE = new AoC152022();

    public AoC152022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
                        Sensor at x=9, y=16: closest beacon is at x=10, y=16
                        Sensor at x=13, y=2: closest beacon is at x=15, y=3
                        Sensor at x=12, y=14: closest beacon is at x=10, y=16
                        Sensor at x=10, y=20: closest beacon is at x=10, y=16
                        Sensor at x=14, y=17: closest beacon is at x=10, y=16
                        Sensor at x=8, y=7: closest beacon is at x=2, y=10
                        Sensor at x=2, y=0: closest beacon is at x=2, y=10
                        Sensor at x=0, y=11: closest beacon is at x=2, y=10
                        Sensor at x=20, y=14: closest beacon is at x=25, y=17
                        Sensor at x=17, y=20: closest beacon is at x=21, y=22
                        Sensor at x=16, y=7: closest beacon is at x=15, y=3
                        Sensor at x=14, y=3: closest beacon is at x=15, y=3
                        Sensor at x=20, y=1: closest beacon is at x=15, y=3
                        """, "26" },
                { FIRST, getInput( INSTANCE ), "4424278" },
                { SECOND, """
                        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
                        Sensor at x=9, y=16: closest beacon is at x=10, y=16
                        Sensor at x=13, y=2: closest beacon is at x=15, y=3
                        Sensor at x=12, y=14: closest beacon is at x=10, y=16
                        Sensor at x=10, y=20: closest beacon is at x=10, y=16
                        Sensor at x=14, y=17: closest beacon is at x=10, y=16
                        Sensor at x=8, y=7: closest beacon is at x=2, y=10
                        Sensor at x=2, y=0: closest beacon is at x=2, y=10
                        Sensor at x=0, y=11: closest beacon is at x=2, y=10
                        Sensor at x=20, y=14: closest beacon is at x=25, y=17
                        Sensor at x=17, y=20: closest beacon is at x=21, y=22
                        Sensor at x=16, y=7: closest beacon is at x=15, y=3
                        Sensor at x=14, y=3: closest beacon is at x=15, y=3
                        Sensor at x=20, y=1: closest beacon is at x=15, y=3
                        """, "56000011" },
                { SECOND, getInput( INSTANCE ), "10382630753392" }
        });
    }
}
