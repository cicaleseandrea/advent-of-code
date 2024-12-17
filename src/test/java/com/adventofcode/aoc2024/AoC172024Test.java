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
public class AoC172024Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC172024();

  public AoC172024Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
                Register A: 729
                Register B: 0
                Register C: 0
                
                Program: 0,1,5,4,3,0
                """, "4,6,3,5,6,3,5,2,1,0"},
        {FIRST, getInput( INSTANCE ), "2,7,4,7,2,1,7,5,1"},
        {SECOND, getInput( INSTANCE ), "37221274271220"}
    } );
  }
}