package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC132018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC132018();

    public AoC132018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        /->-\\       \s
                        |   |  /----\\
                        | /-+--+-\\  |
                        | | |  | v  |
                        \\-+-/  \\-+--/
                          \\------/  \s""", "7,3"},
                //{FIRST, getInput(INSTANCE), "123,18"},
                {SECOND, """
                        />-<\\ \s
                        |   | \s
                        | /<+-\\
                        | | | v
                        \\>+</ |
                          |   ^
                          \\<->/""", "6,4"},
                {SECOND, getInput(INSTANCE), "71,123"}
        });
    }
}