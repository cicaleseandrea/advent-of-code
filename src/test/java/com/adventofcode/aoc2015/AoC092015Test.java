package com.adventofcode.aoc2015;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC092015Test extends Generic {

    private static final Solution INSTANCE = new AoC092015();

    public AoC092015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST,
                 """
                 London to Dublin = 464
                 London to Belfast = 518
                 Dublin to Belfast = 141
                 """, "605"},
                {FIRST, getInput(INSTANCE), "251"},
                {SECOND,
                 """
                 London to Dublin = 464
                 London to Belfast = 518
                 Dublin to Belfast = 141
                 """, "982"},
                {SECOND, getInput(INSTANCE), "898"},
        });
    }
}