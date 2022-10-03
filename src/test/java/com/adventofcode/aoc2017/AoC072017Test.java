package com.adventofcode.aoc2017;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC072017Test extends Generic {

    private static final Solution INSTANCE = new AoC072017();

    public AoC072017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        pbga (66)
                        xhth (57)
                        ebii (61)
                        havc (66)
                        ktlj (57)
                        tknk (41) -> ugml, padx, fwft
                        fwft (72) -> ktlj, cntj, xhth
                        qoyq (66)
                        padx (45) -> pbga, havc, qoyq
                        jptl (61)
                        ugml (68) -> gyxo, ebii, jptl
                        gyxo (61)
                        cntj (57)""", "tknk"},
                {FIRST, getInput(INSTANCE), "vgzejbd"},
                {SECOND, """
                        pbga (66)
                        xhth (57)
                        ebii (61)
                        tknk (41) -> ugml, padx, fwft
                        havc (66)
                        ktlj (57)
                        fwft (72) -> ktlj, cntj, xhth
                        qoyq (66)
                        padx (45) -> pbga, havc, qoyq
                        jptl (61)
                        ugml (68) -> gyxo, ebii, jptl
                        gyxo (61)
                        cntj (57)""", "60"},
                {SECOND, getInput(INSTANCE), "1226"}
        });
    }
}
