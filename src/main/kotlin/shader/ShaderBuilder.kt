package shader

import org.lwjgl.opengl.GL30.*
import readFileText

class ShaderBuilder {
    private val shaders = mutableMapOf<Int, Int>()
    private var shaderProgram: ShaderProgram? = null

    var isCompiled = false
        private set

    fun addShader(sourcePath: String, type: Int) {
        if(isCompiled) return

        val source = readFileText(sourcePath)!!
        val shader = glCreateShader(type)
        glShaderSource(shader, source)
        glCompileShader(shader)

        check(glGetShaderi(shader, GL_COMPILE_STATUS) != GL_FALSE) {
            "Couldn't compile shader\n" + glGetShaderInfoLog(shader, 500)
        }

        shaders[type] = shader
    }

    fun removeShader(type: Int) {
        if(isCompiled) return

        shaders[type]?.let { glDeleteShader(it) }
        shaders.remove(type)
    }

    fun compile(): ShaderProgram {
        if (isCompiled) return shaderProgram!!

        val program = glCreateProgram()
        shaders.values.forEach {
            glAttachShader(program, it)
        }
        glLinkProgram(program)
        check(glGetProgrami(program, GL_LINK_STATUS) != GL_FALSE) {
            "Couldn't compile shader\n" + glGetShaderInfoLog(program, 500)
        }

        deleteShaders()
        isCompiled = true
        shaderProgram = ShaderProgram(program)

        return shaderProgram!!
    }

    private fun deleteShaders() {
        shaders.values.forEach {
            glDeleteShader(it)
        }
        shaders.clear()
    }
}

fun shaderBuilder(init: ShaderBuilder.() -> Unit): ShaderProgram {
    val builder = ShaderBuilder()
    builder.init()
    return builder.compile()
}
