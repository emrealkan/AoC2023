package day2

import readFile

fun main() {

    fun part1(lines: List<String>): Int {
        val GAME_INFO_PATTERN = "Game (?<gameId>\\d+)".toRegex()
        val BAG_DETAIL_PATTERN = "(?<number>\\d+) (?<color>red|green|blue)".toRegex()
        val map = mutableMapOf("red" to 12, "green" to 13, "blue" to 14)
        var sumOfGameIds = 0

        lines.map { line ->
            val (gameInfo, bagDetails) = line.split(':')
            val (gameId) = GAME_INFO_PATTERN.find(gameInfo)!!.destructured
            var isBagValid = true

            bagDetails.split(";").map { bagDetail ->
                BAG_DETAIL_PATTERN.findAll(bagDetail)
                    .associate { it.groups["color"]!!.value to it.groups["number"]!!.value.toInt() }
                    .forEach { entry ->
                        if (entry.value > map.get(entry.key)!!) {
                            isBagValid = false
                        }
                    }
            }

            if (isBagValid) {
                sumOfGameIds += gameId.toInt()
            }
        }
        return sumOfGameIds
    }

    val part1Input = readFile("day2/Part1")
    println("Part One: ${part1(part1Input)}")
}
