package shaderutil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class Shader {

    private int id;
    private int shaderType;

    public Shader(String shaderName, int shaderType) {
        this.shaderType = shaderType;
        this.id = compileShader(readShaderSource(shaderName));
    }

    private String readShaderSource(String filename) {
        StringBuilder code;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("shader/" + filename));
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

    private int compileShader(String shaderSource) {
        int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, shaderSource);
        GL20.glCompileShader(shader);

        checkShaderLogInfo(shader, shaderSource);

        return shader;
    }

    /**
     * Zum Ausgeben von Anmerkungen des Compilers
     */
    private void checkShaderLogInfo(int shader, String shaderName) {
        String compiled;
        IntBuffer iVal = BufferUtils.createIntBuffer(1);

        if (GL20.glGetShader(shader, GL20.GL_COMPILE_STATUS) == 0) {
            compiled = "false";
        } else {
            compiled = "true";
        }


        int length = GL20.glGetShader(shader, GL20.GL_INFO_LOG_LENGTH);
        if (length > 1) {
            System.out.println("[" + shaderName + "] got problems:");
            System.out.println("Is compiled: " + compiled);
            ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            GL20.glGetShaderInfoLog(shader, iVal, infoLog);
            GL20.glDeleteShader(id);
            byte[] infoBytes = new byte[length];
            infoLog.get(infoBytes);
            System.out.println(new String(infoBytes));
//            System.exit(1);

        }

    }

    public int getId() {
        return id;
    }
}
