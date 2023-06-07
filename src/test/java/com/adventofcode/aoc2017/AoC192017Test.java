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
public class AoC192017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC192017();

    public AoC192017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                             |         
                             |  +--+   
                             A  |  C   
                         F---|----E|--+
                             |  |  |  D
                             +B-+  +--+
                        """, "ABCDEF" },
            { FIRST, getInput( INSTANCE ), "FEZDNIVJWT" },
            { SECOND, """
                             |         
                             |  +--+   
                             A  |  C   
                         F---|----E|--+
                             |  |  |  D
                             +B-+  +--+
                        """, "38" },
            { SECOND, getInput( INSTANCE ), "17200" }
        });
    }
}
