package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.GraphUtils;
import com.adventofcode.utils.Point;
import java.util.Collection;
import java.util.List;
import java.util.OptionalLong;
import java.util.function.Predicate;
import java.util.stream.Stream;

class AoC172023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 1, 3 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 4, 10 );
  }

  private static String solve(final Stream<String> input, final int minSteps, final int maxSteps) {
    final int[][] grid = getGrid( input.toList() );
    final Point end = new Point( grid.length - 1, grid[0].length - 1 );
    final OptionalLong shortestDistance = Stream.of( RIGHT, DOWN )
        .map( direction -> new State( new Point( 0, 0 ), direction ) )
        .mapToLong( start -> computeShortestPath( start, end, grid, minSteps, maxSteps ) ).min();
    return itoa( shortestDistance.orElseThrow() );
  }

  private static long computeShortestPath(final State start, final Point end, final int[][] grid,
      final int minSteps, final int maxSteps) {
    final Predicate<State> walkedEnough = state -> state.steps >= minSteps;
    final Predicate<State> isEnd = state -> state.position().equals( end );
    return GraphUtils.computeShortestPath( start, walkedEnough.and( isEnd ),
        state -> getNext( state, grid, minSteps, maxSteps ), state -> getDistance( state, grid ) );
  }

  private static Collection<State> getNext(final State state, final int[][] grid,
      final int minSteps, final int maxSteps) {
    final Direction direction = state.direction;
    final Stream<Direction> nextDirections;
    if ( state.steps >= minSteps ) {
      nextDirections = Stream.of( direction, direction.rotateClockwise(),
          direction.rotateCounterClockwise() );
    } else {
      nextDirections = Stream.of( direction );
    }
    return nextDirections.map( state::move ).filter( next -> next.steps <= maxSteps )
        .filter(
            next -> isInGrid( next.position.i(), next.position.j(), grid.length, grid[0].length ) )
        .toList();
  }

  private static long getDistance(final State state, final int[][] grid) {
    return grid[state.position.i()][state.position.j()];
  }

  private static int[][] getGrid(final List<String> input) {
    final int[][] grid = new int[input.size()][input.get( 0 ).length()];
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        grid[i][j] = charToInt( input.get( i ).charAt( j ) );
      }
    }
    return grid;
  }

  private static boolean isInGrid(final int i, final int j, final int maxI, final int maxJ) {
    return i >= 0 && j >= 0 && i < maxI && j < maxJ;
  }

  private record State(Point position, Direction direction, int steps) {

    State(Point position, Direction direction) {
      this( position, direction, 1 );
    }

    State move(Direction direction) {
      return new State( position.move( direction ), direction,
          direction == this.direction ? steps + 1 : 1 );
    }
  }


}
