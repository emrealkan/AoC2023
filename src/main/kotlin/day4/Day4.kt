package day4

import util.readFile
import kotlin.math.pow

val WHITESPACE_PATTERN = "\\s+".toRegex()
val CARD_PATTERN = "Card\\s+(?<cardId>\\d+)".toRegex()

fun main() {

    fun getTotalMatchedNumber(line: String): Int {
        val (_, numbers) = line.split(':')
        val (winningNumbers, yourNumbers) = numbers.split("|")
        val winningList = winningNumbers.trim().split(WHITESPACE_PATTERN)
        val yourNumberList = yourNumbers.trim().split(WHITESPACE_PATTERN)

        return yourNumberList.count { it in winningList }
    }

    fun part1(lines: List<String>): Int {
        return lines.map { line ->
            val matchedNumbers = getTotalMatchedNumber(line)

            val matchedPoints: Int = if (matchedNumbers == 0) 0
            else 2.0.pow(matchedNumbers.toDouble() - 1).toInt()

            matchedPoints
        }.reduce { acc, i -> acc + i }
    }

    fun part2(lines: List<String>): Int {
        val cardIdToMatchedNumber = lines.map { line ->
            val (cardInfo, _) = line.split(':')
            val (cardId) = CARD_PATTERN.find(cardInfo)!!.destructured
            val matchedNumbers = getTotalMatchedNumber(line)

            cardId to matchedNumbers
        }

        val allCards = Array(cardIdToMatchedNumber.size) { 1 }
        for (index in allCards.indices) {
            val matchedNumbers = cardIdToMatchedNumber[index].second
            if (matchedNumbers > 0) {
                for (m in 0 until matchedNumbers) {
                    val nextIndex = index + m + 1
                    if (nextIndex < allCards.size) {
                        allCards[nextIndex] += allCards[index]
                    }
                }
            }
        }

        return allCards.sum()
    }

    val lines = readFile("day4/Part1&2")
    println("Part One: ${part1(lines)}")
    println("Part Two: ${part2(lines)}")
}
