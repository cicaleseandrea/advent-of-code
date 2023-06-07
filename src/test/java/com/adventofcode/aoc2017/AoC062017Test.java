package com.adventofcode.aoc2017;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC062017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC062017();

    public AoC062017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "0\t2\t7\t0", "5"},
                {FIRST, getInput(INSTANCE), "12841"},
                {SECOND, "0\t2\t7\t0", "4"},
                {SECOND, getInput(INSTANCE), "8038"}
        });
    }
}