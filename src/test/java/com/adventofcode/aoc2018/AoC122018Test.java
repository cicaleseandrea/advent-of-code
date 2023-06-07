package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC122018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC122018();

    public AoC122018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        initial state: #..#.#..##......###...###
                        
                        ...## => #
                        ..#.. => #
                        .#... => #
                        .#.#. => #
                        .#.## => #
                        .##.. => #
                        .#### => #
                        #.#.# => #
                        #.### => #
                        ##.#. => #
                        ##.## => #
                        ###.. => #
                        ###.# => #
                        ####. => #""", "325"},
                {FIRST, getInput(INSTANCE), "3405"},
                {SECOND, getInput(INSTANCE), "3350000000000"}
        });
    }
}