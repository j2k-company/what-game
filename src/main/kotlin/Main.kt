import glfw.Window
import org.lwjgl.Version
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.*
import shader.ShaderProgram
import shader.shaderBuilder
import kotlin.properties.Delegates


private var window by Delegates.notNull<Window>()
private val vertices = floatArrayOf(
    -0.5f, -0.5f, 0.0f,
    0.5f, -0.5f, 0.0f,
    0.0f,  0.5f, 0.0f
)

private var shader by Delegates.notNull<ShaderProgram>()
private var vao by Delegates.notNull<Int>() // TODO: this property should have own wrapper(dataclass or regular class)

fun main(args: Array<String>) {
    println("Version of lwjgl is " + Version.getVersion())

    glfw.init(glfw.Version(3, 3))
    window = Window(640, 480, "My abstract window!")

    GL.createCapabilities()
//    glClearColor(210f / 255f, 105f / 255f, 30f / 255f, 0.0f)
    glClearColor(0f, 0f, 0f, 0.0f) // TODO: move this in Window class
//    glClearColor(1f, 1f, 1f, 0.0f)

    shader = shaderBuilder {
        addShader("shaders/vertex.vert", GL_VERTEX_SHADER)
        addShader("shaders/fragment.frag", GL_FRAGMENT_SHADER)
    }

    vao = glGenVertexArrays()
    glBindVertexArray(vao)

    pushVerticesToBuffer(vertices)
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.SIZE_BYTES, 0)
    glBindBuffer(GL_ARRAY_BUFFER, 0)
    glEnableVertexAttribArray(0) // BUG: maybe Enable and Disable should be in the render loop
    glBindVertexArray(0)

    loop()

    shader.delete()
    window.destroy()
    glfw.terminate()
}

private fun pushVerticesToBuffer(vertices: FloatArray) {
    val vbo = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    val buffer = storeDataToBuffer(vertices)
    glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
}


private fun loop() {
    while (!window.isShouldClose) {
        window.clear()

        shader.attach()

        glBindVertexArray(vao)
        glDrawArrays(GL_TRIANGLES, 0, 3)

        glBindVertexArray(0)

        window.update()
    }
}

//
//    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
//    glfwSetKeyCallback(
//        window,
//        GLFWKeyCallbackI { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
//            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(
//                window,
//                true
//            ) // We will detect this in the rendering loop
//        }
//    )
//    stackPush().use { stack ->
//        val pWidth = stack.mallocInt(1) // int*
//        val pHeight = stack.mallocInt(1) // int*
//
//        // Get the window size passed to glfwCreateWindow
//        glfwGetWindowSize(window, pWidth, pHeight)
//
//        // Get the resolution of the primary monitor
//        val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!
//
//        // Center the window
//        glfwSetWindowPos(
//            window,
//            (vidmode.width() - pWidth[0]) / 2,
//            (vidmode.height() - pHeight[0]) / 2
//        )
