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
public class AoC062025Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC062025();

  public AoC062025Test(final Type type, final String input, final String result) {
    super(INSTANCE, type, input, result);
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of(new Object[][]{
        {FIRST, """
                123 328  51 64
                 45 64  387 23
                  6 98  215 314
                *   +   *   +
                """, "4277556"},
        {FIRST, getInput(INSTANCE), "6417439773370"},
        {SECOND, """
                123 328  51 64
                 45 64  387 23
                  6 98  215 314
                *   +   *   +
                """, "3263827"},
        {SECOND, getInput(INSTANCE), "11044319475191"}
    });
  }
}