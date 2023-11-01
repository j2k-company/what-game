package shader

import org.lwjgl.opengl.GL30.*


class ShaderProgram(private val shaderProgram: Int) {
    fun attach() {
        glUseProgram(shaderProgram)
    }

    fun delete() {
        glDeleteProgram(shaderProgram)
    }
}
