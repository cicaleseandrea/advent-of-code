package com.adventofcode.aoc2018;

import com.adventofcode.Solution;

import java.util.List;
import java.util.function.BiFunction;

import static com.adventofcode.utils.Utils.atoi;

class AoC112018 implements Solution {

    private static final int SIZE = 301;

    private static String solve(final List<String> input, final int minSize, final int maxSize,
                                final BiFunction<String, Integer, String> computeResult) {
        final int[][] matrix = new int[SIZE][SIZE];
        computeSummedAreaTable(atoi(input.get(0)), matrix);
        int resI = 0;
        int resJ = 0;
        int resSize = 1;
        int max = Integer.MIN_VALUE;
        //for each point
        for (int i = 1; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                //for each size
                final int borderDistance = Math.min(SIZE - i, SIZE - j);
                int size = minSize;
                while (size < Math.min(maxSize, borderDistance)) {
                    int sum = 0;
                    sum += matrix[i + size][j + size];
                    sum += matrix[i - 1][j - 1];
                    sum -= matrix[i - 1][j + size];
                    sum -= matrix[i + size][j - 1];
                    //keep track of the max
                    if (max < sum) {
                        max = sum;
                        resI = i;
                        resJ = j;
                        resSize = size + 1;
                    }
                    size++;
                }
            }
        }
        final String res = resJ + "," + resI;
        return computeResult.apply(res, resSize);
    }

    private static void computeSummedAreaTable(final int serial, final int[][] matrix) {
        for (int i = 1; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                final int rackId = j + 10;
                matrix[i][j] = ((((rackId * i) + serial) * rackId) / 100) % 10 - 5;
                matrix[i][j] += matrix[i][j - 1];
                matrix[i][j] += matrix[i - 1][j];
                matrix[i][j] -= matrix[i - 1][j - 1];
            }
        }
    }

    public String solveFirstPart(final List<String> input) {
        return solve(input, 2, 3, (pos, size) -> pos);
    }

    public String solveSecondPart(final List<String> input) {
        return solve(input, 0, SIZE, (pos, size) -> pos + "," + size);
    }
}