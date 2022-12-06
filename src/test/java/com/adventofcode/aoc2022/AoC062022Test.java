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
public class AoC062022Test extends Generic {

    private static final Solution INSTANCE = new AoC062022();

    public AoC062022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "mjqjpqmgbljsphdztnvjfqwrcgsmlb", "7" },
                { FIRST, "bvwbjplbgvbhsrlpgdmjqwftvncz", "5" },
                { FIRST, "nppdvjthqldpwncqszvftbrmjlhg", "6" },
                { FIRST, "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", "10" },
                { FIRST, "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", "11" },
                { FIRST, getInput( INSTANCE ), "1566" },
                { SECOND, "mjqjpqmgbljsphdztnvjfqwrcgsmlb", "19" },
                { SECOND, "bvwbjplbgvbhsrlpgdmjqwftvncz", "23" },
                { SECOND, "nppdvjthqldpwncqszvftbrmjlhg", "23" },
                { SECOND, "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", "29" },
                { SECOND, "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", "26" },
                { SECOND, getInput( INSTANCE ), "2265" }
        });
    }
}
