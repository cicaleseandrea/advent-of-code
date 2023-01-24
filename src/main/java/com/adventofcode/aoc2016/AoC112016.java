package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnTabOrSpace;
import static java.lang.Math.min;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

class AoC112016 implements Solution {

  private static final int FLOOR_SIZE = 4;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    return itoa( computeShortestPath( getFloors( input.toList(), first ) ) );
  }

  private int computeShortestPath(final Floor[] floors) {
    //BFS (unweighted graph, no need for Dijkstra) to find shortest path to destination
    final var queue = new LinkedList<State>();
    final var distances = new HashMap<State, Integer>();

    final State src = new State( 0, floors );
    queue.add( src );
    distances.put( src, 0 );

    while ( !queue.isEmpty() ) {
      final var curr = queue.remove();
      if ( curr.floors[0].isEmpty() && curr.floors[1].isEmpty() && curr.floors[2].isEmpty() ) {
        return distances.get( curr );
      }
      for ( final var neighbour : getNeighbours( curr ) ) {
        if ( !distances.containsKey( neighbour ) ) {
          //add to the queue
          queue.add( neighbour );
          //add distance from source
          distances.put( neighbour, distances.get( curr ) + 1 );
        }
      }
    }

    throw new IllegalStateException();
  }

  private Set<State> getNeighbours(final State curr) {
    final var elevator = curr.elevator;
    final var floors = curr.floors;
    final Set<State> neighbours = new HashSet<>();
    final var fromFloor = floors[elevator];
    for ( int i = 1; i <= min( fromFloor.size(), 2 ); i++ ) {
      for ( final var moving : Sets.combinations( fromFloor.getComponents(), i ) ) {
        getNextState( elevator, elevator + 1, floors, moving ).ifPresent( neighbours::add );
        getNextState( elevator, elevator - 1, floors, moving ).ifPresent( neighbours::add );
      }
    }
    return neighbours;
  }

  private Optional<State> getNextState(final int from, final int to, final Floor[] floors,
      final Set<Component> moving) {
    if ( to < 0 || to > 3 ) {
      return Optional.empty();
    }
    final var state = new State( to, floors );
    final var fromFloor = state.floors[from];
    final var toFloor = state.floors[to];
    moving.forEach( m -> {
      fromFloor.remove( m );
      toFloor.add( m );
    } );
    return fromFloor.isSafe() && toFloor.isSafe() ? Optional.of( state ) : Optional.empty();
  }

  private Floor[] getFloors(final List<String> input, final boolean first) {
    final Floor[] floors = new Floor[FLOOR_SIZE];
    for ( int i = 0; i < FLOOR_SIZE; i++ ) {
      final Set<String> generators = new HashSet<>();
      final Set<String> microchips = new HashSet<>();
      final String additionalComponents;
      if ( !first && i == 0 ) {
        additionalComponents = "An elerium generator. An elerium-compatible microchip. A dilithium generator. A dilithium-compatible microchip.";
      } else {
        additionalComponents = "";
      }
      final var tokens = splitOnTabOrSpace(
          input.get( i ).concat( additionalComponents ).replace( "-compatible", " " )
              .replace( ".", " " ).replace( ",", " " ) );
      for ( int j = 1; j < tokens.size(); j++ ) {
        final var type = tokens.get( j );
        if ( type.equals( "generator" ) || type.equals( "microchip" ) ) {
          final var name = tokens.get( j - 1 );
          var components = type.equals( "generator" ) ? generators : microchips;
          components.add( name );
        }
      }
      floors[i] = new Floor( generators, microchips );
    }
    return floors;
  }

  private record Floor(Set<String> generators, Set<String> microchips) {

    Floor(final Floor floor) {
      this( new HashSet<>( floor.generators ), new HashSet<>( floor.microchips ) );
    }

    void add(final Component component) {
      final var components = component.isGenerator ? generators : microchips;
      components.add( component.name );
    }

    void remove(final Component component) {
      final var components = component.isGenerator ? generators : microchips;
      components.remove( component.name );
    }

    Set<Component> getComponents() {
      return Stream.concat( generators.stream().map( g -> new Component( g, true ) ),
          microchips.stream().map( m -> new Component( m, false ) ) ).collect( toSet() );
    }

    boolean isSafe() {
      //floor is safe if:
      //either there are no generators on this floor
      //or all microchips have their generator
      return generators.isEmpty() || generators.containsAll( microchips );
    }

    int size() {
      return generators.size() + microchips.size();
    }

    boolean isEmpty() {
      return size() == 0;
    }

    @Override
    public boolean equals(Object o) {
      if ( this == o ) {
        return true;
      }
      if ( o == null || getClass() != o.getClass() ) {
        return false;
      }
      Floor floor = (Floor) o;
      //floors can be considered equal if they contain the same number of generators and microchips
      return generators.size() == floor.generators.size()
          && microchips.size() == floor.microchips.size();
    }

    @Override
    public int hashCode() {
      return Objects.hash( generators.size(), microchips.size() );
    }
  }

  private record State(int elevator, Floor[] floors) {

    State(final int elevator, final Floor[] floors) {
      this.elevator = elevator;
      this.floors = new Floor[FLOOR_SIZE];
      for ( int i = 0; i < FLOOR_SIZE; i++ ) {
        this.floors[i] = new Floor( floors[i] );
      }
    }

    @Override
    public boolean equals(Object o) {
      if ( this == o ) {
        return true;
      }
      if ( o == null || getClass() != o.getClass() ) {
        return false;
      }
      State state = (State) o;
      return elevator == state.elevator && Arrays.equals( floors, state.floors );
    }

    @Override
    public int hashCode() {
      int result = Objects.hash( elevator );
      result = 31 * result + Arrays.hashCode( floors );
      return result;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper( this ).add( "elevator", elevator )
          .add( "floors", Arrays.toString( floors ) ).toString();
    }
  }
  private record Component(String name, boolean isGenerator) {

  }
}
