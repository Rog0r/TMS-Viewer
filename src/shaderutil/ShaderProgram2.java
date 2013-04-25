package shaderutil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import opengl_util.OpenGL;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jan-Niklas Bartholomäus
 */
public class ShaderProgram2 {

    private int program_id;
    List<UniformData> uniform_data;

    public ShaderProgram2(String vertex_shader_path, String fragment_shader_path, String geometry_shader_path) {
        if (vertex_shader_path == null || fragment_shader_path == null) {
            System.err.println("ShaderProgram Error: VertexShader and FragmentShader must be specified!");
        }

        program_id = OpenGL.glCreateProgram();
        uniform_data = new LinkedList<>();

        createShaders(vertex_shader_path, fragment_shader_path, geometry_shader_path);

    }

    private void createShaders(String vertex_shader_path, String fragment_shader_path, String geometry_shader_path) {
        int vertex_shader_id = createVertexShader(vertex_shader_path);
    }

    private int createVertexShader(String vertex_shader_path) {
        int vertex_shader_id = OpenGL.glCreateShader(OpenGL.GL_SHADER_TYPE.VERTEX_SHADER);
        String shader_code = readShaderSource(vertex_shader_path);
        parseAttributes(shader_code);
        parseUniforms(shader_code);
        OpenGL.glAttachShader(program_id, vertex_shader_id);
        return vertex_shader_id;
    }

    private String readShaderSource(String path_to_code) {
        StringBuilder code;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path_to_code));
            code = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                code.append(line);
                code.append("\n");
            }
        } catch (Exception e) {
            System.out.println("Fail reading shading code");
            throw new RuntimeException(e.getMessage());
        }
        return code.toString();
    }

    private void parseAttributes(String shader_code) {
        Pattern attributes = Pattern.compile("^\\W*in\\s*\\w*\\s*(\\w*)[*\\W*\\w*\\W*]*;\\W*$", Pattern.MULTILINE);
        Matcher m = attributes.matcher(shader_code);

        int index = 0;

        while (m.find()) {
            OpenGL.glBindAttribLocation(program_id, index++, m.group(1));
            System.out.println("Found VertexAttribute " + m.group(1) + " and will bind it to index " + (index-1));
        }
    }

    private void parseUniforms(String shader_code) {
        Pattern attributes = Pattern.compile("^\\W*uniform\\s*(\\w*)\\s*(\\w*)[*\\W*\\w*\\W*]*;\\W*$", Pattern.MULTILINE);
        Matcher m = attributes.matcher(shader_code);

        while (m.find()) {
            uniform_data.add(new UniformData(m.group(2), m.group(1)));
            System.out.println("Found Uniform " + m.group(2) + " of type " + m.group(1));
            for (UniformData ud : uniform_data) {
                System.out.println(ud);
            }
        }
    }

    private void parseShaderAttribute(int i, String type, String name) {
    }
}
