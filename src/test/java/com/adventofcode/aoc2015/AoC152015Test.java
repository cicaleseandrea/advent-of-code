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
public class AoC152015Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC152015();

    public AoC152015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                        Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
                        """, "62842880"},
                {FIRST, getInput(INSTANCE), "13882464"},
                {SECOND, """
                        Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                        Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
                        """, "57600000"},
                {SECOND, getInput(INSTANCE), "11171160"},
        });
    }
}
