package day4

import readFile
import kotlin.math.pow

val WHITESPACE_PATTERN = "\\s+".toRegex()

fun main() {

    fun part1(lines: List<String>): Int {
        return lines.map { line ->
            val (_, numbers) = line.split(':')
            val (winningNumbers, yourNumbers) = numbers.split("|")
            val winningList = winningNumbers.trim().split(WHITESPACE_PATTERN)
            val yourNumberList = yourNumbers.trim().split(WHITESPACE_PATTERN)

            val matchedNumbers = yourNumberList.count { it in winningList }
            var matchedPoints = 0

            matchedPoints = if ( matchedNumbers == 0) 0
            else 2.0.pow(matchedNumbers.toDouble() - 1).toInt()

            matchedPoints
        }.reduce { acc, i -> acc + i }
    }

    val lines = readFile("day4/Part1")
    println("Part One: ${part1(lines)}")
}
