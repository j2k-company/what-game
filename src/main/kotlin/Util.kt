fun readFileText(path: String) =
    object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.use { it.readText() }

fun Boolean.toInt() = if (this) 1 else 0
