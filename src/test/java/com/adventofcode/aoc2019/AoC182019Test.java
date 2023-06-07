package com.adventofcode.aoc2019;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AoC182019Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC182019();

    public AoC182019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST,
                        """
                        #########
                        #b.A.@.a#
                        #########""", "8" },
                                        { FIRST,
                                                """
                        ########################
                        #f.D.E.e.C.b.A.@.a.B.c.#
                        ######################.#
                        #d.....................#
                        ########################""", "86" },
                { FIRST,
                        """
                        ########################
                        #...............b.C.D.f#
                        #.######################
                        #.....@.a.B.c.d.A.e.F.g#
                        ########################""", "132" },
                { FIRST,
                        """
                        #################
                        #i.G..c...e..H.p#
                        ########.########
                        #j.A..b...f..D.o#
                        ########@########
                        #k.E..a...g..B.n#
                        ########.########
                        #l.F..d...h..C.m#
                        #################""", "136" },
                { FIRST,
                        """
                        ########################
                        #@..............ac.GI.b#
                        ###d#e#f################
                        ###A#B#C################
                        ###g#h#i################
                        ########################""", "81" },
                { FIRST, getInput( INSTANCE ), "5392" },
                { SECOND,
                        """
                        #######
                        #a.#Cd#
                        ##...##
                        ##.@.##
                        ##...##
                        #cB#Ab#
                        #######""", "8" },
                { SECOND,
                        """
                        ###############
                        #d.ABC.#.....a#
                        ######...######
                        ######.@.######
                        ######...######
                        #b.....#.....c#
                        ###############""", "24" },
                { SECOND, getInput( INSTANCE ), "1684" }
        });
    }
}
