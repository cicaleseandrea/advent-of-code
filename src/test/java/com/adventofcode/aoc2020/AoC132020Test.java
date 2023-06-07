package com.adventofcode.aoc2020;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC132020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC132020();

    public AoC132020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        939
                        7,13,x,x,59,x,31,19
                        """, "295"  },
                { FIRST, getInput( INSTANCE ), "3882" },
                { SECOND, """
                        939
                        17,x,13,19
                        """, "3417"  },
                { SECOND, """
                        939
                        7,13,x,x,59,x,31,19
                        """, "1068781"  },
                { SECOND, """
                        939
                        67,7,59,61
                        """, "754018"  },
                { SECOND, """
                        939
                        67,x,7,59,61
                        """, "779210"  },
                { SECOND, """
                        939
                        67,7,x,59,61
                        """, "1261476"  },
                { SECOND, """
                        939
                        1789,37,47,1889
                        """, "1202161486"  },
                { SECOND, getInput( INSTANCE ), "867295486378319" }
        });
    }
}
