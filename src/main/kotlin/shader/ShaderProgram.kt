package shader

import org.lwjgl.opengl.GL30.*
import toInt

class ShaderProgram(private val id: Int) {
    private val uniformLocations = mutableMapOf<String, Int>()

    fun setUniform(name: String, value: Int) {
        glUniform1i(getUniformLocation(name), value)
    }

    fun setUniform(name: String, value: Float) {
        glUniform1f(getUniformLocation(name), value)
    }

    fun setUniform(name: String, value: Boolean) {
        glUniform1i(getUniformLocation(name), value.toInt())
    }

    private fun getUniformLocation(name: String) = uniformLocations.getOrPut(name) {
        glGetUniformLocation(id, "ourColor").also {
            check(it != -1) { "it couldn't find location of uniform" }
        }
    }

    fun attach() {
        glUseProgram(id)
    }

    fun delete() {
        glDeleteProgram(id)
    }
}
