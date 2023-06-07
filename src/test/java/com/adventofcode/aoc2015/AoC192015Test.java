package com.adventofcode.aoc2015;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC192015Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC192015();

    public AoC192015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        H => HO
                        H => OH
                        O => HH
                        
                        HOH
                        """, "4" },
                { FIRST, """
                        H => HO
                        H => OH
                        O => HH
                        
                        HOHOHO
                        """, "7" },
                { FIRST, getInput( INSTANCE ), "518" },
                { SECOND, """
                        e => H
                        e => O
                        H => HO
                        H => OH
                        O => HH
                        
                        HOH
                        """, "3" },
                { SECOND, """
                        e => H
                        e => O
                        H => HO
                        H => OH
                        O => HH
                        
                        HOHOHO
                        """, "6" },
                { SECOND, getInput( INSTANCE ), "200" }
        });
    }
}
