package com.adventofcode.aoc2016;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC122016Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC122016();

    public AoC122016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        cpy 41 a
                        inc a
                        inc a
                        dec a
                        jnz a 2
                        dec a
                        """, "42" },
                { FIRST, getInput( INSTANCE ), "318117" },
                { SECOND, getInput( INSTANCE ), "9227771" },
        });
    }
}
