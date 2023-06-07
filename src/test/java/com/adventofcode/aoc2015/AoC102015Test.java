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
public class AoC102015Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC102015();

    public AoC102015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "1", "2"},
                {FIRST, "11", "2"},
                {FIRST, "21", "4"},
                {FIRST, "1211", "6"},
                {FIRST, "111221", "6"},
                {FIRST, getInput(INSTANCE), "492982"},
                {SECOND, getInput(INSTANCE), "6989950"},
        });
    }
}