package day8

import util.readFile

fun main() {

    fun getNodeCommands(lines: List<String>): List<Pair<String, List<String>>> {
        return lines.drop(2).map {
            val (node, network) = it.split("=")
            val (left, right) = network.trim().split(", ")

            val leftCommand = left.removePrefix("(").trim()
            val rightCommand = right.removeSuffix(")").trim()

            node to listOf(leftCommand, rightCommand)
        }
    }

    fun calculateTotalDirection(
        navigationCommands: List<Int>,
        nodeCommands: List<Pair<String, List<String>>>,
    ): Int {
        var startPoint = "AAA"
        var totalSteps = 0

        while (startPoint != "ZZZ") {
            val directions = nodeCommands.find{ t -> t.first.trim() == startPoint }!!.second
            val direction = navigationCommands[totalSteps.mod(navigationCommands.size)]

            startPoint = directions[direction]
            totalSteps += 1
        }

        return totalSteps
    }

    fun part1(lines: List<String>): Int {
        val navigationCommands = lines[0].map { if (it == 'L') 0 else 1 }
        val nodeCommands = getNodeCommands(lines)
        return calculateTotalDirection(navigationCommands, nodeCommands)
    }

    val lines = readFile("day8/Part1")
    println("Part One: ${part1(lines)}")
}
