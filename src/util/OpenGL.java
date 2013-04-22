/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.nio.FloatBuffer;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

/**
 *
 * @author Roger
 */
public final class OpenGL {

    public static enum GL_BUFFER_TARGET {

        ARRAY_BUFFER(GL15.GL_ARRAY_BUFFER), COPY_READ_BUFFER(GL31.GL_COPY_READ_BUFFER), COPY_WRITE_BUFFER(GL31.GL_COPY_WRITE_BUFFER),
        ELEMENT_ARRAY_BUFFER(GL15.GL_ARRAY_BUFFER), PIXEL_PACK_BUFFER(GL21.GL_PIXEL_PACK_BUFFER), PIXEL_UNPACK_BUFFER(GL21.GL_PIXEL_UNPACK_BUFFER),
        TEXTURE_BUFFER(GL31.GL_TEXTURE_BUFFER), TRANSFORM_FEEDBACK_BUFFER(GL30.GL_TRANSFORM_FEEDBACK_BUFFER), UNIFORM_BUFFER(GL31.GL_UNIFORM_BUFFER);
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
    
    
    private static final OpenGL INSTANCE = new OpenGL();
    private OpenGLState current_state;

    private OpenGL() {
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

    public static int glGenBuffers() {
        return GL15.glGenBuffers();
    }

    public static void glDeleteBuffers(int id) {
        GL15.glDeleteBuffers(id);
    }
    
    public static int glGenVertexArrays() {
        return GL30.glGenVertexArrays();
    }
    
    public static void glBindBuffer(GL_BUFFER_TARGET buffer_target, int buffer_id) {
        GL15.glBindBuffer(buffer_target.getTarget(), buffer_id);
    }
    
    public static void glBufferData(GL_BUFFER_TARGET buffer_target, FloatBuffer vertices, GL_BUFFER_USAGE buffer_usage) {
        GL15.glBufferData(buffer_target.getTarget(), vertices, buffer_usage.getUsage());
    }
    
    public static void glBindVertexArray(int array_id) {
        GL30.glBindVertexArray(array_id);
    }
    
    public static void glVertexAttribPointer(ShaderAttribute shader_attribute) {
        GL20.glVertexAttribPointer(shader_attribute.getIndex(), shader_attribute.getValsPerVertex(), GL11.GL_FLOAT, false, shader_attribute.getStride(),
                    shader_attribute.getBufferOffset());
    }
    
    public static void glEnableVertexAttribArray(int index) {
        glEnableVertexAttribArray(index);
    }
    
    public static void glDeleteVertexArrays(int id) {
        GL30.glDeleteVertexArrays(id);
    }
}
