package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.Solution.getInput;

@RunWith(Parameterized.class)
public class AoC022018Test extends Generic {

    private static final Solution INSTANCE = new AoC022018();

    public AoC022018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "abcdef\n" +
                        "bababc\n" +
                        "abbcde\n" +
                        "abcccd\n" +
                        "aabcdd\n" +
                        "abcdee\n" +
                        "ababab", "12"},
                {FIRST, getInput(INSTANCE), "6175"},
                {SECOND, "abcde\n" +
                        "fghij\n" +
                        "klmno\n" +
                        "pqrst\n" +
                        "fguij\n" +
                        "axcye\n" +
                        "wvxyz", "fgij"},
                {SECOND, getInput(INSTANCE), "asgwjcmzredihqoutcylvzinx"}
        });
    }
}