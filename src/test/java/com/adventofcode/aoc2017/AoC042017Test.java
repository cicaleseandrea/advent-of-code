package com.adventofcode.aoc2017;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.Solution.getInput;

@RunWith(Parameterized.class)
public class AoC042017Test extends Generic {
    static private Solution INSTANCE = new AoC042017();

    public AoC042017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
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