package com.client;

import com.client.matrixutils.FloatMatrix;
import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.array.Int16Array;
import com.googlecode.gwtgl.array.Int32Array;
import com.googlecode.gwtgl.array.Uint16Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;

public class Mesh //implements Renderable {
{
	private WebGLBuffer vertexBuffer;
	private WebGLBuffer texcoordBuffer;
	private WebGLBuffer normalBuffer;
	private WebGLBuffer triangleBuffer;
	private WebGLBuffer colorBuffer;

	public float[] verts,texcoords,normals,colors;
	public int[] triangles;
	int triCount;
	
	public float posX, posY, posZ;
	public float rotX, rotY, rotZ;
	public float scaleX, scaleY, scaleZ;
	public float scale;
	
	private FloatMatrix modelMatrix;
	
	private WebGLUniformLocation texUniform, resolutionUniform, timeUniform,
	matrixUniform, camPosUniform;
	
	public Mesh(float[] vs, float[] ts, float[] ns, int tris, WebGLRenderingContext glContext) {
		
		posX = 0.0f;
		posY = 0.0f;
		posZ = 0.0f;
		rotX = 0.0f;
		rotY = 0.0f;
		rotZ = 0.0f;
		scaleX = 1.0f;
		scaleY = 1.0f;
		scaleZ = 1.0f;
		scale = 0.1f;
		
		updateModelMatrix();
		
		float[] temp = scale(vs,scale);
		System.out.println("**********VERTS*************");
		System.out.println(temp.length);
		printArray(temp);
		verts = temp;
		texcoords = scale(ts,1.0f);
		System.out.println("***********TEXCOORDS***********");
		System.out.println(texcoords.length);
		printArray(texcoords);
		normals = scale(ns,1.0f);
		System.out.println("*********NORMALS************");
		System.out.println(normals.length);
		printArray(normals);
		triCount = tris;
		int[] tempTris = new int[triCount*3];
		tempTris = buildTriangleArray();
		System.out.println("*********TRIS************");
		for(int i = 0; i < tempTris.length; i++) {
			if(i % 3 == 0)
				System.out.println();
			System.out.print(tempTris[i]+", ");
		}
		triangles = tempTris;
		System.out.println();
		System.out.println("Tricount: " + triCount);
		System.out.println("Initializing Mesh buffers...");
		
		initBuffers(glContext);
		
		System.out.println("Complete!");
	}
	
	// Prints a float array!
	private void printArray(float[] arr) {
		for(int i = 0; i < arr.length; i++) {
			if(i % 3 == 0)
				System.out.println();
			System.out.print(arr[i]+", ");
		}
		System.out.println();
	}
	
	private void updateModelMatrix() {
		//FloatMatrix rotMatrix = FloatMatrix.createRotationMatrix(rotX,rotY,rotZ);
		//modelMatrix = FloatMatrix.multiply(FloatMatrix.translationMatrix(posX,posY,posZ),FloatMatrix.flipXZMatrix());
		//modelMatrix = FloatMatrix.multiply(FloatMatrix.translationMatrix(posX,posY,posZ),rotMatrix);
		modelMatrix = FloatMatrix.translationMatrix(posX,posY,posZ);
	}
	
	private float[] scale(float[] arr, float percent) {
		for(int i = 0; i < arr.length; i++)
			arr[i] = arr[i] * percent;
		return arr;
	}
	
	/*
	 * This function constructs the array that enumerates the
	 * triangles to be rendered. Because of how the OBJImporter works, each
	 * triangle has its own set of vertices rather than sharing them between
	 * adjacent triangles (which would be more optimized but who wants to write
	 * an optimizer???). The function just holds [0,1,2,3,4,5,...,(3n-1)].
	 */
	private int[] buildTriangleArray() {
		int[] temp = new int[triCount * 3];
		for(int i = 0; i < triCount * 3; i++)
			temp[i] = i;
		return temp;
	}
	
	private int initBuffers(WebGLRenderingContext glContext) {
		
		// Bind and build the vertexBuffer //
		vertexBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(verts),
				WebGLRenderingContext.STATIC_DRAW);
		
		// Bind and build the texcoordBuffer //
		texcoordBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, texcoordBuffer);
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(texcoords),
				WebGLRenderingContext.STATIC_DRAW);
		
		// Bind and build the normalBuffer //
		normalBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, normalBuffer);
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(normals),
				WebGLRenderingContext.STATIC_DRAW);
		
		// Bind and build the triangleBuffer //
		// 1. Create the triangle definition buffer.
		// 2. "Bind" this buffer, making it the focus of the GPU
		// 3. Set the bufferData of the currently focused buffer
		triangleBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, triangleBuffer);
		glContext.bufferData(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER,
				Int16Array.create(triangles),
				WebGLRenderingContext.STATIC_DRAW);
		
		// Bind and build the colorBuffer //
		// This might prove useful for certain special effects (see simple_outline.fs)
		// but otherwise tiles will likely use textures exclusively.
		// Sets corners to RED and center to WHITE
//		colors = new float[] {
//				1.0f, 0.0f, 0.0f, 1.0f,
//				1.0f, 0.0f, 0.0f, 1.0f,
//				1.0f, 1.0f, 1.0f, 1.0f,
//				1.0f, 0.0f, 0.0f, 1.0f,
//				1.0f, 0.0f, 0.0f, 1.0f
//				};
//		colorBuffer = glContext.createBuffer();
//		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, colorBuffer);
//		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
//				Float32Array.create(colors), WebGLRenderingContext.STATIC_DRAW);
				
		return 0;
	}
	
	/**
	 * Utilizes a custom Shader object to render the Mesh
	 */
	public void render(WebGLRenderingContext glContext, Shader shader, Camera cam) {
		// Don't clear the scene!
		//glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);

		updateModelMatrix();
		
		// Activate the shaderProgram for this object
		glContext.useProgram(shader.shaderProgram);
				
		// Gotta make sure the buffers exist first
		//initBuffers(glContext);
		
		camPosUniform = glContext.getUniformLocation(shader.shaderProgram, "camPos");
		// uniforms
		glContext.uniform3f(camPosUniform, cam.getX(), cam.getY(), cam.getZ());
		
		// Enable the vertexPosition attribute in the vertex shader
		int vertexPositionAttribute = glContext.getAttribLocation(shader.shaderProgram, "vertexPosition");		
		glContext.enableVertexAttribArray(vertexPositionAttribute);
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		glContext.vertexAttribPointer(vertexPositionAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);

		// Enable the vertexTexCoord attribute in the vertex shader
		int texcoordAttribute = glContext.getAttribLocation(shader.shaderProgram, "vertexTexCoord");		
		glContext.enableVertexAttribArray(texcoordAttribute);
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, texcoordBuffer);
		glContext.vertexAttribPointer(texcoordAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);

		// Enable the vertexNormal attribute in the vertex shader
		int normalAttribute = glContext.getAttribLocation(shader.shaderProgram, "vertexNormal");
		glContext.enableVertexAttribArray(normalAttribute);
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, normalBuffer);
		glContext.vertexAttribPointer(normalAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);

		// Enable the vertexColor attribute in the vertex shader
//		int vertexColorAttribute = glContext.getAttribLocation(shader.shaderProgram, "vertexColor");
//		glContext.enableVertexAttribArray(vertexColorAttribute);
//		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, colorBuffer);
//		glContext.vertexAttribPointer(vertexColorAttribute, 4, WebGLRenderingContext.FLOAT, false, 0, 0);

		// perspective matrix
		WebGLUniformLocation uniformLocation = glContext.getUniformLocation(
				shader.shaderProgram, "perspectiveMatrix");
		glContext.uniformMatrix4fv(uniformLocation, false, cam.getCameraMatrix());
		
		// model matrix
		WebGLUniformLocation uniformModelMatrix = glContext.getUniformLocation(
				shader.shaderProgram, "modelMatrix");
		glContext.uniformMatrix4fv(uniformModelMatrix, false, modelMatrix.rowWiseData());
		
		// draw geometry
		glContext.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, triangleBuffer);
		glContext.drawElements(WebGLRenderingContext.TRIANGLES, triCount*3,WebGLRenderingContext.UNSIGNED_SHORT, 0);
		//glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, 36);

		 //Disable attribute Arrays
		glContext.disableVertexAttribArray(vertexPositionAttribute);
		glContext.disableVertexAttribArray(texcoordAttribute);
		glContext.disableVertexAttribArray(normalAttribute);
		
		//glContext.flush();
	}

}
