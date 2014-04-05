package com.client;

import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.shared.Terrain;

public class RenderTile {
	private static int NW = 0b1, NE = 0b10, SW = 0b100, SE = 0b1000;
	
	private Coordinate position; // x y coord of top left
	private int ne, nw, se, sw, iter; // flags used for auto-tiling
	private float size; // length of the tile
	private float depth; // z coordinate
	
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
	
	/* ========== Auto-tiling Helper Functions ========== */
	
	private void setIter(int iter){
		if (this.iter != iter){
			flipFlags();
			this.iter = iter;
		}
	}
	
	private void getEastFrom(RenderTile other, int iter){
		setIter(iter);
		ne = other.nw;
		se = other.sw;
	}
	
	private void getWestFrom(RenderTile other, int iter){
		setIter(iter);
		nw = other.ne;
		sw = other.se;
	}
	
	private void getNorthFrom(RenderTile other, int iter){
		setIter(iter);
		nw = other.sw;
		ne = other.se;
	}
	
	private void getSouthFrom(RenderTile other, int iter){
		setIter(iter);
		sw = other.nw;
		se = other.ne;
	}
	
	private void setFlags(int nw, int ne, int sw, int se, int iter){
		this.nw = nw;
		this.ne = ne;
		this.sw = sw;
		this.se = se;
		this.iter = iter;
	}
	
	private void flipFlags(){
		this.nw = 1;
		this.ne = 1;
		this.se = 1;
		this.sw = 1;
	}
	
	private int flagVals(){
		return (nw * NW) | (sw * SW) | (ne * NE) | (se * SE);
	}
	
	/**
	 * Creates a 2d array of auto-tiled map of dimension 
	 * @param seed			seed to use with DiamondSquare
	 * @param dimension		length of one size
	 * @return				the auto-tiled map
	 */
	public static RenderTile[][] makeMap(long seed, int dimension){
		// generate the height map
		float[][] heightmap = DiamondSquare.DSGen(dimension, 
				seed, 0.2f);
		
		// initialize the map
		RenderTile[][] map = new RenderTile[dimension][dimension];
		for (int x = 0; x < dimension; x++)
			for (int y = 0; y < dimension; y++){
				map[x][y] = new RenderTile(x, y, 1.0f, 0.0f);
			}
		
		int n, s, e, w, val;
		
		// Auto tiling algorithm
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

	
	/**
	 * Adds the current tile's data to the given buffers
	 * @param index				index of the buffers to write data to
	 * @param gl				gl context
	 * @param vertexBuffer		vertex buffer to write to
	 * @param texCoordBuffer	tex coord buffer to write to
	 */
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
}
