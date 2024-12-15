package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.LEFT;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.AT;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Point;
import com.adventofcode.utils.Utils;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

class AoC152024 implements Solution {

  private static final char ROBOT = AT;
  private static final char WALL = HASH;
  private static final char BOX = 'O';
  private static final char LEFTBOX = '[';
  private static final char RIGHTBOX = ']';
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
    List<String> lines = input.toList();
    char[][] grid = getGrid( lines, first );
    List<Direction> directions = lines.stream()
        .dropWhile( Predicate.not( String::isEmpty ) )
        .flatMapToInt( String::chars )
        .mapToObj( c -> (char) c )
        .map( Direction::fromSymbol )
        .toList();

    Point robot = getRobot( grid );
    for ( final Direction direction : directions ) {
      Point next = robot.move( direction );
      if ( canMoveInto( next, grid, direction, first ) ) {
        move( robot, grid, direction, first );
        robot = next;
      }
      print( grid );
    }

    long gps = 0;
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        Point p = new Point( i, j );
        if ( first && isBox( p, grid ) || isLeftBox( p, grid ) ) {
          gps += i * 100L + j;
        }
      }
    }
    return itoa( gps );
  }

  private char[][] getGrid(final List<String> lines, final boolean first) {
    char[][] grid = lines.stream()
        .takeWhile( Predicate.not( String::isEmpty ) )
        .map( String::toCharArray )
        .toArray( char[][]::new );
    if ( !first ) {
      int rows = grid.length;
      int columns = grid[0].length;
      char[][] large = new char[rows][columns * 2];
      for ( int i = 0; i < rows; i++ ) {
        for ( int j = 0; j < columns; j++ ) {
          char c = grid[i][j];
          large[i][2 * j] = c == BOX ? LEFTBOX : c;
          large[i][2 * j + 1] = switch ( c ) {
            case ROBOT -> EMPTY;
            case BOX -> RIGHTBOX;
            default -> c;
          };
        }
      }
      grid = large;
    }
    print( grid );
    return grid;
  }

  private boolean canMoveInto(Point position, final char[][] grid, final Direction direction,
      final boolean first) {
    if ( grid[position.i()][position.j()] == EMPTY ) {
      return true;
    }
    if ( grid[position.i()][position.j()] == WALL ) {
      return false;
    }
    //check box
    Point positionNext = position.move( direction );
    if ( !canMoveInto( positionNext, grid, direction, first ) ) {
      return false;
    } else if ( first || direction.equals( LEFT ) || direction.equals( RIGHT ) ) {
      return true;
    } else {
      //check other half
      Point otherHalfNext = getOtherHalf( position, grid ).move( direction );
      return canMoveInto( otherHalfNext, grid, direction, first );
    }
  }

  private void move(Point position, final char[][] grid, final Direction direction,
      final boolean first) {
    if ( grid[position.i()][position.j()] == EMPTY ) {
      return;
    }
    List<Point> toMove = new ArrayList<>();
    toMove.add( position );
    if ( !first && isBox( position, grid )
        && (direction.equals( UP ) || direction.equals( DOWN )) ) {
      //other half of the box
      toMove.add( getOtherHalf( position, grid ) );
    }
    toMove.forEach( p -> {
      Point next = p.move( direction );
      //push
      move( next, grid, direction, first );
      //move
      grid[next.i()][next.j()] = grid[p.i()][p.j()];
      grid[p.i()][p.j()] = EMPTY;
    } );
  }

  private void print(final char[][] grid) {
    Utils.clearScreen();
    if ( !Utils.shouldPrint() ) {
      return;
    }
    Arrays.stream( grid )
        .map( String::new )
        .forEach( System.out::println );
    System.out.println();
    try {
      Thread.sleep( 50 );
    } catch ( InterruptedException e ) {
      Thread.currentThread().interrupt();
    }
  }

  private boolean isBox(final Point p, final char[][] grid) {
    return grid[p.i()][p.j()] == BOX || grid[p.i()][p.j()] == RIGHTBOX || isLeftBox( p, grid );
  }

  private boolean isLeftBox(final Point p, final char[][] grid) {
    return grid[p.i()][p.j()] == LEFTBOX;
  }

  private Point getOtherHalf(final Point box, final char[][] grid) {
    Preconditions.checkArgument( isBox( box, grid ), grid[box.i()][box.j()] + " is not a box" );
    return box.move( isLeftBox( box, grid ) ? RIGHT : LEFT );
  }

  private Point getRobot(final char[][] grid) {
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        if ( grid[i][j] == ROBOT ) {
          return new Point( i, j );
        }
      }
    }
    throw new IllegalArgumentException( "Grid does not contain robot " + ROBOT );
  }
}
