package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC112018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC112018();

    public AoC112018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "18", "33,45"},
                {FIRST, "42", "21,61"},
                {FIRST, getInput(INSTANCE), "34,72"},
                {SECOND, "18", "90,269,16"},
                {SECOND, "42", "232,251,12"},
                {SECOND, getInput(INSTANCE), "233,187,13"}
        });
    }
}