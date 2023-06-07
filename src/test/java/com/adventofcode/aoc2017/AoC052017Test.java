package com.adventofcode.aoc2017;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC052017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC052017();

    public AoC052017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        0
                        3
                        0
                        1
                        -3
                        """, "5"},
                {FIRST, getInput(INSTANCE), "355965"},
                {SECOND, """
                        0
                        3
                        0
                        1
                        -3
                        """, "10"},
                {SECOND, getInput(INSTANCE), "26948068"}
        });
    }
}