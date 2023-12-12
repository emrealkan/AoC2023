package day5

import util.readFile

val SEED_RAW_PATTERN = ("\\s".toRegex())
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

    fun getParsedData(lines: List<String>): Pair<MutableList<CategoryMap>, List<Long>> {
        val seeds =
            lines[0].removePrefix("seeds: ").trim().split(SEED_RAW_PATTERN).map { it.toLong() }

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

        return Pair(first = categoriesMap, second = seeds)
    }

    fun getMatchedSeed(categoriesMap: MutableList<CategoryMap>, seed: Long): Long {
        val categoryBySourceName = categoriesMap.associateBy { it.sourceName }
        var seedMappingResult = Pair(seed, "seed")
        while (seedMappingResult.second != "location") {
            val category = categoryBySourceName[seedMappingResult.second]!!
            seedMappingResult = category.covertToSeedMapping(seedMappingResult.first)
        }
        return seedMappingResult.first
    }

    fun part1(lines: List<String>): Long {
        val categoriesMap = getParsedData(lines).first
        val seeds = getParsedData(lines).second

        return seeds.minOfOrNull { getMatchedSeed(categoriesMap, it) }!!
    }

    fun part2(lines: List<String>): Long {
        val categoriesMap = getParsedData(lines).first
        val seeds = getParsedData(lines).second
        val pairedSeeds = seeds.chunked(2).map { Pair(it[0], it[1]) }

        return pairedSeeds.minOfOrNull {
            val start = it.first
            val end = it.first + it.second

            val res = (start until end).minOf { getMatchedSeed(categoriesMap, it) }

            res
        }!!
    }

    val lines = readFile("day5/Part1&2")
    println("Part One: ${part1(lines)}")
    println("Part Two: ${part2(lines)}")
}
