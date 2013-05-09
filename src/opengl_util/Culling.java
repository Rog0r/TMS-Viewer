/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opengl_util;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author Jan-Niklas Bartholomäus
 */
public class Culling {

    public static enum CULLFACE {

        FRONT(GL11.GL_FRONT), BACK(GL11.GL_BACK), FRONT_AND_BACK(GL11.GL_FRONT_AND_BACK);
        private final int VALUE;

        CULLFACE(int mode) {
            this.VALUE = mode;
        }

        public int getValue() {
            return this.VALUE;
        }
    }

    public static enum WINDING_ORDER {

        CLOCKWISE(GL11.GL_CW),
        COUNTERCLOCKWISE(GL11.GL_CCW);
        private final int VALUE;

        private WINDING_ORDER(int value) {
            this.VALUE = value;
        }

        public int getValue() {
            return VALUE;
        }
    }
    private CULLFACE cull_face;
    private boolean enabled;
    private WINDING_ORDER winding_order;
    public static final int GL_CULL_FACE = GL11.GL_CULL_FACE;

    public Culling() {
        this.cull_face = CULLFACE.BACK;
        this.enabled = true;
        this.winding_order = WINDING_ORDER.COUNTERCLOCKWISE;
    }

    public CULLFACE getCullFace() {
        return cull_face;
    }

    public void setCullFace(CULLFACE cull_face) {
        this.cull_face = cull_face;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public WINDING_ORDER getWinding_Order() {
        return winding_order;
    }

    public void setWinding_order(WINDING_ORDER winding_order) {
        this.winding_order = winding_order;
    }
}
