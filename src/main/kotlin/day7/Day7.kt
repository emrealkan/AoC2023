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

    fun getCardValue(c: Char): Int {
        return when (c) {
            'A' -> (14)
            'K' -> (13)
            'Q' -> (12)
            'J' -> (11)
            'T' -> (10)
            else -> c.digitToInt()
        }
    }

    data class Game(val cardsPoint: Int, val bid: Int, val handType: HandType)

    fun part1(lines: List<String>): Int {
        val gamesByHandTypes = lines.map { line ->
            val (first, second) = line.split(' ')
            val bid = second.toInt()
            val handType = getHandType(first)
            val cardPoints =
                (first.reversed().mapIndexed { index, c ->
                    (100.0.pow(index.toDouble()) * getCardValue(c)).toInt() //doing 100 pow to make sure each index has enough distance from each other
                }
                    .toList())
                    .reduce { t, u -> (t + u) }
                    .toInt()
            Game(cardPoints, bid, handType)
        }
            .sortedBy { it.handType }
            .groupBy { it.handType }
            .map { it.value }

        var totalScore = 0
        var currentIndex = lines.size
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

    val lines = readFile("day7/Part1")
    println("Part One: ${part1(lines)}")
}
