/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shaderutil;

/**
 *
 * @author Roger
 */
public class VertexAttribute {

    private int index;
    private int valsPerVertex;
    private String name;
    private long bufferOffset;
    private int stride;

    public VertexAttribute(int position, int valsPerVertex, String name) {
    	this(position, valsPerVertex, name, 0, 0);
    }
    
    public VertexAttribute(int position, int valsPerVertex, String name, long bufferOffset) {
    	this(position, valsPerVertex, name, 0, bufferOffset);
    }
    
    public VertexAttribute(int index, int valsPerVertex, String name, int stride, long bufferOffset) {
        this.index = index;
        this.valsPerVertex = valsPerVertex;
        this.name = name;
        this.stride = stride;
        this.bufferOffset = bufferOffset;

    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public int getValsPerVertex() {
        return this.valsPerVertex;
    }
    
    public int getStride() {
        return this.stride;
    }
    
    public long getBufferOffset() {
        return this.bufferOffset;
    }
}