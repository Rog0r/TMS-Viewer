/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bufferutil;

import java.nio.IntBuffer;
import opengl_util.OpenGL;

/**
 *
 * @author Roger
 */
public class IndexBuffer extends BufferObject{

    public IndexBuffer(IntBuffer indices, OpenGL.GL_BUFFER_USAGE buffer_usage) {
        super(OpenGL.GL_BUFFER_TARGET.ELEMENT_ARRAY_BUFFER);
        OpenGL.glBindBuffer(getBufferTarget(), getId());
        OpenGL.glBufferData(getBufferTarget(), indices, buffer_usage);
        OpenGL.glBindBuffer(getBufferTarget(), 0);
    }
    
    public IndexBuffer(IntBuffer indices) {
        this(indices, OpenGL.GL_BUFFER_USAGE.STATIC_DRAW);
    }

    @Override
    public void enable() {
        OpenGL.glBindBuffer(getBufferTarget(), getId());
    }

    @Override
    public void disable() {
        OpenGL.glBindBuffer(getBufferTarget(), 0);
    }
}
