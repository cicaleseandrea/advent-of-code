package com.adventofcode.aoc2021;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC132021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC132021();

    public AoC132021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        6,10
                        0,14
                        9,10
                        0,3
                        10,4
                        4,11
                        6,0
                        6,12
                        4,1
                        0,13
                        10,12
                        3,4
                        3,0
                        8,4
                        1,10
                        2,14
                        8,10
                        9,0
                        
                        fold along y=7
                        fold along x=5
                        """, "17" },
                { FIRST, getInput( INSTANCE ), "716" },
                { SECOND, getInput( INSTANCE ),
                        """
                        ⬛⬛⬛⬜⬜⬛⬛⬛⬜⬜⬜⬛⬛⬜⬜⬛⬜⬜⬛⬜⬛⬛⬛⬛⬜⬛⬛⬛⬜⬜⬛⬜⬜⬜⬜⬛⬛⬛⬜⬜
                        ⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬛⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜
                        ⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬛⬛⬜⬜⬜⬛⬛⬛⬜⬜⬛⬛⬛⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜
                        ⬛⬛⬛⬜⬜⬛⬛⬛⬜⬜⬛⬜⬜⬜⬜⬛⬜⬛⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬛⬛⬛⬜⬜
                        ⬛⬜⬛⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜⬛⬜⬛⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬛⬜⬛⬜⬜
                        ⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬜⬛⬛⬜⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬛⬛⬛⬜⬜⬛⬛⬛⬛⬜⬛⬜⬜⬛⬜"""  }
        });
    }
}
