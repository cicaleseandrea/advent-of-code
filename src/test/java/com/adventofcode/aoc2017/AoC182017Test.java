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
public class AoC182017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC182017();

    public AoC182017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        set a 1
                        add a 2
                        mul a a
                        mod a 5
                        snd a
                        set a 0
                        rcv a
                        jgz a -1
                        set a 1
                        jgz a -2
                        """, "4" },
            { FIRST, getInput( INSTANCE ), "7071" },
            { SECOND, """
                        snd 1
                        snd 2
                        snd p
                        rcv a
                        rcv b
                        rcv c
                        rcv d
                        """, "3" },
            { SECOND, getInput( INSTANCE ), "8001" }
        });
    }
}
