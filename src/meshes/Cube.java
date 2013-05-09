/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package meshes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import mesh_util.Geometry;
import org.lwjgl.BufferUtils;

/**
 *
 * @author Jan-Niklas Bartholomäus
 */
public class Cube extends Geometry {

    public Cube() {
        init();
    }

    private void init() {
        FloatBuffer vertices = BufferUtils.createFloatBuffer(8);
        IntBuffer indices = BufferUtils.createIntBuffer(10);

        // Left/Right (L/R), Bottom/Top (B/T),  Back/FRONT (B/F) Left-Handed-Coordinate-System

        // LBF 0
        vertices.put(-0.5f);
        vertices.put(-0.5f);
        vertices.put(-0.5f);

        // LTF 1
        vertices.put(-0.5f);
        vertices.put(0.5f);
        vertices.put(-0.5f);

        // RTF 2
        vertices.put(0.5f);
        vertices.put(0.5f);
        vertices.put(-0.5f);

        // RBF 3
        vertices.put(0.5f);
        vertices.put(-0.5f);
        vertices.put(-0.5f);
        
        // LBB 4
        vertices.put(-0.5f);
        vertices.put(-0.5f);
        vertices.put(0.5f);

        // LTB 5
        vertices.put(-0.5f);
        vertices.put(0.5f);
        vertices.put(0.5f);

        // RTB 6
        vertices.put(0.5f);
        vertices.put(0.5f);
        vertices.put(0.5f);

        // RBB 7
        vertices.put(0.5f);
        vertices.put(-0.5f);
        vertices.put(0.5f);
       
        vertices.rewind();
        
        indices.put(0); // LBF
        indices.put(3); // RBF
        indices.put(1); // LTF
        indices.put(2); // RTF
        indices.put(5); // LTB
        indices.put(6); // RTB
        indices.put(4); // LBB
        indices.put(7); // RBB
        indices.put(0); // LBF
        indices.put(3); // RBF
//        indices.put(op)
                
    }

    @Override
    public void draw() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
