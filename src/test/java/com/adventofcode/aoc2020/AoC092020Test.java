package com.adventofcode.aoc2020;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC092020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC092020();

    public AoC092020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        35
                        20
                        15
                        25
                        47
                        40
                        62
                        55
                        65
                        95
                        102
                        117
                        150
                        182
                        127
                        219
                        299
                        277
                        309
                        576
                        """, "127" },
                { FIRST, getInput( INSTANCE ), "57195069" },
                { SECOND, """
                        35
                        20
                        15
                        25
                        47
                        40
                        62
                        55
                        65
                        95
                        102
                        117
                        150
                        182
                        127
                        219
                        299
                        277
                        309
                        576
                        """, "62"  },
                { SECOND, getInput( INSTANCE ), "7409241" }
        });
    }
}
