package shaderutil;

import java.nio.*;
import java.util.Objects;
import opengl_util.OpenGL;

public final class UniformData {

    private String name;
    private int location;
    private Object data;
    private boolean transpose;
    private OpenGL.GL_UNIFORM_TYPE type;

    public UniformData(String name, String type) {
        this.type = OpenGL.GL_UNIFORM_TYPE.parseType(type);
        this.name = name;
        this.location = -1;
    }
    
    public void setLocation(int program_id) {
        location = OpenGL.glGetUniformLocation(program_id, name);
    }

    public void setData(int data) {
        if (type == OpenGL.GL_UNIFORM_TYPE.INT || type == OpenGL.GL_UNIFORM_TYPE.UINT) {
            this.data = new Integer(data);
        } else {
            System.err.println("UniformData Error: uniform of type " + type + " can't be assigned single int value");
        }
    }

    public void setData(float data) {
        if (type == OpenGL.GL_UNIFORM_TYPE.FLOAT) {
            this.data = new Float(data);
        } else {
            System.err.println("UniformData Error: uniform of type " + type + " can't be assigned single float value");
        }
    }

    public void setData(IntBuffer data) {
        switch (type) {
            case INT:
            case IVEC2:
            case IVEC3:
            case IVEC4:
            case UINT:
            case UVEC2:
            case UVEC3:
            case UVEC4:
                if (data.limit() % type.getColumns() != 0 || data.limit() < type.getColumns()) {
                    System.err.println("UniformData Error: wrong input data for uniform " + name);
                } else {
                    this.data = data;
                }
                break;
            default:
                System.err.println("UniformData Error: uniform of type " + type + " can't be assigned  int values");
        }
    }

    public void setData(FloatBuffer data) {
        switch (type) {
            case FLOAT:
            case VEC2:
            case VEC3:
            case VEC4:
            case MAT2:
            case MAT2X2:
            case MAT2X3:
            case MAT2X4:
            case MAT3:
            case MAT3X2:
            case MAT3X3:
            case MAT3X4:
            case MAT4:
            case MAT4X2:
            case MAT4X3:
            case MAT4X4:
                if (data.limit() % (type.getRows() * type.getColumns()) != 0 || data.limit() < (type.getRows() * type.getColumns())) {
                    System.err.println("UniformData Error: wrong input data for uniform " + name);
                } else {
                    this.data = data;
                }
                break;
            default:
                System.err.println("UniformData Error: uniform of type " + type + " can't be assigned float values");
        }
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
        return "UniformData[name " + name
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UniformData other = (UniformData) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
}
