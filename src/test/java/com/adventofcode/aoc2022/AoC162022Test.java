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
public class AoC162022Test extends Generic {

    private static final Solution INSTANCE = new AoC162022();

    public AoC162022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
                        Valve BB has flow rate=13; tunnels lead to valves CC, AA
                        Valve CC has flow rate=2; tunnels lead to valves DD, BB
                        Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
                        Valve EE has flow rate=3; tunnels lead to valves FF, DD
                        Valve FF has flow rate=0; tunnels lead to valves EE, GG
                        Valve GG has flow rate=0; tunnels lead to valves FF, HH
                        Valve HH has flow rate=22; tunnel leads to valve GG
                        Valve II has flow rate=0; tunnels lead to valves AA, JJ
                        Valve JJ has flow rate=21; tunnel leads to valve II
                        """, "1651" },
            { FIRST, getInput( INSTANCE ), "1751" },
            { SECOND, """
                        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
                        Valve BB has flow rate=13; tunnels lead to valves CC, AA
                        Valve CC has flow rate=2; tunnels lead to valves DD, BB
                        Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
                        Valve EE has flow rate=3; tunnels lead to valves FF, DD
                        Valve FF has flow rate=0; tunnels lead to valves EE, GG
                        Valve GG has flow rate=0; tunnels lead to valves FF, HH
                        Valve HH has flow rate=22; tunnel leads to valve GG
                        Valve II has flow rate=0; tunnels lead to valves AA, JJ
                        Valve JJ has flow rate=21; tunnel leads to valve II
                        """, "1707" },
            { SECOND, getInput( INSTANCE ), "2207" }
        });
    }
}
