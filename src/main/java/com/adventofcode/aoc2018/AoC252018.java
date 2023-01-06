package com.adventofcode.aoc2018;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.abs;

import com.adventofcode.Solution;
import com.adventofcode.utils.DisjointSet;
import com.adventofcode.utils.Utils;
import java.util.List;
import java.util.stream.Stream;

class AoC252018 implements Solution {

    @Override
    public String solveFirstPart(final Stream<String> input) {
        final List<Point> points = input.map( Utils::toLongList ).map( Point::new ).toList();
        final DisjointSet<Point> constellations = new DisjointSet<>();
        for ( int i = 0; i < points.size(); i++ ) {
            final var one = points.get( i );
            constellations.makeSet( one );
            for ( int j = i + 1; j < points.size(); j++ ) {
                final var two = points.get( j );
                constellations.makeSet( two );
                final long dist = getDistance( one, two );
                if ( dist <= 3 ) {
                    constellations.union( one, two );
                }
            }
        }
        return itoa( constellations.getSize() );
    }

    private static long getDistance(final Point one, final Point two) {
        return abs( one.x - two.x ) + abs( one.y - two.y ) + abs( one.z - two.z ) + abs(
            one.t - two.t );
    }

    @Override
    public String solveSecondPart(final Stream<String> input) {
        return MERRY_CHRISTMAS;
    }

    private record Point(long x, long y, long z, long t) {

        Point(final List<Long> numbers) {
            this( numbers.get( 0 ), numbers.get( 1 ), numbers.get( 2 ), numbers.get( 3 ) );
        }
    }
}