package day3

import kotlin.math.max
import kotlin.math.min
import util.readFile

val SYMBOL_REGEX = "\\w*[0-9.]+\\w*+".toRegex()
val DIGIT_REGEX = "\\w*[0-9]+\\w*+".toRegex()
val GEAR_REGEX = "\\w*[*]+\\w*+".toRegex()

fun main() {
    fun checkMatchSymbol(line: String?, startDigitIndex: Int, digitSize: Int): Boolean {
        val lineSubSequence =
            line?.subSequence(
                max(startDigitIndex - 1, 0),
                min(startDigitIndex + digitSize + 1, line.length)
            )

        return lineSubSequence !== null && !lineSubSequence.matches(SYMBOL_REGEX)
    }

    fun checkNeighbors(
        startDigitIndex: Int,
        digitSize: Int,
        currentLine: String,
        previousLine: String?,
        nextLine: String?
    ): Int {
        if (
            checkMatchSymbol(previousLine, startDigitIndex, digitSize) ||
            checkMatchSymbol(nextLine, startDigitIndex, digitSize) ||
            checkMatchSymbol(currentLine, startDigitIndex, digitSize)
        )
            return currentLine
                .subSequence(startDigitIndex, startDigitIndex + digitSize)
                .toString()
                .toInt()
        return 0
    }

    fun part1(lines: List<String>): Int {
        var sumOfEngineSchematic = 0

        lines.mapIndexed { lineIndex, line ->
            val previousLine = if (lineIndex == 0) null else lines[lineIndex - 1]
            val nextLine = if (lineIndex == lines.size - 1) null else lines[lineIndex + 1]
            var digitSize = 0
            var startDigitIndex: Int
            line.mapIndexed { charIndex, c ->
                if (Character.isDigit(c)) {
                    if (charIndex == line.length - 1) {
                        startDigitIndex = charIndex - digitSize
                        digitSize += 1
                        sumOfEngineSchematic +=
                            checkNeighbors(startDigitIndex, digitSize, line, previousLine, nextLine)
                        digitSize = 0
                    } else {
                        digitSize += 1
                    }
                } else if (digitSize != 0) {
                    startDigitIndex = charIndex - digitSize
                    sumOfEngineSchematic +=
                        checkNeighbors(startDigitIndex, digitSize, line, previousLine, nextLine)
                    digitSize = 0
                }
            }
        }

        return sumOfEngineSchematic
    }

    fun getNumericNeighbors(charIndex: Int, line: String?): Array<Int?> {
        val isRightDigit = charIndex + 1 < line?.length!! && line[charIndex + 1].isDigit()
        val isLeftDigit = charIndex - 1 >= 0 && line[charIndex - 1].isDigit()
        val rightNeighbor =
            if (isRightDigit)
                line.let { DIGIT_REGEX.find(it.substring(charIndex + 1, line.length)) }
            else null
        val leftNeighbor =
            if (isLeftDigit) {
                line.let { DIGIT_REGEX.find(it.substring(0, charIndex).reversed()) }
            } else null

        val neighbors: Array<Int?>
        if (line[charIndex].isDigit()) {
            var result = line[charIndex].toString()
            if (leftNeighbor != null) {
                result = leftNeighbor.value.reversed() + result
            }
            if (rightNeighbor != null) {
                result += rightNeighbor.value
            }
            neighbors = arrayOf(result.toInt())
        } else {
            neighbors =
                arrayOf(rightNeighbor?.value?.toInt(), leftNeighbor?.value?.reversed()?.toInt())
        }

        return neighbors
    }

    fun checkGearNeighbors(
        charIndex: Int,
        line: String,
        previousLine: String?,
        nextLine: String?
    ): Int {
        val leftAndRightNeighbors = getNumericNeighbors(charIndex, line)
        val previousLineNeighbors = getNumericNeighbors(charIndex, previousLine)
        val nextLineNeighbors = getNumericNeighbors(charIndex, nextLine)

        val allNeighbors =
            arrayOf(leftAndRightNeighbors, previousLineNeighbors, nextLineNeighbors)
                .flatten()
                .filterNotNull()

        return if (allNeighbors.size == 2) allNeighbors.reduce { acc, i -> acc * i } else 0
    }

    fun part2(lines: List<String>): Int {
        var sumOfGearRatio = 0

        lines.mapIndexed { lineIndex, line ->
            val previousLine = if (lineIndex == 0) null else lines[lineIndex - 1]
            val nextLine = if (lineIndex == lines.size - 1) null else lines[lineIndex + 1]
            line.mapIndexed { charIndex, c ->
                if (GEAR_REGEX.matches(c.toString())) {
                    sumOfGearRatio += checkGearNeighbors(charIndex, line, previousLine, nextLine)
                }
            }
        }

        return sumOfGearRatio
    }

    val lines = readFile("day3/Part1&2")
    println("Part One: ${part1(lines)}")
    println("Part Two: ${part2(lines)}")
}
