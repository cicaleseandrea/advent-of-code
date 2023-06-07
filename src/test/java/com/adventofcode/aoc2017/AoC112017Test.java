package com.adventofcode.aoc2017;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC112017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC112017();

    public AoC112017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "ne,ne,ne", "3"},
                {FIRST, "ne,ne,sw,sw", "0"},
                {FIRST, "ne,ne,s,s", "2"},
                {FIRST, "se,sw,se,sw,sw", "3"},
                {FIRST, getInput(INSTANCE), "834"},
                {SECOND, getInput(INSTANCE), "1569"}
        });
    }
}
