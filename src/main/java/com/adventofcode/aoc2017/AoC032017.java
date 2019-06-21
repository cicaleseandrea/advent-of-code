package com.adventofcode.aoc2017;

import com.adventofcode.Solution;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;

class AoC032017 implements Solution {
    private static String solveFirstPartAlternative(final int inputNumber) {
        //compute minimum length of a side to fit our input in a square
        final long length = computeLength(inputNumber);
        //start from the last number, which has the farthest distance
        long res = length - 1;
        boolean decrease = true;
        long n = length * length;
        //move around the matrix, until you find the input
        while (n != inputNumber) {
            if (decrease) {
                res--;
            } else {
                res++;
            }
            //minimum distance is length / 2
            //maximum distance is 2 * (length / 2) == length - 1
            if (res % (length / 2) == 0) {
                decrease = !decrease;
            }
            n--;
        }
        return itoa(res);
    }

    private static String solve(final int inputNumber,
                                BiPredicate<Long, Integer> check,
                                final Function<long[][], BiFunction<Integer, Integer, LongUnaryOperator>> computePartialResult,
                                final BiFunction<Integer, Integer, Function<Integer, LongUnaryOperator>> computeFinalResult) {
        final int length = computeLength(inputNumber);
        final long[][] matrix = new long[length][length];
        final int center = length / 2;
        int i = center;
        int j = center;
        long res = matrix[i][j] = 1;
        int direction = 3;
        int steps = 0;
        int max = 1;
        while (check.test(res, inputNumber)) {
            matrix[i][j] = res = computePartialResult.apply(matrix).apply(i, j).applyAsLong(res);
            //perform a step in the right direction
            steps++;
            switch (direction) {
                case 0 -> i--; //up
                case 1 -> j--; //left
                case 2 -> i++; //down
                case 3 -> j++; //right
            }
            //change direction
            if (steps == max) {
                steps = 0;
                direction = incrementMod(direction, 4);
                //increase number of steps per direction
                if (direction % 2 == 1) {
                    max++;
                }
            }
        }
        return itoa(computeFinalResult.apply(i, j).apply(center).applyAsLong(res));
    }

    private static int computeLength(final int input) {
        int length = 1;
        while (length * length < input) {
            length += 2;
        }
        return length;
    }

    public String solveFirstPart(final Stream<String> input) {
        final int inputNumber = atoi(getFirstString(input));
        if (ThreadLocalRandom.current().nextInt(2) == 0) return solveFirstPartAlternative(inputNumber);
        return solve(inputNumber, (res, number) -> res < number,
                m -> (i, j) -> res -> res + 1,
                (i, j) -> center -> res -> manhattanDistance(i, j, center, center));
    }

    public String solveSecondPart(final Stream<String> input) {
        final int inputNumber = atoi(getFirstString(input));
        return solve(inputNumber, (res, number) -> res <= number,
                m -> (i, j) -> res -> computePartialResult(m, i, j),
                (i, j) -> center -> res -> res);
    }

    private long computePartialResult(final long[][] matrix, final int i, final int j) {
        return sumNeighbors8(matrix, i, j) + matrix[i][j];
    }
}
