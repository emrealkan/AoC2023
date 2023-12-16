package day7

import util.readFile
import kotlin.math.pow

enum class HandType(val value: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1),
}

fun main() {

    data class Game(val cardsPoint: Int, val bid: Int, val handType: HandType)

    fun getHandType(cards: String): HandType {
        val groups = cards.groupingBy { it }.eachCount()
        return when (groups.entries.size) {
            1 -> HandType.FIVE_OF_A_KIND
            2 -> if (groups.values.max() == 4) HandType.FOUR_OF_A_KIND else HandType.FULL_HOUSE
            3 -> if (groups.values.max() == 3) HandType.THREE_OF_A_KIND else HandType.TWO_PAIR
            4 -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }
    }

    fun getHandTypeForPart2(cards: String): HandType {
        return if (cards.contains('J')) {
            val jokerTarget = cards.groupingBy { it }
                .eachCount()
                .filterKeys { it != 'J' }
                .maxByOrNull { (_, groupSize) -> groupSize }
                ?.key ?: 'A'

            val replacedHand = cards.replace('J', jokerTarget)

            getHandType(replacedHand)
        } else {
            getHandType(cards)
        }
    }

    fun getCardValue(c: Char, isJokerWeakest: Boolean = false): Int {
        return when (c) {
            'A' -> (14)
            'K' -> (13)
            'Q' -> (12)
            'J' -> if (isJokerWeakest) (1) else (11)
            'T' -> (10)
            else -> c.digitToInt()
        }
    }

    fun getListOfGamesByHandType(lines: List<String>, isJokerWeakest: Boolean = false): List<List<Game>> {
        return lines.map { line ->
            val (first, second) = line.split(' ')
            val bid = second.toInt()
            val handType = if(isJokerWeakest) getHandTypeForPart2(first) else getHandType(first)
            val cardPoints =
                (first.reversed().mapIndexed { index, c ->
                    (100.0.pow(index.toDouble()) * getCardValue(c, isJokerWeakest)).toInt() //doing 100 pow to make sure each index has enough distance from each other
                }
                    .toList())
                    .reduce { t, u -> (t + u) }
                    .toInt()
            Game(cardPoints, bid, handType)
        }
            .sortedBy { it.handType }
            .groupBy { it.handType }
            .map { it.value }
    }

    fun calculateTotalWinning(gamesByHandTypes: List<List<Game>>, lineSize: Int): Int {
        var totalScore = 0
        var currentIndex = lineSize
        gamesByHandTypes.forEach { games: List<Game> ->
            val cardPointComparator = Comparator { game1: Game, game2: Game -> game2.cardsPoint - game1.cardsPoint }
            games.sortedWith(cardPointComparator)
                .forEach { game ->
                    totalScore += game.bid * currentIndex
                    currentIndex -= 1
                }
        }
        return totalScore
    }

    fun part1(lines: List<String>): Int {
        val listOfGamesByHandTypes = getListOfGamesByHandType(lines, false)
        return calculateTotalWinning(listOfGamesByHandTypes, 1000)
    }

    fun part2(lines: List<String>): Int {
        val listOfGamesByHandTypes = getListOfGamesByHandType(lines, true)
        return calculateTotalWinning(listOfGamesByHandTypes, 1000)
    }

    val lines = readFile("day7/Part1&2")
    println("Part One: ${part1(lines)}")
    println("Part Two: ${part2(lines)}")
}
