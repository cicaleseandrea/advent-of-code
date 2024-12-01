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
public class AoC012024Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC012024();

  public AoC012024Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """, "11"},
        {FIRST, getInput( INSTANCE ), "2378066"},
        {SECOND, """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
                """, "31"},
        {SECOND, getInput( INSTANCE ), "18934359"}
    } );
  }
}