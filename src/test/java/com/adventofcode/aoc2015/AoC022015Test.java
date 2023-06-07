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
public class AoC022015Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC022015();

    public AoC022015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "2x3x4", "58"},
                {FIRST, "1x1x10", "43"},
                {FIRST, getInput(INSTANCE), "1598415"},
                {SECOND, "2x3x4", "34"},
                {SECOND, "1x1x10", "14"},
                {SECOND, getInput(INSTANCE), "3812909"},
        });
    }
}