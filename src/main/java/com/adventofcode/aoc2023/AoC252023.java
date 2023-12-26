package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.DisjointSet;
import com.adventofcode.utils.DisjointSet.Node;
import com.adventofcode.utils.Utils;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

class AoC252023 implements Solution {

  private static final int MIN_CUT_SIZE = 3;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    final List<Edge> wires = getEdges( input );

    Optional<Long> result = Optional.empty();
    while ( result.isEmpty() ) {
      result = computeResult( wires );
    }
    return itoa( result.orElseThrow() );
  }

  private static List<Edge> getEdges(final Stream<String> input) {
    return input.map( line -> Utils.splitOnTabOrSpace( line.replace( ":", "" ) ) )
        .flatMap( components -> components.subList( 1, components.size() ).stream()
            .map( component -> new Edge( components.get( 0 ), component ) ) )
        .toList();
  }

  private static Optional<Long> computeResult(final List<Edge> wires) {
    //create disjoint sets
    final DisjointSet<String> subgraphs = new DisjointSet<>();
    wires.stream().flatMap( e -> Stream.of( e.a, e.b ) ).forEach( subgraphs::makeSet );

    //1. find a cut
    //continue until there are only two subgraphs
    while ( subgraphs.getSize() > 2 ) {
      //choose an edge at random
      final Edge wire = wires.get( ThreadLocalRandom.current().nextInt( wires.size() ) );
      //contract the edge
      subgraphs.union( wire.a, wire.b );
    }

    //2. find edges crossing the cut
    final List<Edge> cutWires = wires.stream().filter( wire -> {
      final Node<String> subgraphA = subgraphs.find( wire.a );
      final Node<String> subgraphB = subgraphs.find( wire.b );
      return !subgraphA.equals( subgraphB );
    } ).limit( MIN_CUT_SIZE + 1L ).toList();

    //3. check if cut is minimum
    if ( cutWires.size() > MIN_CUT_SIZE ) {
      return Optional.empty();
    }

    //4. compute result
    final Edge wireToDisconnect = cutWires.get( 0 );
    final Node<String> subgraphA = subgraphs.find( wireToDisconnect.a );
    final Node<String> subgraphB = subgraphs.find( wireToDisconnect.b );
    return Optional.of( (long) subgraphA.getSize() * subgraphB.getSize() );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return MERRY_CHRISTMAS;
  }

  private record Edge(String a, String b) {

  }
}
