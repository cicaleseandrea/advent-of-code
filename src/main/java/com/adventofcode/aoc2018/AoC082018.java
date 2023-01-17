package com.adventofcode.aoc2018;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnTabOrSpace;

import com.adventofcode.Solution;
import com.adventofcode.utils.TreeNode;
import java.util.LinkedList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

class AoC082018 implements Solution {

    private static int index;

    private static void reset() {
        index = 0;
    }

    private static String solve(final List<String> input, final ToIntFunction<TreeNode<List<Integer>>> computeResult) {
        reset();
        final var root = getNode(input);
        final int res = computeResult.applyAsInt(root);
        return itoa(res);
    }

    private static TreeNode<List<Integer>> getNode(final List<String> input) {
        final List<Integer> metadata = new LinkedList<>();
        final var n = new TreeNode<>(metadata);
        final int nChildren = getNextInt(input);
        final int nMetadata = getNextInt(input);
        for (int i = 0; i < nChildren; i++) {
            n.addChild(getNode(input));
        }
        for (int i = 0; i < nMetadata; i++) {
            metadata.add(getNextInt(input));
        }
        return n;
    }

    private static int getNextInt(final List<String> input) {
        return atoi(input.get(index++));
    }

    private static int computeNodeValue(final TreeNode<List<Integer>> n) {
        final var children = n.getChildren();
        final int nChildren = children.size();
        final Stream<Integer> metadataStream = n.getKey().parallelStream();
        final int res;
        if (nChildren > 0) {
            res = metadataStream.mapToInt(Integer::intValue)
                    .filter(i -> i > 0).filter(i -> i - 1 < nChildren).
                            mapToObj(i -> children.get(i - 1)).mapToInt(AoC082018::computeNodeValue).sum();
        } else {
            res = metadataStream.mapToInt(Integer::intValue).sum();
        }
        return res;
    }

    private static int computeNodeSum(final TreeNode<List<Integer>> n) {
        int res = n.getKey().parallelStream().mapToInt(Integer::intValue).sum();
        res += n.getChildren().parallelStream().mapToInt(AoC082018::computeNodeSum).sum();
        return res;
    }

    public String solveFirstPart(final Stream<String> input) {
        return solve(splitOnTabOrSpace(getFirstString(input)), AoC082018::computeNodeSum);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(splitOnTabOrSpace(getFirstString(input)), AoC082018::computeNodeValue);
    }
}