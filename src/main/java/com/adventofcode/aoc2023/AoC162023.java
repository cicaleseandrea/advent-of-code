package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.LEFT;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.GraphUtils;
import java.util.Collection;
import java.util.List;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC162023 implements Solution {


  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input.toList(), List.of( new Beam( new Position( 0, 0 ), RIGHT ) ) );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    final List<String> inputList = input.toList();
    final int maxI = inputList.size();
    final int maxJ = inputList.get( 0 ).length();
    final List<Beam> starts = IntStream.range( 0, max( maxI, maxJ ) ).boxed()
        .flatMap( n -> Stream.of(
            new Beam( new Position( 0, min( n, maxJ - 1 ) ), DOWN ),
            new Beam( new Position( maxI - 1, min( n, maxJ - 1 ) ), UP ),
            new Beam( new Position( min( n, maxI - 1 ), 0 ), RIGHT ),
            new Beam( new Position( min( n, maxI - 1 ), maxJ - 1 ), LEFT ) ) )
        .distinct()
        .toList();
    return solve( inputList, starts );
  }

  private static String solve(final List<String> input, final List<Beam> starts) {
    final OptionalLong maxEnergized = starts.stream()
        .mapToLong( start -> countEnergized( start, getGrid( input ) ) ).max();
    return itoa( maxEnergized.orElseThrow() );
  }

  private static long countEnergized(final Beam start, final char[][] grid) {
    final Set<Beam> beams = GraphUtils.fill( start, beam -> getNext( beam, grid ) );
    return beams.stream().map( Beam::position ).distinct().count();
  }

  private static Collection<Beam> getNext(final Beam beam, final char[][] grid) {
    final char c = grid[beam.position.i][beam.position.j];
    return beam.direction.rotate( c ).split( c ).stream().map( beam::move )
        .filter( next -> isInGrid( next.position.i, next.position.j, grid.length, grid[0].length ) )
        .toList();
  }

  private static char[][] getGrid(final List<String> input) {
    final char[][] grid = new char[input.size()][input.get( 0 ).length()];
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        grid[i][j] = input.get( i ).charAt( j );
      }
    }
    return grid;
  }

  private static boolean isInGrid(final int i, final int j, final int maxI, final int maxJ) {
    return i >= 0 && j >= 0 && i < maxI && j < maxJ;
  }

  private record Beam(Position position, Direction direction) {

    Beam move(Direction direction) {
      return new Beam( position.move( direction ), direction );
    }
  }

  private record Position(int i, int j) {

    Position move(Direction direction) {
      return switch ( direction ) {
        case UP -> new Position( i - 1, j );
        case DOWN -> new Position( i + 1, j );
        case LEFT -> new Position( i, j - 1 );
        case RIGHT -> new Position( i, j + 1 );
      };
    }
  }
}
