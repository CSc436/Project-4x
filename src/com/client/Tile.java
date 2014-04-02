package com.client;

import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.array.Uint16Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;

/* Tile
 * A tile is a single 1x1 unit that makes up the world grid.  A tile contains a
 * position (int x, int y), a land type, and 4 corner height values for creating
 * geographical formations such as hills and mountains.  The official position of
 * a tile is the top left corner.
 */
public class Tile {
	
	public Coordinate position;
	public LandType landtype;
	// Corner heights coming later!
	
	private float[] verts;
	private int[] triangles;
	private float[] colors;
	
	float size;
	float depth;
	
	private WebGLRenderingContext glContext;
	private WebGLBuffer vertexBuffer;
	private WebGLBuffer triangleBuffer;
	private WebGLBuffer texCoordBuffer;
	private WebGLBuffer colorBuffer;
	
	private int vertexPositionAttribute, vertexTexCoordAttrib;
	private WebGLUniformLocation texUniform, matrixUniform, camPosUniform;
	
	public Tile (int x, int y, LandType l, WebGLRenderingContext gl) {
		position = new Coordinate(x,y);
		landtype = l;
		glContext = gl;
		size = 1.0f;
		depth = 0.0f;
		
		// Tile vertices are declared in this order:
		/* 1-----2
		 * |\   /|
		 * | \ / |
		 * |  3  |
		 * | / \ |
		 * |/   \|
		 * 4-----5
		 */
		verts = new float[] {
				(float) (position.x), (float)(position.y), depth,
				(float) (position.x + size), (float)(position.y), depth,
				//(float) (position.x + size/2), (float)(position.y - size/2), depth,
				(float) (position.x), (float)(position.y - size), depth,
				(float) (position.x + size), (float)(position.y - size), depth
		};
		
		// ZERO BASED INDEXING AAAHHHHHHH!!!!
		triangles = new int[] {
				0, 2, 1,
				0, 3, 2,
				3, 4, 2,
				4, 1, 2
		};
		
		this.initBuffers();
	}
	
	private void initBuffers(){
		vertexBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		float[] vertices = verts;
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(vertices),
				WebGLRenderingContext.STATIC_DRAW);

		texCoordBuffer = glContext.createBuffer();
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, texCoordBuffer);
		float[] colors = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
				0.0f, 1.0f };
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(colors), WebGLRenderingContext.STATIC_DRAW);
		
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, null);
	}
	
	private int init() {
		
		// Bind and build the vertexBuffer //
		vertexBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(verts),
				WebGLRenderingContext.STATIC_DRAW);
		
		// Bind and build the triangleBuffer //
		// 1. Create the triangle definition buffer.
		// 2. "Bind" this buffer, making it the focus of the GPU
		// 3. Set the bufferData of the currently focused buffer
		triangleBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, triangleBuffer);
		glContext.bufferData(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER,
				Uint16Array.create(triangles),
				WebGLRenderingContext.STATIC_DRAW);
		
		// Bind and build the colorBuffer //
		// This might prove useful for certain special effects (see simple_outline.fs)
		// but otherwise tiles will likely use textures exclusively.
		// Sets corners to RED and center to WHITE
		colors = new float[] {
				1.0f, 0.0f, 0.0f, 1.0f,
				1.0f, 0.0f, 0.0f, 1.0f,
				1.0f, 1.0f, 1.0f, 1.0f,
				1.0f, 0.0f, 0.0f, 1.0f,
				1.0f, 0.0f, 0.0f, 1.0f
				};
		colorBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, colorBuffer);
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(colors), WebGLRenderingContext.STATIC_DRAW);
				
		return 0;
	}
	
	public void render2(WebGLProgram shaderProgram,  int texLoc, float[] cameraMatrix, float camX, float camY, float camZ){
		vertexPositionAttribute = glContext.getAttribLocation(shaderProgram,
				"vertexPosition");
		vertexTexCoordAttrib = glContext.getAttribLocation(shaderProgram,
				"vertexTexCoord");
		
		texUniform = glContext.getUniformLocation(shaderProgram, "texture");
		matrixUniform = glContext.getUniformLocation(shaderProgram, "perspectiveMatrix");
		camPosUniform = glContext.getUniformLocation(shaderProgram, "camPos");
		
		glContext.useProgram(shaderProgram);
		
		glContext.enableVertexAttribArray(vertexPositionAttribute);
		glContext.enableVertexAttribArray(vertexTexCoordAttrib);

		// vertices
		glContext.uniformMatrix4fv(matrixUniform, false, cameraMatrix);
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		glContext.vertexAttribPointer(vertexPositionAttribute, 3,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// texture coordinates
		glContext
				.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, texCoordBuffer);
		glContext.vertexAttribPointer(vertexTexCoordAttrib, 2,
				WebGLRenderingContext.FLOAT, false, 0, 0);

		// uniforms
		glContext.uniform3f(camPosUniform, camX, camY, -camZ);

	   // Point the uniform sampler to texture unit 0
        glContext.uniform1i(texUniform, texLoc);
		
		// draw geometry
		glContext.drawArrays(WebGLRenderingContext.TRIANGLE_STRIP, 0, 4);
		
		// unbind/disable things
		//glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, null);
		/*glContext.disableVertexAttribArray(vertexPositionAttribute);
		glContext.disableVertexAttribArray(vertexTexCoordAttrib);*/
		
		//glContext.useProgram(null);
	}
	
	/**
	 * Utilizes a custom Shader object to render the tile.
	 */
	public void render(WebGLProgram shaderProgram, float[] cameraMatrix, float[] camPos) {
		// Don't clear the scene!
		//glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);

		// Enable the vertexPosition attribute in the vertex shader
		int vertexPositionAttribute = glContext.getAttribLocation(shaderProgram, "vertexPosition");		
		glContext.enableVertexAttribArray(vertexPositionAttribute);
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		glContext.vertexAttribPointer(vertexPositionAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);
	
		// Enable the vertexColor attribute in the vertex shader
		int vertexColorAttribute = glContext.getAttribLocation(shaderProgram, "vertexColor");
		glContext.enableVertexAttribArray(vertexColorAttribute);
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, colorBuffer);
		glContext.vertexAttribPointer(vertexColorAttribute, 4, WebGLRenderingContext.FLOAT, false, 0, 0);
	
		// Activate the shaderProgram for this object
		glContext.useProgram(shaderProgram);
		
		// create perspective matrix
		WebGLUniformLocation uniformLocation = glContext.getUniformLocation(
				shaderProgram, "perspectiveMatrix");

		// triangles
		glContext.uniformMatrix4fv(uniformLocation, false, cameraMatrix);
		
		glContext.uniform3f(glContext.getUniformLocation(shaderProgram, "camPos"), camPos[0], camPos[1], camPos[2]);
		
		// draw geometry
		glContext.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, triangleBuffer);
		glContext.drawElements(WebGLRenderingContext.TRIANGLES, 12,WebGLRenderingContext.UNSIGNED_SHORT, 0);
		
		 //Disable attribute Arrays
		glContext.disableVertexAttribArray(vertexPositionAttribute);
		glContext.disableVertexAttribArray(vertexColorAttribute);
		
		//glContext.flush();
	}
}
