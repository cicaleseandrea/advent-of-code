package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.LongStream.range;

import com.adventofcode.Solution;
import com.adventofcode.utils.Triplet;
import com.adventofcode.utils.Utils;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class AoC182022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var lava = input.map( Utils::toLongList )
        .map( points -> new Triplet<>( points.get( 0 ), points.get( 1 ), points.get( 2 ) ) )
        .collect( toSet() );
    final var totalSurfaceArea = getSurfaceArea( lava );
    final var air = getInternalCubes( lava );
    final var airSurfaceArea = getSurfaceArea( air );
    final var lavaSurfaceArea = totalSurfaceArea - airSurfaceArea;
    return itoa( first ? totalSurfaceArea : lavaSurfaceArea );
  }

  private Set<Triplet<Long, Long, Long>> getInternalCubes(
      final Set<Triplet<Long, Long, Long>> lava) {
    final var pond = range( 0, 20 ).boxed().flatMap( i -> range( 0, 20 ).boxed()
            .flatMap( j -> range( 0, 20 ).boxed().map( k -> new Triplet<>( i, j, k ) ) ) )
        .collect( toSet() );

    //BFS to find all reachable cubes
    final var queue = new LinkedList<Triplet<Long, Long, Long>>();
    final var reachable = new HashSet<Triplet<Long, Long, Long>>();
    //start from 0,0,0
    queue.add( Triplet.ZERO );
    reachable.add( Triplet.ZERO );

    while ( !queue.isEmpty() ) {
      final var cube = queue.remove();
      for ( final var neighbour : getNeighbours( cube ) ) {
        //unexplored cube, in pond, not part of lava
        if ( pond.contains( neighbour ) && !lava.contains( neighbour ) && reachable.add(
            neighbour ) ) {
          //add to the queue
          queue.add( neighbour );
        }
      }
    }

    //remove lava and reachable cubes from the pond. what is left is air
    final var air = new HashSet<>( pond );
    air.removeAll( reachable );
    air.removeAll( lava );

    return air;
  }

  private long getSurfaceArea(final Set<Triplet<Long, Long, Long>> input) {
    final var cubes = new HashSet<>( input );
    var totalSides = cubes.size() * 6;
    for ( final var iterator = cubes.iterator(); iterator.hasNext(); iterator.remove() ) {
      final var cube = iterator.next();
      //remove 2 sides for each connected cube
      totalSides -= 2 * getNeighbours( cube ).stream().filter( cubes::contains ).count();
    }
    return totalSides;
  }

  private List<Triplet<Long, Long, Long>> getNeighbours(final Triplet<Long, Long, Long> cube) {
    return List.of( new Triplet<>( cube.getFirst() + 1, cube.getSecond(), cube.getThird() ),
        new Triplet<>( cube.getFirst() - 1, cube.getSecond(), cube.getThird() ),
        new Triplet<>( cube.getFirst(), cube.getSecond() + 1, cube.getThird() ),
        new Triplet<>( cube.getFirst(), cube.getSecond() - 1, cube.getThird() ),
        new Triplet<>( cube.getFirst(), cube.getSecond(), cube.getThird() + 1 ),
        new Triplet<>( cube.getFirst(), cube.getSecond(), cube.getThird() - 1 ) );
  }
}
