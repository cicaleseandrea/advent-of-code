package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class AoC052023 implements Solution {


  @Override
  public String solveFirstPart(final Stream<String> input) {
    final Iterator<String> inputIterator = input.iterator();
    final List<Long> seeds = Utils.toLongList( inputIterator.next() );
    final Mapping seedToSoil = readMapping( inputIterator, "seed-to-soil" );
    final Mapping soilToFertilizer = readMapping( inputIterator, "soil-to-fertilizer" );
    final Mapping fertilizerToWater = readMapping( inputIterator, "fertilizer-to-water" );
    final Mapping waterToLight = readMapping( inputIterator, "water-to-light" );
    final Mapping lightToTemperature = readMapping( inputIterator, "light-to-temperature" );
    final Mapping temperatureToHumidity = readMapping( inputIterator, "temperature-to-humidity" );
    final Mapping humidityToLocation = readMapping( inputIterator, "humidity-to-location" );
    final List<Long> locations = seeds.stream()
        .map( seedToSoil::getDestination )
        .map( soilToFertilizer::getDestination )
        .map( fertilizerToWater::getDestination )
        .map( waterToLight::getDestination )
        .map( lightToTemperature::getDestination )
        .map( temperatureToHumidity::getDestination )
        .map( humidityToLocation::getDestination ).toList();

    return itoa( locations.stream().mapToLong( Long::longValue ).min().orElseThrow() );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input );
  }

  private String solve(final Stream<String> input) {
    final Iterator<String> inputIterator = input.iterator();
    final List<Long> numbers = Utils.toLongList( inputIterator.next() );
    final List<Long> seeds = new ArrayList<>();
    for ( int i = 0; i < numbers.size(); i += 2 ) {
      final long start = numbers.get( i );
      for ( int j = 0; j < numbers.get( i + 1 ); j++ ) {
        seeds.add( start + j );
      }
    }
    System.out.println( seeds );
    final Mapping seedToSoil = readMapping( inputIterator, "seed-to-soil" );
    final Mapping soilToFertilizer = readMapping( inputIterator, "soil-to-fertilizer" );
    final Mapping fertilizerToWater = readMapping( inputIterator, "fertilizer-to-water" );
    final Mapping waterToLight = readMapping( inputIterator, "water-to-light" );
    final Mapping lightToTemperature = readMapping( inputIterator, "light-to-temperature" );
    final Mapping temperatureToHumidity = readMapping( inputIterator, "temperature-to-humidity" );
    final Mapping humidityToLocation = readMapping( inputIterator, "humidity-to-location" );
    final List<Long> locations = seeds.stream()
        .map( seedToSoil::getDestination )
        .map( soilToFertilizer::getDestination )
        .map( fertilizerToWater::getDestination )
        .map( waterToLight::getDestination )
        .map( lightToTemperature::getDestination )
        .map( temperatureToHumidity::getDestination )
        .map( humidityToLocation::getDestination ).toList();

    return itoa( locations.stream().mapToLong( Long::longValue ).min().orElseThrow() );
  }

  private Mapping readMapping(final Iterator<String> inputIterator, final String mappingName) {
    while ( inputIterator.hasNext() && !inputIterator.next().startsWith( mappingName ) ) {
      //reach expected mapping starting line
    }

    String line = "";
    final List<Range> ranges = new ArrayList<>();
    while ( inputIterator.hasNext() && !(line = inputIterator.next()).isEmpty() ) {
      final List<Long> numbers = Utils.toLongList( line );
      final long low = numbers.get( 1 );
      final long high = numbers.get( 1 ) + numbers.get( 2 ) - 1;
      final long offset = numbers.get( 0 ) - numbers.get( 1 );
      ranges.add( new Range( low, high, offset ) );
    }
    //sort by source range
    ranges.sort( Comparator.comparingLong( Range::low ) );
    return new Mapping( ranges );
  }

  private record Range(long low, long high, long offset) {

    long getDestination(long source) {
      if ( contains( source ) ) {
        return source + offset;
      } else {
        throw new IllegalArgumentException();
      }
    }

    boolean contains(long source) {
      return low <= source && source <= high;
    }

    public int compareTo(long source) {
      if ( contains( source ) ) {
        return 0;
      } else if ( source < low ) {
        return 1;
      } else {
        return -1;
      }
    }

  }

  private record Mapping(List<Range> ranges) {

    private Mapping(final List<Range> ranges) {
      this.ranges = new ArrayList<>( ranges );
    }

    long getDestination(long source) {
      final Optional<Range> rangeMaybe = binarySearch( ranges.toArray( Range[]::new ),
          source );
      return rangeMaybe.map( r -> r.getDestination( source ) ).orElse( source );
    }
  }

  private static Optional<Range> binarySearch(
      final Range[] ranges, final long source) {
    int low = 0;
    int high = ranges.length - 1;
    while ( low <= high ) {
      int mid = low + ((high - low) / 2);
      int comparison = ranges[mid].compareTo( source );
      if ( comparison < 0 ) {
        low = mid + 1;
      } else if ( comparison > 0 ) {
        high = mid - 1;
      } else {
        return Optional.of( ranges[mid] );
      }
    }
    return Optional.empty();
  }
}
