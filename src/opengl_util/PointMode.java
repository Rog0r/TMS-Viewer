/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opengl_util;

/**
 *
 * @author Jan-Niklas Bartholomäus
 */
public class PointMode {
 
    private float point_size;
    private boolean program_point_size;
    
    public PointMode() {
        this.point_size = 1.0f;
        this.program_point_size = false;
    }

    public float getPointSize() {
        return point_size;
    }

    public void setPointSize(float point_size) {
        this.point_size = point_size;
    }

    public boolean ProgramPointSizeEnabled() {
        return program_point_size;
    }

    public void setProgramPointSize(boolean program_point_size) {
        this.program_point_size = program_point_size;
    }
     
}
