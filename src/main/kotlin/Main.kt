import glfw.Window
import org.lwjgl.Version
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.GL30.*
import shader.shaderBuilder
import kotlin.properties.Delegates


private var window by Delegates.notNull<Window>()
private val vertices = floatArrayOf(
    -0.5f, -0.5f, 0.0f,
    0.5f, -0.5f, 0.0f,
    0.0f,  0.5f, 0.0f
)

fun main(args: Array<String>) {
    println("Version of lwjgl is " + Version.getVersion())

    glfw.init(glfw.Version(3, 3))
    window = Window(640, 480, "My abstract window!")

    GL.createCapabilities()
    glClearColor(210f / 255f, 105f / 255f, 30f / 255f, 0.0f)

    pushVerticesToBuffer(vertices)

    val shader = shaderBuilder {
        addShader("shaders/vertex.vert", GL_VERTEX_SHADER)
        addShader("shaders/fragment.frag", GL_FRAGMENT_SHADER)
    }

    shader.attach()

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
