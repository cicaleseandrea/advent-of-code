package com.adventofcode.aoc2017

import com.adventofcode.Solution
import com.adventofcode.utils.Utils.*
import java.util.stream.Stream

object AoC012017 : Solution {

    private fun String.computeSum(nextIndex: (Int) -> Int): String =
        asSequence()
            .filterIndexed { i, ch -> ch == this[nextIndex(i)] }
            .sumBy(::charToInt)
            .run { toString() }

    override fun solveFirstPart(input: Stream<String>): String =
        with(getFirstString(input)) {
            computeSum { incrementMod(it, length) }
        }

    override fun solveSecondPart(input: Stream<String>): String =
        with(getFirstString(input)) {
            computeSum { incrementMod(it, length / 2, length) }
        }
}
