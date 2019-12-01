package com.adventofcode;


import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.adventofcode.Generic.Type.NONE;
import static com.adventofcode.utils.Utils.*;
import static java.lang.ClassLoader.getSystemResource;
import static org.junit.Assert.assertEquals;

@Ignore
public class Generic {
    private final Solution solution;
    private final Type type;
    private final String input;
    private final String result;
    protected static final String PARAMETERS_MESSAGE = "{index}: {0} = {2}";

    public Generic() {
        this(null, NONE, EMPTY, EMPTY);
    }

    protected Generic(final Solution solution, final Type type, final String input, final String result) {
        this.solution = solution;
        this.type = type;
        this.input = input;
        this.result = result;
    }

    protected static String getInput(final Solution sol) {
        final String packageName = sol.getClass().getPackage().getName();
        final String className = sol.getClass().getSimpleName();
        final String extension = "txt";
        final String fileName = packageName + File.separator + className + DOT + extension;
        try {
            return Files.readString(Path.of(getSystemResource(fileName).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(fileName + "not found");
        }
    }

    private static void checkSolution(final Function<Stream<String>, String> solution, final String input, final String result) {
        assertEquals(result, solution.apply(splitOnNewLine(input)));
    }

    @Test
    public void test() {
        final Function<Stream<String>, String> solutionFunction = switch (type) {
            case FIRST -> solution::solveFirstPart;
            case SECOND -> solution::solveSecondPart;
            default -> throw new IllegalArgumentException();
        };
        checkSolution(solutionFunction, input, result);
    }

    public enum Type {NONE, FIRST, SECOND}
}
