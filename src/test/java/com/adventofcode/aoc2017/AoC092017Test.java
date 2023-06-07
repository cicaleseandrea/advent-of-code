package com.adventofcode.aoc2017;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC092017Test extends AbstractSolutionTest {

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
