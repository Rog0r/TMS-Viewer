/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opengl_util;

/**
 *
 * @author Jan-Niklas Bartholomäus
 */
public class PolygonMode {
    
    public static enum RASTERIZATION {
        POINT,
        LINE,
        FILL;
    }
    
    public static enum POLYGONFACE {
        FRONT,
        BACK,
        FRONTANDBACK;
    }
    
    private final POLYGONFACE face;
    private RASTERIZATION rasterization;
    
    public PolygonMode(POLYGONFACE face, RASTERIZATION rasterization) {
        this.face = face;
        this.rasterization = rasterization;
    }

    public POLYGONFACE getFace() {
        return face;
    }

    public RASTERIZATION getRasterization() {
        return rasterization;
    }

    public void setRasterization(RASTERIZATION rasterization) {
        this.rasterization = rasterization;
    }
    
    
}
