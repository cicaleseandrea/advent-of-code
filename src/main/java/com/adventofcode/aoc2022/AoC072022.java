package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.LongStream.concat;

import com.adventofcode.Solution;
import com.adventofcode.utils.Node;
import com.adventofcode.utils.Pair;
import java.util.stream.LongStream;
import java.util.stream.Stream;

class AoC072022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var root = getNode( input );
    final var sizes = getDirSizes( root );
    final long result;
    if ( first ) {
      result = sizes.filter( s -> s <= 100000L ).sum();
    } else {
      final var needed = 30000000L - (70000000L - root.getKey().getSecond());
      result = sizes.filter( s -> s >= needed ).min().orElseThrow();
    }
    return itoa( result );
  }

  private LongStream getDirSizes(final Node<Pair<String, Long>> n) {
    if ( n.getChildren().isEmpty() ) {
      return LongStream.of();
    } else {
      final var dirSize = n.getKey().getSecond();
      final var sizes = n.getChildren().stream().flatMapToLong( this::getDirSizes );
      return concat( sizes, LongStream.of( dirSize ) );
    }
  }

  private long computeDirSizes(final Node<Pair<String, Long>> n) {
    if ( n.getChildren().isEmpty() ) {
      return n.getKey().getSecond();
    } else {
      final var dirSize = n.getChildren().stream().mapToLong( this::computeDirSizes ).sum();
      n.getKey().setSecond( dirSize );
      return dirSize;
    }
  }

  private Node<Pair<String, Long>> getNode(final Stream<String> input) {
    var curr = new Node<>( new Pair<>( "", 0L ) );
    final var root = new Node<>( new Pair<>( "/", 0L ) );
    curr.addChild( root );
    for ( final var line : input.toList() ) {
      if ( line.startsWith( "$ cd" ) ) {
        final var dir = line.substring( 5 );
        if ( dir.equals( ".." ) ) {
          curr = curr.getParent().orElseThrow();
        } else {
          curr = curr.getChildren().stream().filter( s -> s.getKey().getFirst().equals( dir ) )
              .findFirst().orElseThrow();
        }
      } else if ( !line.startsWith( "$" ) ) {
        final var info = line.split( " " );
        final var size = line.startsWith( "dir" ) ? 0L : atol( info[0] );
        final var name = info[1];
        final var n = new Node<>( new Pair<>( name, size ) );
        curr.addChild( n );
      }
    }
    computeDirSizes( root );
    return root;
  }
}
