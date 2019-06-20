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
public class AoC152018Test extends Generic {

    private static final Solution INSTANCE = new AoC152018();

    public AoC152018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "#######\n" +
                        "#.G...#\n" +
                        "#...EG#\n" +
                        "#.#.#G#\n" +
                        "#..G#E#\n" +
                        "#.....#\n" +
                        "#######", "27730"},
                {FIRST, "#######\n" +
                        "#G..#E#\n" +
                        "#E#E.E#\n" +
                        "#G.##.#\n" +
                        "#...#E#\n" +
                        "#...E.#\n" +
                        "#######", "36334"},
                {FIRST, "#######\n" +
                        "#E..EG#\n" +
                        "#.#G.E#\n" +
                        "#E.##E#\n" +
                        "#G..#.#\n" +
                        "#..E#.#\n" +
                        "#######", "39514"},
                {FIRST, "#######\n" +
                        "#E.G#.#\n" +
                        "#.#G..#\n" +
                        "#G.#.G#\n" +
                        "#G..#.#\n" +
                        "#...E.#\n" +
                        "#######", "27755"},
                {FIRST, "#######\n" +
                        "#.E...#\n" +
                        "#.#..G#\n" +
                        "#.###.#\n" +
                        "#E#G#G#\n" +
                        "#...#G#\n" +
                        "#######", "28944"},
                {FIRST, "#########\n" +
                        "#G......#\n" +
                        "#.E.#...#\n" +
                        "#..##..G#\n" +
                        "#...##..#\n" +
                        "#...#...#\n" +
                        "#.G...G.#\n" +
                        "#.....G.#\n" +
                        "#########", "18740"},
                {FIRST, getInput(INSTANCE), "216270"},
                {SECOND, "#######\n" +
                        "#.G...#\n" +
                        "#...EG#\n" +
                        "#.#.#G#\n" +
                        "#..G#E#\n" +
                        "#.....#\n" +
                        "#######", "4988"},
                {SECOND, "#######\n" +
                        "#E..EG#\n" +
                        "#.#G.E#\n" +
                        "#E.##E#\n" +
                        "#G..#.#\n" +
                        "#..E#.#\n" +
                        "#######", "31284"},
                {SECOND, "#######\n" +
                        "#E.G#.#\n" +
                        "#.#G..#\n" +
                        "#G.#.G#\n" +
                        "#G..#.#\n" +
                        "#...E.#\n" +
                        "#######", "3478"},
                {SECOND, "#######\n" +
                        "#.E...#\n" +
                        "#.#..G#\n" +
                        "#.###.#\n" +
                        "#E#G#G#\n" +
                        "#...#G#\n" +
                        "#######", "6474"},
                {SECOND, "#########\n" +
                        "#G......#\n" +
                        "#.E.#...#\n" +
                        "#..##..G#\n" +
                        "#...##..#\n" +
                        "#...#...#\n" +
                        "#.G...G.#\n" +
                        "#.....G.#\n" +
                        "#########", "1140"},
                {SECOND, getInput(INSTANCE), "59339"}
        });
    }
}