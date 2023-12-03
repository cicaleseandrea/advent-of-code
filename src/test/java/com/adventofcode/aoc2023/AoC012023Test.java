package com.adventofcode.aoc2023;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC012023Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC012023();

  public AoC012023Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
                """, "142"},
        {FIRST, getInput( INSTANCE ), "54632"},
        {SECOND, """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
                """, "281"},
        {SECOND, getInput( INSTANCE ), "54019"}
    } );
  }
}