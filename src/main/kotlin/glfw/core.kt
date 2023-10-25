package glfw

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.GL_FALSE
import org.lwjgl.opengl.GL11.GL_TRUE

data class Version(val major: Int, val minor: Int)

fun init(version: Version? = null) {
    glfwSetErrorCallback { error, description ->
        System.err.println("Error $error: $description")
    }

    check(glfwInit()) { "Unable to initialize GLFW" }

    version?.let {
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, it.major);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, it.minor);
    }

    glfwWindowHint(GLFW_VISIBLE, GL_FALSE)
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE)

    glfwDefaultWindowHints()
}

fun terminate() {
    glfwTerminate()
    glfwSetErrorCallback(null)?.free()
}
