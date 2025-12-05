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
public class AoC052025Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC052025();

  public AoC052025Test(final Type type, final String input, final String result) {
    super(INSTANCE, type, input, result);
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of(new Object[][]{
        {FIRST, """
                3-5
                10-14
                16-20
                12-18
                
                1
                5
                8
                11
                17
                32
                """, "3"},
        {FIRST, getInput(INSTANCE), "828"},
        {SECOND, """
                3-5
                10-14
                16-20
                12-18
                
                1
                5
                8
                11
                17
                32
                """, "14"},
        {SECOND, getInput(INSTANCE), "352681648086146"}
    });
  }
}