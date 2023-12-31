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

    fun calculateTotalDirectionPart1(
        navigationCommands: List<Int>,
        nodeCommands: List<Pair<String, List<String>>>,
    ): Int {
        var startPoint = "AAA"
        var totalSteps = 0

        while (startPoint != "ZZZ") {
            val directions = nodeCommands.find { t -> t.first.trim() == startPoint }!!.second
            val direction = navigationCommands[totalSteps.mod(navigationCommands.size)]

            startPoint = directions[direction]
            totalSteps += 1
        }

        return totalSteps
    }

    fun gcd(a: Long, b: Long): Long {
        var num1 = a
        var num2 = b
        while (num2 != 0L) {
            val temp = num2
            num2 = num1 % num2
            num1 = temp
        }
        return num1
    }

    fun lcm(first: Long, second: Long): Long {
        return first * second / gcd(first, second)
    }

    fun calculateTotalDirectionPart2(
        navigationCommands: List<Int>,
        nodeCommands: List<Pair<String, List<String>>>,
    ): Long {
        val startPoints = nodeCommands.filter { t -> t.first.trim().endsWith('A') }

        return startPoints.map { t ->
            var startPoint = t.first.trim()
            var totalSteps = 0L

            while (!startPoint.endsWith('Z')) {
                val directions = nodeCommands.find { it.first.trim() == startPoint }!!.second
                val direction = navigationCommands[totalSteps.mod(navigationCommands.size)]

                startPoint = directions[direction]
                totalSteps++
            }

            totalSteps
        }.reduce { acc, i -> lcm(acc, i) }
    }

    fun part1(lines: List<String>): Int {
        val navigationCommands = lines[0].map { if (it == 'L') 0 else 1 }
        val nodeCommands = getNodeCommands(lines)

        return calculateTotalDirectionPart1(navigationCommands, nodeCommands)
    }

    fun part2(lines: List<String>): Long {
        val navigationCommands = lines[0].map { if (it == 'L') 0 else 1 }
        val nodeCommands = getNodeCommands(lines)

        return calculateTotalDirectionPart2(navigationCommands, nodeCommands)
    }

    val lines = readFile("day8/Part1&2")
    println("Part One: ${part1(lines)}")
    println("Part Two: ${part2(lines)}")
}
