package com.client;

import java.util.Arrays;

import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.shared.Terrain;

public class RenderTile {
	private static int NW = 0b1, NE = 0b10, SW = 0b100, SE = 0b1000;
	
	public Coordinate position;
	private int ne, nw, se, sw, iter;
	private float size;
	private float depth;
	
	public RenderTile(float x, float y, float size, float depth){
		position = new Coordinate(x, y);
		this.size = size;
		this.depth = depth;
		
		ne = 0;
		nw = 0;
		se = 0;
		sw = 0;
		
		iter = -1;
	}
	
	public void setIter(int iter){
		if (this.iter != iter){
			invert();
			this.iter = iter;
		}
	}
	
	public void getEastFrom(RenderTile other, int iter){
		setIter(iter);
		ne = other.nw;
		se = other.sw;
	}
	
	public void getWestFrom(RenderTile other, int iter){
		setIter(iter);
		nw = other.ne;
		sw = other.se;
	}
	
	public void getNorthFrom(RenderTile other, int iter){
		setIter(iter);
		nw = other.sw;
		ne = other.se;
	}
	
	public void getSouthFrom(RenderTile other, int iter){
		setIter(iter);
		sw = other.nw;
		se = other.ne;
	}
	
	public void setFlags(int nw, int ne, int sw, int se, int iter){
		this.nw = nw;
		this.ne = ne;
		this.sw = sw;
		this.se = se;
		this.iter = iter;
	}
	
	public void invert(){
		this.nw = 1 - nw;
		this.ne = 1 - ne;
		this.se = 1 - se;
		this.sw = 1 - sw;
	}
	
	public static RenderTile[][] makeMap(long seed, int dimension){
		float[][] heightmap = DiamondSquare.DSGen(dimension,
				seed, 0.2f);
		
		RenderTile[][] map = new RenderTile[dimension][dimension];
		for (int x = 0; x < dimension; x++)
			for (int y = 0; y < dimension; y++){
				map[x][y] = new RenderTile(x, y, 1.0f, 0.0f);
			}
		
		int n, s, e, w, val;
		
		for (Terrain t: Terrain.values()){
			if (t == Terrain.FOREST || t == Terrain.WATER)
				continue;
			
			val = t.getValue();
			
			for (int x = 0; x < dimension; x++)
				for (int y = 0; y < dimension; y++)
					if (heightmap[x][y] * 255 > t.getLower()){
						RenderTile curr = map[x][y];
						curr.setFlags(0, 0, 0, 0, val);
						
						e = (x + 1) % dimension;
						w = (x - 1 + dimension) % dimension;
						n = (y - 1 + dimension) % dimension;
						s = (y + 1) % dimension;
	
						// cardinal directions
						map[w][y].getEastFrom(curr, val);
						map[e][y].getWestFrom(curr, val);
						map[x][n].getSouthFrom(curr, val);
						map[x][s].getNorthFrom(curr, val);
						
						// corners
						map[w][n].getEastFrom(map[x][n], val);
						map[w][n].getSouthFrom(map[w][y], val);
						
						map[w][s].getEastFrom(map[x][s], val);
						map[w][s].getNorthFrom(map[w][y], val);
						
						map[e][n].getWestFrom(map[x][n], val);
						map[e][n].getSouthFrom(map[e][y], val);
						
						map[e][s].getWestFrom(map[x][s], val);
						map[e][s].getNorthFrom(map[e][y], val);
					}
		}
		return map;
	}
	
	public int flagVals(){
		return (nw * NW) | (sw * SW) | (ne * NE) | (se * SE);
	}
	
	public void addToBuffer(int index, WebGLRenderingContext gl, Float32Array vertexBuffer, Float32Array texCoordBuffer){
		
		float startx = (iter < 0 ? 15 : flagVals()) / 16.0f, starty = (iter < 0 ? 0 : iter) / 16.0f;
		float delta = 32.0f / 512.0f;
		
		float[] texCoords = new float[] { 
				startx, starty, 
				startx + delta, starty,
				startx, starty + delta,
				
				startx, starty + delta,
				startx + delta, starty + delta,
				startx + delta, starty
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
	
	public static void addTileToBuffer(float x, float y, float size, int index, Terrain land, WebGLRenderingContext gl, Float32Array vertexBuffer, Float32Array texCoordBuffer){
		Coordinate position = new Coordinate(x, y);

		float depth = 0.0f;

		float startx, starty;
		float delta = 0.25f;
		
		switch(land){
		case GRASS:		startx = 0.0f;
						starty = 0.0f;
						break;
		case SAND: 		startx = 0.0f;
						starty = delta;
						break;
		case WATER: 	startx = delta;
						starty = 0.0f;
						break;
		case MOUNTAIN:	startx = 2 * delta;
						starty = 0.0f;
						break;
		case SNOW:		startx = 2 * delta;
						starty = delta;
						break;
		default:		startx = 2*delta;
						starty = 2*delta;
						break;
		}
		
		float[] texCoords = new float[] { 
				startx, starty, 
				startx + delta, starty,
				startx, starty + delta,
				
				startx, starty + delta,
				startx + delta, starty + delta,
				startx + delta, starty
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
	
	public static void main(String[] args){
/*		int width = 32;
		RenderTile[][] map = makeMap(121891L, width);
		for (int x = 0; x < width; x++)
			System.out.printf("---------");
		System.out.printf("\n");
		for (int y = 0; y < width; y++){
			for (int x = 0; x < width; x++)
				System.out.printf("[%d %3s %d]", map[x][y].nw, "", map[x][y].ne);
			System.out.printf("\n");
			for (int x = 0 ; x < width; x++){
				System.out.printf("|   %s   |", map[x][y].iter < 0 ? "W" : map[x][y].iter == 0 ? "S" : map[x][y].iter == 1 ? "G" : map[x][y].iter == 2 ? "M" : "I");
			}
			System.out.printf("\n");
			for (int x = 0; x < width; x++)
				System.out.printf("[%d %3s %d]", map[x][y].sw,"", map[x][y].se);
			System.out.printf("\n");
			for (int x = 0; x < width; x++)
				System.out.printf("---------");
			System.out.printf("\n");
		}*/
	}
}
