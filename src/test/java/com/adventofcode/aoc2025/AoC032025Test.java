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
public class AoC032025Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC032025();

  public AoC032025Test(final Type type, final String input, final String result) {
    super(INSTANCE, type, input, result);
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of(new Object[][]{
        {FIRST, """
                987654321111111
                811111111111119
                234234234234278
                818181911112111
                """, "357"},
        {FIRST, getInput(INSTANCE), "17443"},
        {SECOND, """
                987654321111111
                811111111111119
                234234234234278
                818181911112111
                """, "3121910778619"},
        {SECOND, getInput(INSTANCE), "172167155440541"}
    });
  }
}