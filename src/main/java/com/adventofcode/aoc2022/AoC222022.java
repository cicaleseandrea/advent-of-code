package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.LEFT;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toPositiveLongList;
import static com.adventofcode.utils.Utils.toWordStream;
import static java.util.Comparator.comparingLong;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class AoC222022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var inputList = input.toList();
    final var map = getMap( inputList );
    final var directions = getDirections( inputList.get( inputList.size() - 1 ) );
    final var steps = directions.getFirst();
    final var rotations = directions.getSecond();
    var position = getStart( map );
    var direction = RIGHT;
    for ( int i = 0; i < steps.size(); i++ ) {

      //1. move
      for ( int j = 0; j < steps.get( i ); j++ ) {
        final var nextPosition = new Pair<>( position );
        final Direction nextDirection;
        //try to move, wrapping around
        if ( first ) {
          nextDirection = moveAroundEdges( nextPosition, direction, map );
        } else {
          nextDirection = moveAroundCube( nextPosition, direction );
        }
        if ( map.get( nextPosition ) != HASH ) {
          //move
          position = nextPosition;
          direction = nextDirection;
        } else {
          //hit a wall
          break;
        }
      }

      //2. rotate
      if ( i < rotations.size() ) {
        direction = rotations.get( i ) == 'R' ? direction.rotateClockwise()
            : direction.rotateCounterClockwise();
      }
    }
    return itoa( position.getSecond() * 1000 + position.getFirst() * 4 + direction.getValue() );
  }

  private Direction moveAroundCube(final Pair<Long, Long> point, final Direction direction) {
    final var x = point.getFirst();
    final var y = point.getSecond();

    //hardcoded rules to wrap around 14 edges 😬
    if ( direction == LEFT ) {
      if ( x == 1 ) {
        if ( 100 < y && y <= 150 ) {
          point.setFirst( 51L );
          point.setSecond( 151 - y );
          return RIGHT;
        } else if ( 150 < y && y <= 200 ) {
          point.setFirst( y - 100L );
          point.setSecond( 1L );
          return DOWN;
        }
      } else if ( x == 51 ) {
        if ( 0 < y && y <= 50 ) {
          point.setFirst( 1L );
          point.setSecond( 151L - y );
          return RIGHT;
        } else if ( 50 < y && y <= 100 ) {
          point.setFirst( y - 50L );
          point.setSecond( 101L );
          return DOWN;
        }
      }
    } else if ( direction == RIGHT ) {
      if ( x == 50 && 150 < y && y <= 200 ) {
        point.setFirst( y - 100L );
        point.setSecond( 150L );
        return UP;
      } else if ( x == 100 ) {
        if ( 50 < y && y <= 100 ) {
          point.setFirst( y + 50L );
          point.setSecond( 50L );
          return UP;
        } else if ( 100 < y && y <= 150 ) {
          point.setFirst( 150L );
          point.setSecond( 151L - y );
          return LEFT;
        }
      } else if ( x == 150 && 0 < y && y <= 50 ) {
        point.setFirst( 100L );
        point.setSecond( 151L - y );
        return LEFT;
      }
    } else if ( direction == UP ) {
      if ( y == 1 ) {
        if ( 50 < x && x <= 100 ) {
          point.setFirst( 1L );
          point.setSecond( x + 100L );
          return RIGHT;
        } else if ( 100 < x && x <= 150 ) {
          point.setFirst( x - 100L );
          point.setSecond( 200L );
          return UP;
        }
      } else if ( y == 101 && 0 < x && x <= 50 ) {
        point.setFirst( 51L );
        point.setSecond( x + 50L );
        return RIGHT;
      }
    } else if ( direction == DOWN ) {
      if ( y == 50 && 100 < x && x <= 150 ) {
        point.setFirst( 100L );
        point.setSecond( x - 50L );
        return LEFT;
      } else if ( y == 150 && 50 < x && x <= 100 ) {
        point.setFirst( 50L );
        point.setSecond( x + 100L );
        return LEFT;
      } else if ( y == 200 && 0 < x && x <= 50 ) {
        point.setFirst( x + 100L );
        point.setSecond( 1L );
        return DOWN;
      }
    }

    switch ( direction ) {
      case UP -> point.setSecond( y - 1L );
      case DOWN -> point.setSecond( y + 1L );
      case LEFT -> point.setFirst( x - 1L );
      case RIGHT -> point.setFirst( x + 1L );
    }
    return direction;
  }

  private Direction moveAroundEdges(final Pair<Long, Long> point, final Direction direction,
      final Map<Pair<Long, Long>, Character> map) {
    final var x = point.getFirst();
    final var y = point.getSecond();
    switch ( direction ) {
      case UP -> {
        point.setSecond( y - 1L );
        if ( !map.containsKey( point ) ) {
          final var maxY = map.keySet().stream().filter( p -> p.getFirst().equals( x ) )
              .mapToLong( Pair::getSecond ).max().orElseThrow();
          point.setSecond( maxY );
        }
      }
      case DOWN -> {
        point.setSecond( y + 1L );
        if ( !map.containsKey( point ) ) {
          final var minY = map.keySet().stream().filter( p -> p.getFirst().equals( x ) )
              .mapToLong( Pair::getSecond ).min().orElseThrow();
          point.setSecond( minY );
        }
      }
      case LEFT -> {
        point.setFirst( x - 1L );
        if ( !map.containsKey( point ) ) {
          final var maxX = map.keySet().stream().filter( p -> p.getSecond().equals( y ) )
              .mapToLong( Pair::getFirst ).max().orElseThrow();
          point.setFirst( maxX );
        }
      }
      case RIGHT -> {
        point.setFirst( x + 1L );
        if ( !map.containsKey( point ) ) {
          final var minX = map.keySet().stream().filter( p -> p.getSecond().equals( y ) )
              .mapToLong( Pair::getFirst ).min().orElseThrow();
          point.setFirst( minX );
        }
      }
    }
    return direction;
  }

  private Map<Pair<Long, Long>, Character> getMap(final List<String> inputList) {
    final Map<Pair<Long, Long>, Character> map = new HashMap<>();
    for ( int i = 0; i < inputList.size(); i++ ) {
      final var row = inputList.get( i );
      for ( int j = 0; j < row.length(); j++ ) {
        final var c = row.charAt( j );
        if ( c == DOT || c == HASH ) {
          map.put( new Pair<>( j + 1L, i + 1L ), c );
        }
      }
    }
    return map;
  }

  private Pair<List<Long>, List<Character>> getDirections(final String directions) {
    return new Pair<>( toPositiveLongList( directions ),
        toWordStream( directions ).map( s -> s.charAt( 0 ) ).toList() );
  }

  private Pair<Long, Long> getStart(final Map<Pair<Long, Long>, Character> map) {
    return map.keySet().stream()
        .min( comparingLong( Pair<Long, Long>::getSecond ).thenComparingLong( Pair::getFirst ) )
        .orElseThrow();
  }
}
