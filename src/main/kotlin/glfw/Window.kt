package glfw

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
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

    init {
        // set viewport
        glfwSetFramebufferSizeCallback(window) { _, width, height ->
            glViewport(0, 0, width, height)
        }

        // center window
        // it is should be optional?
        stackPush().use { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*

            glfwGetWindowSize(window, pWidth, pHeight)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!

            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }

        // TODO: This is should placed in the input manager?
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(
                window,
                true
            )
        }
    }

    fun update() {
        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    fun clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    fun destroy() {
        glfwFreeCallbacks(window) // TODO: In the future will be added input manager
        glfwDestroyWindow(window)
    }
}
