package com.adventofcode.aoc2017;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC032017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC032017();

    public AoC032017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "1", "0"},
                {FIRST, "2", "1"},
                {FIRST, "12", "3"},
                {FIRST, "23", "2"},
                {FIRST, "1024", "31"},
                {FIRST, "25", "4"},
                {FIRST, getInput(INSTANCE), "438"},
                {SECOND, "300", "304"},
                {SECOND, "100", "122"},
                {SECOND, "700", "747"},
                {SECOND, "747", "806"},
                {SECOND, getInput(INSTANCE), "266330"},
        });
    }
}