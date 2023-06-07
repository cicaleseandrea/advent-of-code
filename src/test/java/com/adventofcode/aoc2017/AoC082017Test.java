package com.adventofcode.aoc2017;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC082017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC082017();

    public AoC082017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        b inc 5 if a > 1
                        a inc 1 if b < 5
                        c dec -10 if a >= 1
                        c inc -20 if c == 10""", "1"},
                {FIRST, getInput(INSTANCE), "3745"},
                {SECOND, """
                        b inc 5 if a > 1
                        a inc 1 if b < 5
                        c dec -10 if a >= 1
                        c inc -20 if c == 10""", "10"},
                {SECOND, getInput(INSTANCE), "4644"}
        });
    }
}
