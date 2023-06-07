package com.adventofcode.aoc2016;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC062016Test extends Generic {

    private static final Solution INSTANCE = new AoC062016();

    public AoC062016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        eedadn
                        drvtee
                        eandsr
                        raavrd
                        atevrs
                        tsrnev
                        sdttsa
                        rasrtv
                        nssdts
                        ntnada
                        svetve
                        tesnvt
                        vntsnd
                        vrdear
                        dvrsen
                        enarar
                        """, "easter" },
                { FIRST, getInput( INSTANCE ), "xdkzukcf" },
                { SECOND, """
                        eedadn
                        drvtee
                        eandsr
                        raavrd
                        atevrs
                        tsrnev
                        sdttsa
                        rasrtv
                        nssdts
                        ntnada
                        svetve
                        tesnvt
                        vntsnd
                        vrdear
                        dvrsen
                        enarar
                        """, "advent" },
                { SECOND, getInput( INSTANCE ), "cevsgyvd" },
        });
    }
}
