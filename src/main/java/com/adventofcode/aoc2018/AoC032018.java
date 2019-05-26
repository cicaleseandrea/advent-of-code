package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

import java.util.List;

import static com.adventofcode.utils.Utils.*;

class AoC032018 implements Solution {

    private static final int SIZE = 1000;

    public String solveFirstPart(final List<String> input) {
        final long[][] matrix = createMatrix(input);
        //count overlapping
        final long res =
                matrixToLongStream(matrix).filter(i -> i == -1).count();
        return itoa(res);
    }

    public String solveSecondPart(final List<String> input) {
        final long[][] matrix = createMatrix(input);
        long res = -1;
        for (final String fabric : input) {
            //check which id does not overlap
            res = computationOnFabric(matrix, fabric);
            if (res != -1) break;
        }
        return itoa(res);
    }

    private long[][] createMatrix(final List<String> input) {
        final long[][] matrix = new long[SIZE][SIZE];
        for (final String fabric : input) {
            //fill matrix
            computationOnFabric(matrix, fabric);
        }
        return matrix;
    }

    private long computationOnFabric(final long[][] matrix, final String fabric) {
        final List<String> parameters = splitOnTabOrSpace(fabric);
        final long id = atol(parameters.get(0).replace("#", EMPTY));
        final Pair<Integer, Integer> start = createPairInteger(parameters.get(2).replace(":", EMPTY).split(","));
        final Pair<Integer, Integer> size = createPairInteger(parameters.get(3).split("x"));
        boolean noOverlap = true;
        for (int i = start.getSecond(); i < start.getSecond() + size.getSecond(); i++) {
            for (int j = start.getFirst(); j < start.getFirst() + size.getFirst(); j++) {
                if ((matrix[i][j] == 0) || (matrix[i][j] == id)) {
                    matrix[i][j] = id;
                } else {
                    matrix[i][j] = -1;
                    noOverlap = false;
                }
            }
        }
        return noOverlap ? id : -1;
    }
}
