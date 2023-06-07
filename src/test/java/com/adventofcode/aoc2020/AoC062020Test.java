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
public class AoC062020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC062020();

    public AoC062020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        abc
                        
                        a
                        b
                        c
                        
                        ab
                        ac
                        
                        a
                        a
                        a
                        a
                        
                        b
                        """, "11" },
                { FIRST, getInput( INSTANCE ), "6457" },
                { SECOND, """
                        abc
                        
                        a
                        b
                        c
                        
                        ab
                        ac
                        
                        a
                        a
                        a
                        a
                        
                        b
                        """, "6" },
                { SECOND, getInput( INSTANCE ), "3260" }
        });
    }
}
