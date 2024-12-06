package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static com.google.common.base.Preconditions.checkNotNull;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
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
    List<List<Character>> grid = Utils.getCharMatrix( input );
    Pair<Long, Long> start = getStart( grid );
    List<Pair<Long, Long>> path = checkNotNull( moveGuard( start, grid, true ) );
    if ( first ) {
      return itoa( path.size() );
    }

    int result = 0;
    //consider all positions except the starting one
    for ( Pair<Long, Long> position : path.subList( 1, path.size() ) ) {
      int i = position.getFirst().intValue();
      int j = position.getSecond().intValue();
      //add obstacle
      grid.get( i ).set( j, OBSTACLE );
      if ( moveGuard( start, grid, false ) == null ) {
        //found a loop
        result++;
      }
      //remove obstacle
      grid.get( i ).set( j, EMPTY );
    }
    return itoa( result );
  }

  /**
   * Simulate movement. Return null if there is a loop. Otherwise, return the full path if fullPath
   * is true, otherwise return an empty list
   *
   * @param position starting position
   * @param grid     grid with obstacles and starting position
   * @param fullPath enable returning the full path
   * @return null if there is a loop, otherwise all positions explored if fullPath is true,
   * otherwise empty list
   */
  private List<Pair<Long, Long>> moveGuard(Pair<Long, Long> position,
      final List<List<Character>> grid, boolean fullPath) {
    Direction direction = UP;
    Set<State> states = new LinkedHashSet<>();
    states.add( new State( position, direction ) );
    while ( true ) {
      Pair<Long, Long> nextPosition = getNextPosition( position, direction );
      if ( isOutsideGrid( nextPosition, grid ) ) {
        //leave the area
        return fullPath ? states.stream().map( State::position ).distinct().toList() : List.of();
      } else {
        char nextSymbol = grid.get( nextPosition.getFirst().intValue() )
            .get( nextPosition.getSecond().intValue() );
        if ( nextSymbol == OBSTACLE ) {
          //turn around
          direction = direction.rotateClockwise();
          if ( !states.add( new State( position, direction ) ) ) {
            //was here with same direction before: found a loop
            return null;
          }
        } else {
          //move
          position = nextPosition;
          if ( fullPath ) { //conditional to speedup part 2
            states.add( new State( position, direction ) );
          }
        }
      }
    }
  }

  private Pair<Long, Long> getStart(final List<List<Character>> grid) {
    for ( int i = 0; i < grid.size(); i++ ) {
      for ( int j = 0; j < grid.get( 0 ).size(); j++ ) {
        if ( grid.get( i ).get( j ) == UP.getSymbol() ) {
          return new Pair<>( (long) i, (long) j );
        }
      }
    }
    throw new IllegalArgumentException( "Grid does not contain symbol ^" );
  }

  private boolean isOutsideGrid(final Pair<Long, Long> position, final List<List<Character>> grid) {
    int i = position.getFirst().intValue();
    int j = position.getSecond().intValue();
    int rows = grid.size();
    int columns = grid.get( 0 ).size();
    return i < 0 || rows <= i || j < 0 || columns <= j;
  }

  private static Pair<Long, Long> getNextPosition(final Pair<Long, Long> position,
      final Direction direction) {
    Pair<Long, Long> nextPosition = new Pair<>( position );
    direction.move( nextPosition );
    return nextPosition;
  }

  private record State(Pair<Long, Long> position, Direction direction) {

  }

}
