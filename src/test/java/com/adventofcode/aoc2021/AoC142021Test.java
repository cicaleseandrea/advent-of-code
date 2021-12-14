package com.adventofcode.aoc2021;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC142021Test extends Generic {

    private static final Solution INSTANCE = new AoC142021();

    public AoC142021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        NNCB
                        
                        CH -> B
                        HH -> N
                        CB -> H
                        NH -> C
                        HB -> C
                        HC -> B
                        HN -> C
                        NN -> C
                        BH -> H
                        NC -> B
                        NB -> B
                        BN -> B
                        BB -> N
                        BC -> B
                        CC -> N
                        CN -> C
                        """, "1588" },
                { FIRST, getInput( INSTANCE ), "2549" },
                { SECOND, """
                        NNCB
                        
                        CH -> B
                        HH -> N
                        CB -> H
                        NH -> C
                        HB -> C
                        HC -> B
                        HN -> C
                        NN -> C
                        BH -> H
                        NC -> B
                        NB -> B
                        BN -> B
                        BB -> N
                        BC -> B
                        CC -> N
                        CN -> C
                        """, "2188189693529" },
                { SECOND, getInput( INSTANCE ), "2516901104210" }
        });
    }
}
