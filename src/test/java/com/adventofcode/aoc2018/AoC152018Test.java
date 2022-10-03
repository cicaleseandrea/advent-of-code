package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC152018Test extends Generic {

    private static final Solution INSTANCE = new AoC152018();

    public AoC152018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        #######
                        #.G...#
                        #...EG#
                        #.#.#G#
                        #..G#E#
                        #.....#
                        #######""", "27730"},
                {FIRST, """
                        #######
                        #G..#E#
                        #E#E.E#
                        #G.##.#
                        #...#E#
                        #...E.#
                        #######""", "36334"},
                {FIRST, """
                        #######
                        #E..EG#
                        #.#G.E#
                        #E.##E#
                        #G..#.#
                        #..E#.#
                        #######""", "39514"},
                {FIRST, """
                        #######
                        #E.G#.#
                        #.#G..#
                        #G.#.G#
                        #G..#.#
                        #...E.#
                        #######""", "27755"},
                {FIRST, """
                        #######
                        #.E...#
                        #.#..G#
                        #.###.#
                        #E#G#G#
                        #...#G#
                        #######""", "28944"},
                {FIRST, """
                        #########
                        #G......#
                        #.E.#...#
                        #..##..G#
                        #...##..#
                        #...#...#
                        #.G...G.#
                        #.....G.#
                        #########""", "18740"},
                {FIRST, getInput(INSTANCE), "216270"},
                {SECOND, """
                        #######
                        #.G...#
                        #...EG#
                        #.#.#G#
                        #..G#E#
                        #.....#
                        #######""", "4988"},
                {SECOND, """
                        #######
                        #E..EG#
                        #.#G.E#
                        #E.##E#
                        #G..#.#
                        #..E#.#
                        #######""", "31284"},
                {SECOND, """
                        #######
                        #E.G#.#
                        #.#G..#
                        #G.#.G#
                        #G..#.#
                        #...E.#
                        #######""", "3478"},
                {SECOND, """
                        #######
                        #.E...#
                        #.#..G#
                        #.###.#
                        #E#G#G#
                        #...#G#
                        #######""", "6474"},
                {SECOND, """
                        #########
                        #G......#
                        #.E.#...#
                        #..##..G#
                        #...##..#
                        #...#...#
                        #.G...G.#
                        #.....G.#
                        #########""", "1140"},
                {SECOND, getInput(INSTANCE), "59339"}
        });
    }
}