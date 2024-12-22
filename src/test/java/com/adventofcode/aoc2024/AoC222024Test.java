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
public class AoC222024Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC222024();

  public AoC222024Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
                1
                10
                100
                2024
                """, "37327623"},
        {FIRST, getInput( INSTANCE ), "20441185092"},
        {SECOND, """
                1
                2
                3
                2024
                """, "23"},
        {SECOND, getInput( INSTANCE ), "2268"}
    } );
  }
}