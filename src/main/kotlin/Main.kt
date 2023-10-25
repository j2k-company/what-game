import glfw.Window
import org.lwjgl.Version
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.glClearColor
import kotlin.properties.Delegates


var window by Delegates.notNull<Window>()

fun main(args: Array<String>) {
    println("Version of lwjgl is " + Version.getVersion())

    glfw.init()
    window = Window(640, 480, "My abstract window!")
    loop()

    window.destroy()
    glfw.terminate()
}

private fun loop() {
    GL.createCapabilities()
    
    glClearColor(210f/255f, 105f/255f, 30f/255f, 0.0f)

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
