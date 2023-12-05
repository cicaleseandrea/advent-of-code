package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Comparator.comparingLong;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

class AoC052023 implements Solution {


  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, AoC052023::readSeedsAsNumbers );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, AoC052023::readSeedsAsRanges );
  }

  private static String solve(final Stream<String> input,
      final Function<List<Long>, List<Range>> getSeeds) {
    final Iterator<String> inputIterator = input.iterator();

    final List<Range> seeds = getSeeds.apply( Utils.toLongList( inputIterator.next() ) );
    final Mappings seedToSoil = readMapping( inputIterator, "seed-to-soil" );
    final Mappings soilToFertilizer = readMapping( inputIterator, "soil-to-fertilizer" );
    final Mappings fertilizerToWater = readMapping( inputIterator, "fertilizer-to-water" );
    final Mappings waterToLight = readMapping( inputIterator, "water-to-light" );
    final Mappings lightToTemperature = readMapping( inputIterator, "light-to-temperature" );
    final Mappings temperatureToHumidity = readMapping( inputIterator, "temperature-to-humidity" );
    final Mappings humidityToLocation = readMapping( inputIterator, "humidity-to-location" );

    final List<Range> locations = seeds.stream().flatMap( seedToSoil::toDestinations )
        .flatMap( soilToFertilizer::toDestinations ).flatMap( fertilizerToWater::toDestinations )
        .flatMap( waterToLight::toDestinations ).flatMap( lightToTemperature::toDestinations )
        .flatMap( temperatureToHumidity::toDestinations )
        .flatMap( humidityToLocation::toDestinations ).toList();

    return itoa( locations.stream().mapToLong( Range::low ).min().orElseThrow() );
  }

  private static List<Range> readSeedsAsNumbers(final List<Long> numbers) {
    return Lists.transform( numbers, Range::new );
  }

  private static List<Range> readSeedsAsRanges(final List<Long> numbers) {
    final List<Range> seeds = new ArrayList<>();
    for ( int i = 0; i < numbers.size(); i += 2 ) {
      final long low = numbers.get( i );
      final long length = numbers.get( i + 1 );
      seeds.add( new Range( low, low + length - 1 ) );
    }
    return seeds;
  }

  private static Mappings readMapping(final Iterator<String> inputIterator,
      final String mappingName) {
    while ( inputIterator.hasNext() && !inputIterator.next().startsWith( mappingName ) ) {
      //reach expected mapping starting line
    }

    String line = "";
    final List<Mapping> mappings = new ArrayList<>();
    while ( inputIterator.hasNext() && !(line = inputIterator.next()).isEmpty() ) {
      final List<Long> numbers = Utils.toLongList( line );
      final long low = numbers.get( 1 );
      final long high = numbers.get( 1 ) + numbers.get( 2 ) - 1;
      final Range source = new Range( low, high );
      final long offset = numbers.get( 0 ) - numbers.get( 1 );
      mappings.add( new Mapping( source, offset ) );
    }
    return new Mappings( mappings );
  }

  private record Range(long low, long high) {

    Range(final long element) {
      this( element, element );
    }

    private boolean overlaps(final Range other) {
      return !(low > other.high || other.low > high);
    }

    static Optional<Range> create(final long low, final long high) {
      if ( high < low ) {
        return Optional.empty();
      } else {
        return Optional.of( new Range( low, high ) );
      }
    }
  }

  private record Mapping(Range range, long offset) {

    Range toDestination(final Range source) {
      checkArgument( range.low <= source.low && source.high <= range.high,
          "Source %s is not inside range %s", source, range );
      return new Range( source.low + offset, source.high + offset );
    }
  }

  private record Mappings(List<Mapping> mappings) {

    Mappings(final List<Mapping> mappings) {
      //sort by source mapping
      this.mappings = new ArrayList<>( mappings );
      this.mappings.sort( comparingLong( mapping -> mapping.range.low ) );
    }

    Stream<Range> toDestinations(final Range source) {
      final List<Range> destinations = new ArrayList<>();
      Range unmapped = source;
      for ( Mapping mapping : mappings ) {
        if ( mapping.range.overlaps( unmapped ) ) {
          //mapping applies to source range. source range can be split into 3
          final Optional<Range> unmappedLeft = Range.create( unmapped.low, mapping.range.low - 1 );
          unmappedLeft.ifPresent( destinations::add );

          final Optional<Range> intersection = Range.create( max( mapping.range.low, unmapped.low ),
              min( mapping.range.high, unmapped.high ) );
          intersection.map( mapping::toDestination ).ifPresent( destinations::add );

          final Optional<Range> unmappedRight = Range.create( mapping.range.high + 1,
              unmapped.high );
          if ( unmappedRight.isPresent() ) {
            //continue checking source range against other mappings
            unmapped = unmappedRight.get();
          } else {
            //return all destinations
            return destinations.stream();
          }
        }
      }
      //add last unmapped source range
      destinations.add( unmapped );
      return destinations.stream();
    }
  }

}
