import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer

fun readFileText(path: String) =
    object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.use { it.readText() }

fun storeDataToBuffer(data: FloatArray): FloatBuffer = MemoryUtil.memAllocFloat(data.size).apply {
    put(data)
    flip()
}