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
public class AoC112021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC112021();

    public AoC112021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        5483143223
                        2745854711
                        5264556173
                        6141336146
                        6357385478
                        4167524645
                        2176841721
                        6882881134
                        4846848554
                        5283751526
                        """, "1656" },
                { FIRST, getInput( INSTANCE ), "1617" },
                { SECOND, """
                        5483143223
                        2745854711
                        5264556173
                        6141336146
                        6357385478
                        4167524645
                        2176841721
                        6882881134
                        4846848554
                        5283751526
                        """, "195" },
                { SECOND, getInput( INSTANCE ), "258" }
        });
    }
}
