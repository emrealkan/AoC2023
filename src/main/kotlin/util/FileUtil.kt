package util

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readFile(name: String) = Path("src/main/kotlin/$name").readLines()