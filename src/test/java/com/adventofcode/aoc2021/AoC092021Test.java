package com.adventofcode.aoc2021;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC092021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC092021();

    public AoC092021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        2199943210
                        3987894921
                        9856789892
                        8767896789
                        9899965678
                        """, "15" },
                { FIRST, getInput( INSTANCE ), "496" },
                { SECOND, """
                        2199943210
                        3987894921
                        9856789892
                        8767896789
                        9899965678
                        """, "1134" },
                { SECOND, getInput( INSTANCE ), "902880" }
        });
    }
}
