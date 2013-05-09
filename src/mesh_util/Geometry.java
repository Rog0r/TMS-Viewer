package mesh_util;

import bufferutil.BufferObject;
import opengl_util.OpenGLState;

/**
 *
 * @author Roger
 */
public abstract class Geometry {

    private OpenGLState state;
    private BufferObject vertex_buffer;
    private BufferObject index_buffer;
    private int primitive_type;
    private int indice_count;

    protected Geometry() {
        state = new OpenGLState();
        this.primitive_type = -1;
        this.indice_count = -1;
    }

    public abstract void draw();

    public int getIndicesCount() {
        return indice_count;
    }

    public int getPrimitiveType() {
        return primitive_type;
    }

    public BufferObject getVertexObject() {
        return vertex_buffer;
    }

    public BufferObject getIndexObject() {
        return index_buffer;
    }

    public void setIndexObject(BufferObject indexObjet) {
        this.index_buffer = indexObjet;
    }

    public void setIndicesCount(int indicesCount) {
        this.indice_count = indicesCount;
    }

    public void setVertexObject(BufferObject vertexObject) {
        this.vertex_buffer = vertexObject;
    }

    public void delete() {
        this.vertex_buffer.delete();
        this.index_buffer.delete();
    }

    public OpenGLState getState() {
        return state;
    }
}
