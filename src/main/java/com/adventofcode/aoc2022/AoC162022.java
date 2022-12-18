package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.Arrays.stream;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC162022 implements Solution {

  private static final String START = "AA";

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var valves = getValves( input );
    final var valvePositiveFlows = valves.getFirst();
    valvePositiveFlows.values().removeIf( flow -> flow == 0 );
    final var neighbours = valves.getSecond();
    final var shortestPaths = computeShortestPaths( neighbours, valvePositiveFlows.keySet() );

    final int maxPressure;
    if ( first ) {
      maxPressure = findMaxPressure( START, 0, valvePositiveFlows, 30, shortestPaths );
    } else {
      maxPressure = findMaxPressureWithHelp( START, 0, valvePositiveFlows, 26, shortestPaths );
    }
    return itoa( maxPressure );
  }

  private int findMaxPressureWithHelp(final String currValve, final int currValveFlow,
      final Map<String, Integer> closedValveFlows, final int minutes,
      final Map<String, Map<String, Integer>> shortestPaths) {
    final var forEachSizeInParallel = IntStream.rangeClosed( 0, closedValveFlows.size() / 2 )
        .parallel();
    return forEachSizeInParallel.flatMap(
        size -> Sets.combinations( closedValveFlows.keySet(), size ).stream().mapToInt( valves -> {
          //split valves in two complementary sets
          final var valvesForMe = closedValveFlows.entrySet().stream()
              .filter( e -> valves.contains( e.getKey() ) )
              .collect( toMap( Entry::getKey, Entry::getValue ) );
          final var valvesForElephant = closedValveFlows.entrySet().stream()
              .filter( e -> !valves.contains( e.getKey() ) )
              .collect( toMap( Entry::getKey, Entry::getValue ) );
          return findMaxPressure( currValve, currValveFlow, valvesForMe, minutes, shortestPaths )
              + findMaxPressure( currValve, currValveFlow, valvesForElephant, minutes,
              shortestPaths );
        } ) ).max().orElseThrow();
  }

  private int findMaxPressure(final String currValve, final int currValveFlow,
      final Map<String, Integer> closedValveFlows, int minutes,
      final Map<String, Map<String, Integer>> shortestPaths) {
    int maxPressure = 0;
    if ( currValveFlow > 0 && minutes >= 1 ) {
      //open current valve
      minutes--;
      maxPressure = currValveFlow * minutes;
    }
    if ( minutes == 0 ) {
      //no time left to explore more valves
      return maxPressure;
    }
    for ( final var valve : new HashSet<>( closedValveFlows.entrySet() ) ) {
      //select next valve to open
      final var valveName = valve.getKey();
      final var valveFlow = valve.getValue();
      //compute time needed to reach valve
      final var timeSpentMoving = shortestPaths.get( currValve ).get( valveName );
      final var minutesLeft = minutes - timeSpentMoving;
      if ( minutesLeft < 1 ) {
        //no time left to open next valve
        continue;
      }

      closedValveFlows.remove( valveName );
      final var pressure =
          currValveFlow * minutes + findMaxPressure( valveName, valveFlow, closedValveFlows,
              minutesLeft, shortestPaths );
      closedValveFlows.put( valveName, valveFlow );

      if ( pressure > maxPressure ) {
        maxPressure = pressure;
      }
    }
    return maxPressure;
  }

  private Map<String, Map<String, Integer>> computeShortestPaths(
      final Map<String, Set<String>> neighbours, final Set<String> positiveFlowValves) {
    return neighbours.keySet().stream()
        //we only need shortest paths between START valve and valves with positive flow
        .filter( valve -> valve.equals( START ) || positiveFlowValves.contains( valve ) )
        .collect( toMap( identity(), valve -> computeShortestPaths( valve, neighbours ) ) );
  }

  private Map<String, Integer> computeShortestPaths(final String src,
      final Map<String, Set<String>> neighbours) {
    //BFS to find shortest path (unweighted graph, no need for Dijkstra)
    final var queue = new LinkedList<String>();
    final var distances = new HashMap<String, Integer>();
    //start from source
    queue.add( src );
    distances.put( src, 0 );

    while ( !queue.isEmpty() ) {
      final var curr = queue.remove();
      for ( final var neighbour : neighbours.get( curr ) ) {
        if ( !distances.containsKey( neighbour ) ) {
          //add to the queue
          queue.add( neighbour );
          //add distance from source
          distances.put( neighbour, distances.get( curr ) + 1 );
        }
      }
    }

    return distances;
  }


  private Pair<Map<String, Integer>, Map<String, Set<String>>> getValves(
      final Stream<String> input) {
    final var flows = new HashMap<String, Integer>();
    final var neighbours = new HashMap<String, Set<String>>();
    input.forEach( line -> {
      final var tokens = line.split( " " );
      final var valve = tokens[1];
      final var flow = extractIntegerFromString( line );
      flows.put( valve, flow );
      final var valves = stream( tokens ).skip( 9 ).map( s -> s.replace( ",", "" ) )
          .collect( toSet() );
      neighbours.put( valve, valves );
    } );
    return new Pair<>( flows, neighbours );
  }
}
