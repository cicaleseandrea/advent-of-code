package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AoC252018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC252018();

    public AoC252018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        0,0,0,0
                        3,0,0,0
                        0,3,0,0
                        0,0,3,0
                        0,0,0,3
                        0,0,0,6
                        9,0,0,0
                        12,0,0,0""", "2"},
                {FIRST, """
                        -1,2,2,0
                        0,0,2,-2
                        0,0,0,-2
                        -1,2,0,0
                        -2,-2,-2,2
                        3,0,2,-1
                        -1,3,2,2
                        -1,0,-1,0
                        0,2,1,-2
                        3,0,0,0""", "4"},
                {FIRST, """
                        1,-1,0,1
                        2,0,-1,0
                        3,2,-1,0
                        0,0,3,1
                        0,0,-1,-1
                        2,3,-2,0
                        -2,2,0,0
                        2,-2,0,-1
                        1,-1,0,-1
                        3,2,0,2""", "3"},
                {FIRST, """
                        1,-1,-1,-2
                        -2,-2,0,1
                        0,2,1,3
                        -2,3,-2,1
                        0,2,3,-2
                        -1,-1,1,-2
                        0,-2,-1,0
                        -2,2,3,-1
                        1,2,2,0
                        -1,-2,0,-2""", "8"},
                {FIRST, getInput(INSTANCE), "305"},
                {SECOND, getInput(INSTANCE), MERRY_CHRISTMAS}
        });
    }
}