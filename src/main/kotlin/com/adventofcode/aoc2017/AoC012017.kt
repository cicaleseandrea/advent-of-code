package com.adventofcode.aoc2017

import com.adventofcode.Solution
import com.adventofcode.utils.Utils.*
import java.util.stream.Stream

class AoC012017 : Solution {

    private fun solve(s: String, computeNext: (Int) -> Int): String {
        var res = 0
        for (i in 0 until s.length) {
            //compute where is the value to check
            val next = computeNext.invoke(i)
            val toCheck = s[next]
            val curr = s[i]
            if (curr == toCheck) {
                res += charToInt(curr)
            }
        }
        return itoa(res.toLong())
    }

    override fun solveFirstPart(input: Stream<String>): String {
        val s = getFirstString(input)
        return solve(s) { i -> (i + 1) % s.length }
    }

    override fun solveSecondPart(input: Stream<String>): String {
        val s = getFirstString(input)
        return solve(s) { i -> (i + s.length / 2) % s.length }
    }
}
