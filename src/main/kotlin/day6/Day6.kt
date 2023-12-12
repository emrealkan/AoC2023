package day6

import util.readFile

var DIGIT_REGEX = ("""\d+""").toRegex()

data class Race(
    val time: Int,
    val distance: Int,
) {
    fun calculateWiningOptions(): Int {
        var winingOption = 0
        for (t in 1 until distance) {
            val remainingTime = time - t
            if ((remainingTime * t) > distance) {
                winingOption += 1
            }
        }
        return winingOption
    }
}

fun main() {

    fun part1(lines: List<String>): Int {
        val timeList = DIGIT_REGEX.findAll(lines[0]).map { it.value.toInt() }.toList()
        val distanceList = DIGIT_REGEX.findAll(lines[1]).map { it.value.toInt() }.toList()

        val raceLists = timeList.mapIndexed { index, t -> Race(t, distanceList[index]) }

        return raceLists.map { it.calculateWiningOptions() }.reduce { acc, i -> acc * i }
    }

    val lines = readFile("day6/Part1")
    println("Part One: ${part1(lines)}")
}
