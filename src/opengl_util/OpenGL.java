/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opengl_util;

import shaderutil.VertexAttribute;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import shaderutil.GLUniformData;

/**
 *
 * @author Roger
 */
public final class OpenGL {

    public static enum GL_ERROR_FLAG {

        GL_NO_ERROR,
        GL_INVALID_ENUM,
        GL_INVALID_VALUE,
        GL_INVALID_OPERATION,
        GL_INVALID_FRAMEBUFFER_OPERATION,
        GL_OUT_OF_MEMORY;
    }

    public static enum GL_BUFFER_TARGET {

        ARRAY_BUFFER(GL15.GL_ARRAY_BUFFER),
        COPY_READ_BUFFER(GL31.GL_COPY_READ_BUFFER),
        COPY_WRITE_BUFFER(GL31.GL_COPY_WRITE_BUFFER),
        ELEMENT_ARRAY_BUFFER(GL15.GL_ARRAY_BUFFER),
        PIXEL_PACK_BUFFER(GL21.GL_PIXEL_PACK_BUFFER),
        PIXEL_UNPACK_BUFFER(GL21.GL_PIXEL_UNPACK_BUFFER),
        TEXTURE_BUFFER(GL31.GL_TEXTURE_BUFFER),
        TRANSFORM_FEEDBACK_BUFFER(GL30.GL_TRANSFORM_FEEDBACK_BUFFER),
        UNIFORM_BUFFER(GL31.GL_UNIFORM_BUFFER);
        private int target;

        GL_BUFFER_TARGET(int target) {
            this.target = target;
        }

        public int getTarget() {
            return target;
        }
    }

    public static enum GL_BUFFER_USAGE {

        STREAM_DRAW(GL15.GL_STREAM_DRAW), STREAM_READ(GL15.GL_STREAM_READ), STREAM_COPY(GL15.GL_STREAM_COPY),
        STATIC_DRAW(GL15.GL_STATIC_DRAW), STATIC_READ(GL15.GL_STATIC_READ), STATIC_COPY(GL15.GL_STATIC_COPY),
        DYNAMIC_DRAW(GL15.GL_DYNAMIC_DRAW), DYNAMIC_READ(GL15.GL_DYNAMIC_READ), DYNAMIC_COPY(GL15.GL_DYNAMIC_COPY);
        private int usage;

        GL_BUFFER_USAGE(int usage) {
            this.usage = usage;
        }

        public int getUsage() {
            return usage;
        }
    }

    public static enum GL_SHADER_TYPE {

        VERTEX_SHADER(GL20.GL_VERTEX_SHADER),
        FRAGMENT_SHADER(GL20.GL_FRAGMENT_SHADER),
        GEOMETRY_SHADER(GL32.GL_GEOMETRY_SHADER);
        private int shader_type;

        GL_SHADER_TYPE(int shader_type) {
            this.shader_type = shader_type;
        }

        public int getShaderType() {
            return shader_type;
        }
    }

    public static enum GL_UNIFORM_TYPE {

        FLOAT(1, 1), INT(1, 1), UINT(1, 1),
        VEC2(1, 2), IVEC2(1, 2), UVEC2(1, 2),
        VEC3(1, 3), IVEC3(1, 3), UVEC3(1, 3),
        VEC4(1, 4), IVEC4(1, 4), UVEC4(1, 4),
        MAT2(2, 2), MAT3(3, 3), MAT4(4, 4),
        MAT2X2(2, 2), MAT2X3(2, 3), MAT2X4(2, 4),
        MAT3X2(3, 2), MAT3X3(3, 3), MAT3X4(3, 4),
        MAT4X2(4, 2), MAT4X3(4, 3), MAT4X4(4, 4);
        private int rows, columns;

        private GL_UNIFORM_TYPE(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
        }

        public int getRows() {
            return rows;
        }

        public int getColumns() {
            return columns;
        }

        public static GL_UNIFORM_TYPE parseType(String type_name) {
            for (GL_UNIFORM_TYPE type : GL_UNIFORM_TYPE.values()) {
                if (type_name.toLowerCase().equals(type.name().toLowerCase())) {
                    return type;
                }
            }
            return null;
        }
    }
    private static final OpenGL INSTANCE = new OpenGL();
    private OpenGLState current_state;
    private static boolean debug;

    private OpenGL() {
        try {
            Display.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(OpenGL.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.debug = false;
        this.setState(new OpenGLState());
    }

    public static OpenGL getInstance() {
        return INSTANCE;
    }

    public void setState(OpenGLState state) {
        if (current_state == null || current_state.getClearColor() != state.getClearColor()) {
            GL11.glClearColor(state.getClearColor().x, state.getClearColor().y, state.getClearColor().z, state.getClearColor().w);
        }
        if (current_state == null || current_state.getCullFace() != state.getCullFace()) {
            GL11.glCullFace(state.getCullFace().getMode());
        }
        if (current_state == null || current_state.getDepthTest() != state.getDepthTest()) {
            if (state.getDepthTest() == OpenGLState.GL_DEPTH_TEST.ON) {
                GL11.glEnable(OpenGLState.GL_DEPTH_TEST.getKey());
            } else {
                GL11.glDisable(OpenGLState.GL_DEPTH_TEST.getKey());
            }
        }

        current_state = state;
    }

    public static void setDebug(boolean debug_value) {
        debug = debug_value;
    }

    public static GL_ERROR_FLAG glGetError() {
        switch (GL11.glGetError()) {
            case GL11.GL_NO_ERROR:
                return GL_ERROR_FLAG.GL_NO_ERROR;
            case GL11.GL_INVALID_ENUM:
                return GL_ERROR_FLAG.GL_INVALID_ENUM;
            case GL11.GL_INVALID_VALUE:
                return GL_ERROR_FLAG.GL_INVALID_VALUE;
            case GL11.GL_INVALID_OPERATION:
                return GL_ERROR_FLAG.GL_INVALID_OPERATION;
            case GL30.GL_INVALID_FRAMEBUFFER_OPERATION:
                return GL_ERROR_FLAG.GL_INVALID_FRAMEBUFFER_OPERATION;
            case GL11.GL_OUT_OF_MEMORY:
                return GL_ERROR_FLAG.GL_OUT_OF_MEMORY;
            default:
                return null;
        }
    }

    public static int glGenBuffers() {
        if (debug) {
            int tmp = GL15.glGenBuffers();
            printError("generating a Buffer");
            return tmp;
        } else {
            return GL15.glGenBuffers();
        }
    }

    public static void glDeleteBuffers(int id) {
        GL15.glDeleteBuffers(id);
        if (debug) {
            printError("deleting Buffer " + id + "..");
        }
    }

    public static int glGenVertexArrays() {
        if (debug) {
            int tmp = GL30.glGenVertexArrays();
            printError("generating an Vertex Array..");
            return tmp;
        } else {
            return GL30.glGenVertexArrays();
        }
    }

    public static void glBindBuffer(GL_BUFFER_TARGET buffer_target, int buffer_id) {
        GL15.glBindBuffer(buffer_target.getTarget(), buffer_id);
        if (debug) {
            printError("binding Buffer " + buffer_id + "to target " + buffer_target.name() + "..");
        }
    }

    public static void glBufferData(GL_BUFFER_TARGET buffer_target, FloatBuffer vertices, GL_BUFFER_USAGE buffer_usage) {
        GL15.glBufferData(buffer_target.getTarget(), vertices, buffer_usage.getUsage());
        if (debug) {
            printError("writing to Buffertarget " + buffer_target + "..");
        }
    }

    public static void glBindVertexArray(int array_id) {
        GL30.glBindVertexArray(array_id);
        if (debug) {
            printError("binding Vertexarray " + array_id + "..");
        }
    }

    public static void glVertexAttribPointer(VertexAttribute shader_attribute) {
        GL20.glVertexAttribPointer(shader_attribute.getIndex(), shader_attribute.getValsPerVertex(), GL11.GL_FLOAT, false, shader_attribute.getStride(),
                shader_attribute.getBufferOffset());
        if (debug) {
            printError("defining Vertex Attribute Data");
        }
    }

    public static void glEnableVertexAttribArray(int index) {
        glEnableVertexAttribArray(index);
        if (debug) {
            printError("enabling Vertexattributearray on index" + index + "..");
        }
    }

    public static void glDeleteVertexArrays(int id) {
        GL30.glDeleteVertexArrays(id);
        if (debug) {
            printError("deleting Vertexattributearray with id" + id + "..");
        }
    }

    public static int glCreateProgram() {
        if (debug) {
            int tmp = GL20.glCreateProgram();
            printError("creating ShaderProgram");
            return tmp;
        } else {
            return GL20.glCreateProgram();
        }
    }

    public static void glAttachShader(int program_id, int shader_id) {
        GL20.glAttachShader(program_id, shader_id);
        if (debug) {
            printError("attaching shader with id " + shader_id + " to program with id " + program_id);
        }
    }

    public static void glLinkProgram(int program_id) {
        GL20.glLinkProgram(program_id);
        if (debug) {
            printError("linking program with id " + program_id);
        }
    }

    public static void glDeleteShader(int shader_id) {
        GL20.glDeleteShader(shader_id);
        if (debug) {
            printError("deleting shader with id " + shader_id);
        }
    }

    public static void glBindAttribLocation(int program_id, int attribute_index, String attribute_name) {
        GL20.glBindAttribLocation(program_id, attribute_index, attribute_name);
        if (debug) {
            printError("binding attribute + " + attribute_name + " to index " + attribute_index);
        }
    }

    public static void glUseProgram(int program_id) {
        GL20.glUseProgram(program_id);
        if (debug) {
            printError("using program with id " + program_id);
        }
    }

    public static void glUniform(GLUniformData uniform) {
        switch (uniform.getUniformType()) {
            case FLOAT:
                GL20.glUniform1(uniform.getLocation(), uniform.floatBufferValue());
                break;
            case INT:
                GL20.glUniform1(uniform.getLocation(), uniform.intBufferValue());
                break;
            case UINT:
                GL30.glUniform1u(uniform.getLocation(), uniform.intBufferValue());
                break;
            case VEC2:
                GL20.glUniform2(uniform.getLocation(), uniform.floatBufferValue());
                break;
            case IVEC2:
                GL20.glUniform2(uniform.getLocation(), uniform.intBufferValue());
                break;
            case UVEC2:
                GL30.glUniform2u(uniform.getLocation(), uniform.intBufferValue());
                break;
            case VEC3:
                GL20.glUniform3(uniform.getLocation(), uniform.floatBufferValue());
                break;
            case IVEC3:
                GL20.glUniform3(uniform.getLocation(), uniform.intBufferValue());
                break;
            case UVEC3:
                GL30.glUniform3u(uniform.getLocation(), uniform.intBufferValue());
                break;
            case VEC4:
                GL20.glUniform4(uniform.getLocation(), uniform.floatBufferValue());
                break;
            case IVEC4:
                GL20.glUniform4(uniform.getLocation(), uniform.intBufferValue());
                break;
            case UVEC4:
                GL30.glUniform4u(uniform.getLocation(), uniform.intBufferValue());
                break;
            case MAT2:
            case MAT2X2:
                GL20.glUniformMatrix2(uniform.getLocation(), uniform.isTranspose(), uniform.floatBufferValue());
                break;
            case MAT2X3:
                GL21.glUniformMatrix2x3(uniform.getLocation(), uniform.isTranspose(), uniform.floatBufferValue());
                break;
            case MAT2X4:
                GL21.glUniformMatrix2x4(uniform.getLocation(), uniform.isTranspose(), uniform.floatBufferValue());
                break;
            case MAT3:
            case MAT3X3:
                GL20.glUniformMatrix3(uniform.getLocation(), uniform.isTranspose(), uniform.floatBufferValue());
                break;
            case MAT3X2:
                GL21.glUniformMatrix3x2(uniform.getLocation(), uniform.isTranspose(), uniform.floatBufferValue());
                break;
            case MAT3X4:
                GL21.glUniformMatrix3x4(uniform.getLocation(), uniform.isTranspose(), uniform.floatBufferValue());
                break;
            case MAT4:
            case MAT4X4:
                GL20.glUniformMatrix4(uniform.getLocation(), uniform.isTranspose(), uniform.floatBufferValue());
                break;
            case MAT4X2:
                GL21.glUniformMatrix4x2(uniform.getLocation(), uniform.isTranspose(), uniform.floatBufferValue());
                break;
            case MAT4X3:
                GL21.glUniformMatrix4x3(uniform.getLocation(), uniform.isTranspose(), uniform.floatBufferValue());
                break;
        }
        if (debug) {
            printError("writing uniform data " + uniform.getName());
        }
    }

    public static int glGetUniformLocation(int program_id, String name) {
        if (debug) {
            int tmp = GL20.glGetUniformLocation(program_id, name);
            printError("retrieving uniform location of uniform " + name + " in program with id " + program_id);
            return tmp;
        } else {
            return GL20.glGetUniformLocation(program_id, name);
        }
    }

    public static int glCreateShader(GL_SHADER_TYPE type) {
        if (debug) {
            int tmp = GL20.glCreateShader(type.getShaderType());
            printError("creating shader of type " + type.name());
            return tmp;
        } else {
            return GL20.glCreateShader(type.getShaderType());
        }
    }

    private static void printError(String description) {
        switch (glGetError()) {
            case GL_NO_ERROR:
                break;
            case GL_INVALID_ENUM:
                System.err.println("An invalid enum error occured on " + description);
            case GL_INVALID_VALUE:
                System.err.println("An invalid value error occured: " + description);
            case GL_INVALID_OPERATION:
                System.err.println("An invalid operation error occured: " + description);
            case GL_INVALID_FRAMEBUFFER_OPERATION:
                System.err.println("An invalid framebuffer operation error occured: " + description);
            case GL_OUT_OF_MEMORY:
                System.err.println("An out of memory error occured: " + description);
        }
    }
}
