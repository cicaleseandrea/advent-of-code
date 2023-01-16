package com.adventofcode.aoc2016;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC172016Test extends Generic {

    private static final Solution INSTANCE = new AoC172016();

    public AoC172016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, "ihgpwlah", "DDRRRD" },
            { FIRST, "kglvqrro", "DDUDRLRRUDRD" },
            { FIRST, "ulqzkmiv", "DRURDRUDDLLDLUURRDULRLDUUDDDRR" },
            { FIRST, getInput( INSTANCE ), "DDRLRRUDDR" },
            { SECOND, "ihgpwlah", "370" },
            { SECOND, "kglvqrro", "492" },
            { SECOND, "ulqzkmiv", "830" },
            { SECOND, getInput( INSTANCE ), "556" },
        });
    }
}
