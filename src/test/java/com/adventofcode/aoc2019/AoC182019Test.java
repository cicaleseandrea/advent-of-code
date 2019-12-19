package com.adventofcode.aoc2019;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC182019Test extends Generic {

    private static final Solution INSTANCE = new AoC182019();

    public AoC182019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST,
                    "#########\n" +
                    "#b.A.@.a#\n" +
                    "#########", "8" },
                { FIRST,
                    "########################\n" +
                    "#f.D.E.e.C.b.A.@.a.B.c.#\n" +
                    "######################.#\n" +
                    "#d.....................#\n" +
                    "########################", "86" },
                { FIRST,
                    "########################\n" +
                    "#...............b.C.D.f#\n" +
                    "#.######################\n" +
                    "#.....@.a.B.c.d.A.e.F.g#\n" +
                    "########################", "132" },
                { FIRST,
                    "#################\n" +
                    "#i.G..c...e..H.p#\n" +
                    "########.########\n" +
                    "#j.A..b...f..D.o#\n" +
                    "########@########\n" +
                    "#k.E..a...g..B.n#\n" +
                    "########.########\n" +
                    "#l.F..d...h..C.m#\n" +
                    "#################", "136" },
                { FIRST,
                    "########################\n" +
                    "#@..............ac.GI.b#\n" +
                    "###d#e#f################\n" +
                    "###A#B#C################\n" +
                    "###g#h#i################\n" +
                    "########################", "81" },
                { FIRST, getInput( INSTANCE ), "" },
                { SECOND, "", "" },
                { SECOND, getInput( INSTANCE ), "" }
        });
    }
}
