package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC162018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC162018();

    public AoC162018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        Before: [3, 2, 1, 1]
                        9 2 1 2
                        After:  [3, 2, 2, 1]
                        
                        Before: [3, 2, 1, 1]
                        9 2 1 2
                        After:  [3, 2, 2, 1]""", "2"},
                {FIRST, getInput(INSTANCE), "590"},
                {SECOND, getInput(INSTANCE), "475"}
        });
    }
}