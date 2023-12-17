package day9

import util.readFile

fun main() {
    fun getNext(list: List<Long>): Long {
        return if (list.all { it == 0L }) 0L
        else getNext(list.windowed(2).map { it[1] - it[0] }) + list.last()
    }

    fun part1(lines: List<String>): Long {
        val mappedLines = lines.map { line -> getNext(line.split(" ").map { it.toLong() }) }

        return mappedLines.sum()
    }

    fun getPrevious(list: List<Long>): Long {
        return if (list.all { it == 0L }) 0L
        else list.first() - getPrevious(list.windowed(2).map { it[1] - it[0] })
    }

    fun part2(lines: List<String>): Long {
        val mappedLines = lines.map { line -> getPrevious(line.split(" ").map { it.toLong() }) }

        return mappedLines.sum()
    }

    val lines = readFile("day9/Part1&2")
    println("Part One: ${part1(lines)}")
    println("Part Two: ${part2(lines)}")
}
