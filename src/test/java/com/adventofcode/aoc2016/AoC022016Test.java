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
public class AoC022016Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC022016();

    public AoC022016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        ULL
                        RRDDD
                        LURDL
                        UUUUD
                        """, "1985"},
                {FIRST, getInput(INSTANCE), "19636"},
                { SECOND, """
                        ULL
                        RRDDD
                        LURDL
                        UUUUD
                        """, "5DB3"},
                {SECOND, getInput(INSTANCE), "3CC43"},
        });
    }
}
