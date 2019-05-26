package com.adventofcode.aoc2017;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.Solution.getInput;

@Ignore
@RunWith(Parameterized.class)
public class AoC092017Test extends Generic {

    static private Solution INSTANCE = new AoC092017();

    public AoC092017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
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
                {FIRST, getInput(INSTANCE), "3745"},
                {SECOND, "{}", "1"},
                {SECOND, "{{{}}}", "6"},
                {SECOND, "{{},{}}", "5"},
                {SECOND, "{{{},{},{{}}}}", "16"},
                {SECOND, "{<a>,<a>,<a>,<a>}", "1"},
                {SECOND, "{{<ab>},{<ab>},{<ab>},{<ab>}}", "9"},
                {SECOND, "{{<!!>},{<!!>},{<!!>},{<!!>}}", "9"},
                {SECOND, "{{<a!>},{<a!>},{<a!>},{<ab>}}", "3"},
                {SECOND, getInput(INSTANCE), "1226"}
        });
    }
}
