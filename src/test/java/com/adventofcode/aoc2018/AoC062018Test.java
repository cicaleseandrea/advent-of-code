package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC062018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC062018();

    public AoC062018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        1, 1
                        1, 6
                        8, 3
                        3, 4
                        5, 5
                        8, 9""", "17"},
                {FIRST, getInput(INSTANCE), "4589"},
                {SECOND, """
                        1, 1
                        1, 6
                        8, 3
                        3, 4
                        5, 5
                        8, 9""", "16"},
                {SECOND, getInput(INSTANCE), "40252"},
        });
    }
}