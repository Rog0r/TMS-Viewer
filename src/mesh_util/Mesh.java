/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mesh_util;

import java.util.LinkedList;
import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import opengl_util.OpenGL;

/**
 *
 * @author Roger
 */
public class Mesh {
    
    private Matrix4f model_world_matrix;
    private List<Geometry> geometry;
    protected OpenGL openGL;
    
    public Mesh() {
        model_world_matrix = new Matrix4f();
        Matrix4f.setIdentity(model_world_matrix);
        
        geometry = new LinkedList<>();
        
        openGL = OpenGL.getInstance();
    }

    protected void addGeometry(Geometry geometry) {
        this.geometry.add(geometry);
    }
    
    public void translate(Vector3f translation) {
        Matrix4f.translate(translation, model_world_matrix, model_world_matrix);
    }
    
    public void scale_uniform(Float s) {
        Matrix4f.scale(new Vector3f(s,s,s), model_world_matrix, model_world_matrix);
    }
    
    public void rotate(Float phi, Vector3f axis) {
        Matrix4f.rotate(phi, axis, model_world_matrix, model_world_matrix);
    }
    
    public void draw() {
        for (Geometry g : geometry) {
            openGL.setState(g.getState());
            g.draw();
        }
    }
    
}
