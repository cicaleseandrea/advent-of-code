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
public class AoC182020Test extends Generic {

    private static final Solution INSTANCE = new AoC182020();

    public AoC182020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "1 + 2 * 3 + 4 * 5 + 6", "71"  },
                { FIRST, "1 + (2 * 3) + (4 * (5 + 6))", "51"  },
                { FIRST, "2 * 3 + (4 * 5)", "26"  },
                { FIRST, "5 + (8 * 3 + 9 + 3 * 4 * 3)", "437"  },
                { FIRST, "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", "12240"  },
                { FIRST, "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", "13632"  },
                { FIRST, getInput( INSTANCE ), "4940631886147" },
                { SECOND, "1 + 2 * 3 + 4 * 5 + 6", "231"  },
                { SECOND, "1 + (2 * 3) + (4 * (5 + 6))", "51"  },
                { SECOND, "2 * 3 + (4 * 5)", "46"  },
                { SECOND, "5 + (8 * 3 + 9 + 3 * 4 * 3)", "1445"  },
                { SECOND, "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", "669060"  },
                { SECOND, "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", "23340"  },
                { SECOND, getInput( INSTANCE ), "283582817678281" }
        });
    }
}
