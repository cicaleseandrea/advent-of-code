package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.DisjointSet;
import com.adventofcode.utils.DisjointSet.Node;
import com.adventofcode.utils.Utils;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

class AoC252023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    final List<Edge> wires = getEdges( input );
    final Set<String> components = wires.stream().flatMap( e -> Stream.of( e.a, e.b ) )
        .collect( toSet() );

    Optional<Long> result = Optional.empty();
    while ( result.isEmpty() ) {
      result = computeResult( components, wires );
    }
    return itoa( result.orElseThrow() );
  }

  private static List<Edge> getEdges(final Stream<String> input) {
    return input.map( line -> Utils.splitOnTabOrSpace( line.replace( ":", "" ) ) )
        .flatMap( components -> components.subList( 1, components.size() ).stream()
            .map( component -> new Edge( components.get( 0 ), component ) ) )
        .toList();
  }

  private static Optional<Long> computeResult(final Set<String> components,
      final List<Edge> wires) {
    //create disjoint sets
    final DisjointSet<String> subgraphs = new DisjointSet<>();
    components.forEach( subgraphs::makeSet );

    //1. find a cut
    final int nWires = wires.size();
    int nSubgraphs = components.size();
    //continue until there are only two subgraphs
    while ( nSubgraphs > 2 ) {
      //choose an edge at random
      final Edge wire = wires.get( ThreadLocalRandom.current().nextInt( nWires ) );
      //contract the edge
      if ( subgraphs.union( wire.a, wire.b ) ) {
        nSubgraphs--;
      }
    }

    //2. check if cut is minimum
    int nWiresToDisconnect = 0;
    long result = 0;
    for ( final Edge wire : wires ) {
      final Node<String> subgraphA = subgraphs.find( wire.a );
      final Node<String> subgraphB = subgraphs.find( wire.b );
      if ( !subgraphA.equals( subgraphB ) ) {
        //this wire crosses the cut
        nWiresToDisconnect++;
        if ( nWiresToDisconnect > 3 ) {
          //this cut is not minimum
          return Optional.empty();
        }
        result = (long) subgraphA.getSize() * subgraphB.getSize();
      }
    }
    return Optional.of( result );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return MERRY_CHRISTMAS;
  }

  private record Edge(String a, String b) {

  }
}
