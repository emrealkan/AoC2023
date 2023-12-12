package day5

import util.readFile

val DIGIT_RAW_PATTERN = ("\\d+ \\d+ \\d+".toRegex())
val MAP_RAW_PATTERN = (".* map:".toRegex())

data class RangeMap(
    private val destinationRangeStart: Long,
    private val sourceRangeStart: Long,
    private val rangeLength: Int,
) {
    fun mapOrNull(value: Long): Long? =
        if (value in sourceRangeStart until sourceRangeStart + rangeLength) {
            (value - sourceRangeStart) + destinationRangeStart
        } else null
}

data class CategoryMap(
    val sourceName: String,
    val targetName: String,
    private val rangeMaps: List<RangeMap>,
) {
    fun covertToSeedMapping(value: Long): Pair<Long, String> {
        val valueFromRange = rangeMaps.firstNotNullOfOrNull { it.mapOrNull(value) } ?: value
        return Pair(valueFromRange, targetName)
    }
}

fun main() {

    fun part1(lines: List<String>): Long {
        val seeds = lines[0].removePrefix("seeds: ").trim().split("\\s".toRegex()).map { it.toLong() }
        val categoriesMap = mutableListOf<CategoryMap>()
        var rangeMappers = mutableListOf<RangeMap>()
        var sourceName = ""
        var targetName = ""

        lines.drop(2).mapIndexed { index, line ->
            if (MAP_RAW_PATTERN.matches(line)) {
                val ls = line.removeSuffix(" map:").split("-to-")
                sourceName = ls.first()
                targetName = ls.last()
            } else if (DIGIT_RAW_PATTERN.matches(line)) {
                val ls1 = line.split(" ")
                rangeMappers.add(RangeMap(ls1[0].toLong(), ls1[1].toLong(), ls1[2].toInt()))
                if (index == lines.size - 3) {
                    categoriesMap.add(CategoryMap(sourceName, targetName, rangeMappers))
                }
            } else {
                categoriesMap.add(CategoryMap(sourceName, targetName, rangeMappers))
                rangeMappers = mutableListOf()
            }
        }

        val categoryBySourceName = categoriesMap.associateBy { it.sourceName }
        val locationList = mutableListOf<Long>()
        seeds.forEach { seed ->
            var seedMappingResult = Pair(seed, "seed")
            while (seedMappingResult.second != "location") {
                val category = categoryBySourceName[seedMappingResult.second]!!
                seedMappingResult = category.covertToSeedMapping(seedMappingResult.first)
            }
            locationList.add(seedMappingResult.first)
        }
        return locationList.min()
    }

    val lines = readFile("day5/Part1")
    println("Part One: ${part1(lines)}")
}
