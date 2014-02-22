package com.client;

import java.util.Arrays;

import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;

public class RenderTile {
	public Coordinate position;
	private int index;
	private float size;
	private float depth;
	
	private LandType land;
	
	private WebGLRenderingContext gl;
	private Float32Array vertexBuffer, texCoordBuffer;
	
	public static void addTileToBuffer(float x, float y, float size, int index, LandType land, WebGLRenderingContext gl, Float32Array vertexBuffer, Float32Array texCoordBuffer){
		Coordinate position = new Coordinate(x, y);

		float depth = 0.0f;

		float startx, starty;
		
		switch(land){
		case Grass:	startx = 0.01f;
					starty = 0.01f;
					break;
		case Beach: startx = 0.01f;
					starty = 0.51f;
					break;
		case Shore: startx = 0.51f;
					starty = 0.01f;
					break;
		default:	startx = 0.51f;
					starty = 0.51f;
		}
		
		float[] texCoords = new float[] { 
				startx, starty, 
				startx + 0.48f, starty,
				startx, starty + 0.48f,
				
				startx, starty + 0.48f,
				startx + 0.48f, starty + 0.48f,
				startx + 0.48f, starty
		};
		
		texCoordBuffer.set(texCoords, index*texCoords.length);
		
		float[] verts = new float[] {
				// first triangle
				position.x, position.y, depth,
				position.x + size, position.y, depth,
				position.x, position.y + size, depth,
				
				// second triangle
				position.x, position.y + size, depth,
				position.x + size, position.y + size, depth,
				position.x + size, position.y, depth
		};
		vertexBuffer.set(verts, index*verts.length);
	}
}
