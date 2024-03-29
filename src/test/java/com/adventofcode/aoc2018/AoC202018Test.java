package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC202018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC202018();

    public AoC202018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
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