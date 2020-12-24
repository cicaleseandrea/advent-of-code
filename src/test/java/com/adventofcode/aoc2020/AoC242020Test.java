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
public class AoC242020Test extends Generic {

    private static final Solution INSTANCE = new AoC242020();

    public AoC242020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        sesenwnenenewseeswwswswwnenewsewsw
                        neeenesenwnwwswnenewnwwsewnenwseswesw
                        seswneswswsenwwnwse
                        nwnwneseeswswnenewneswwnewseswneseene
                        swweswneswnenwsewnwneneseenw
                        eesenwseswswnenwswnwnwsewwnwsene
                        sewnenenenesenwsewnenwwwse
                        wenwwweseeeweswwwnwwe
                        wsweesenenewnwwnwsenewsenwwsesesenwne
                        neeswseenwwswnwswswnw
                        nenwswwsewswnenenewsenwsenwnesesenew
                        enewnwewneswsewnwswenweswnenwsenwsw
                        sweneswneswneneenwnewenewwneswswnese
                        swwesenesewenwneswnwwneseswwne
                        enesenwswwswneneswsenwnewswseenwsese
                        wnwnesenesenenwwnenwsewesewsesesew
                        nenewswnwewswnenesenwnesewesw
                        eneswnwswnwsenenwnwnwwseeswneewsenese
                        neswnwewnwnwseenwseesewsenwsweewe
                        wseweeenwnesenwwwswnew""", "10"  },
                { FIRST, getInput( INSTANCE ), "346" },
                { SECOND, """
                        sesenwnenenewseeswwswswwnenewsewsw
                        neeenesenwnwwswnenewnwwsewnenwseswesw
                        seswneswswsenwwnwse
                        nwnwneseeswswnenewneswwnewseswneseene
                        swweswneswnenwsewnwneneseenw
                        eesenwseswswnenwswnwnwsewwnwsene
                        sewnenenenesenwsewnenwwwse
                        wenwwweseeeweswwwnwwe
                        wsweesenenewnwwnwsenewsenwwsesesenwne
                        neeswseenwwswnwswswnw
                        nenwswwsewswnenenewsenwsenwnesesenew
                        enewnwewneswsewnwswenweswnenwsenwsw
                        sweneswneswneneenwnewenewwneswswnese
                        swwesenesewenwneswnwwneseswwne
                        enesenwswwswneneswsenwnewswseenwsese
                        wnwnesenesenenwwnenwsewesewsesesew
                        nenewswnwewswnenesenwnesewesw
                        eneswnwswnwsenenwnwnwwseeswneewsenese
                        neswnwewnwnwseenwseesewsenwsweewe
                        wseweeenwnesenwwwswnew""", "2208"  },
                { SECOND, getInput( INSTANCE ), "3802" }
        });
    }
}
