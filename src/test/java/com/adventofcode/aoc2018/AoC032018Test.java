package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC032018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC032018();

    public AoC032018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        #1 @ 1,3: 4x4
                        #2 @ 3,1: 4x4
                        #3 @ 5,5: 2x2""", "4"},
                {FIRST, getInput(INSTANCE), "113966"},
                {SECOND, """
                        #1 @ 1,3: 4x4
                        #2 @ 3,1: 4x4
                        #3 @ 5,5: 2x2""", "3"},
                {SECOND, getInput(INSTANCE), "235"},
        });
    }
}