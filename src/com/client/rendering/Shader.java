package com.client.rendering;

import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;

/**
 * A shader is a container for a compiled fragmentShader and vertexShader
 * which are attached to a shaderProgram.  The supplied WebGLRenderingContext
 * is used to compile the shaders and it should also point to the context of the
 * entire game renderer.
 */
public class Shader {

	private WebGLRenderingContext glContext;
	
	private WebGLShader fragmentShader;
	private WebGLShader vertexShader;
	
	public WebGLProgram shaderProgram;
	
	public Shader(WebGLRenderingContext gl, String vSource, String fSource) {
		glContext = gl;
		createVertexShader(vSource);
		createFragmentShader(fSource);
		createShaderProgram();
	}
	
	public int createFragmentShader(String source) {
		fragmentShader = buildShader(
				WebGLRenderingContext.FRAGMENT_SHADER, source);
		
		return 0;
	}
	
	public int createVertexShader(String source) {
		vertexShader = buildShader(
				WebGLRenderingContext.VERTEX_SHADER, source);
		
		return 0;
	}
	
	public int createShaderProgram() {
		shaderProgram = glContext.createProgram();
		glContext.attachShader(shaderProgram, vertexShader);
		glContext.attachShader(shaderProgram, fragmentShader);
		glContext.linkProgram(shaderProgram);
		
		if (!glContext.getProgramParameterb(shaderProgram,
				WebGLRenderingContext.LINK_STATUS)) {
			throw new RuntimeException("Could not initialise shaders");
		}
		
		 return 0;
	}
	
	/**
	 * Compiles the given source of text into a shader of either type fragment
	 * or type vertex (are there more?).
	 * @param type
	 * @param source
	 * @return Returns a complete WebGLShader of the specified type (fragment or vertex).
	 */
	private WebGLShader buildShader(int type, String source) {
		WebGLShader shader = glContext.createShader(type);

		glContext.shaderSource(shader, source);
		glContext.compileShader(shader);

		if (!glContext.getShaderParameterb(shader,
				WebGLRenderingContext.COMPILE_STATUS)) {
			throw new RuntimeException(glContext.getShaderInfoLog(shader));
		}

		return shader;
	}
}
