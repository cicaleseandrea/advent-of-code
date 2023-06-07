package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC172018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC172018();

    public AoC172018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        x=495, y=2..7
                        y=7, x=495..501
                        x=501, y=3..7
                        x=498, y=2..4
                        x=506, y=1..2
                        x=498, y=10..13
                        x=504, y=10..13
                        y=13, x=498..504""", "57"},
                {FIRST, getInput(INSTANCE), "27042"},
                {SECOND, """
                        x=495, y=2..7
                        y=7, x=495..501
                        x=501, y=3..7
                        x=498, y=2..4
                        x=506, y=1..2
                        x=498, y=10..13
                        x=504, y=10..13
                        y=13, x=498..504""", "29"},
                {SECOND, getInput(INSTANCE), "22214"}
        });
    }
}