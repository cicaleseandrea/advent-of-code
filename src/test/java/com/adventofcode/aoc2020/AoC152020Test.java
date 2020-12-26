package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC152020Test extends Generic {

    private static final Solution INSTANCE = new AoC152020();

    public AoC152020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "0,3,6", "436"  },
                { FIRST, "1,3,2", "1"  },
                { FIRST, "2,1,3", "10"  },
                { FIRST, "1,2,3", "27"  },
                { FIRST, "2,3,1", "78"  },
                { FIRST, "3,2,1", "438"  },
                { FIRST, "3,1,2", "1836"  },
                { FIRST, getInput( INSTANCE ), "240" },
                { SECOND, "0,3,6", "175594"  },
                { SECOND, getInput( INSTANCE ), "505" }
        });
    }
}
