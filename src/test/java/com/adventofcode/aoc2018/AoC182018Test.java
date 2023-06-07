package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AoC182018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC182018();

    public AoC182018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        .#.#...|#.
                        .....#|##|
                        .|..|...#.
                        ..|#.....#
                        #.#|||#|#|
                        ...#.||...
                        .|....|...
                        ||...#|.#|
                        |.||||..|.
                        ...#.|..|.""", "1147"},
                {FIRST, getInput(INSTANCE), "589931"},
                {SECOND, getInput(INSTANCE), "222332"}
        });
    }
}