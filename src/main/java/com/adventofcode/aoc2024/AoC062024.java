package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static com.google.common.base.Preconditions.checkNotNull;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Point;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class AoC062024 implements Solution {

  private static final char OBSTACLE = HASH;
  private static final char EMPTY = DOT;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    char[][] grid = input.map( String::toCharArray ).toArray( char[][]::new );
    Point start = getStart( grid );
    List<Point> path = checkNotNull( moveGuard( start, grid, true ) );
    if ( first ) {
      return itoa( path.size() );
    }
    int result = 0;
    //consider all positions except the starting one
    for ( Point position : path.subList( 1, path.size() ) ) {
      //add obstacle
      grid[position.i()][position.j()] = OBSTACLE;
      if ( moveGuard( start, grid, false ) == null ) {
        //found a loop
        result++;
      }
      //remove obstacle
      grid[position.i()][position.j()] = EMPTY;
    }
    return itoa( result );
  }

  /**
   * Simulate movement. Return null if there is a loop. Otherwise, return all positions explored if
   * allPositions is true, otherwise return an empty list
   *
   * @param position     starting position
   * @param grid         grid with obstacles and starting position
   * @param allPositions enable returning all positions
   * @return null if there is a loop, otherwise all positions explored if allPositions is true,
   * otherwise empty list
   */
  private List<Point> moveGuard(Point position, final char[][] grid, boolean allPositions) {
    Direction direction = UP;
    Set<State> states = new LinkedHashSet<>();
    states.add( new State( position, direction ) );
    while ( true ) {
      Point nextPosition = position.move( direction );
      if ( isOutsideGrid( nextPosition, grid ) ) {
        //leave the area
        return allPositions ?
            states.stream().map( State::position ).distinct().toList() : List.of();
      } else if ( grid[nextPosition.i()][nextPosition.j()] == OBSTACLE ) {
        //turn around
        direction = direction.rotateClockwise();
        if ( !states.add( new State( position, direction ) ) ) {
          //was here with same direction before: found a loop
          return null;
        }
      } else {
        //move
        position = nextPosition;
        if ( allPositions ) { //conditional to speedup part 2
          states.add( new State( position, direction ) );
        }
      }
    }
  }

  private Point getStart(final char[][] grid) {
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        if ( grid[i][j] == UP.getSymbol() ) {
          return new Point( i, j );
        }
      }
    }
    throw new IllegalArgumentException( "Grid does not contain symbol ^" );
  }

  private boolean isOutsideGrid(final Point position, final char[][] grid) {
    int rows = grid.length;
    int columns = grid[0].length;
    return position.i() < 0 || rows <= position.i() || position.j() < 0 || columns <= position.j();
  }

  private record State(Point position, Direction direction) {

  }

}
