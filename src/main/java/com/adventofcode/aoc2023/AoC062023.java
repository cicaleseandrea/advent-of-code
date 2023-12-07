package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

class AoC062023 implements Solution {


  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input.toList() );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input.map( s -> s.replace( " ", "" ) ).toList() );
  }

  private String solve(final List<String> input) {
    final Stream<Long> times = Utils.toLongStream( input.get( 0 ) );
    final Stream<Long> distances = Utils.toLongStream( input.get( 1 ) );
    final Stream<Race> races = Streams.zip( times, distances, Race::new );

    return itoa( races.mapToLong( AoC062023::countVictories ).reduce( 1, (a, b) -> a * b ) );
  }

  private static long countVictories(final Race race) {
    return LongStream.range( 1, race.time ).parallel().map( n -> n * (race.time - n) )
        .filter( n -> n > race.distance ).count();
  }

  private record Race(long time, long distance) {

  }
}
