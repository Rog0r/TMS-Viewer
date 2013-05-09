/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opengl_util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

/**
 *
 * @author Roger
 */
public class OpenGLState {

    public static enum POLYGON_MODE {
        
    }
    
    public static enum GL_CULL_FACE {

        FRONT(GL11.GL_FRONT), BACK(GL11.GL_BACK), FRONT_AND_BACK(GL11.GL_FRONT_AND_BACK);
        private final int mode;

        GL_CULL_FACE(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return this.mode;
        }
    }

    public static enum GL_DEPTH_TEST {

        ON, OFF;

        public static int getKey() {
            return GL11.GL_DEPTH_TEST;
        }
    }

    public static enum GL_DEPTH_FUNC {

        NEVER(GL11.GL_NEVER),
        LESS(GL11.GL_LESS),
        EQUAL(GL11.GL_EQUAL),
        LEQUAL(GL11.GL_LEQUAL),
        GREATER(GL11.GL_GREATER),
        GEQUAL(GL11.GL_GEQUAL),
        ALWAYS(GL11.GL_ALWAYS);
        private int mode;

        private GL_DEPTH_FUNC(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }
    }

    public static enum GL_POLYGON_MODE {

        POINT(GL11.GL_POINT),
        LINE(GL11.GL_POINT),
        POLYGON(GL11.GL_POLYGON);
        private int mode;

        private GL_POLYGON_MODE(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return mode;
        }
    }
    private Vector4f clear_color;
    private GL_CULL_FACE cull_face;
    private GL_DEPTH_TEST depth_test;
    private GL_DEPTH_FUNC depth_func;
    private GL_POLYGON_MODE polygon_mode;

    public OpenGLState() {
        this.clear_color = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
        this.cull_face = GL_CULL_FACE.BACK;
        this.depth_test = GL_DEPTH_TEST.ON;
        this.depth_func = GL_DEPTH_FUNC.LESS;
        this.polygon_mode = GL_POLYGON_MODE.POLYGON;
    }

    public void setClear_color(Vector4f clear_color) {
        this.clear_color = clear_color;
    }

    public void setCullFace(GL_CULL_FACE cull_face) {
        this.cull_face = cull_face;
    }

    public void setDepthTest(GL_DEPTH_TEST depth_test) {
        this.depth_test = depth_test;
    }

    public void setDepth_func(GL_DEPTH_FUNC depth_func) {
        this.depth_func = depth_func;
    }

    public void setPolygon_mode(GL_POLYGON_MODE polygon_mode) {
        this.polygon_mode = polygon_mode;
    }

    public Vector4f getClearColor() {
        return clear_color;
    }

    public GL_CULL_FACE getCullFace() {
        return cull_face;
    }

    public GL_DEPTH_TEST getDepthTest() {
        return depth_test;
    }

    public GL_DEPTH_FUNC getDepth_func() {
        return depth_func;
    }

    public GL_POLYGON_MODE getPolygon_mode() {
        return polygon_mode;
    }
}
