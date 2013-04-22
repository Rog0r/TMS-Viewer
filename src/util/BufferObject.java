/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;


/**
 *
 * @author Roger
 */
public abstract class BufferObject {

    private int id;
    private OpenGL.GL_BUFFER_TARGET bufferTarget;

    public BufferObject(OpenGL.GL_BUFFER_TARGET bufferTarget) {
        this.bufferTarget = bufferTarget;
        id = OpenGL.glGenBuffers();
    }

    public int getId() {
        return id;
    }

    public OpenGL.GL_BUFFER_TARGET getBufferTarget() {
        return bufferTarget;
    }

    public abstract void enable();

    public abstract void disable();

    public void delete() {
        OpenGL.glDeleteBuffers(id);
    }
}
