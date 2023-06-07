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
public class AoC042016Test extends Generic {

    private static final Solution INSTANCE = new AoC042016();

    public AoC042016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "aaaaa-bbb-z-y-x-123[abxyz]", "123" },
                { FIRST, "a-b-c-d-e-f-g-h-987[abcde]", "987" },
                { FIRST, "not-a-real-room-404[oarel]", "404" },
                { FIRST, "totally-real-room-200[decoy]", "0" },
                { FIRST, getInput( INSTANCE ), "361724" },
                { SECOND, getInput( INSTANCE ), "482" },
        });
    }
}
