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
public class AoC232016Test extends Generic {

    private static final Solution INSTANCE = new AoC232016();

    public AoC232016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        cpy 2 a
                        tgl a
                        tgl a
                        tgl a
                        cpy 1 a
                        dec a
                        dec a
                        """, "3" },
            { FIRST, getInput( INSTANCE ), "13468" },
            { SECOND, getInput( INSTANCE ), "479010028" },
        });
    }
}
