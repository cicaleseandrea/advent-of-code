package com.adventofcode.aoc2017;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC042017Test extends AbstractSolutionTest {
    private static final Solution INSTANCE = new AoC042017();

    public AoC042017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "aa bb cc dd ee", "1"},
                {FIRST, "aa bb cc dd aa", "0"},
                {FIRST, "aa bb cc dd aaa", "1"},
                {FIRST, getInput(INSTANCE), "451"},
                {SECOND, "abcde fghij", "1"},
                {SECOND, "abcde xyz ecdab", "0"},
                {SECOND, "a ab abc abd abf abj", "1"},
                {SECOND, "iiii oiii ooii oooi oooo", "1"},
                {SECOND, "oiii ioii iioi iiio", "0"},
                {SECOND, getInput(INSTANCE), "223"}
        });
    }
}