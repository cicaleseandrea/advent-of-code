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
public class AoC072016Test extends Generic {

    private static final Solution INSTANCE = new AoC072016();

    public AoC072016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "abba[mnop]qrst", "1" },
                { FIRST, "abcd[bddb]xyyx", "0" },
                { FIRST, "aaaa[qwer]tyui", "0" },
                { FIRST, "ioxxoj[asdfgh]zxcvbn", "1" },
                { FIRST, getInput( INSTANCE ), "110" },
                { SECOND, "aba[bab]xyz", "1" },
                { SECOND, "xyx[xyx]xyx", "0" },
                { SECOND, "aaa[kek]eke", "1" },
                { SECOND, "zazbz[bzb]cdb", "1" },
                { SECOND, "aaa[kk]eke[sdakek]", "1" },
                { SECOND, "aaa[kk]eke[sda]", "0" },
                { SECOND, "eeee[bbb]cdzaz[aza]", "1" },
                { SECOND, "cdzaz[bbb]eee[aza]", "1" },
                { SECOND, "fff[bbb]eee[aza]zaz", "1" },
                { SECOND, "eeazaee[bbb]cdzaz[aaa]", "0" },
                { SECOND, "cdzaz[bbb]eee[aaa]aza", "0" },
                { SECOND, "fff[bab]eee[aza]aba", "1" },
                { SECOND, "fff[baba]eee[aza]bab", "1" },
                { SECOND, "fff[baba]eee[aza]aba", "1" },
                { SECOND, "fff[baba]eee[aza]aba", "1" },
                { SECOND, getInput( INSTANCE ), "242" },
        });
    }
}
