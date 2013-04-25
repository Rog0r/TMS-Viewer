/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mesh_util;

import opengl_util.OpenGLState;

/**
 *
 * @author Roger
 */
public class Geometry {
    
    OpenGLState state;
    
    public Geometry() {
        state = new OpenGLState();
    }
    
    public void draw() {
        
    }

    public OpenGLState getState() {
        return state;
    }
        
}
