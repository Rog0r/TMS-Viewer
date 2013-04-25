/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shaderutil;

import java.util.LinkedList;
import java.util.List;
import opengl_util.OpenGL;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Roger
 */
public class ShaderProgram {

    private Shader vertex_shader;
    private Shader fragment_shader;
    private int program_id;
    private boolean linked;
    private List<GLUniformData> dynamic_uniforms;
    private List<VertexAttribute> attributes;

    public ShaderProgram(String vertex_shader_path, String fragment_shader_path, List<VertexAttribute> attributes) {
        this.linked = false;
        this.attributes = attributes;
//      vertex_shader = new Shader(vertex_shader_path, OpenGL.GL_SHADER_TYPE.VERTEX_SHADER);
//        fragment_shader = new Shader(fragment_shader_path, OpenGL.GL_SHADER_TYPE.FRAGMENT_SHADER);
        program_id = OpenGL.glCreateProgram();
        OpenGL.glAttachShader(program_id, vertex_shader.getId());
        OpenGL.glAttachShader(program_id, fragment_shader.getId());
    }

    public void link() {
        OpenGL.glLinkProgram(program_id);
        OpenGL.glDeleteShader(vertex_shader.getId());
        OpenGL.glDeleteShader(fragment_shader.getId());

        for (VertexAttribute attrib : attributes) {
            OpenGL.glBindAttribLocation(program_id, attrib.getIndex(), attrib.getName());
        }

        linked = true;
    }

    public void use() {
        if (linked) {
            OpenGL.glUseProgram(program_id);
        } else {
            System.out.println("Error: ShaderProgram not yet linked!");
        }

        if (dynamic_uniforms != null) {
            for (GLUniformData uniform : dynamic_uniforms) {
                OpenGL.glUniform(uniform);
            }
        }
    }

    public void unUse() {
        GL20.glUseProgram(0);
    }

    public void addDynamicUniform(GLUniformData uniform) {
//        uniform.setLocation(GL20.glGetUniformLocation(program_id, uniform.getName()));
        if (dynamic_uniforms == null) {
            dynamic_uniforms = new LinkedList<GLUniformData>();
        }
        dynamic_uniforms.add(uniform);
        if (GL20.glGetUniformLocation(program_id, uniform.getName()) == -1) {
            System.err.println("Dynamic: "
                    + uniform.getName() + " id: "
                    + GL20.glGetUniformLocation(program_id, uniform.getName()));
        }
    }

    public void delete() {
        if (linked) {
            linked = false;
            GL20.glDeleteProgram(program_id);
        }
    }

    public int getProgramId() {
        return program_id;
    }
}
