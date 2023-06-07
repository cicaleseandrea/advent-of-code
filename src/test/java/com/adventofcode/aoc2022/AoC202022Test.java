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
public class AoC202022Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC202022();

    public AoC202022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        1
                        2
                        -3
                        3
                        -2
                        0
                        4
                        """, "3" },
                { FIRST, getInput( INSTANCE ), "7004" },
                { SECOND, """
                        1
                        2
                        -3
                        3
                        -2
                        0
                        4
                        """, "1623178306" },
                { SECOND, getInput( INSTANCE ), "17200008919529" }
        });
    }
}
