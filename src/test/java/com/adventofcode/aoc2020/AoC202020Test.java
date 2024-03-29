package com.adventofcode.aoc2020;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC202020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC202020();

    public AoC202020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        Tile 2311:
                        ..##.#..#.
                        ##..#.....
                        #...##..#.
                        ####.#...#
                        ##.##.###.
                        ##...#.###
                        .#.#.#..##
                        ..#....#..
                        ###...#.#.
                        ..###..###
                        
                        Tile 1951:
                        #.##...##.
                        #.####...#
                        .....#..##
                        #...######
                        .##.#....#
                        .###.#####
                        ###.##.##.
                        .###....#.
                        ..#.#..#.#
                        #...##.#..
                        
                        Tile 1171:
                        ####...##.
                        #..##.#..#
                        ##.#..#.#.
                        .###.####.
                        ..###.####
                        .##....##.
                        .#...####.
                        #.##.####.
                        ####..#...
                        .....##...
                        
                        Tile 1427:
                        ###.##.#..
                        .#..#.##..
                        .#.##.#..#
                        #.#.#.##.#
                        ....#...##
                        ...##..##.
                        ...#.#####
                        .#.####.#.
                        ..#..###.#
                        ..##.#..#.
                        
                        Tile 1489:
                        ##.#.#....
                        ..##...#..
                        .##..##...
                        ..#...#...
                        #####...#.
                        #..#.#.#.#
                        ...#.#.#..
                        ##.#...##.
                        ..##.##.##
                        ###.##.#..
                        
                        Tile 2473:
                        #....####.
                        #..#.##...
                        #.##..#...
                        ######.#.#
                        .#...#.#.#
                        .#########
                        .###.#..#.
                        ########.#
                        ##...##.#.
                        ..###.#.#.
                        
                        Tile 2971:
                        ..#.#....#
                        #...###...
                        #.#.###...
                        ##.##..#..
                        .#####..##
                        .#..####.#
                        #..#.#..#.
                        ..####.###
                        ..#.#.###.
                        ...#.#.#.#
                        
                        Tile 2729:
                        ...#.#.#.#
                        ####.#....
                        ..#.#.....
                        ....#..#.#
                        .##..##.#.
                        .#.####...
                        ####.#.#..
                        ##.####...
                        ##..#.##..
                        #.##...##.
                        
                        Tile 3079:
                        #.#.#####.
                        .#..######
                        ..#.......
                        ######....
                        ####.#..#.
                        .#...#.##.
                        #.#####.##
                        ..#.###...
                        ..#.......
                        ..#.###...""", "20899048083289"  },
                { FIRST, getInput( INSTANCE ), "16937516456219" },
                { SECOND, """
                        Tile 2311:
                        ..##.#..#.
                        ##..#.....
                        #...##..#.
                        ####.#...#
                        ##.##.###.
                        ##...#.###
                        .#.#.#..##
                        ..#....#..
                        ###...#.#.
                        ..###..###
                        
                        Tile 1951:
                        #.##...##.
                        #.####...#
                        .....#..##
                        #...######
                        .##.#....#
                        .###.#####
                        ###.##.##.
                        .###....#.
                        ..#.#..#.#
                        #...##.#..
                        
                        Tile 1171:
                        ####...##.
                        #..##.#..#
                        ##.#..#.#.
                        .###.####.
                        ..###.####
                        .##....##.
                        .#...####.
                        #.##.####.
                        ####..#...
                        .....##...
                        
                        Tile 1427:
                        ###.##.#..
                        .#..#.##..
                        .#.##.#..#
                        #.#.#.##.#
                        ....#...##
                        ...##..##.
                        ...#.#####
                        .#.####.#.
                        ..#..###.#
                        ..##.#..#.
                        
                        Tile 1489:
                        ##.#.#....
                        ..##...#..
                        .##..##...
                        ..#...#...
                        #####...#.
                        #..#.#.#.#
                        ...#.#.#..
                        ##.#...##.
                        ..##.##.##
                        ###.##.#..
                        
                        Tile 2473:
                        #....####.
                        #..#.##...
                        #.##..#...
                        ######.#.#
                        .#...#.#.#
                        .#########
                        .###.#..#.
                        ########.#
                        ##...##.#.
                        ..###.#.#.
                        
                        Tile 2971:
                        ..#.#....#
                        #...###...
                        #.#.###...
                        ##.##..#..
                        .#####..##
                        .#..####.#
                        #..#.#..#.
                        ..####.###
                        ..#.#.###.
                        ...#.#.#.#
                        
                        Tile 2729:
                        ...#.#.#.#
                        ####.#....
                        ..#.#.....
                        ....#..#.#
                        .##..##.#.
                        .#.####...
                        ####.#.#..
                        ##.####...
                        ##..#.##..
                        #.##...##.
                        
                        Tile 3079:
                        #.#.#####.
                        .#..######
                        ..#.......
                        ######....
                        ####.#..#.
                        .#...#.##.
                        #.#####.##
                        ..#.###...
                        ..#.......
                        ..#.###...""", "273"  },
                { SECOND, getInput( INSTANCE ), "1858" }
        });
    }
}
