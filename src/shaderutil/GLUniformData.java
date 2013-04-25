package shaderutil;

import java.nio.*;
import opengl_util.OpenGL;

public final class GLUniformData {

    private String name;
    private int location;
    private Object data;
    private boolean transpose;
    private OpenGL.GL_UNIFORM_TYPE type;

    public GLUniformData(String name, String type) {
        this.type = OpenGL.GL_UNIFORM_TYPE.parseType(type);
        this.name = name;
        this.location = -1;
    }

    /**
     * int atom
     *
     * Number of objects is 1
     *
     */
    public GLUniformData(int program_id, String name, int val, boolean unsigned) {
        this.type = unsigned ? OpenGL.GL_UNIFORM_TYPE.UINT : OpenGL.GL_UNIFORM_TYPE.INT;
        this.name = name;
        this.location = -1;
        setData(val);
    }

    /**
     * float atom
     *
     * Number of objects is 1
     *
     */
    public GLUniformData(int program_id, String name, float val) {
        this.type = OpenGL.GL_UNIFORM_TYPE.FLOAT;
        this.name = name;
        this.location = -1;
        setData(val);
    }

    /**
     * Multiple IntBuffer Vector
     *
     * Number of objects is calculated by data.limit()/components
     *
     * @param components number of elements of one object, ie 4 for
     * GL_FLOAT_VEC4,
     */
    public GLUniformData(int program_id, String name, int components, boolean unsigned, IntBuffer data) {
        determineVectorType(components, true, unsigned);
        this.name = name;
        this.location = -1;
        setData(data);
    }

    /**
     * Multiple FloatBuffer Vector
     *
     * Number of objects is calculated by data.limit()/components
     *
     * @param components number of elements of one object, ie 4 for
     * GL_FLOAT_VEC4,
     */
    public GLUniformData(String name, int components, FloatBuffer data) {
        determineVectorType(components, false, false);
        this.name = name;
        this.location = -1;
        setData(data);
    }

    /**
     * Multiple FloatBuffer Matrix
     *
     * Number of objects is calculated by data.limit()/(rows*columns)
     *
     * @param rows the matrix rows
     * @param column the matrix column
     */
    public GLUniformData(String name, int rows, int columns, boolean transpose, FloatBuffer data) {
        determineMatrixType(rows, columns);
        setData(data);
        this.transpose = transpose;
    }

    private void setData(int data) {
        this.data = new Integer(data);
    }

    private void setData(float data) {
        this.data = new Float(data);
    }

    private void setData(IntBuffer data) {
        if (data.limit() % (type.getRows() * type.getColumns()) != 0 || data.limit() < (type.getRows() * type.getColumns())) {
            System.err.println("GLUniformData Error: wrong input data for uniform " + name);
        }
        this.data = data;
    }

    private void setData(FloatBuffer data) {
        if (data.limit() % (type.getRows() * type.getColumns()) != 0 || data.limit() < (type.getRows() * type.getColumns())) {
            System.err.println("GLUniformData Error: wrong input data for uniform " + name);
        }
        this.data = data;
    }

    public int intValue() {
        if (type != OpenGL.GL_UNIFORM_TYPE.FLOAT) {
            System.err.println("GLUniformData Error: uniform type does not match INT\n" + this);
        }
        return ((Integer) data).intValue();
    }

    public float floatValue() {
        if (type != OpenGL.GL_UNIFORM_TYPE.FLOAT) {
            System.err.println("GLUniformData Error: uniform type does not match FLOAT\n" + this);
        }
        return ((Float) data).floatValue();
    }

    public IntBuffer intBufferValue() {
        return (IntBuffer) data;
    }

    public FloatBuffer floatBufferValue() {
        return (FloatBuffer) data;
    }

    @Override
    public String toString() {
        return "GLUniformData[name " + name
                + ", location " + location
                + ", type " + type
                + ", data " + data
                + "]";
    }

    public String getName() {
        return name;
    }

    public int getLocation() {
        return location;
    }

    public OpenGL.GL_UNIFORM_TYPE getUniformType() {
        return type;
    }

    private void determineVectorType(int components, boolean integer, boolean unsigned) {
        if (integer) {
            if (unsigned) {
                switch (components) {
                    case 1:
                        this.type = OpenGL.GL_UNIFORM_TYPE.UINT;
                        break;
                    case 2:
                        this.type = OpenGL.GL_UNIFORM_TYPE.UVEC2;
                        break;
                    case 3:
                        this.type = OpenGL.GL_UNIFORM_TYPE.UVEC3;
                        break;
                    case 4:
                        this.type = OpenGL.GL_UNIFORM_TYPE.UVEC4;
                        break;
                    default:
                        System.err.println("GLUniformData Error: Unrecognized number of components: " + components);
                }
            } else {
                switch (components) {
                    case 1:
                        this.type = OpenGL.GL_UNIFORM_TYPE.INT;
                        break;
                    case 2:
                        this.type = OpenGL.GL_UNIFORM_TYPE.IVEC2;
                        break;
                    case 3:
                        this.type = OpenGL.GL_UNIFORM_TYPE.IVEC3;
                        break;
                    case 4:
                        this.type = OpenGL.GL_UNIFORM_TYPE.IVEC4;
                        break;
                    default:
                        System.err.println("GLUniformData Error: Unrecognized number of components: " + components);
                }
            }
        } else {
            switch (components) {
                case 1:
                    this.type = OpenGL.GL_UNIFORM_TYPE.FLOAT;
                    break;
                case 2:
                    this.type = OpenGL.GL_UNIFORM_TYPE.VEC2;
                    break;
                case 3:
                    this.type = OpenGL.GL_UNIFORM_TYPE.VEC3;
                    break;
                case 4:
                    this.type = OpenGL.GL_UNIFORM_TYPE.VEC4;
                    break;
                default:
                    System.err.println("GLUniformData Error: Unrecognized number of components: " + components);
            }
        }
    }

    private void determineMatrixType(int rows, int columns) {
        switch (rows) {
            case 2:
                switch (columns) {
                    case 2:
                        type = OpenGL.GL_UNIFORM_TYPE.MAT2;
                        break;
                    case 3:
                        type = OpenGL.GL_UNIFORM_TYPE.MAT2X3;
                        break;
                    case 4:
                        type = OpenGL.GL_UNIFORM_TYPE.MAT2X4;
                        break;
                    default:
                        System.err.println("GLUniformData Error: Unrecognized matrix dimension: " + rows + "x" + columns);
                }
                break;
            case 3:
                switch (columns) {
                    case 2:
                        type = OpenGL.GL_UNIFORM_TYPE.MAT3X2;
                        break;
                    case 3:
                        type = OpenGL.GL_UNIFORM_TYPE.MAT3;
                        break;
                    case 4:
                        type = OpenGL.GL_UNIFORM_TYPE.MAT3X4;
                        break;
                    default:
                        System.err.println("GLUniformData Error: Unrecognized matrix dimension: " + rows + "x" + columns);
                }
                break;
            case 4:
                switch (columns) {
                    case 2:
                        type = OpenGL.GL_UNIFORM_TYPE.MAT4X2;
                        break;
                    case 3:
                        type = OpenGL.GL_UNIFORM_TYPE.MAT4X3;
                        break;
                    case 4:
                        type = OpenGL.GL_UNIFORM_TYPE.MAT4;
                        break;
                    default:
                        System.err.println("GLUniformData Error: Unrecognized matrix dimension: " + rows + "x" + columns);
                }
                break;
        }
    }

    public boolean isTranspose() {
        return transpose;
    }
}
