package glfw

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL

class Window(
    width: Int,
    height: Int,
    title: String,
    enableVSync: Boolean = true
) {
    var isShouldClose: Boolean
        get() = glfwWindowShouldClose(window)
        set(value) = glfwSetWindowShouldClose(window, value)

    private val window = glfwCreateWindow(width, height, title, NULL, NULL).also {
        if (it == NULL) throw RuntimeException("Failed to create the GLFW window")
        glfwMakeContextCurrent(it)

        if (enableVSync) glfwSwapInterval(1)

        glfwShowWindow(it)
    }

    fun update() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) // TODO: maybe i should move it to another function
        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    fun destroy() {
        glfwFreeCallbacks(window) // TODO: In the future will be added input manager
        glfwDestroyWindow(window)
    }
}
