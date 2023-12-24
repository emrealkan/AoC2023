package day15

import util.readFile

var SEQUENCE_REGEX = "([a-z]*)([-|=])([1-9]?)".toRegex()
fun main() {

    fun calculateHash(sequence: String): Long {
        return sequence.fold(0) { hash, char ->
            val newHash = (hash + char.code) * 17 % 256
            newHash
        }.toLong()
    }

    fun part1(lines: List<String>): Long {
        return lines[0]
            .split(",")
            .sumOf {
                calculateHash(it)
            }
    }

    fun part2(lines: List<String>): Int {
        var totalFocusingPower = 0
        val lensBoxes: List<LinkedHashMap<String, Int>> = List(256) { LinkedHashMap() };
        lines[0]
            .split(",")
            .forEach {
                val (left, operation, right) = SEQUENCE_REGEX.find(it)!!.destructured

                val hash = calculateHash(left).toInt()
                val lensBox = lensBoxes[hash]
                if (operation == "-") {
                    lensBox.remove(left)
                } else {
                    lensBox[left] = right.toInt()
                }
            }

        lensBoxes.mapIndexed { index, box ->
            var slot = 1
            box.forEach {
                totalFocusingPower += (index + 1) * slot++ * it.value
            }
        }

        return totalFocusingPower
    }

    val lines = readFile("day15/Part1&2")
    println("Part One: ${part1(lines)}")
    println("Part Two: ${part2(lines)}")
}