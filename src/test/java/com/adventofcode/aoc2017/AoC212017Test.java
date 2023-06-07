package com.adventofcode.aoc2017;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class AoC212017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC212017();

    public AoC212017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        ../.# => ##./#../...
                        .#./..#/### => #..#/..../..../#..#
                        """, "12" },
            { FIRST, getInput( INSTANCE ), "173" },
            { SECOND, getInput( INSTANCE ), "2456178" }
        });
    }
}
