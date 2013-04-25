/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bufferutil;

import bufferutil.BufferObject;
import shaderutil.VertexAttribute;
import java.nio.FloatBuffer;
import java.util.List;
import opengl_util.OpenGL;


/**
 *
 * @author Roger
 */
public class VertexBuffer extends BufferObject {

    private int vertex_array_id;

    public VertexBuffer(FloatBuffer vertices, List<VertexAttribute> attributes, OpenGL.GL_BUFFER_USAGE buffer_usage) {
        super(OpenGL.GL_BUFFER_TARGET.ARRAY_BUFFER);
        this.vertex_array_id = OpenGL.glGenVertexArrays();
        OpenGL.glBindBuffer(getBufferTarget(), getId());
        OpenGL.glBufferData(getBufferTarget(), vertices, buffer_usage);
        OpenGL.glBindBuffer(getBufferTarget(), 0);
        writeVertexArrayObject(attributes);
    }
    
    public VertexBuffer(FloatBuffer vertices, List<VertexAttribute> attributes) {
        this(vertices, attributes, OpenGL.GL_BUFFER_USAGE.STATIC_DRAW);
    }

    @Override
    public void enable() {
        OpenGL.glBindVertexArray(vertex_array_id);
    }

    private void writeVertexArrayObject(List<VertexAttribute> attributes) {
        OpenGL.glBindVertexArray(vertex_array_id);
        OpenGL.glBindBuffer(getBufferTarget(), getId());
        for (VertexAttribute attrib : attributes) {
            OpenGL.glVertexAttribPointer(attrib);
            OpenGL.glEnableVertexAttribArray(attrib.getIndex());
        }
        OpenGL.glBindVertexArray(0);
    }

    @Override
    public void disable() {
        OpenGL.glBindVertexArray(0);
    }

    @Override
    public void delete() {
        super.delete();
        OpenGL.glDeleteVertexArrays(vertex_array_id);
    }
}
