package com.adventofcode.utils;

import static com.adventofcode.utils.Utils.decrementMod;
import static com.adventofcode.utils.Utils.incrementMod;
import static java.util.Comparator.comparingInt;

import java.util.Arrays;
import java.util.List;

public enum Direction {
  RIGHT( '>', 0 ), DOWN( 'v', 1 ), LEFT( '<', 2 ), UP( '^', 3 );

  static final List<Direction> BY_VALUE = Arrays.stream( values() )
      .sorted( comparingInt( Direction::getValue ) ).toList();

  final char symbol;
  final int value;

  Direction(final char c, final int value) {
    this.symbol = c;
    this.value = value;
  }

  public Direction rotateClockwise() {
    return BY_VALUE.get( incrementMod( value, BY_VALUE.size() ) );
  }

  public Direction rotateCounterClockwise() {
    return BY_VALUE.get( decrementMod( value, BY_VALUE.size() ) );
  }

  public Direction reverse() {
    return BY_VALUE.get( incrementMod( value, 2, BY_VALUE.size() ) );
  }

  public Direction rotate(final char c) {
    if ( this == UP || this == DOWN ) {
      if ( c == '\\' ) {
        return rotateCounterClockwise();
      } else if ( c == '/' ) {
        return rotateClockwise();
      }
    } else if ( this == RIGHT || this == LEFT ) {
      if ( c == '\\' ) {
        return rotateClockwise();
      } else if ( c == '/' ) {
        return rotateCounterClockwise();
      }
    }
    return this;
  }

  public void move(final Pair<Long, Long> point) {
    switch ( this ) {
      case RIGHT -> point.setSecond( point.getSecond() + 1L );
      case LEFT -> point.setSecond( point.getSecond() - 1L );
      case UP -> point.setFirst( point.getFirst() - 1L );
      case DOWN -> point.setFirst( point.getFirst() + 1L );
    }
  }

  public static Direction fromSymbol(final char c) {
    for ( final Direction d : Direction.values() ) {
      if ( d.getSymbol() == c ) {
        return d;
      }
    }
    return null;
  }

  public char getSymbol() {
    return symbol;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf( symbol );
  }
}
