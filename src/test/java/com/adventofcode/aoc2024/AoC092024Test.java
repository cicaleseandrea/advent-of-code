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
public class AoC092024Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC092024();

  public AoC092024Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, "2333133121414131402", "1928"},
        {FIRST, getInput( INSTANCE ), "6225730762521"},
        {SECOND, "2333133121414131402", "2858"},
        {SECOND, getInput( INSTANCE ), "6250605700557"}
    } );
  }
}