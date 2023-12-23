package day15

import util.readFile

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

    val lines = readFile("day15/Part1")
    println("Part One: ${part1(lines)}")
}