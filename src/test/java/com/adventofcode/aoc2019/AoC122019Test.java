package com.adventofcode.aoc2019;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AoC122019Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC122019();

    public AoC122019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        <x=-1, y=0, z=2>
                        <x=2, y=-10, z=-7>
                        <x=4, y=-8, z=8>
                        <x=3, y=5, z=-1>""", "179" },
                { FIRST, """
                        <x=-8, y=-10, z=0>
                        <x=5, y=5, z=10>
                        <x=2, y=-7, z=3>
                        <x=9, y=-8, z=-3>""", "1940" },
                { FIRST, getInput( INSTANCE ), "14907" },
                { SECOND, """
                        <x=-1, y=0, z=2>
                        <x=2, y=-10, z=-7>
                        <x=4, y=-8, z=8>
                        <x=3, y=5, z=-1>""", "2772" },
                { SECOND, getInput( INSTANCE ), "467081194429464" }
        });
    }
}
