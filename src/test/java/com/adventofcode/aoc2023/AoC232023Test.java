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
public class AoC232023Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC232023();

  public AoC232023Test(final Type type, final String input, final String result) {
    super( INSTANCE, type, input, result );
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of( new Object[][]{
        {FIRST, """
                #.#####################
                #.......#########...###
                #######.#########.#.###
                ###.....#.>.>.###.#.###
                ###v#####.#v#.###.#.###
                ###.>...#.#.#.....#...#
                ###v###.#.#.#########.#
                ###...#.#.#.......#...#
                #####.#.#.#######.#.###
                #.....#.#.#.......#...#
                #.#####.#.#.#########v#
                #.#...#...#...###...>.#
                #.#.#v#######v###.###v#
                #...#.>.#...>.>.#.###.#
                #####v#.#.###v#.#.###.#
                #.....#...#...#.#.#...#
                #.#########.###.#.#.###
                #...###...#...#...#.###
                ###.###.#.###v#####v###
                #...#...#.#.>.>.#.>.###
                #.###.###.#.###.#.#v###
                #.....###...###...#...#
                #####################.#
                """, "94"},
        {FIRST, getInput( INSTANCE ), "2218"},
        {SECOND, """
                #.#####################
                #.......#########...###
                #######.#########.#.###
                ###.....#.>.>.###.#.###
                ###v#####.#v#.###.#.###
                ###.>...#.#.#.....#...#
                ###v###.#.#.#########.#
                ###...#.#.#.......#...#
                #####.#.#.#######.#.###
                #.....#.#.#.......#...#
                #.#####.#.#.#########v#
                #.#...#...#...###...>.#
                #.#.#v#######v###.###v#
                #...#.>.#...>.>.#.###.#
                #####v#.#.###v#.#.###.#
                #.....#...#...#.#.#...#
                #.#########.###.#.#.###
                #...###...#...#...#.###
                ###.###.#.###v#####v###
                #...#...#.#.>.>.#.>.###
                #.###.###.#.###.#.#v###
                #.....###...###...#...#
                #####################.#
                """, "154"},
        {SECOND, getInput( INSTANCE ), "6674"}
    } );
  }
}