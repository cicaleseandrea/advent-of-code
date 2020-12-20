package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC192020Test extends Generic {

    private static final Solution INSTANCE = new AoC192020();

    public AoC192020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        0: 4 1 5
                        1: 2 3 | 3 2
                        2: 4 4 | 5 5
                        3: 4 5 | 5 4
                        4: "a"
                        5: "b"
                        
                        ababbb
                        bababa
                        abbbab
                        aaabbb
                        aaaabbb""", "2"  },
                { FIRST, """
                        42: 9 14 | 10 1
                        9: 14 27 | 1 26
                        10: 23 14 | 28 1
                        1: "a"
                        11: 42 31
                        5: 1 14 | 15 1
                        19: 14 1 | 14 14
                        12: 24 14 | 19 1
                        16: 15 1 | 14 14
                        31: 14 17 | 1 13
                        6: 14 14 | 1 14
                        2: 1 24 | 14 4
                        0: 8 11
                        13: 14 3 | 1 12
                        15: 1 | 14
                        17: 14 2 | 1 7
                        23: 25 1 | 22 14
                        28: 16 1
                        4: 1 1
                        20: 14 14 | 1 15
                        3: 5 14 | 16 1
                        27: 1 6 | 14 18
                        14: "b"
                        21: 14 1 | 1 14
                        25: 1 1 | 1 14
                        22: 14 14
                        8: 42
                        26: 14 22 | 1 20
                        18: 15 15
                        7: 14 5 | 1 21
                        24: 14 1
                        
                        abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
                        bbabbbbaabaabba
                        babbbbaabbbbbabbbbbbaabaaabaaa
                        aaabbbbbbaaaabaababaabababbabaaabbababababaaa
                        bbbbbbbaaaabbbbaaabbabaaa
                        bbbababbbbaaaaaaaabbababaaababaabab
                        ababaaaaaabaaab
                        ababaaaaabbbaba
                        baabbaaaabbaaaababbaababb
                        abbbbabbbbaaaababbbbbbaaaababb
                        aaaaabbaabaaaaababaa
                        aaaabbaaaabbaaa
                        aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
                        babaaabbbaaabaababbaabababaaab
                        aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba""", "3"  },
                { FIRST, getInput( INSTANCE ), "107" },
                { SECOND, """
                        42: 9 14 | 10 1
                        9: 14 27 | 1 26
                        10: 23 14 | 28 1
                        1: "a"
                        11: 42 31
                        5: 1 14 | 15 1
                        19: 14 1 | 14 14
                        12: 24 14 | 19 1
                        16: 15 1 | 14 14
                        31: 14 17 | 1 13
                        6: 14 14 | 1 14
                        2: 1 24 | 14 4
                        0: 8 11
                        13: 14 3 | 1 12
                        15: 1 | 14
                        17: 14 2 | 1 7
                        23: 25 1 | 22 14
                        28: 16 1
                        4: 1 1
                        20: 14 14 | 1 15
                        3: 5 14 | 16 1
                        27: 1 6 | 14 18
                        14: "b"
                        21: 14 1 | 1 14
                        25: 1 1 | 1 14
                        22: 14 14
                        8: 42
                        26: 14 22 | 1 20
                        18: 15 15
                        7: 14 5 | 1 21
                        24: 14 1
                        
                        abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
                        bbabbbbaabaabba
                        babbbbaabbbbbabbbbbbaabaaabaaa
                        aaabbbbbbaaaabaababaabababbabaaabbababababaaa
                        bbbbbbbaaaabbbbaaabbabaaa
                        bbbababbbbaaaaaaaabbababaaababaabab
                        ababaaaaaabaaab
                        ababaaaaabbbaba
                        baabbaaaabbaaaababbaababb
                        abbbbabbbbaaaababbbbbbaaaababb
                        aaaaabbaabaaaaababaa
                        aaaabbaaaabbaaa
                        aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
                        babaaabbbaaabaababbaabababaaab
                        aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba""", "12"  },
                { SECOND, getInput( INSTANCE ), "321" }
        });
    }
}
