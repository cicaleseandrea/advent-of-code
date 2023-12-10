package com.adventofcode.aoc2023;

import static com.adventofcode.aoc2023.AoC102023.Direction.EAST;
import static com.adventofcode.aoc2023.AoC102023.Direction.NORTH;
import static com.adventofcode.aoc2023.AoC102023.Direction.NORTHEAST;
import static com.adventofcode.aoc2023.AoC102023.Direction.NORTHWEST;
import static com.adventofcode.aoc2023.AoC102023.Direction.SOUTH;
import static com.adventofcode.aoc2023.AoC102023.Direction.SOUTHEAST;
import static com.adventofcode.aoc2023.AoC102023.Direction.SOUTHWEST;
import static com.adventofcode.aoc2023.AoC102023.Direction.WEST;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.listGetOrDefault;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;

import com.adventofcode.Solution;
import com.adventofcode.utils.GraphUtils;
import com.adventofcode.utils.Utils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AoC102023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private static String solve(final Stream<String> input, final boolean first) {
    final List<List<Character>> matrix = Utils.getCharMatrix( input );
    final Map<Position, Tile> tiles = new HashMap<>();
    final Multimap<Position, Position> connections = HashMultimap.create();
    final Position start = parseInput( matrix, tiles, connections );

    final Map<Position, Long> loopTileToDistance = GraphUtils.computeShortestPaths( start,
        connections::get );

    if ( first ) {
      final long maxDistance = loopTileToDistance.values().stream()
          .max( Comparator.comparing( Long::longValue ) ).orElseThrow();
      return itoa( maxDistance );
    } else {
      return itoa( countInnerTiles( loopTileToDistance.keySet(), matrix, tiles ) );
    }
  }

  private static int countInnerTiles(final Set<Position> loopTiles,
      final List<List<Character>> matrix, final Map<Position, Tile> tiles) {
    final int maxI = matrix.size() - 1;
    final int maxJ = matrix.get( 0 ).size() - 1;

    final Set<Position> toExplore = new HashSet<>( tiles.keySet() );
    loopTiles.forEach( toExplore::remove );

    final Set<Position> innerTiles = new HashSet<>();
    final Set<Position> outerTiles = new HashSet<>();
    while ( !toExplore.isEmpty() ) {
      final Set<Position> connected = GraphUtils.fill( toExplore.iterator().next(),
          position -> getNeighbours( position, tiles, loopTiles, maxI, maxJ ) );
      final Set<Position> connectedReal = connected.stream()
          .filter( Predicate.not( Position::isSqueezed ) ).collect( Collectors.toSet() );
      if ( connectedReal.stream().anyMatch( p -> isBoundary( p, maxI, maxJ ) ) ) {
        outerTiles.addAll( connectedReal );
      } else {
        innerTiles.addAll( connectedReal );
      }
      connectedReal.forEach( toExplore::remove );
    }
    print( matrix, innerTiles, outerTiles );
    return innerTiles.size();
  }

  private static Collection<Position> getNeighbours(Position position,
      final Map<Position, Tile> tiles,
      final Set<Position> loopTiles, final int maxI, final int maxJ) {
    //TODO
    final Set<Position> neighbours = new HashSet<>();
    if ( position.isSqueezed() ) {
      Stream.of( new Position( 0.5, 0.5 ), new Position( 0.5, -0.5 ), new Position( -0.5, 0.5 ),
              new Position( -0.5, -0.5 ) ).map( direction -> position.add( direction.i, direction.j ) )
          .filter( p -> canSqueeze( p, tiles, loopTiles ) ).forEach( neighbours::add );
      if ( position.isSqueezedVertically() ) {
        Stream.of( WEST, EAST ).flatMap(
                direction -> Stream.of( new Position( floor( position.i ), position.j + direction.j ),
                    new Position( ceil( position.i ), position.j + direction.j ) ) )
            .filter( Predicate.not( loopTiles::contains ) ).forEach( neighbours::add );
        Stream.of( WEST, EAST )
            .map( direction -> new Position( position.i, position.j + direction.j ) )
            .filter( p -> canSqueeze( p, tiles, loopTiles ) ).forEach( neighbours::add );
      } else {
        Stream.of( NORTH, SOUTH ).flatMap(
                direction -> Stream.of( new Position( position.i + direction.i, floor( position.j ) ),
                    new Position( position.i + direction.i, ceil( position.j ) ) ) )
            .filter( Predicate.not( loopTiles::contains ) ).forEach( neighbours::add );
        Stream.of( NORTH, SOUTH )
            .map( direction -> new Position( position.i + direction.i, position.j ) )
            .filter( p -> canSqueeze( p, tiles, loopTiles ) ).forEach( neighbours::add );
      }
    } else {
      Stream.of( NORTH, SOUTH, WEST, EAST, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST )
          .map( position::add ).filter( Predicate.not( loopTiles::contains ) )
          .forEach( neighbours::add );
      Stream.of( new Position( 1, 0.5 ), new Position( 1, -0.5 ), new Position( -1, 0.5 ),
              new Position( -1, -0.5 ), new Position( 0.5, 1 ), new Position( 0.5, -1 ),
              new Position( -0.5, 1 ), new Position( -0.5, -1 ) )
          .map( direction -> position.add( direction.i, direction.j ) )
          .filter( p -> canSqueeze( p, tiles, loopTiles ) ).forEach( neighbours::add );
    }

    return neighbours.stream()
        .filter( p -> p.i >= -1 && p.j >= -1 && p.i <= maxI + 1 && p.j <= maxJ + 1 )
        .collect( Collectors.toSet() );
  }

  private static boolean canSqueeze(final Position position, final Map<Position, Tile> tiles,
      final Set<Position> loopTiles) {
    if ( !position.isSqueezed() ) {
      return false;
    }
    final Position pipePosition = new Position( floor( position.i ), floor( position.j ) );
    if ( !loopTiles.contains( pipePosition ) ) {
      return false;
    }
    final Pipe pipe = Pipe.of( tiles.get( pipePosition ).symbol );
    if ( position.isSqueezedVertically() ) {
      return !pipe.isConnected( SOUTH );
    } else {
      return !pipe.isConnected( EAST );
    }
  }

  private static Position parseInput(final List<List<Character>> matrix,
      final Map<Position, Tile> tiles, final Multimap<Position, Position> connections) {
    Position start = null;
    for ( int i = 0; i < matrix.size(); i++ ) {
      final var row = matrix.get( i );
      for ( int j = 0; j < row.size(); j++ ) {
        final Position position = new Position( i, j );
        char symbol = row.get( j );
        if ( symbol == 'S' ) {
          start = position;
          symbol = getStartSymbol( position, matrix );
        }
        tiles.put( position, new Tile( position, symbol ) );
        connections.putAll( position, getConnectedTiles( position, symbol ) );
      }
    }
    return start;
  }

  private static boolean isBoundary(final Position position, final int maxI, final int maxJ) {
    return position.i == 0 || position.i == maxI || position.j == 0 || position.j == maxJ;
  }

  private static List<Position> getConnectedTiles(final Position position, final char symbol) {
    return Pipe.of( symbol ).connections.stream().map( position::add ).toList();
  }

  private static char getStartSymbol(final Position position, final List<List<Character>> matrix) {
    final Pipe north = getPipe( position.add( NORTH ), matrix );
    final Pipe south = getPipe( position.add( SOUTH ), matrix );
    final Pipe east = getPipe( position.add( EAST ), matrix );
    if ( north.isConnected( SOUTH ) ) {
      if ( south.isConnected( NORTH ) ) {
        return '|';
      } else {
        return east.isConnected( WEST ) ? 'L' : 'J';
      }
    }
    if ( south.isConnected( NORTH ) ) {
      return east.isConnected( WEST ) ? 'F' : '7';
    }
    return '-';
  }

  private static Pipe getPipe(final Position position, final List<List<Character>> matrix) {
    return Pipe.of(
        listGetOrDefault( listGetOrDefault( matrix, (int) position.i, List.of() ), (int) position.j,
            DOT ) );
  }

  private static void print(final List<List<Character>> matrix, final Set<Position> innerTiles,
      final Set<Position> outerTiles) {
    if ( !Utils.shouldPrint() ) {
      return;
    }
    for ( int i = 0; i < matrix.size(); i++ ) {
      final var row = matrix.get( i );
      for ( int j = 0; j < row.size(); j++ ) {
        final Position position = new Position( i, j );
        if ( innerTiles.contains( position ) ) {
          System.out.print( "I" );
        } else if ( outerTiles.contains( position ) ) {
          System.out.print( "O" );
        } else {
          System.out.print( row.get( j ) );
        }
      }
      System.out.println();
    }
  }

  private record Tile(Position position, Character symbol) {

  }

  private record Position(double i, double j) {

    Position(int i, int j) {
      this( (double) i, (double) j );
    }

    Position add(double i, double j) {
      return new Position( this.i + i, this.j + j );
    }

    Position add(final Direction direction) {
      return new Position( i + direction.i, j + direction.j );
    }

    boolean isSqueezed() {
      return isSqueezedVertically() || isSqueezedHorizontally();
    }

    boolean isSqueezedVertically() {
      return floor( i ) != i;
    }

    boolean isSqueezedHorizontally() {
      return floor( j ) != j;
    }
  }

  enum Direction {
    //@formatter:off
    NORTH( -1, 0 ), SOUTH( 1, 0 ), WEST( 0, -1 ), EAST( 0, 1 ),
    NORTHWEST( -1, -1 ), NORTHEAST( -1, 1 ), SOUTHWEST( 1, -1 ), SOUTHEAST( 1, 1 );
    //@formatter:on
    final int i;
    final int j;

    Direction(int i, int j) {
      this.i = i;
      this.j = j;
    }
  }

  enum Pipe {
    //@formatter:off
    VERTICAL( List.of( NORTH, SOUTH ) ),
    HORIZONTAL( List.of( EAST, WEST ) ),
    L( List.of( NORTH, EAST ) ),
    J( List.of( NORTH, WEST ) ),
    SEVEN( List.of( SOUTH, WEST ) ),
    F( List.of( SOUTH, EAST ) ),
    DOT( List.of() );
    //@formatter:on

    private final List<Direction> connections;

    Pipe(final List<Direction> connections) {
      this.connections = connections;
    }

    static Pipe of(final char symbol) {
      return switch ( symbol ) {
        case '|' -> VERTICAL;
        case '-' -> HORIZONTAL;
        case 'L' -> L;
        case 'J' -> J;
        case '7' -> SEVEN;
        case 'F' -> F;
        case '.' -> DOT;
        default -> throw new IllegalStateException( "Unexpected value: " + symbol );
      };
    }

    boolean isConnected(final Direction direction) {
      return connections.contains( direction );
    }
  }
}
