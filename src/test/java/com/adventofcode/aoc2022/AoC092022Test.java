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
public class AoC092022Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC092022();

    public AoC092022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        R 4
                        U 4
                        L 3
                        D 1
                        R 4
                        D 1
                        L 5
                        R 2
                        """, "13" },
                { FIRST, getInput( INSTANCE ), "6494" },
                { SECOND, """
                        R 4
                        U 4
                        L 3
                        D 1
                        R 4
                        D 1
                        L 5
                        R 2
                        """, "1" },
                { SECOND, """
                        R 5
                        U 8
                        L 8
                        D 3
                        R 17
                        D 10
                        L 25
                        U 20
                        """, "36" },
                { SECOND, getInput( INSTANCE ), "2691" }
        });
    }
}
