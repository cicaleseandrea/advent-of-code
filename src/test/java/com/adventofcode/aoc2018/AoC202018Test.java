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
public class AoC202018Test extends Generic {

    private static final Solution INSTANCE = new AoC202018();

    public AoC202018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "^WNE$", "3"},
                {FIRST, "^ENWWW(NEEE|SSE(EE|N))$", "10"},
                {FIRST, "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$", "18"},
                {FIRST, "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$", "23"},
                {FIRST, "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$", "31"},
                {FIRST, "^(SSS|EEESSSWWW)ENNES$", "8"},
                {FIRST, getInput(INSTANCE), "4025"},
                {SECOND, getInput(INSTANCE), "8186"}
        });
    }
}