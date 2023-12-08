package day3

import readFile
import kotlin.math.max
import kotlin.math.min

val SYMBOL_REGEX = "\\w*[0-9.]+\\w*+".toRegex()
fun main() {

    var sumOfEngineSchematic = 0

    fun checkMatchSymbol(line: String?, startDigitIndex: Int, digitSize: Int): Boolean {
        val nextLineSub = line?.subSequence(max(startDigitIndex-1, 0), min(startDigitIndex + digitSize + 1, line.length));

        return nextLineSub !== null && !nextLineSub.matches(SYMBOL_REGEX)
    }

    fun checkNeighbors(startDigitIndex: Int, digitSize: Int, currentLine:String, previousLine: String?, nextLine: String?) {
        if(checkMatchSymbol(previousLine, startDigitIndex, digitSize)
                || checkMatchSymbol(nextLine, startDigitIndex, digitSize)
                || checkMatchSymbol(currentLine, startDigitIndex, digitSize))

            sumOfEngineSchematic += currentLine.subSequence(startDigitIndex, startDigitIndex + digitSize).toString().toInt()
    }

    fun part1(lines: List<String>): Int {
        lines.mapIndexed{ lineIndex, line ->
            val previousLine = if (lineIndex == 0)  null else lines[lineIndex-1]
            val nextLine = if (lineIndex == lines.size -1) null else lines[lineIndex+1]
            var digitSize = 0
            var startDigitIndex: Int
            line.mapIndexed { charIndex, c ->
                if (Character.isDigit(c)) {
                    if (charIndex == line.length - 1) {
                        startDigitIndex = charIndex - digitSize
                        digitSize += 1
                        checkNeighbors(startDigitIndex, digitSize, line, previousLine, nextLine)
                        digitSize = 0
                    } else {
                        digitSize += 1
                    }
                } else if (digitSize != 0) {
                    startDigitIndex = charIndex - digitSize
                    checkNeighbors(startDigitIndex, digitSize, line, previousLine, nextLine)
                    digitSize = 0
                }
            }
        }

        return sumOfEngineSchematic
    }

    val lines = readFile("day3/Part1")
    println("Part One: ${part1(lines)}")
}
