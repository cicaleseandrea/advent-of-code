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
public class AoC222016Test extends Generic {

    private static final Solution INSTANCE = new AoC222016();

    public AoC222016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, getInput( INSTANCE ), "941" },
            { SECOND, """
                        root@ebhq-gridcenter# df -h
                        Filesystem            Size  Used  Avail  Use%
                        /dev/grid/node-x0-y0   10T    8T     2T   80%
                        /dev/grid/node-x0-y1   11T    6T     5T   54%
                        /dev/grid/node-x0-y2   32T   28T     4T   87%
                        /dev/grid/node-x1-y0    9T    7T     2T   77%
                        /dev/grid/node-x1-y1    8T    0T     8T    0%
                        /dev/grid/node-x1-y2   11T    7T     4T   63%
                        /dev/grid/node-x2-y0   10T    6T     4T   60%
                        /dev/grid/node-x2-y1    9T    8T     1T   88%
                        /dev/grid/node-x2-y2    9T    6T     3T   66%
                        """, "7" },
            { SECOND, getInput( INSTANCE ), "249" },
        });
    }
}
