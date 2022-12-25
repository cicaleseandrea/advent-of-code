package com.adventofcode.aoc2022;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC252022Test extends Generic {

    private static final Solution INSTANCE = new AoC252022();

    public AoC252022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        1=-0-2
                        12111
                        2=0=
                        21
                        2=01
                        111
                        20012
                        112
                        1=-1=
                        1-12
                        12
                        1=
                        122
                        """, "2=-1=0" },
                { FIRST, getInput( INSTANCE ), "2=112--220-=-00=-=20" },
                { SECOND, getInput( INSTANCE ), MERRY_CHRISTMAS }
        });
    }
}
