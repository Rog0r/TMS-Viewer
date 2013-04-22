/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
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
}
