package com.adventofcode.aoc2023;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC092023Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC092023();

  public AoC092023Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45
                """, "114"},
        {FIRST, getInput( INSTANCE ), "1921197370"},
        {SECOND, """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45
                """, "2"},
        {SECOND, getInput( INSTANCE ), "1124"}
    } );
  }
}