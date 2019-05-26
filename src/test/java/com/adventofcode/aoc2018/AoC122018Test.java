package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.Solution.getInput;

@RunWith(Parameterized.class)
public class AoC122018Test extends Generic {

    static private Solution INSTANCE = new AoC122018();

    public AoC122018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "initial state: #..#.#..##......###...###\n" +
                        "\n" +
                        "...## => #\n" +
                        "..#.. => #\n" +
                        ".#... => #\n" +
                        ".#.#. => #\n" +
                        ".#.## => #\n" +
                        ".##.. => #\n" +
                        ".#### => #\n" +
                        "#.#.# => #\n" +
                        "#.### => #\n" +
                        "##.#. => #\n" +
                        "##.## => #\n" +
                        "###.. => #\n" +
                        "###.# => #\n" +
                        "####. => #", "325"},
                {FIRST, getInput(INSTANCE), "3405"},
                {SECOND, getInput(INSTANCE), "3350000000000"}
        });
    }
}