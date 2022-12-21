package com.adventofcode.aoc2022;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC212022Test extends Generic {

    private static final Solution INSTANCE = new AoC212022();

    public AoC212022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        root: pppw + sjmn
                        dbpl: 5
                        cczh: sllz + lgvd
                        zczc: 2
                        ptdq: humn - dvpt
                        dvpt: 3
                        lfqf: 4
                        humn: 5
                        ljgn: 2
                        sjmn: drzm * dbpl
                        sllz: 4
                        pppw: cczh / lfqf
                        lgvd: ljgn * ptdq
                        drzm: hmdt - zczc
                        hmdt: 32
                        """, "152" },
                { FIRST, getInput( INSTANCE ), "82225382988628" },
                { SECOND, """
                        root: pppw + sjmn
                        dbpl: 5
                        cczh: sllz + lgvd
                        zczc: 2
                        ptdq: humn - dvpt
                        dvpt: 3
                        lfqf: 4
                        humn: 5
                        ljgn: 2
                        sjmn: drzm * dbpl
                        sllz: 4
                        pppw: cczh / lfqf
                        lgvd: ljgn * ptdq
                        drzm: hmdt - zczc
                        hmdt: 32
                        """, "301" },
                { SECOND, getInput( INSTANCE ), "3429411069028" }
        });
    }
}
