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
public class DepthTest {
    
        public static enum DEPTH_FUNCTION {

        NEVER(GL11.GL_NEVER),
        LESS(GL11.GL_LESS),
        EQUAL(GL11.GL_EQUAL),
        LEQUAL(GL11.GL_LEQUAL),
        GREATER(GL11.GL_GREATER),
        GEQUAL(GL11.GL_GEQUAL),
        ALWAYS(GL11.GL_ALWAYS);
        private final int value;

        private DEPTH_FUNCTION(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        
        private boolean enabled;
        private DEPTH_FUNCTION depth_function;
        
        public static final int GL_DEPTH_TEST = GL11.GL_DEPTH_TEST;
        
    }
}
