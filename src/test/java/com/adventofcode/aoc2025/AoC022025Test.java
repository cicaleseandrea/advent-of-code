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
public class AoC022025Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC022025();

  public AoC022025Test(final Type type, final String input, final String result) {
    super(INSTANCE, type, input, result);
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of(new Object[][]{
        {FIRST, """
                11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
                1698522-1698528,446443-446449,38593856-38593862,565653-565659,
                824824821-824824827,2121212118-2121212124
                """, "1227775554"},
        {FIRST, getInput(INSTANCE), "21139440284"},
        {SECOND, """
                11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
                1698522-1698528,446443-446449,38593856-38593862,565653-565659,
                824824821-824824827,2121212118-2121212124
                """, "4174379265"},
        {SECOND, getInput(INSTANCE), "38731915928"}
    });
  }
}