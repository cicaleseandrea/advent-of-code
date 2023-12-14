package com.adventofcode.aoc2023;

import static com.adventofcode.aoc2023.AoC142023.Direction.EAST;
import static com.adventofcode.aoc2023.AoC142023.Direction.NORTH;
import static com.adventofcode.aoc2023.AoC142023.Direction.SOUTH;
import static com.adventofcode.aoc2023.AoC142023.Direction.WEST;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

class AoC142023 implements Solution {

  private static final char ROUND = 'O';

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private static String solve(final Stream<String> input, final boolean first) {
    Platform platform = new Platform( input.map( String::toCharArray ).toArray( char[][]::new ) );
    if ( first ) {
      platform.tilt( NORTH );
      return itoa( platform.getLoad() );
    }

    final Map<Platform, Integer> seen = new HashMap<>();
    int steps = 0;
    boolean isCycle = false;
    while ( !isCycle ) {
      seen.put( platform, steps );
      platform = spin( platform );
      isCycle = seen.containsKey( platform );
      steps++;
    }
    final int cycleStart = seen.get( platform );
    final int cycleLength = steps - cycleStart;
    final int stepsLeft = 1_000_000_000 - steps;
    for ( int i = 0; i < stepsLeft % cycleLength; i++ ) {
      platform = spin( platform );
    }
    return itoa( platform.getLoad() );
  }

  private static Platform spin(final Platform platform) {
    final Platform copy = platform.copy();
    copy.tilt( NORTH );
    copy.tilt( WEST );
    copy.tilt( SOUTH );
    copy.tilt( EAST );
    return copy;
  }

  private record Platform(char[][] grid) {

    private void tilt(final Direction direction) {
      final boolean horizontal = (direction == WEST || direction == EAST);
      final int rows = grid.length;
      final int cols = grid[0].length;
      for ( int a = 0; a < (horizontal ? rows : cols); a++ ) {
        int free = switch ( direction ) {
          case NORTH, WEST -> 0;
          case EAST -> cols - 1;
          case SOUTH -> rows - 1;
        };
        final IntUnaryOperator move =
            (direction == NORTH || direction == WEST) ? n -> n + 1 : n -> n - 1;
        for ( int b = free; 0 <= b && b < (horizontal ? cols : rows); b = move.applyAsInt( b ) ) {
          int row = horizontal ? a : b;
          int col = horizontal ? b : a;
          final char c = grid[row][col];
          if ( c == HASH ) {
            free = move.applyAsInt( b );
          } else if ( c == ROUND ) {
            grid[row][col] = DOT;
            grid[horizontal ? row : free][horizontal ? free : col] = ROUND;
            free = move.applyAsInt( free );
          }
        }
      }
    }

    private int getLoad() {
      int load = 0;
      for ( int i = 0; i < grid.length; i++ ) {
        for ( int j = 0; j < grid[0].length; j++ ) {
          if ( grid[i][j] == ROUND ) {
            load += grid.length - i;
          }
        }
      }
      return load;
    }

    private Platform copy() {
      final int rows = grid.length;
      final int cols = grid[0].length;
      final char[][] copy = new char[rows][cols];
      for ( int i = 0; i < rows; i++ ) {
        System.arraycopy( grid[i], 0, copy[i], 0, cols );
      }
      return new Platform( copy );
    }

    @Override
    public boolean equals(final Object o) {
      if ( this == o ) {
        return true;
      }
      if ( o == null || getClass() != o.getClass() ) {
        return false;
      }
      final Platform platform = (Platform) o;
      return Arrays.deepEquals( grid, platform.grid );
    }

    @Override
    public int hashCode() {
      return Arrays.deepHashCode( grid );
    }
  }

  enum Direction {
    NORTH, SOUTH, WEST, EAST,
  }
}
