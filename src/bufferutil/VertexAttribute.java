/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bufferutil;

import opengl_util.OpenGL;

/**
 *
 * @author Roger
 */
public class VertexAttribute {

    private int index;
    private int size;
    private OpenGL.GL_VERTEXATTRIBUTE_TYPE type;
    private boolean normalized;
    private long buffer_offset;
    private int stride;

    public VertexAttribute(int index, int size, OpenGL.GL_VERTEXATTRIBUTE_TYPE type, boolean normalized, int stride, long buffer_offset) {
        this.index = index;
        this.size = size;
        this.type = type;
        this.normalized = normalized;
        this.stride = stride;
        this.buffer_offset = buffer_offset;

    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return this.size;
    }

    public OpenGL.GL_VERTEXATTRIBUTE_TYPE getType() {
        return type;
    }

    public boolean isNormalized() {
        return normalized;
    }

    public int getStride() {
        return this.stride;
    }

    public long getBufferOffset() {
        return this.buffer_offset;
    }
}
