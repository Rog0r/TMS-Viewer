package shaderutil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import opengl_util.OpenGL;

/**
 *
 * @author Jan-Niklas Bartholomäus
 */
public class ShaderProgram {

    private int program_id;
    List<UniformData> uniform_data;

    public ShaderProgram(String vertex_shader_path, String fragment_shader_path, String geometry_shader_path) {
        if (vertex_shader_path == null || fragment_shader_path == null) {
            System.err.println("ShaderProgram Error: VertexShader and FragmentShader must be specified!");
        }

        program_id = OpenGL.glCreateProgram();
        uniform_data = new LinkedList<>();
        System.out.println("ShaderProgram: Created new ShaderProgram with id " + program_id);

        createShaders(vertex_shader_path, fragment_shader_path, geometry_shader_path);

    }

    private void createShaders(String vertex_shader_path, String fragment_shader_path, String geometry_shader_path) {
        createVertexShader(vertex_shader_path);
        createFragmentShader(fragment_shader_path);
        if (geometry_shader_path != null) {
            createGeometryShader(geometry_shader_path);
        }
        linkProgram();

    }

    private void createVertexShader(String vertex_shader_path) {
        int vertex_shader_id = OpenGL.glCreateShader(OpenGL.GL_SHADER_TYPE.VERTEX_SHADER);
        System.out.println("ShaderProgram: Created new Vertex Shader with id " + vertex_shader_id);

        String shader_source = readShaderSource(vertex_shader_path);
        parseAttributes(shader_source);
        parseUniforms(shader_source);
        OpenGL.glShaderSource(vertex_shader_id, shader_source);

        OpenGL.glCompileShader(vertex_shader_id);
        checkShader(vertex_shader_id);
        OpenGL.glAttachShader(program_id, vertex_shader_id);
        OpenGL.glDeleteShader(vertex_shader_id);
    }

    private void createFragmentShader(String fragment_shader_path) {
        int fragment_shader_id = OpenGL.glCreateShader(OpenGL.GL_SHADER_TYPE.FRAGMENT_SHADER);
        System.out.println("ShaderProgram: Created new Fragment Shader with id " + fragment_shader_id);

        String shader_source = readShaderSource(fragment_shader_path);
        parseUniforms(shader_source);
        OpenGL.glShaderSource(fragment_shader_id, shader_source);

        OpenGL.glCompileShader(fragment_shader_id);
        checkShader(fragment_shader_id);
        OpenGL.glAttachShader(program_id, fragment_shader_id);
        OpenGL.glDeleteShader(fragment_shader_id);
    }

    private void createGeometryShader(String geometry_shader_path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void linkProgram() {
        OpenGL.glLinkProgram(program_id);

        for (UniformData ud : uniform_data) {
            ud.setLocation(program_id);
        }
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
        Pattern attributes = Pattern.compile("^\\W*in\\s*\\w*\\s*(\\w*)\\[*\\W*\\w*\\W*\\]*;\\W*$", Pattern.MULTILINE);
        Matcher m = attributes.matcher(shader_code);

        int index = 0;

        System.out.println("ShaderProgram: Parsed following Vertex Attributes:");
        while (m.find()) {
            OpenGL.glBindAttribLocation(program_id, index++, m.group(1));
            System.out.println("ShaderAttribute " + index + ": " + m.group(1));
        }
    }

    private void parseUniforms(String shader_code) {
        Pattern attributes = Pattern.compile("^\\W*uniform\\s*(\\w*)\\s*(\\w*)\\[*\\W*\\w*\\W*\\]*;\\W*$", Pattern.MULTILINE);
        Matcher m = attributes.matcher(shader_code);
        
        System.out.println("ShaderProgram: Parsed following Uniforms:");
        while (m.find()) {
            UniformData tmp = new UniformData(m.group(2), m.group(1));
            System.out.println(tmp.getUniformType() + " " + tmp.getName());
            if (!uniform_data.contains(tmp)) {
                uniform_data.add(tmp);
            }
        }
    }

    private void checkShader(int shader_id) {
        if (OpenGL.glGetShader(shader_id, OpenGL.GL_SHADER_PARAMETER.COMPILE_STATUS) == OpenGL.GL_BOOLEAN.TRUE.getValue()) {
            System.out.println("ShaderProgram: Shader with id " + shader_id + " compiled successfully");
        } else {
            System.err.println("ShaderProgram: Error compiling Shader with id " + shader_id + ". Error Log:\n" + OpenGL.glGetShaderInfoLog(shader_id, 4096));
//            System.out.println(OpenGL.glGetShaderInfoLog(shader_id, 4096));
        }
    }
}
