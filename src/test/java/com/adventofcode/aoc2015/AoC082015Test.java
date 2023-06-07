package com.adventofcode.aoc2015;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC082015Test extends Generic {

    private static final Solution INSTANCE = new AoC082015();

    public AoC082015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST,
                 """
                    ""
                 """, "2"},
                {FIRST,
                 """
                    "abc"
                 """, "2"},
                {FIRST,
                 """
                    "aaa\\"aaa"
                 """, "3"},
                {FIRST,
                 """
                    "aaa\\\\aaa"
                 """, "3"},
                {FIRST,
                 """
                    "\\x27"
                 """, "5"},
                {FIRST, getInput(INSTANCE), "1333"},
                {SECOND,
                 """
                    ""
                 """, "4"},
                {SECOND,
                 """
                    "abc"
                 """, "4"},
                {SECOND,
                 """
                    "aaa\\"aaa"
                 """, "6"},
                {SECOND,
                 """
                    "aaa\\\\aaa"
                 """, "6"},
                {SECOND,
                 """
                    "\\x27"
                 """, "5"},
                {SECOND, getInput(INSTANCE), "2046"},
        });
    }
}