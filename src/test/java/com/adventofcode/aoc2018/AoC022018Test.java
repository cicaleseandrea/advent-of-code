package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC022018Test extends Generic {

    private static final Solution INSTANCE = new AoC022018();

    public AoC022018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        abcdef
                        bababc
                        abbcde
                        abcccd
                        aabcdd
                        abcdee
                        ababab""", "12"},
                {FIRST, getInput(INSTANCE), "6175"},
                {SECOND, """
                        abcde
                        fghij
                        klmno
                        pqrst
                        fguij
                        axcye
                        wvxyz""", "fgij"},
                {SECOND, getInput(INSTANCE), "asgwjcmzredihqoutcylvzinx"}
        });
    }
}