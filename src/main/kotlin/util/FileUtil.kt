package util

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

fun readFile(name: String) = Path("src/main/kotlin/$name").readLines()