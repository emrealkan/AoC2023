fun main() {

    fun part1(file: List<String>): Int {
        return file
            .sumOf { line ->
                val first = line.first { c -> c.isDigit() }
                val last = line.last { c -> c.isDigit() }

                "$first$last".toInt()
            }
        }


    val input: List<String> = readFile("Day1")
    println("Part One: ${part1(input)}")
}