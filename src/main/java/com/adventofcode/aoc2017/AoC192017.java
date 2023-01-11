package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.PLUS;
import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Character.isAlphabetic;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;
import java.util.stream.Stream;

class AoC192017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var res = new StringBuilder();
    final var grid = getGrid( input );
    final var pos = getStart( grid );
    var direction = Direction.DOWN;
    int steps = 0;
    char c;
    do {
      direction.move( pos );
      c = getChar( grid, pos );
      if ( isAlphabetic( c ) ) {
        res.append( c );
      } else if ( c == PLUS ) {
        final var nextPos = new Pair<>( pos );
        //try to rotate clockwise
        direction.rotateClockwise().move( nextPos );
        direction = getChar( grid, nextPos ) != SPACE ? direction.rotateClockwise()
            : direction.rotateCounterClockwise();
      }
      steps++;
    } while ( c != SPACE );

    return first ? res.toString() : itoa( steps );
  }

  private Pair<Long, Long> getStart(final Character[][] grid) {
    var j = 0;
    while ( grid[0][j] == SPACE ) {
      j++;
    }
    return new Pair<>( 0L, (long) j );
  }

  private Character getChar(final Character[][] grid, final Pair<Long, Long> pos) {
    if ( pos.getFirst() < grid.length && pos.getSecond() < grid[pos.getFirst()
        .intValue()].length ) {
      return grid[pos.getFirst().intValue()][pos.getSecond().intValue()];
    } else {
      return SPACE;
    }
  }

  private Character[][] getGrid(final Stream<String> input) {
    return input.map( row -> row.chars().mapToObj( c -> (char) c ).toArray( Character[]::new ) )
        .toArray( Character[][]::new );
  }
}
