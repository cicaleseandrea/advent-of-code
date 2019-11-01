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
public class AoC092017Test extends Generic {

    private static final Solution INSTANCE = new AoC092017();

    public AoC092017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "{}", "1"},
                {FIRST, "{{{}}}", "6"},
                {FIRST, "{{},{}}", "5"},
                {FIRST, "{{{},{},{{}}}}", "16"},
                {FIRST, "{<a>,<a>,<a>,<a>}", "1"},
                {FIRST, "{{<ab>},{<ab>},{<ab>},{<ab>}}", "9"},
                {FIRST, "{{<!!>},{<!!>},{<!!>},{<!!>}}", "9"},
                {FIRST, "{{<a!>},{<a!>},{<a!>},{<ab>}}", "3"},
                {FIRST, getInput(INSTANCE), "20530"},
                {SECOND, "<>", "0"},
                {SECOND, "<random characters>", "17"},
                {SECOND, "<<<<>", "3"},
                {SECOND, "<{!>}>", "2"},
                {SECOND, "<!!>", "0"},
                {SECOND, "<!!!>>", "0"},
                {SECOND, "<{o\"i!a,<{i<a>", "10"},
                {SECOND, getInput(INSTANCE), "9978"}
        });
    }
}
