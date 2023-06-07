package com.adventofcode.aoc2021;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC122021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC122021();

    public AoC122021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        start-A
                        start-b
                        A-c
                        A-b
                        b-d
                        A-end
                        b-end
                        """, "10" },
                { FIRST, """
                        dc-end
                        HN-start
                        start-kj
                        dc-start
                        dc-HN
                        LN-dc
                        HN-end
                        kj-sa
                        kj-HN
                        kj-dc
                        """, "19" },
                { FIRST, """
                        fs-end
                        he-DX
                        fs-he
                        start-DX
                        pj-DX
                        end-zg
                        zg-sl
                        zg-pj
                        pj-he
                        RW-he
                        fs-DX
                        pj-RW
                        zg-RW
                        start-pj
                        he-WI
                        zg-he
                        pj-fs
                        start-RW
                        """, "226" },
                { FIRST, getInput( INSTANCE ), "4659" },
                { SECOND, """
                        start-A
                        start-b
                        A-c
                        A-b
                        b-d
                        A-end
                        b-end
                        """, "36" },
                { SECOND, """
                        dc-end
                        HN-start
                        start-kj
                        dc-start
                        dc-HN
                        LN-dc
                        HN-end
                        kj-sa
                        kj-HN
                        kj-dc
                        """, "103" },
                { SECOND, """
                        fs-end
                        he-DX
                        fs-he
                        start-DX
                        pj-DX
                        end-zg
                        zg-sl
                        zg-pj
                        pj-he
                        RW-he
                        fs-DX
                        pj-RW
                        zg-RW
                        start-pj
                        he-WI
                        zg-he
                        pj-fs
                        start-RW
                        """, "3509" },
                { SECOND, getInput( INSTANCE ), "148962" }
        });
    }
}
