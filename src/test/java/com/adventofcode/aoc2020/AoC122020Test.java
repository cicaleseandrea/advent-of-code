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
public class AoC122020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC122020();

    public AoC122020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        F10
                        N3
                        F7
                        R90
                        F11
                        """, "25" },
                { FIRST, getInput( INSTANCE ), "882" },
                { SECOND, """
                        F10
                        N3
                        F7
                        R90
                        F11
                        """, "286"  },
                { SECOND, getInput( INSTANCE ), "28885" }
        });
    }
}
