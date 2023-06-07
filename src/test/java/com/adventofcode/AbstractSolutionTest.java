package com.adventofcode;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.splitOnNewLine;
import static java.lang.ClassLoader.getSystemResource;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.Test;

public abstract class AbstractSolutionTest {
	private final Solution solution;
	private final Type type;
	private final String input;
	private final String result;
	protected static final String PARAMETERS_MESSAGE = "{index}: {0} = {2}";

	protected AbstractSolutionTest( final Solution solution, final Type type, final String input,
			final String result ) {
		this.solution = solution;
		this.type = type;
		this.input = input;
		this.result = result;
	}

	protected static String getInput( final Solution sol ) {
		final String packageName = sol.getClass().getPackage().getName();
		final String className = sol.getClass().getSimpleName();
		final String extension = "txt";
		final String fileName = packageName + File.separator + className + DOT + extension;
		try {
			return Files.readString( Path.of( getSystemResource( fileName ).toURI() ) );
		} catch ( IOException | URISyntaxException e ) {
			throw new IllegalStateException( fileName + "not found" );
		}
	}

	@Test
	public void test() {
		final Function<Stream<String>, String> solutionFunction =
				( type == Type.FIRST ) ? solution::solveFirstPart : solution::solveSecondPart;
		assertEquals( result, solutionFunction.apply( splitOnNewLine( input ) ) );
	}

	public enum Type {
		FIRST,
		SECOND
	}
}
