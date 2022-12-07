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
public class AoC072022Test extends Generic {

    private static final Solution INSTANCE = new AoC072022();

    public AoC072022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        $ cd /
                        $ ls
                        dir a
                        14848514 b.txt
                        8504156 c.dat
                        dir d
                        $ cd a
                        $ ls
                        dir e
                        29116 f
                        2557 g
                        62596 h.lst
                        $ cd e
                        $ ls
                        584 i
                        $ cd ..
                        $ cd ..
                        $ cd d
                        $ ls
                        4060174 j
                        8033020 d.log
                        5626152 d.ext
                        7214296 k
                        """, "95437" },
                { FIRST, getInput( INSTANCE ), "1845346" },
                { SECOND, """
                        $ cd /
                        $ ls
                        dir a
                        14848514 b.txt
                        8504156 c.dat
                        dir d
                        $ cd a
                        $ ls
                        dir e
                        29116 f
                        2557 g
                        62596 h.lst
                        $ cd e
                        $ ls
                        584 i
                        $ cd ..
                        $ cd ..
                        $ cd d
                        $ ls
                        4060174 j
                        8033020 d.log
                        5626152 d.ext
                        7214296 k
                        """, "24933642" },
                { SECOND, getInput( INSTANCE ), "3636703" }
        });
    }
}
