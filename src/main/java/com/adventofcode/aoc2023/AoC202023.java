package com.adventofcode.aoc2023;

import static com.adventofcode.aoc2023.AoC202023.Pulse.HIGH;
import static com.adventofcode.aoc2023.AoC202023.Pulse.LOW;
import static com.adventofcode.aoc2023.AoC202023.Type.BROADCAST;
import static com.adventofcode.aoc2023.AoC202023.Type.CONJUNCTION;
import static com.adventofcode.aoc2023.AoC202023.Type.FLIP_FLOP;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

class AoC202023 implements Solution {

  private static final Message START = new Message( "button", "broadcaster", LOW );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private static String solve(final Stream<String> input, final boolean first) {
    final Map<String, Module> modules = getModules( input );
    final Set<String> conjunctionInputs = getConjunctionInputs( modules );
    final Queue<Message> messages = new LinkedList<>();
    final Map<String, Long> firstHigh = new HashMap<>();
    long nHigh = 0;
    long nLow = 0;
    for ( long i = 1; i <= 1000 || !first; i++ ) {
      messages.add( START ); //push button
      while ( !messages.isEmpty() ) {
        final Message message = messages.remove();
        if ( message.pulse == LOW ) {
          nLow++;
        } else {
          nHigh++;
          if ( conjunctionInputs.contains( message.sender ) ) {
            firstHigh.putIfAbsent( message.sender, i );
            if ( firstHigh.size() == conjunctionInputs.size() ) {
              //"rx" will receive LOW when all inputs to conjunction module are HIGH
              return itoa( firstHigh.values().stream().reduce( 1L, Utils::lcm ) );
            }
          }
        }
        final Module receiver = modules.getOrDefault( message.receiver, Module.EMPTY );
        messages.addAll( receiver.receive( message ) );
      }
    }
    return itoa( nLow * nHigh );
  }

  private static Set<String> getConjunctionInputs(final Map<String, Module> modules) {
    //get inputs of the conjunction module connected to "rx"
    return modules.values().stream().filter( module -> module.destinations.contains( "rx" ) )
        .findFirst().map( Module::inputs ).map( Map::keySet ).orElse( Set.of() );
  }

  private static Map<String, Module> getModules(final Stream<String> input) {
    final Map<String, Module> modules = new HashMap<>();
    input.forEach( line -> {
      final List<String> names = Utils.toWordList( line );
      final String name = names.get( 0 );
      final Type type = switch ( line.charAt( 0 ) ) {
        case '%' -> FLIP_FLOP;
        case '&' -> CONJUNCTION;
        default -> BROADCAST;
      };
      //add module with destinations
      final Module module = new Module( name, type );
      names.stream().skip( 1 ).forEach( module::addDestination );
      modules.put( name, module );
    } );
    //update modules inputs
    modules.values().forEach( module -> module.destinations.forEach(
        destination -> modules.getOrDefault( destination, Module.EMPTY )
            .addInput( module.name ) ) );
    return modules;
  }

  //not immutable ¯\_(ツ)_/¯
  private record Module(String name, Type type, Map<String, Pulse> inputs, Set<String> destinations,
                        AtomicBoolean on) {

    static final Module EMPTY = new Module( "", BROADCAST );

    Module(String name, Type type) {
      this( name, type, new HashMap<>(), new HashSet<>(), new AtomicBoolean() );
    }

    List<Message> receive(Message message) {
      switch ( type ) {
        case FLIP_FLOP -> {
          if ( message.pulse == LOW ) {
            on.set( !on.get() );
            return createMessages( on.get() ? HIGH : LOW );
          }
        }
        case CONJUNCTION -> {
          inputs.put( message.sender, message.pulse );
          return createMessages(
              inputs.values().stream().allMatch( pulse -> pulse == HIGH ) ? LOW : HIGH );
        }
        case BROADCAST -> {
          return createMessages( message.pulse );
        }
      }
      return List.of();
    }

    private List<Message> createMessages(Pulse pulse) {
      return destinations.stream().map( destination -> new Message( name, destination, pulse ) )
          .toList();
    }

    void addInput(String input) {
      inputs.put( input, LOW );
    }

    void addDestination(String destination) {
      destinations.add( destination );
    }
  }

  private record Message(String sender, String receiver, Pulse pulse) {

  }

  enum Pulse {LOW, HIGH;}

  enum Type {FLIP_FLOP, CONJUNCTION, BROADCAST;}
}
