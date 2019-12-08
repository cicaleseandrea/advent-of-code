package com.adventofcode.aoc2019;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC082019Test extends Generic {

    private static final Solution INSTANCE = new AoC082019();

    public AoC082019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, getInput( INSTANCE ), "828" },
                { SECOND, "0222112222120000",
                    " #\n" +
                    "# \n" },
                { SECOND, getInput( INSTANCE ),
                    "#### #    ###    ## #### \n" +
                    "   # #    #  #    # #    \n" +
                    "  #  #    ###     # ###  \n" +
                    " #   #    #  #    # #    \n" +
                    "#    #    #  # #  # #    \n" +
                    "#### #### ###   ##  #    \n" }
        });
    }
}
