/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opengl_util;

/**
 *
 * @author Jan-Niklas Bartholomäus
 */
public class PrimitiveRestart {
    
    private boolean enabled;
    private int index;
    
    public PrimitiveRestart() {
        this.enabled = true;
        this.index = Integer.MIN_VALUE;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    
    
}
