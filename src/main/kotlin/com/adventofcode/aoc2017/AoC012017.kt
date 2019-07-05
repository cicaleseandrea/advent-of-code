package com.adventofcode.aoc2017

import com.adventofcode.Solution
import com.adventofcode.utils.Utils.*
import java.util.stream.Stream

class AoC012017 : Solution {

    private fun String.sum(computeNext: (Int) -> Int): String {
        var res = 0
        for (i in 0 until length) {
            //compute where is the value to check
            val next = computeNext(i)
            val toCheck = this[next]
            val curr = this[i]
            if (curr == toCheck) {
                res += charToInt(curr)
            }
        }
        return itoa(res)
    }

    override fun solveFirstPart(input: Stream<String>): String =
            with(getFirstString(input)) { sum { (it + 1) % length } }

    override fun solveSecondPart(input: Stream<String>): String =
            with(getFirstString(input)) { sum { (it + length / 2) % length } }
}
