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
public class AoC182024Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC182024();

  public AoC182024Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
                5,4
                4,2
                4,5
                3,0
                2,1
                6,3
                2,4
                1,5
                0,6
                3,3
                2,6
                5,1
                1,2
                5,5
                2,5
                6,5
                1,4
                0,4
                6,4
                1,1
                6,1
                1,0
                0,5
                1,6
                2,0
                """, "22"},
        {FIRST, getInput( INSTANCE ), "268"},
        {SECOND, """
                5,4
                4,2
                4,5
                3,0
                2,1
                6,3
                2,4
                1,5
                0,6
                3,3
                2,6
                5,1
                1,2
                5,5
                2,5
                6,5
                1,4
                0,4
                6,4
                1,1
                6,1
                1,0
                0,5
                1,6
                2,0
                """, "6,1"},
        {SECOND, getInput( INSTANCE ), "64,11"}
    } );
  }
}