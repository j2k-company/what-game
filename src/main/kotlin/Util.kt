fun readFileText(path: String) =
    object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.use { it.readText() }
