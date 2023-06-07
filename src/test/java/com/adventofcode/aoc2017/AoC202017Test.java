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
public class AoC202017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC202017();

    public AoC202017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>
                        p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>
                        """, "0" },
            { FIRST, getInput( INSTANCE ), "376" },
            { SECOND, """
                        p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>
                        p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>
                        p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>
                        p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>
                        """, "1" },
            { SECOND, getInput( INSTANCE ), "574" }
        });
    }
}
