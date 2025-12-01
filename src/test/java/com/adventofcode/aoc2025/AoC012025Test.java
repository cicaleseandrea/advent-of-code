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
public class AoC012025Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC012025();

  public AoC012025Test(final Type type, final String input, final String result) {
    super(INSTANCE, type, input, result);
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of(new Object[][]{
        {FIRST, """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82
                """, "3"},
        {FIRST, getInput(INSTANCE), "1029"},
        {SECOND, """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82
                """, "6"},
        {SECOND, """
                R1000
                """, "10"},
        {SECOND, """
                L1000
                """, "10"},
        {SECOND, getInput(INSTANCE), "5892"}
    });
  }
}