package com.adventofcode.aoc2016;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC022016Test extends Generic {

    private static final Solution INSTANCE = new AoC022016();

    public AoC022016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        ULL
                        RRDDD
                        LURDL
                        UUUUD
                        """, "1985"},
                {FIRST, getInput(INSTANCE), "19636"},
                { SECOND, """
                        ULL
                        RRDDD
                        LURDL
                        UUUUD
                        """, "5DB3"},
                {SECOND, getInput(INSTANCE), "3CC43"},
        });
    }
}
