package day2

import readFile

val GAME_INFO_PATTERN = "Game (?<gameId>\\d+)".toRegex()
val BAG_DETAIL_PATTERN = "(?<number>\\d+) (?<color>red|green|blue)".toRegex()

fun main() {

    fun part1(lines: List<String>): Int {
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
                        if (entry.value > map[entry.key]!!) {
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

    fun part2(lines: List<String>): Int {
        var sumOfCubes = 0

        lines.map { line ->
            val map = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            val (_, bagDetails) = line.split(':')

            bagDetails.split(";").map { bagDetail ->
                BAG_DETAIL_PATTERN.findAll(bagDetail)
                    .associate { it.groups["color"]!!.value to it.groups["number"]!!.value.toInt() }
                    .forEach { entry ->
                        if (entry.value > map[entry.key]!!) {
                            map[entry.key] = entry.value
                        }
                    }
            }
            sumOfCubes += map.values.reduce { a, b -> a * b }
        }
        return sumOfCubes
    }

    val input = readFile("day2/Part1&2")
    println("Part One: ${part1(input)}")
    println("Part Two: ${part2(input)}")
}
