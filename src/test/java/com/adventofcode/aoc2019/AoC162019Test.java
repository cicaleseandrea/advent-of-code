package com.adventofcode.aoc2019;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AoC162019Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC162019();

    public AoC162019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "12345678", "01029498" },
                { FIRST, "80871224585914546619083218645595", "24176176" },
                { FIRST, "19617804207202209144916044189917", "73745418" },
                { FIRST, "69317163492948606335995924319873", "52432133" },
                { FIRST, getInput( INSTANCE ), "25131128" },
                { SECOND, "03036732577212944063491565474664", "84462026" },
                { SECOND, "02935109699940807407585447034323", "78725270" },
                { SECOND, "03081770884921959731165446850517", "53553731" },
                { SECOND, getInput( INSTANCE ), "53201602" }
        });
    }
}
