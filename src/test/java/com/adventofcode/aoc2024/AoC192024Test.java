package com.adventofcode.aoc2024;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC192024Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC192024();

  public AoC192024Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
                r, wr, b, g, bwu, rb, gb, br
                
                brwrr
                bggr
                gbbr
                rrbgbr
                ubwu
                bwurrg
                brgr
                bbrgwb
                """, "6"},
        {FIRST, getInput( INSTANCE ), "358"},
        {SECOND, """
                r, wr, b, g, bwu, rb, gb, br
                
                brwrr
                bggr
                gbbr
                rrbgbr
                ubwu
                bwurrg
                brgr
                bbrgwb
                """, "16"},
        {SECOND, getInput( INSTANCE ), "600639829400603"}
    } );
  }
}