package day11

import util.readFile
import kotlin.math.abs

data class Galaxy(val x: Int, val y: Int) {
    companion object {
        private val allGalaxies = mutableListOf<Galaxy>()

        fun addGalaxy(galaxy: Galaxy) {
            allGalaxies.add(galaxy)
        }

        private fun shortestPath(first: Galaxy, second: Galaxy): Int { // manhattan distance (AKA Taxicab Metric)
            return abs(first.x - second.x) + abs(first.y - second.y)
        }

        fun calculateTotalSumOfShortestPath(): Int {
            val galaxyPairs = allGalaxies.flatMap { first -> allGalaxies.map { second -> first to second } }

            return galaxyPairs.sumOf { shortestPath(it.first, it.second) } / 2
        }
    }

    init {
        addGalaxy(this)
    }
}

fun main() {

    fun findEmptyColumns(lines: List<String>): List<Int> {
        return lines.first().indices.filter { column -> lines.all { it[column] == '.' } }
    }

    fun findEmptyRaws(lines: List<String>): List<Int> {
        return lines.indices.filter { row -> lines[row].all { it == '.' } }
    }

    fun expandLines(lines: List<String>): List<String> {
        val expandedLines = mutableListOf<String>()
        val emptyColumns = findEmptyColumns(lines)
        val emptyRaws = findEmptyRaws(lines)

        lines.forEachIndexed { rawIndex, line ->
            val newLine = StringBuilder()
            line.forEachIndexed { columnIndex, character ->
                if (columnIndex in emptyColumns) {
                    newLine.append(character.toString())
                    newLine.append(character.toString())
                } else {
                    newLine.append(character.toString())
                }
            }
            expandedLines.add(newLine.toString())
            if (rawIndex in emptyRaws) {
                expandedLines.add(line)
            }

        }
        return expandedLines
    }

    fun part1(lines: List<String>): Int {
        expandLines(lines)
            .forEachIndexed { y, yC ->
                yC.forEachIndexed { x, xC ->
                    if (xC == '#')
                        Galaxy(x, y)
                }
            }

        return Galaxy.calculateTotalSumOfShortestPath()
    }

    val lines = readFile("day11/Part1")
    println("Part One: ${part1(lines)}")
}
