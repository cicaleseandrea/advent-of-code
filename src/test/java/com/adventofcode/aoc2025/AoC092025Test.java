package com.adventofcode.aoc2025;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC092025Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC092025();

  public AoC092025Test(final Type type, final String input, final String result) {
    super(INSTANCE, type, input, result);
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of(new Object[][]{
        {FIRST, """
                7,1
                11,1
                11,7
                9,7
                9,5
                2,5
                2,3
                7,3
                """, "50"},
        {FIRST, getInput(INSTANCE), "4777409595"},
        {SECOND, """
                7,1
                11,1
                11,7
                9,7
                9,5
                2,5
                2,3
                7,3
                """, "24"},
        {SECOND, getInput(INSTANCE), "1473551379"}
    });
  }
}