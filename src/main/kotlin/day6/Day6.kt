package day6

import util.readFile

var DIGIT_REGEX = ("""\d+""").toRegex()

data class Race(
    val time: Long,
    val distance: Long,
) {
    fun calculateWiningOptions(): Long {
        var winingOption = 0L
        for (t in 1 until distance) {
            val remainingTime = time - t
            if ((remainingTime * t) > distance) {
                winingOption += 1L
            }

            if(remainingTime < 0) break
        }
        return winingOption
    }
}

fun main() {

    fun part1(lines: List<String>): Long {
        val timeList = DIGIT_REGEX.findAll(lines[0]).map { it.value.toLong() }.toList()
        val distanceList = DIGIT_REGEX.findAll(lines[1]).map { it.value.toLong() }.toList()

        val raceLists = timeList.mapIndexed { index, t -> Race(t, distanceList[index]) }

        return raceLists.map { it.calculateWiningOptions() }.reduce { acc, i -> acc * i }
    }

    fun part2(lines: List<String>): Long {
        val time = DIGIT_REGEX.findAll(lines[0]).map { it.value }.joinToString("").toLong()
        val distance = DIGIT_REGEX.findAll(lines[1]).map { it.value }.joinToString("").toLong()

        return Race(time, distance).calculateWiningOptions()
    }

    val lines = readFile("day6/Part1&2")
    println("Part One: ${part1(lines)}")
    println("Part Two: ${part2(lines)}")
}
