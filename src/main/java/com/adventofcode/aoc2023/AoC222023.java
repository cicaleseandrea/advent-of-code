package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.max;
import static java.util.Comparator.comparingInt;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

@SuppressWarnings("UnstableApiUsage")
class AoC222023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private static String solve(final Stream<String> input, final boolean first) {
    final List<Brick> bricks = input.map( AoC222023::toBrick )
        .sorted( comparingInt( brick -> brick.end.z ) ) //sort by end point z axis
        .toList();
    final List<Brick> settledBricks = fallDown( bricks );

    final Graph<Brick> supportingGraph = computeSupportingGraph( settledBricks );
    if ( first ) {
      return itoa( settledBricks.size() - countUnsafe( supportingGraph ) );
    } else {
      return itoa( countFallen( supportingGraph ) );
    }
  }

  private static long countUnsafe(final Graph<Brick> supportingGraph) {
    return supportingGraph.nodes().stream()
        .filter( supported -> supportingGraph.inDegree( supported ) == 1 )
        .map( supportingGraph::predecessors )
        .flatMap( Collection::stream )
        .distinct().count();
  }

  private static int countFallen(final Graph<Brick> supporting) {
    int fallen = 0;
    //remove one brick at a time
    for ( final Brick removed : supporting.nodes() ) {
      final MutableGraph<Brick> graphCopy = Graphs.copyOf( supporting );
      final Queue<Brick> fallingBricks = new LinkedList<>();
      fallingBricks.add( removed );
      while ( !fallingBricks.isEmpty() ) {
        final Brick fallingBrick = fallingBricks.remove();
        //find bricks supported only by this falling brick
        final List<Brick> willFall = graphCopy.successors( fallingBrick ).stream()
            .filter( supported -> graphCopy.inDegree( supported ) == 1 ).toList();
        fallingBricks.addAll( willFall );
        fallen += willFall.size();
        //remove falling brick
        graphCopy.removeNode( fallingBrick );
      }
    }
    return fallen;
  }

  private static List<Brick> fallDown(final List<Brick> bricks) {
    final List<Brick> settledBricks = new ArrayList<>();
    for ( final Brick brick : bricks ) {
      int newZ = 1;
      for ( final Brick settledBrick : settledBricks ) {
        if ( settledBrick.couldSupport( brick ) ) {
          newZ = max( newZ, settledBrick.end.z + 1 );
        }
      }
      settledBricks.add( brick.withStartZ( newZ ) );
    }
    return settledBricks;
  }

  private static Graph<Brick> computeSupportingGraph(final List<Brick> bricks) {
    final ImmutableGraph.Builder<Brick> supportingGraph = GraphBuilder.directed().immutable();
    bricks.forEach( supporting -> bricks.stream().filter( supporting::supports )
        .forEach( supported -> supportingGraph.putEdge( supporting, supported ) ) );
    return supportingGraph.build();
  }

  private static Brick toBrick(final String str) {
    final List<Long> numbers = Utils.toPositiveLongList( str );
    return new Brick(
        new Point( numbers.get( 0 ).intValue(), numbers.get( 1 ).intValue(),
            numbers.get( 2 ).intValue() ),
        new Point( numbers.get( 3 ).intValue(), numbers.get( 4 ).intValue(),
            numbers.get( 5 ).intValue() )
    );
  }

  private record Brick(Point start, Point end) {

    boolean couldSupport(final Brick other) {
      return overlaps( start.x, end.x, other.start.x, other.end.x ) && overlaps( start.y, end.y,
          other.start.y, other.end.y ) && end.z < other.start.z;
    }

    boolean supports(final Brick other) {
      return couldSupport( other ) && end.z + 1 == other.start.z;
    }

    Brick withStartZ(final int z) {
      return new Brick( start.withZ( z ), end.withZ( end.z - (start.z - z) ) );
    }
  }

  private record Point(int x, int y, int z) {

    Point withZ(int z) {
      return new Point( x, y, z );
    }
  }

  private static boolean overlaps(int startA, int endA, int startB, int endB) {
    return !(startA > endB || startB > endA);
  }
}
