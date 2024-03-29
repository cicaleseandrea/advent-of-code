package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC102018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC102018();

    public AoC102018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        position=< 9,  1> velocity=< 0,  2>
                        position=< 7,  0> velocity=<-1,  0>
                        position=< 3, -2> velocity=<-1,  1>
                        position=< 6, 10> velocity=<-2, -1>
                        position=< 2, -4> velocity=< 2,  2>
                        position=<-6, 10> velocity=< 2, -2>
                        position=< 1,  8> velocity=< 1, -1>
                        position=< 1,  7> velocity=< 1,  0>
                        position=<-3, 11> velocity=< 1, -2>
                        position=< 7,  6> velocity=<-1, -1>
                        position=<-2,  3> velocity=< 1,  0>
                        position=<-4,  3> velocity=< 2,  0>
                        position=<10, -3> velocity=<-1,  1>
                        position=< 5, 11> velocity=< 1, -2>
                        position=< 4,  7> velocity=< 0, -1>
                        position=< 8, -2> velocity=< 0,  1>
                        position=<15,  0> velocity=<-2,  0>
                        position=< 1,  6> velocity=< 1,  0>
                        position=< 8,  9> velocity=< 0, -1>
                        position=< 3,  3> velocity=<-1,  1>
                        position=< 0,  5> velocity=< 0, -1>
                        position=<-2,  2> velocity=< 2,  0>
                        position=< 5, -2> velocity=< 1,  2>
                        position=< 1,  4> velocity=< 2,  1>
                        position=<-2,  7> velocity=< 2, -2>
                        position=< 3,  6> velocity=<-1, -1>
                        position=< 5,  0> velocity=< 1,  0>
                        position=<-6,  0> velocity=< 2,  0>
                        position=< 5,  9> velocity=< 1, -2>
                        position=<14,  7> velocity=<-2,  0>
                        position=<-3,  6> velocity=< 2, -1>""",
                        """
                        ⬛⬜⬜⬜⬛⬜⬜⬛⬛⬛
                        ⬛⬜⬜⬜⬛⬜⬜⬜⬛⬜
                        ⬛⬜⬜⬜⬛⬜⬜⬜⬛⬜
                        ⬛⬛⬛⬛⬛⬜⬜⬜⬛⬜
                        ⬛⬜⬜⬜⬛⬜⬜⬜⬛⬜
                        ⬛⬜⬜⬜⬛⬜⬜⬜⬛⬜
                        ⬛⬜⬜⬜⬛⬜⬜⬜⬛⬜
                        ⬛⬜⬜⬜⬛⬜⬜⬛⬛⬛"""},
                { FIRST, getInput(INSTANCE),
                        """
                        ⬛⬛⬛⬛⬛⬛⬜⬜⬛⬛⬛⬛⬛⬜⬜⬜⬛⬛⬛⬛⬛⬛⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬛⬛⬜⬜⬛⬛⬛⬛⬛⬛⬜⬜⬜⬛⬛⬛⬛⬜
                        ⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬛⬜⬜⬜⬛⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬛⬜⬜⬜⬜⬛
                        ⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬛⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬛⬜⬜⬜⬜⬜
                        ⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬛⬜⬛⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬛⬜⬜⬜⬜⬜
                        ⬛⬛⬛⬛⬛⬜⬜⬜⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬛⬛⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜
                        ⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬛⬛⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬛⬜⬜⬛⬛⬛
                        ⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬛⬜⬛⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬛
                        ⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬛⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬛
                        ⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬛⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬛⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬛⬛
                        ⬛⬜⬜⬜⬜⬜⬜⬜⬛⬜⬜⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛⬛⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬛⬛⬛⬛⬛⬜⬜⬜⬛⬛⬛⬜⬜⬜⬜⬛⬛⬛⬛⬛⬛⬜⬜⬜⬛⬛⬛⬜⬛"""
                },
                { SECOND, """
                        position=< 9,  1> velocity=< 0,  2>
                        position=< 7,  0> velocity=<-1,  0>
                        position=< 3, -2> velocity=<-1,  1>
                        position=< 6, 10> velocity=<-2, -1>
                        position=< 2, -4> velocity=< 2,  2>
                        position=<-6, 10> velocity=< 2, -2>
                        position=< 1,  8> velocity=< 1, -1>
                        position=< 1,  7> velocity=< 1,  0>
                        position=<-3, 11> velocity=< 1, -2>
                        position=< 7,  6> velocity=<-1, -1>
                        position=<-2,  3> velocity=< 1,  0>
                        position=<-4,  3> velocity=< 2,  0>
                        position=<10, -3> velocity=<-1,  1>
                        position=< 5, 11> velocity=< 1, -2>
                        position=< 4,  7> velocity=< 0, -1>
                        position=< 8, -2> velocity=< 0,  1>
                        position=<15,  0> velocity=<-2,  0>
                        position=< 1,  6> velocity=< 1,  0>
                        position=< 8,  9> velocity=< 0, -1>
                        position=< 3,  3> velocity=<-1,  1>
                        position=< 0,  5> velocity=< 0, -1>
                        position=<-2,  2> velocity=< 2,  0>
                        position=< 5, -2> velocity=< 1,  2>
                        position=< 1,  4> velocity=< 2,  1>
                        position=<-2,  7> velocity=< 2, -2>
                        position=< 3,  6> velocity=<-1, -1>
                        position=< 5,  0> velocity=< 1,  0>
                        position=<-6,  0> velocity=< 2,  0>
                        position=< 5,  9> velocity=< 1, -2>
                        position=<14,  7> velocity=<-2,  0>
                        position=<-3,  6> velocity=< 2, -1>""", "3"
                },
                { SECOND, getInput(INSTANCE), "10867" }
        });
    }
}