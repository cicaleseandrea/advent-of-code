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
public class AoC122024Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC122024();

  public AoC122024Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
                AAAA
                BBCD
                BBCC
                EEEC
                """, "140"},
        {FIRST, """
                OOOOO
                OXOXO
                OOOOO
                OXOXO
                OOOOO
                """, "772"},
        {FIRST, """
                RRRRIICCFF
                RRRRIICCCF
                VVRRRCCFFF
                VVRCCCJFFF
                VVVVCJJCFE
                VVIVCCJJEE
                VVIIICJJEE
                MIIIIIJJEE
                MIIISIJEEE
                MMMISSJEEE
                """, "1930"},
        {FIRST, getInput( INSTANCE ), "1450422"},
        {SECOND, """
                AAAA
                BBCD
                BBCC
                EEEC
                """, "80"},
        {SECOND, """
                OOOOO
                OXOXO
                OOOOO
                OXOXO
                OOOOO
                """, "436"},
        {SECOND, """
                EEEEE
                EXXXX
                EEEEE
                EXXXX
                EEEEE
                """, "236"},
        {SECOND, """
                AAAAAA
                AAABBA
                AAABBA
                ABBAAA
                ABBAAA
                AAAAAA
                """, "368"},
        {SECOND, """
                RRRRIICCFF
                RRRRIICCCF
                VVRRRCCFFF
                VVRCCCJFFF
                VVVVCJJCFE
                VVIVCCJJEE
                VVIIICJJEE
                MIIIIIJJEE
                MIIISIJEEE
                MMMISSJEEE
                """, "1206"},
        {SECOND, getInput( INSTANCE ), "906606"}
    } );
  }
}