package com.adventofcode.aoc2021;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC252021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC252021();

    public AoC252021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        v...>>.vv>
                        .vv>>.vv..
                        >>.>v>...v
                        >>v>>.>.v.
                        v>v.vv.v..
                        >.>>..v...
                        .vv..>.>v.
                        v.v..>>v.v
                        ....v..v.>
                        """, "58" },
                { FIRST, getInput( INSTANCE ), "530" },
                { SECOND, getInput( INSTANCE ), MERRY_CHRISTMAS }
        });
    }

}
