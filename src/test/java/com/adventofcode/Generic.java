package com.adventofcode;


import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Stream;

import static com.adventofcode.Generic.Type.*;
import static com.adventofcode.utils.Utils.EMPTY;
import static com.adventofcode.utils.Utils.splitOnNewLine;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeFalse;

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

    private static void checkCorrect(final Function<Stream<String>, String> solution, final String input, final String result) {
        assertEquals(result, solution.apply(splitOnNewLine(input)));
    }

    @Test
    public void test() {
        assumeFalse(type == NONE);
        if (type == FIRST) {
            checkCorrect(solution::solveFirstPart, input, result);
        } else if (type == SECOND) {
            checkCorrect(solution::solveSecondPart, input, result);
        }
    }

    public enum Type {NONE, FIRST, SECOND}
}
