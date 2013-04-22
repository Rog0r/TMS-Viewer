/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.lwjgl.opengl.GL15;

/**
 *
 * @author Roger
 */
public abstract class BufferObject {

    private int id;
    private int bufferTarget;

    public BufferObject(int bufferTarget) {
        this.bufferTarget = bufferTarget;
        id = GL15.glGenBuffers();
    }

    public int getId() {
        return id;
    }

    public int getBufferTarget() {
        return bufferTarget;
    }

    public abstract void enable();

    public abstract void disable();

    public void delete() {
        GL15.glDeleteBuffers(id);
    }
}
