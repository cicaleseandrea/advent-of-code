package com.adventofcode.utils;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Future;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Utils {
	public static final String EMPTY = "";
	public static final String MERRY_CHRISTMAS = "MERRY CHRISTMAS";
	public static final char SPACE = ' ';
	public static final char DOT = '.';
	public static final char TILDE = '~';
	public static final char PLUS = '+';
	public static final char AT = '@';
	public static final char HASH = '#';
	public static final char PIPE = '|';
	public static final char BLACK = '⬛';
	public static final char WHITE = '⬜';
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
			"yyyy-MM-dd HH:mm" );
	public static final Pattern LONG_PATTERN = Pattern.compile( "-?\\d+" );
	public static final Pattern POSITIVE_LONG_PATTERN = Pattern.compile( "\\d+" );
	public static final Pattern DOUBLE_PATTERN = Pattern.compile( "-?\\d+(?:\\.\\d+)?" );
	public static final Pattern WORD_PATTERN = Pattern.compile( "[a-zA-Z]+" );
	public static final List<Pair<Integer, Integer>> NEIGHBOURS = List.of( new Pair<>( -1, 0 ),
			new Pair<>( 1, 0 ), new Pair<>( 0, -1 ), new Pair<>( 0, 1 ) );

	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );

	private Utils() {
	}

	public static String reverseString( final String source ) {
		return new StringBuilder( source ).reverse().toString();
	}

	public static String getFirstString( final Stream<String> s ) {
		return s.findFirst().orElse( EMPTY );
	}

	public static Pair<Long, Long> createPairLong( final long[] tmp ) {
		return new Pair<>( tmp[0], tmp[1] );
	}

	public static Pair<Long, Long> createPairLong( final String[] tmp ) {
		return new Pair<>( atol( tmp[0].trim() ), atol( tmp[1].trim() ) );
	}

	public static Pair<Integer, Integer> createPairInteger( final int[] tmp ) {
		return new Pair<>( tmp[0], tmp[1] );
	}

	public static Pair<Integer, Integer> createPairInteger( final String[] tmp ) {
		return new Pair<>( atoi( tmp[0].trim() ), atoi( tmp[1].trim() ) );
	}

	public static long atol( final String s ) {
		return Long.parseLong( s );
	}

	public static int atoi( final String s ) {
		return Integer.parseInt( s );
	}

	public static Pair<String, String> createPairString( final String[] tmp ) {
		return new Pair<>( tmp[0], tmp[1] );
	}

	public static long boolToLong( final boolean b ) {
		return b ? 1 : 0;
	}

	public static boolean longToBool( final long i ) {
		return i != 0;
	}

	public static int charToInt( final char c ) {
		return Character.getNumericValue( c );
	}

	public static int charToInt( final int c ) {
		return Character.getNumericValue( c );
	}

	public static Comparator<Pair<Long, Long>> getPairComparator() {
		return Comparator.<Pair<Long, Long>>comparingLong( Pair::getSecond ).thenComparingLong(
				Pair::getFirst );
	}

	public static long incrementMod( final long i, final long mod ) {
		return incrementMod( i, 1, mod );
	}

	public static int incrementMod( final int i, final int mod ) {
		return incrementMod( i, 1, mod );
	}

	public static long incrementMod( final long i, final long j, final long mod ) {
		return mod != 0 ? ( i + j ) % mod : 0;
	}

	public static int incrementMod( final int i, final int j, final int mod ) {
		return mod != 0 ? ( i + j ) % mod : 0;
	}

	public static long decrementMod( final long i, final long mod ) {
		return decrementMod( i, 1, mod );
	}

	public static int decrementMod( final int i, final int mod ) {
		return decrementMod( i, 1, mod );
	}

	public static long decrementMod( final long i, final long j, final long mod ) {
		return mod != 0 ? Math.floorMod( i - j, mod ) : 0;
	}

	public static int decrementMod( final int i, final int j, final int mod ) {
		return mod != 0 ? Math.floorMod( i - j, mod ) : 0;
	}

	public static boolean areDistinct( final List<?> elements ) {
		return elements.stream().allMatch( new HashSet<>()::add );
	}

	public static boolean areNotAnagrams( final List<String> strings ) {
		return areDistinct( strings.stream()
				.map( String::toCharArray )
				.peek( Arrays::sort )
				.map( String::new )
				.toList() );
	}

	public static Stream<String> splitOnNewLine( final String s ) {
		return s.lines();
	}

	public static Stream<String> splitOnRegex( final String s, final String regex ) {
		return Stream.of( s.split( regex ) );
	}

	public static List<Long> toPositiveLongList( final String input ) {
		return toPositiveLongStream( input ).collect( toList() );
	}

	public static List<Long> toLongList( final String input ) {
		return toLongStream( input ).collect( toList() );
	}

	public static List<Long> toLongList( final Stream<String> stream ) {
		return toLongStream( stream ).collect( toList() );
	}

	public static List<String> toWordList( final String input ) {
		return toWordStream( input ).collect( toList() );
	}

	public static Stream<Long> toLongStream( final Stream<String> stream ) {
		return stream.map( Long::valueOf );
	}

	public static Stream<Long> toLongStream( final String input ) {
		return toLongStream( LONG_PATTERN.matcher( input ).results().map( MatchResult::group ) );
	}

	public static Stream<Long> toPositiveLongStream( final String input ) {
		return toLongStream(
				POSITIVE_LONG_PATTERN.matcher( input ).results().map( MatchResult::group ) );
	}

	public static List<Double> toDoubleList( final String input ) {
		return toDoubleStream( input ).collect( toList() );
	}

	public static List<Double> toDoubleList( final Stream<String> stream ) {
		return toDoubleStream( stream ).collect( toList() );
	}

	public static Stream<Double> toDoubleStream( final Stream<String> stream ) {
		return stream.map( Double::valueOf );
	}

	public static Stream<Double> toDoubleStream( final String input ) {
		return toDoubleStream(
				DOUBLE_PATTERN.matcher( input ).results().map( MatchResult::group ) );
	}

	public static Stream<String> toWordStream( final String input ) {
		return WORD_PATTERN.matcher( input ).results().map( MatchResult::group );
	}

	public static <T> Stream<T> iteratorToStream( final Iterator<T> iterator ) {
		return StreamSupport.stream(
				Spliterators.spliteratorUnknownSize( iterator, Spliterator.ORDERED ), false );
	}

	public static <T> Iterable<T> getIterable( final Stream<T> s ) {
		return s::iterator;
	}

	public static List<String> splitOnTabOrSpace( final String s ) {
		return List.of( s.split( "\\s+" ) );
	}

	public static <K> K listGetOrDefault( final List<K> list, final int i, final K defaultValue ) {
		if ( i >= 0 && i < list.size() ) {
			return list.get( i );
		} else {
			return defaultValue;
		}
	}

	public static <K> long incrementMapElement( final Map<K, Long> map, final K key ) {
		return map.merge( key, 1L, Long::sum );
	}

	public static <K> long decrementMapElement( final Map<K, Long> map, final K key ) {
		return decrementMapElement( map, key, true );
	}

	public static <K> long decrementMapElement( final Map<K, Long> map, final K key,
			final boolean remove ) {
		final long res = map.merge( key, -1L, Long::sum );
		if ( remove && res == 0 ) {
			return map.remove( key );
		}
		return res;
	}

	public static long incrementListElement( final List<Long> list, final int pos ) {
		return incrementListElement( list, pos, 1 );
	}

	public static long incrementListElement( final List<Long> list, final int pos,
			final long increment ) {
		final long newVal = list.get( pos ) + increment;
		list.set( pos, newVal );
		return newVal;
	}

	public static long decrementListElement( final List<Long> list, final int pos ) {
		return decrementListElement( list, pos, 1 );
	}

	public static long decrementListElement( final List<Long> list, final int pos,
			final long decrement ) {
		return incrementListElement( list, pos, -decrement );
	}

	public static Integer extractIntegerFromString( final String msg ) {
		final Matcher matcher = LONG_PATTERN.matcher( msg );
		if ( matcher.find() ) {
			return atoi( matcher.group() );
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static Long readOutput( final BlockingDeque<Long> output, final Future<?> writer )
			throws InterruptedException {
		Long x;
		do {
			x = output.poll( 50, MILLISECONDS );
		} while ( x == null && !writer.isDone() );
		return x;
	}

	public static Set<Character> stringToCharacterSet( final String string ) {
		return string.chars().mapToObj( c -> (char) c ).collect( toSet() );
	}

	public static List<List<Character>> getCharMatrix( final Stream<String> input ) {
		return input.map( row -> row.chars().mapToObj( c -> (char) c ).toList() ).toList();
	}

	public static List<List<Integer>> getDigitsMatrix( final Stream<String> input ) {
		return input.map( row -> row.chars().mapToObj( Utils::charToInt ).collect( toList() ) )
				.collect( toList() );
	}

	public static boolean shouldPrint() {
		return PRINT;
	}

	public static <E> void printMatrix( final List<? extends List<E>> matrix ) {
		for ( final List<E> row : matrix ) {
			for ( final E e : row ) {
				System.out.print( e );
			}
			System.out.println();
		}
	}

	public static <E> void printMatrix( final E[][] matrix ) {
		for ( E[] row : matrix ) {
			for ( E e : row ) {
				System.out.print( e );
			}
			System.out.println();
		}
	}

	public static LongStream matrixToLongStream( final long[][] matrix ) {
		return Arrays.stream( matrix ).flatMapToLong( Arrays::stream );
	}

	public static int[] toDigitsArray( final String i ) {
		return i.chars().map( Utils::charToInt ).toArray();
	}

	public static int[] toDigitsArray( final long i ) {
		return toDigitsArray( String.valueOf( i ) );
	}

	public static String itoa( final long i ) {
		return Long.toString( i );
	}

	public static String itoa( final Number i ) {
		return Long.toString( i.longValue() );
	}

	public static long sumNeighbors8( final long[][] matrix, final int i, final int j ) {
		//with diagonals
		return sumNeighbors4( matrix, i, j ) + getCell( matrix, i - 1, j - 1 ) + getCell( matrix,
				i - 1, j + 1 ) + getCell( matrix, i + 1, j - 1 ) + getCell( matrix, i + 1, j + 1 );
	}

	//no checks on matrix size: use at your own risk!
	public static long sumNeighbors4( final long[][] matrix, final int i, final int j ) {
		//no diagonals
		if ( matrix == null ) {
			return 0;
		}
		return getCell( matrix, i - 1, j ) + getCell( matrix, i + 1, j ) + getCell( matrix, i,
				j - 1 ) + getCell( matrix, i, j + 1 );

	}

	public static long getCell( final long[][] matrix, final int i, final int j ) {
		return ( matrix.length > i && i >= 0 && matrix[i].length > j && j >= 0 ) ? matrix[i][j] : 0;
	}

	public static Stream<Character> upperCaseLetters() {
		return IntStream.rangeClosed( 'A', 'Z' ).mapToObj( c -> (char) c );
	}

	public static Stream<Character> lowerCaseLetters() {
		return IntStream.rangeClosed( 'a', 'z' ).mapToObj( c -> (char) c );
	}

	public static Stream<Character> letters() {
		return Stream.concat( lowerCaseLetters(), upperCaseLetters() );
	}

	public static long manhattanDistance( final Pair<Long, Long> p, final Pair<Long, Long> q ) {
		return manhattanDistance( p.getFirst(), p.getSecond(), q.getFirst(), q.getSecond() );
	}

	public static long manhattanDistance( final Triplet<Long, Long, Long> p,
			final Triplet<Long, Long, Long> q ) {
		return manhattanDistance( p.getFirst(), p.getSecond(), q.getFirst(),
				q.getSecond() ) + Math.abs( p.getThird() - q.getThird() );
	}

	public static long manhattanDistance( final long x, final long y, final long i, final long j ) {
		return Math.abs( x - i ) + Math.abs( y - j );
	}

	public static <E> void rotate( final Deque<E> deque, final long n ) {
		if ( deque == null || deque.isEmpty() ) {
			return;
		}
		if ( n < 0 ) {
			for ( long i = 0; i < -n; i++ ) {
				deque.push( deque.pollLast() );
			}
		} else if ( n > 0 ) {
			for ( long i = 0; i < n; i++ ) {
				deque.add( deque.poll() );
			}
		}
	}

	public static void clearScreen() {
		try {
			if ( System.getProperty( "os.name" ).contains( "Windows" ) ) {
				new ProcessBuilder( "cmd", "/c", "cls" ).inheritIO().start().waitFor();
			} else {
				Runtime.getRuntime().exec( "clear" );
			}
		} catch ( InterruptedException | IOException e ) {
			e.printStackTrace();
		}
	}
}
