package day1

import readFile

fun main() {
    fun part1(lines: List<String>): Int {
        return lines.sumOf { line ->
            val first = line.first { c -> c.isDigit() }
            val last = line.last { c -> c.isDigit() }

            "$first$last".toInt()
        }
    }

    fun part2(lines: List<String>): Int {
        val digitStrings =
            arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        return lines
            .map { line ->
                line.mapIndexedNotNull { index, c ->
                    when {
                        c.isDigit() -> c
                        else ->
                            digitStrings
                                .find { line.startsWith(it, index) }
                                ?.let {
                                    val number = digitStrings.indexOf(it) + 1
                                    number.digitToChar()
                                }
                    }
                }
            }
            .sumOf { line ->
                val first = line.first()
                val last = line.last()

                "$first$last".toInt()
            }
    }

    val part1Input: List<String> = readFile("day1/Part1")
    println("Part One: ${part1(part1Input)}")

    val part2Input: List<String> = readFile("day1/Part2")
    println("Part Two: ${part2(part2Input)}")
}
