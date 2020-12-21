package com.adventofcode.aoc2020;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.reverseString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

class AoC202020 implements Solution {
	//@formatter:off
	private static final Pattern TOP_REGEX    = Pattern.compile( "..................#" );
	private static final Pattern MIDDLE_REGEX = Pattern.compile( "#....##....##....###" );
	private static final Pattern BOTTOM_REGEX = Pattern.compile( ".#..#..#..#..#..#" );
	//@formatter:on

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final HashMap<Long, String[]> tiles = new HashMap<>();
		final ListMultimap<Long, String> borders = ArrayListMultimap.create();
		final var it = input.iterator();
		while ( it.hasNext() ) {
			addTile( it, tiles, borders );
		}
		final var matches = findMatches( borders );
		if ( first ) {
			return itoa( matches.keySet()
					.stream()
					.filter( k -> matches.get( k ).size() == 2 )
					.reduce( 1L, ( a, b ) -> a * b ) );
		}
		final var image = getImage( tiles, matches );
		final long sea = Arrays.stream( image )
				.flatMapToInt( String::chars )
				.filter( i -> i == HASH )
				.count();
		final int monsters = findMonsters( image );
		final int monsterSize = 15;
		return itoa( sea - monsters * monsterSize );
	}

	private int findMonsters( final String[] image ) {
		var orientedImage = image;
		int monsters = 0;
		boolean rotate = false;
		while ( monsters == 0L ) {
			orientedImage = flipImage( orientedImage );
			if ( rotate ) {
				orientedImage = rotateImage( orientedImage );
			}
			monsters = findPattern( orientedImage );
			rotate = !rotate;
		}
		return monsters;
	}

	private int findPattern( final String[] image ) {
		int count = 0;
		final int topSize = TOP_REGEX.pattern().length();
		final int bottomSize = BOTTOM_REGEX.pattern().length();
		for ( int i = 1; i < image.length - 1; i++ ) {
			final var middle = MIDDLE_REGEX.matcher( image[i] );
			int j = 0;
			while ( middle.find( j ) ) {
				j = middle.start();
				if ( TOP_REGEX.matcher( image[i - 1].substring( j, j + topSize ) )
						.matches() && BOTTOM_REGEX.matcher(
						image[i + 1].substring( j, j + bottomSize ) ).matches() ) {
					count++;
				}
				j++;
			}
		}
		return count;
	}

	private String[] getImage( final HashMap<Long, String[]> tiles,
			final Multimap<Long, Long> matches ) {
		final int imageSize = (int) Math.sqrt( tiles.size() );
		final var tilesPositions = new Long[imageSize][imageSize];
		final int cutTileSize = tiles.values().iterator().next().length - 2;
		final String[][][] assembledImage = new String[imageSize][imageSize][];
		final Set<Long> tilesAvailable = new HashSet<>( tiles.keySet() );
		final HashMap<Long, String[]> orientedTiles = new HashMap<>();
		for ( int i = 0; i < imageSize; i++ ) {
			for ( int j = 0; j < imageSize; j++ ) {
				boolean findFirstCorner = false;
				Long previous = -1L;
				Function<String[], String> getTouchingBorder = null;
				Function<String[], String> getBorder = null;
				Predicate<Long> filter = k -> true;
				if ( i == 0 && j == 0 ) {
					findFirstCorner = true;
					filter = k -> isCornerTile( k, matches );
				} else if ( i == 0 ) {
					previous = tilesPositions[i][j - 1];
					getTouchingBorder = this::getRightBorder;
					getBorder = this::getLeftBorder;
					filter = k -> !isInternalTile( k, matches );
				} else {
					previous = tilesPositions[i - 1][j];
					getTouchingBorder = this::getBottomBorder;
					getBorder = this::getTopBorder;
				}
				findNextTile( i, j, previous, tiles, matches, cutTileSize, getTouchingBorder,
						getBorder, filter, findFirstCorner, orientedTiles, tilesAvailable,
						tilesPositions, assembledImage );
			}
		}
		return mergeTiles( assembledImage, cutTileSize );
	}

	private void findNextTile( final int i, final int j, final Long previous,
			final HashMap<Long, String[]> tiles, final Multimap<Long, Long> matches,
			final int cutTileSize, final Function<String[], String> getTouchingBorder,
			final Function<String[], String> getBorder, final Predicate<Long> filter,
			final boolean findFirstCorner, final HashMap<Long, String[]> orientedTiles,
			final Set<Long> tilesAvailable, final Long[][] tilesPositions,
			final String[][][] assembledImage ) {
		final var startingMatches = findFirstCorner ? matches.keys() : matches.get( previous );
		final var possibleMatches = startingMatches.stream()
				.filter( tilesAvailable::contains )
				.filter( filter )
				.iterator();

		Long tileNumber;
		String[] orientedTile;
		do {
			tileNumber = possibleMatches.next();
			final String[] notOrientedTile = tiles.get( tileNumber );
			orientedTile = findFirstCorner ? orientateCorner( notOrientedTile,
					matches.get( tileNumber ).iterator(), tiles ) : orientateTile( notOrientedTile,
					getBorder, getTouchingBorder.apply( orientedTiles.get( previous ) ) );
		} while ( orientedTile == null );

		orientedTiles.put( tileNumber, orientedTile );
		tilesAvailable.remove( tileNumber );
		tilesPositions[i][j] = tileNumber;
		assembledImage[i][j] = cutTile( orientedTile, cutTileSize );
	}

	private String[] orientateTile( final String[] tile, final Function<String[], String> getBorder,
			final String touchingBorder ) {
		String[] orientedTile = tile;
		boolean oriented = false;
		int k = 0;
		while ( k < 8 && !oriented ) {
			orientedTile = flipImage( orientedTile );
			if ( k % 2 == 0 ) {
				orientedTile = rotateImage( orientedTile );
			}
			oriented = touchingBorder.equals( getBorder.apply( orientedTile ) );
			k++;
		}
		return oriented ? orientedTile : null;
	}

	private String[] orientateCorner( final String[] corner, final Iterator<Long> matchingTiles,
			final HashMap<Long, String[]> tiles ) {
		final String[] tileA = tiles.get( matchingTiles.next() );
		final String[] tileB = tiles.get( matchingTiles.next() );
		final var bordersA = Stream.of( getTopBorder( tileA ), getRightBorder( tileA ),
				getBottomBorder( tileA ), getLeftBorder( tileA ) )
				.flatMap( s -> Stream.of( s, reverseString( s ) ) )
				.collect( toList() );
		final var bordersB = Stream.of( getTopBorder( tileB ), getRightBorder( tileB ),
				getBottomBorder( tileB ), getLeftBorder( tileB ) )
				.flatMap( s -> Stream.of( s, reverseString( s ) ) )
				.collect( toList() );

		String[] orientedCorner = corner;
		boolean oriented = false;
		boolean rotate = false;
		while ( !oriented ) {
			orientedCorner = flipImage( orientedCorner );
			if ( rotate ) {
				orientedCorner = rotateImage( orientedCorner );
			}
			oriented = tilesMatch( List.of( getRightBorder( orientedCorner ) ),
					bordersA ) && tilesMatch( List.of( getBottomBorder( orientedCorner ) ),
					bordersB ) || tilesMatch( List.of( getRightBorder( orientedCorner ) ),
					bordersB ) && tilesMatch( List.of( getBottomBorder( orientedCorner ) ),
					bordersA );
			rotate = !rotate;
		}
		return orientedCorner;
	}

	private Multimap<Long, Long> findMatches( final ListMultimap<Long, String> borders ) {
		final var tilesToCheck = new HashSet<>( borders.keySet() );
		final HashMultimap<Long, Long> matches = HashMultimap.create();
		for ( final var tileA : new HashSet<>( tilesToCheck ) ) {
			final var bordersA = borders.get( tileA );
			tilesToCheck.remove( tileA );
			for ( final var tileB : new HashSet<>( tilesToCheck ) ) {
				final var bordersB = borders.get( tileB );
				if ( tilesMatch( bordersA.subList( 0, 4 ), bordersB ) ) {
					matches.put( tileA, tileB );
					matches.put( tileB, tileA );
				}
			}
		}
		return matches;
	}

	private boolean tilesMatch( final List<String> bordersA, final List<String> bordersB ) {
		for ( final var borderA : bordersA ) {
			for ( final var borderB : bordersB ) {
				if ( borderA.equals( borderB ) ) {
					return true;
				}
			}
		}
		return false;
	}

	private String[] cutTile( final String[] tile, final int cutTileSize ) {
		final String[] tileCut = new String[cutTileSize];
		for ( int k = 0; k < cutTileSize; k++ ) {
			tileCut[k] = tile[k + 1].substring( 1, cutTileSize + 1 );
		}
		return tileCut;
	}

	private String[] mergeTiles( final String[][][] tiles, final int cutTileSize ) {
		final String[] image = new String[tiles.length * cutTileSize];
		for ( int i = 0; i < image.length; i++ ) {
			final var row = new StringBuilder();
			for ( int j = 0; j < tiles.length; j++ ) {
				row.append( tiles[i / cutTileSize][j][i % cutTileSize] );
			}
			image[i] = row.toString();
		}
		return image;
	}

	private void addTile( final Iterator<String> it, final HashMap<Long, String[]> tiles,
			final Multimap<Long, String> borders ) {
		final long tile = Utils.toLongList( it.next() ).get( 0 );
		final String[] image = getTileFromInput( it );

		tiles.put( tile, image );

		final var bordersList = List.of( getTopBorder( image ), getRightBorder( image ),
				getBottomBorder( image ), getLeftBorder( image ) );
		borders.putAll( tile, bordersList );
		borders.putAll( tile,
				bordersList.stream().map( Utils::reverseString ).collect( toList() ) );
	}

	private String[] getTileFromInput( final Iterator<String> it ) {
		final var lines = new ArrayList<>();
		String line;
		while ( it.hasNext() && !( line = it.next() ).isEmpty() ) {
			lines.add( line );
		}
		return lines.toArray( String[]::new );
	}

	private String getTopBorder( final String[] lines ) {
		return lines[0];
	}

	private String getBottomBorder( final String[] lines ) {
		return lines[lines.length - 1];
	}

	private String getRightBorder( final String[] lines ) {
		final var border = new StringBuilder();
		for ( final String line : lines ) {
			border.append( line.charAt( lines.length - 1 ) );
		}
		return border.toString();
	}

	private String getLeftBorder( final String[] lines ) {
		final var border = new StringBuilder();
		for ( final String line : lines ) {
			border.append( line.charAt( 0 ) );
		}
		return border.toString();
	}

	private String[] flipImage( final String[] image ) {
		return Arrays.stream( image ).map( Utils::reverseString ).toArray( String[]::new );
	}

	private String[] rotateImage( final String[] image ) {
		final int size = image.length;
		final var rotated = new String[size];
		for ( int i = 0; i < size; ++i ) {
			final var row = new StringBuilder();
			for ( int j = size - 1; j >= 0; --j ) {
				row.append( image[j].charAt( i ) );
			}
			rotated[i] = row.toString();
		}
		return rotated;
	}

	private boolean isInternalTile( final Long tile, final Multimap<Long, Long> matches ) {
		return matches.get( tile ).size() == 4;
	}

	private boolean isCornerTile( final Long tile, final Multimap<Long, Long> matches ) {
		return matches.get( tile ).size() == 2;
	}

}
