package com.adventofcode.aoc2018;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.PIPE;
import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.TILDE;
import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.getIterable;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.shouldPrint;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC172018 implements Solution {

    public String solveFirstPart( final Stream<String> input ) {
        return solve( input, c -> c == TILDE || c == PIPE );
    }

    public String solveSecondPart( final Stream<String> input ) {
        return solve( input, c -> c == TILDE );
    }

    private static String solve( final Stream<String> input, final Predicate<Character> count ) {
        final var borders = new Pair<>( new Pair<>( 0, 0 ), new Pair<>( 0, 0 ) );
        final char[][] map = initialize( input, borders );
        int x = 500;
        int y = 0;
        map[x][y] = PIPE;
        final int lastY = borders.getSecond().getSecond();
        fall( map, x, y, lastY );
        if ( shouldPrint() ) {
            print( map, borders );
        }
        return itoa( countWater( map, borders, count ) );
    }

    private static void fall( final char[][] map, final int x, int y, final int lastY ) {
        boolean withinBorders;
        // fall until you go under the last point or you hit clay (#) or still water (~)
        while ( ( withinBorders = y <= lastY ) && canFlow( map[x][y] ) ) {
            // running water
            map[x][y] = PIPE;
            y++;
        }
        // if you did not go under the last point, spread out horizontally
        if ( withinBorders ) {
            boolean fall;
            do {
                // when blocked, go up and fill with running water
                y--;
                final boolean fallLeft = fill( true, map, x, y, PIPE, lastY );
                final boolean fallRight = fill( false, map, x + 1, y, PIPE, lastY );
                fall = fallLeft || fallRight;
                // if the water is blocked, this level becomes still water
                if ( !fall ) {
                    fill( true, map, x, y, TILDE, lastY );
                    fill( false, map, x + 1, y, TILDE, lastY );
                }
            } while ( !fall );
        }
    }

    private static boolean fill( final boolean left, final char[][] map, int x, int y,
            final char water, final int lastY ) {
        boolean fall;
        // move until you either fall or hit clay (#) or still water (~)
        while ( !( fall = canFlow( map[x][y + 1] ) ) && canFlow( map[x][y] ) ) {
            // fill with water
            map[x][y] = water;
            if ( left ) {
                x--;
            } else {
                x++;
            }
        }
        // fall
        if ( fall ) {
            fall( map, x, y, lastY );
        }
        return fall;
    }

    private static int countWater( final char[][] map,
            final Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> borders,
            final Predicate<Character> count ) {
        final int firstX = 0;
        final int firstY = borders.getFirst().getSecond();
        final int lastX = borders.getSecond().getFirst();
        final int lastY = borders.getSecond().getSecond();
        int res = 0;
        for ( int y = firstY; y <= lastY; y++ ) {
            for ( int x = firstX; x <= lastX; x++ ) {
                if ( count.test( map[x][y] ) ) {
                    res++;
                }
            }
        }
        return res;
    }

    private static char[][] initialize( final Stream<String> input,
            final Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> borders ) {
        final Map<Pair<Integer, Integer>, Character> temporaryMap = new HashMap<>();
        for ( final String row : getIterable( input ) ) {
            final boolean startX = row.charAt( 0 ) == 'x';
            String[] tmp = row.split( "=" );
            int x = 0;
            int y = 0;
            int i = extractIntegerFromString( tmp[1] );
            if ( startX ) {
                x = i;
            } else {
                y = i;
            }
            tmp = tmp[2].split( "\\.+" );
            final int min = extractIntegerFromString( tmp[0] );
            final int max = extractIntegerFromString( tmp[1] );
            for ( i = min; i <= max; i++ ) {
                if ( startX ) {
                    y = i;
                } else {
                    x = i;
                }
                temporaryMap.put( new Pair<>( x, y ), HASH );
            }
        }
        final var statsX = temporaryMap.keySet()
                .stream()
                .mapToInt( Pair::getFirst )
                .summaryStatistics();
        final var statsY = temporaryMap.keySet()
                .stream()
                .mapToInt( Pair::getSecond )
                .summaryStatistics();
        borders.getFirst().setFirst( statsX.getMin() );
        borders.getFirst().setSecond( statsY.getMin() );
        borders.getSecond().setFirst( statsX.getMax() );
        borders.getSecond().setSecond( statsY.getMax() );
        final char[][] map = new char[statsX.getMax() + 1][statsY.getMax() + 1];
        for ( final var point : temporaryMap.entrySet() ) {
            map[point.getKey().getFirst()][point.getKey().getSecond()] = point.getValue();
        }
        return map;
    }

    private static void print( final char[][] map,
            final Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> borders ) {
        final Pair<Integer, Integer> first = borders.getFirst();
        final Pair<Integer, Integer> last = borders.getSecond();
        for ( int y = first.getSecond(); y <= last.getSecond(); y++ ) {
            for ( int x = first.getFirst(); x <= last.getFirst(); x++ ) {
                final char c = map[x][y];
                System.out.print( c == 0 ? SPACE : c );
            }
            System.out.println();
        }
        System.out.println();
        try {
            Thread.sleep( 2000 );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

    private static boolean canFlow( final char c ) {
        return c != HASH && c != TILDE;
    }
}
