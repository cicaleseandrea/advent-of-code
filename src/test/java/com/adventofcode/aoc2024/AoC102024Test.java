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
public class AoC102024Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC102024();

  public AoC102024Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732
                """, "36"},
        {FIRST, getInput( INSTANCE ), "746"},
        {SECOND, """
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732
                """, "81"},
        {SECOND, getInput( INSTANCE ), "1541"}
    } );
  }
}