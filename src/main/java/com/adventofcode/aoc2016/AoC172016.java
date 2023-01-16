package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.LEFT;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.computeMD5AsString;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

class AoC172016 implements Solution {

  private static final List<Direction> NEIGHBOURS = List.of( UP, DOWN, LEFT, RIGHT );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    return getShortestPath( new Pair<>( 0L, 0L ), new Pair<>( 3L, 3L ), getFirstString( input ),
        first );
  }

  private String getShortestPath(final Pair<Long, Long> src, final Pair<Long, Long> dst,
      final String passCode, final boolean first) {
    //BFS (unweighted graph, no need for Dijkstra) to find:
    //shortest path to destination
    //longest path to destination
    final var queue = new LinkedList<Node>();
    final var seen = new HashSet<Node>();
    //start from source
    final var srcNode = new Node( src, "" );
    queue.add( srcNode );
    seen.add( srcNode );

    int max = 0;
    while ( !queue.isEmpty() ) {
      final var curr = queue.remove();
      if ( curr.pos.equals( dst ) ) {
        if ( first ) {
          //shortest path
          return curr.path;
        } else {
          //store path length
          max = curr.path.length();
          //stop this path
        }
      } else {
        for ( final var neighbour : getNeighbours( curr, passCode ) ) {
          //unseen neighbour
          if ( seen.add( neighbour ) ) {
            //add to the queue
            queue.add( neighbour );
          }
        }
      }
    }

    return itoa( max );
  }

  private List<Node> getNeighbours(final Node curr, final String passCode) {
    final var hash = computeMD5AsString( passCode + curr.path );
    final List<Node> neighbours = new ArrayList<>();
    for ( int i = 0; i < NEIGHBOURS.size(); i++ ) {
      if ( isOpen( hash.charAt( i ) ) ) {
        final var direction = NEIGHBOURS.get( i );
        final var nPos = new Pair<>( curr.pos );
        direction.move( nPos );
        final var x = nPos.getFirst();
        final var y = nPos.getSecond();
        if ( x >= 0 && x < 4 && y >= 0 && y < 4 ) {
          final var neighbour = new Node( nPos, curr.path + direction.name().charAt( 0 ) );
          neighbours.add( neighbour );
        }
      }
    }
    return neighbours;
  }

  private boolean isOpen(final char c) {
    return switch ( c ) {
      case 'b', 'c', 'd', 'e', 'f' -> true;
      default -> false;
    };
  }

  private record Node(Pair<Long, Long> pos, String path) {

  }
}
