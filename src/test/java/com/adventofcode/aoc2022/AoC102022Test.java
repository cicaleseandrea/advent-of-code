package com.adventofcode.aoc2022;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC102022Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC102022();

    public AoC102022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        addx 15
                        addx -11
                        addx 6
                        addx -3
                        addx 5
                        addx -1
                        addx -8
                        addx 13
                        addx 4
                        noop
                        addx -1
                        addx 5
                        addx -1
                        addx 5
                        addx -1
                        addx 5
                        addx -1
                        addx 5
                        addx -1
                        addx -35
                        addx 1
                        addx 24
                        addx -19
                        addx 1
                        addx 16
                        addx -11
                        noop
                        noop
                        addx 21
                        addx -15
                        noop
                        noop
                        addx -3
                        addx 9
                        addx 1
                        addx -3
                        addx 8
                        addx 1
                        addx 5
                        noop
                        noop
                        noop
                        noop
                        noop
                        addx -36
                        noop
                        addx 1
                        addx 7
                        noop
                        noop
                        noop
                        addx 2
                        addx 6
                        noop
                        noop
                        noop
                        noop
                        noop
                        addx 1
                        noop
                        noop
                        addx 7
                        addx 1
                        noop
                        addx -13
                        addx 13
                        addx 7
                        noop
                        addx 1
                        addx -33
                        noop
                        noop
                        noop
                        addx 2
                        noop
                        noop
                        noop
                        addx 8
                        noop
                        addx -1
                        addx 2
                        addx 1
                        noop
                        addx 17
                        addx -9
                        addx 1
                        addx 1
                        addx -3
                        addx 11
                        noop
                        noop
                        addx 1
                        noop
                        addx 1
                        noop
                        noop
                        addx -13
                        addx -19
                        addx 1
                        addx 3
                        addx 26
                        addx -30
                        addx 12
                        addx -1
                        addx 3
                        addx 1
                        noop
                        noop
                        noop
                        addx -9
                        addx 18
                        addx 1
                        addx 2
                        noop
                        noop
                        addx 9
                        noop
                        noop
                        noop
                        addx -1
                        addx 2
                        addx -37
                        addx 1
                        addx 3
                        noop
                        addx 15
                        addx -21
                        addx 22
                        addx -6
                        addx 1
                        noop
                        addx 2
                        addx 1
                        noop
                        addx -10
                        noop
                        noop
                        addx 20
                        addx 1
                        addx 2
                        addx 2
                        addx -6
                        addx -11
                        noop
                        noop
                        noop
                        """, "13140" },
                { FIRST, getInput( INSTANCE ), "17840" },
                { SECOND, """
                        addx 15
                        addx -11
                        addx 6
                        addx -3
                        addx 5
                        addx -1
                        addx -8
                        addx 13
                        addx 4
                        noop
                        addx -1
                        addx 5
                        addx -1
                        addx 5
                        addx -1
                        addx 5
                        addx -1
                        addx 5
                        addx -1
                        addx -35
                        addx 1
                        addx 24
                        addx -19
                        addx 1
                        addx 16
                        addx -11
                        noop
                        noop
                        addx 21
                        addx -15
                        noop
                        noop
                        addx -3
                        addx 9
                        addx 1
                        addx -3
                        addx 8
                        addx 1
                        addx 5
                        noop
                        noop
                        noop
                        noop
                        noop
                        addx -36
                        noop
                        addx 1
                        addx 7
                        noop
                        noop
                        noop
                        addx 2
                        addx 6
                        noop
                        noop
                        noop
                        noop
                        noop
                        addx 1
                        noop
                        noop
                        addx 7
                        addx 1
                        noop
                        addx -13
                        addx 13
                        addx 7
                        noop
                        addx 1
                        addx -33
                        noop
                        noop
                        noop
                        addx 2
                        noop
                        noop
                        noop
                        addx 8
                        noop
                        addx -1
                        addx 2
                        addx 1
                        noop
                        addx 17
                        addx -9
                        addx 1
                        addx 1
                        addx -3
                        addx 11
                        noop
                        noop
                        addx 1
                        noop
                        addx 1
                        noop
                        noop
                        addx -13
                        addx -19
                        addx 1
                        addx 3
                        addx 26
                        addx -30
                        addx 12
                        addx -1
                        addx 3
                        addx 1
                        noop
                        noop
                        noop
                        addx -9
                        addx 18
                        addx 1
                        addx 2
                        noop
                        noop
                        addx 9
                        noop
                        noop
                        noop
                        addx -1
                        addx 2
                        addx -37
                        addx 1
                        addx 3
                        noop
                        addx 15
                        addx -21
                        addx 22
                        addx -6
                        addx 1
                        noop
                        addx 2
                        addx 1
                        noop
                        addx -10
                        noop
                        noop
                        addx 20
                        addx 1
                        addx 2
                        addx 2
                        addx -6
                        addx -11
                        noop
                        noop
                        noop
                        """,
                        """
                        ⬜⬜⬛⬛⬜⬜⬛⬛⬜⬜⬛⬛⬜⬜⬛⬛⬜⬜⬛⬛⬜⬜⬛⬛⬜⬜⬛⬛⬜⬜⬛⬛⬜⬜⬛⬛⬜⬜⬛⬛
                        ⬜⬜⬜⬛⬛⬛⬜⬜⬜⬛⬛⬛⬜⬜⬜⬛⬛⬛⬜⬜⬜⬛⬛⬛⬜⬜⬜⬛⬛⬛⬜⬜⬜⬛⬛⬛⬜⬜⬜⬛
                        ⬜⬜⬜⬜⬛⬛⬛⬛⬜⬜⬜⬜⬛⬛⬛⬛⬜⬜⬜⬜⬛⬛⬛⬛⬜⬜⬜⬜⬛⬛⬛⬛⬜⬜⬜⬜⬛⬛⬛⬛
                        ⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛
                        ⬜⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜
                        ⬜⬜⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛⬛⬛⬜⬜⬜⬜⬜⬜⬜⬛⬛⬛⬛⬛
                        """},
                { SECOND, getInput( INSTANCE ),
                        """
                        ⬜⬜⬜⬜⬛⬛⬜⬜⬛⬛⬜⬛⬛⬛⬛⬛⬜⬜⬛⬛⬜⬛⬛⬜⬛⬜⬛⬛⬛⬛⬜⬜⬜⬛⬛⬛⬜⬜⬛⬛
                        ⬜⬛⬛⬛⬛⬜⬛⬛⬜⬛⬜⬛⬛⬛⬛⬜⬛⬛⬜⬛⬜⬛⬛⬜⬛⬜⬛⬛⬛⬛⬜⬛⬛⬜⬛⬜⬛⬛⬜⬛
                        ⬜⬜⬜⬛⬛⬜⬛⬛⬜⬛⬜⬛⬛⬛⬛⬜⬛⬛⬛⬛⬜⬛⬛⬜⬛⬜⬛⬛⬛⬛⬜⬛⬛⬜⬛⬜⬛⬛⬛⬛
                        ⬜⬛⬛⬛⬛⬜⬜⬜⬜⬛⬜⬛⬛⬛⬛⬜⬛⬜⬜⬛⬜⬛⬛⬜⬛⬜⬛⬛⬛⬛⬜⬜⬜⬛⬛⬜⬛⬜⬜⬛
                        ⬜⬛⬛⬛⬛⬜⬛⬛⬜⬛⬜⬛⬛⬛⬛⬜⬛⬛⬜⬛⬜⬛⬛⬜⬛⬜⬛⬛⬛⬛⬜⬛⬛⬛⬛⬜⬛⬛⬜⬛
                        ⬜⬜⬜⬜⬛⬜⬛⬛⬜⬛⬜⬜⬜⬜⬛⬛⬜⬜⬜⬛⬛⬜⬜⬛⬛⬜⬜⬜⬜⬛⬜⬛⬛⬛⬛⬛⬜⬜⬜⬛
                        """ }
        });
    }
}
