/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opengl_util;

/**
 *
 * @author Jan-Niklas Bartholomäus
 */
public class LineMode {
    
    private float line_width;
    private boolean antialiazed;
    
    public LineMode() {
        this.line_width = 1.0f;
        antialiazed = false;
    }

    public float getLine_width() {
        return line_width;
    }

    public void setLine_width(float line_width) {
        this.line_width = line_width;
    }

    public boolean isAntialiazed() {
        return antialiazed;
    }

    public void setAntialiazed(boolean antialiazed) {
        this.antialiazed = antialiazed;
    }
}
