package com.adventofcode.aoc2022;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC042022Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC042022();

    public AoC042022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        2-4,6-8
                        2-3,4-5
                        5-7,7-9
                        2-8,3-7
                        6-6,4-6
                        2-6,4-8
                        """, "2" },
                { FIRST, getInput( INSTANCE ), "498" },
                { SECOND, """
                        2-4,6-8
                        2-3,4-5
                        5-7,7-9
                        2-8,3-7
                        6-6,4-6
                        2-6,4-8
                        """, "4" },
                { SECOND, getInput( INSTANCE ), "859" }
        });
    }
}
