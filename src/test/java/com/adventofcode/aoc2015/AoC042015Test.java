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
public class AoC042015Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC042015();

    public AoC042015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "abcdef", "609043"},
                {FIRST, "pqrstuv", "1048970"},
                {FIRST, getInput(INSTANCE), "282749"},
                {SECOND, getInput(INSTANCE), "9962624"},
        });
    }
}