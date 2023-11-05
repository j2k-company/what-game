import glfw.Window
import org.lwjgl.Version
import org.lwjgl.glfw.GLFW.glfwGetTime
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.GL30.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL30.GL_VERTEX_SHADER
import shader.ShaderProgram
import shader.shaderBuilder
import kotlin.math.sin
import kotlin.properties.Delegates


private var window by Delegates.notNull<Window>()
private val vertices = floatArrayOf(
    0.5f,  0.5f, 0.0f,  // top right
    0.5f, -0.5f, 0.0f,  // bottom right
   -0.5f, -0.5f, 0.0f,  // bottom left
   -0.5f,  0.5f, 0.0f   // top left
)
private val indices = intArrayOf(
    0, 1, 3,   // first triangle
    1, 2, 3    // second triangle
)

//private val vertices = floatArrayOf(
//    // positions        // colors
//    0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // bottom right
//    -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // bottom left
//    0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f    // top
//)
//private val indices = intArrayOf(
//    0, 1, 2,   // first triangle
//)

private var shader by Delegates.notNull<ShaderProgram>()
private var shape by Delegates.notNull<Model>()

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

    shape = Model(vertices, indices)
//    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE)

    loop()

    shape.delete()
    shader.delete()
    window.destroy()
    glfw.terminate()
}

private fun loop() {
    while (!window.isShouldClose) {
        window.clear()
        shader.attach()

        val time = glfwGetTime()
        val red = (sin(time).toFloat() / 2.0f) + 0.5f
        // TODO: should i get the uniform location at every time?
        shader.setUniform("ourColor", red)
        shape.draw()

        window.update()
    }
}
