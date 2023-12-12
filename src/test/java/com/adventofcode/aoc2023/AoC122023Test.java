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
public class AoC122023Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC122023();

  public AoC122023Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
               ???.### 1,1,3
               .??..??...?##. 1,1,3
               ?#?#?#?#?#?#?#? 1,3,1,6
               ????.#...#... 4,1,1
               ????.######..#####. 1,6,5
               ?###???????? 3,2,1
                """, "21"},
        {FIRST, getInput( INSTANCE ), "7705"},
        {SECOND, """
               ???.### 1,1,3
               .??..??...?##. 1,1,3
               ?#?#?#?#?#?#?#? 1,3,1,6
               ????.#...#... 4,1,1
               ????.######..#####. 1,6,5
               ?###???????? 3,2,1
                """, "525152"},
        {SECOND, getInput( INSTANCE ), "50338344809230"}
    } );
  }
}