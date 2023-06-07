package com.adventofcode.aoc2015;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC132015Test extends Generic {

    private static final Solution INSTANCE = new AoC132015();

    public AoC132015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        Alice would gain 54 happiness units by sitting next to Bob.
                        Alice would lose 79 happiness units by sitting next to Carol.
                        Alice would lose 2 happiness units by sitting next to David.
                        Bob would gain 83 happiness units by sitting next to Alice.
                        Bob would lose 7 happiness units by sitting next to Carol.
                        Bob would lose 63 happiness units by sitting next to David.
                        Carol would lose 62 happiness units by sitting next to Alice.
                        Carol would gain 60 happiness units by sitting next to Bob.
                        Carol would gain 55 happiness units by sitting next to David.
                        David would gain 46 happiness units by sitting next to Alice.
                        David would lose 7 happiness units by sitting next to Bob.
                        David would gain 41 happiness units by sitting next to Carol.
                        """, "330"},
                {FIRST, getInput(INSTANCE), "618"},
                {SECOND, getInput(INSTANCE), "601"},
        });
    }
}
