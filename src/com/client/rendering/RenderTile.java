package com.client.rendering;

import com.client.GameCanvas;
import com.client.gameinterface.Console;
import com.googlecode.gwtgl.array.Float32Array;
import com.shared.model.Terrain;
import com.shared.model.gameboard.Resource;
import com.shared.model.gameboard.GameBoard;
import com.shared.utils.Coordinate;
import com.shared.utils.DiamondSquare;

// FINDBUG - added suppress warning for unused GWT imports.
@SuppressWarnings("unused")
public class RenderTile {
	private static int NW = 0b1, NE = 0b10, SW = 0b100, SE = 0b1000;
	
	private Coordinate position; // x y coord of top left
	private int ne, nw, se, sw, iter; // flags used for auto-tiling
	private float size; // length of the tile
	private float depth; // z coordinate
	private Resource resource;
	
	public RenderTile(float x, float y, float size, float depth, Resource resource){
		position = new Coordinate(x, y);
		this.size = size;
		this.depth = depth;
		this.resource = resource;
		
		ne = 0;
		nw = 0;
		se = 0;
		sw = 0;
		
		iter = -1;
	}
	
	public void setResource(Resource resource){
		this.resource = resource;
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
	 * makes a flat version of renderTiles, used to see if all of the 
	 * terrain adjustment is actually translating to server, and if so maybe 
	 * where tiling is going wrong. 
	 * 
	 * @param gameBoard	gameboard to use
	 * @param dimension	dimension of gameboard
	 * @return renderTiles for gameCanvas
	 */
	public static RenderTile[][] makeFlatMap(GameBoard gameBoard, int dimension)
	{
		// create the map.
		RenderTile[][] map = new RenderTile[dimension][dimension];
		for (int x = 0; x < dimension; x++)
		{
			for (int y = 0; y < dimension; y++)
			{
				map[x][y] = new RenderTile(x, y, 1.0f, 1.0f, Resource.NONE);
				map[x][y].setFlags(0, 0, 0, 0, gameBoard.getTileAt(x, y).getTerrainType().getValue());
			}
		}
		return map;
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
				map[x][y] = new RenderTile(x, y, 1.0f, 0.0f, Resource.NONE);
			}
		
		int n, s, e, w, val;
		
		// Auto tiling algorithm
		for (Terrain t: Terrain.values()){
			if (/*t == Terrain.FOREST ||*/ t == Terrain.WATER)
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
	
						// cardinal directions - Need to do only with in boundaries, this is causing issues along edges!
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
	 * @param vertexBuffer		vertex buffer to write to
	 * @param texCoordBuffer	tex coord buffer to write to
	 */
	public void addToBuffer(int index, Float32Array vertexBuffer, Float32Array texCoordBuffer, Float32Array  selectColorBuffer){
		
		float startx = (iter < 0 ? 15 : flagVals()) / 16.0f, starty = (iter < 0 ? 0 : iter) / 16.0f;
		float delta = 32.0f / 512.0f;
		
		if (resource != Resource.NONE){
			starty = 4.0f / 16.0f;
			startx = resource.ordinal() / 16.0f;
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
				position.fx(), position.fy(), depth,
				position.fx() + size, position.fy(), depth,
				position.fx(), position.fy() + size, depth,
				
				// second triangle
				position.fx(), position.fy() + size, depth,
				position.fx() + size, position.fy() + size, depth,
				position.fx() + size, position.fy(), depth
		};
		vertexBuffer.set(verts, index*verts.length);
		
		float R = position.fx() / ((float)GameCanvas.GRID_WIDTH);
		float G = position.fy() / ((float)GameCanvas.GRID_WIDTH);
		// Console.log("R: " + R + ", G: " + G);
		
		float[] selectColor = new float[] {
				R, G,
				R, G,
				R, G,
				
				R, G,
				R, G,
				R, G
		};

		selectColorBuffer.set(selectColor, index*selectColor.length);
	}
	
	public static RenderTile[][] makeMap(GameBoard gameBoard, int dimension) {
		// generate the height map
		//float[][] heightmap = DiamondSquare.DSGen(dimension, 
			//	seed, 0.2f);
		
		Console.log("2nd print");
		Console.log(gameBoard.toString());
		
		int padding = 2; 
		
		// initialize the map
		RenderTile[][] map = new RenderTile[dimension + padding][dimension + padding];
		for (int x = 0; x < dimension + padding; x++)
			for (int y = 0; y < dimension + padding; y++){
				map[x][y] = new RenderTile(x, y, 1.0f, 0.0f, Resource.NONE);
			}
		
		int n, s, e, w, val;
		
		// Auto tiling algorithm
		for (Terrain t: Terrain.values()){
			if (/*t == Terrain.FOREST ||*/ t == Terrain.WATER)
				continue;
			
			val = t.getValue();
			
			for (int x = 0; x < dimension + padding; x++)
				for (int y = 0; y < dimension + padding; y++)
					if (gameBoard.getUntrimmedMap()[x+1][y+1].getTerrainType().getLower() >= t.getLower()){
						RenderTile curr = map[x][y];
						curr.setResource(gameBoard.getUntrimmedMap()[x+1][y+1].getResource());
						curr.setFlags(0, 0, 0, 0, val);
						
						e = (x + 1) % (dimension + padding);
						w = (x - 1 + (dimension + padding)) % (dimension + padding);
						n = (y - 1 + (dimension + padding)) % (dimension + padding);
						s = (y + 1) % (dimension + padding);
	
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
		
		
		return trimEdges(map, dimension);		
	}
	
	/**
	 * changes the x and y coordinates of the tile
	 * @param x new x coordinate
	 * @param y new y coordinate
	 */
	private void setCoordinate(int x, int y)
	{
		this.position = new Coordinate(x,y);
	}
	
	/**
	 * trim off excess edges from rendertiles
	 * @param map
	 * @param dimension
	 * @return
	 */
	private static RenderTile[][] trimEdges(RenderTile[][] map, int dimension)
	{
		RenderTile[][] nMap = new RenderTile[dimension][dimension];
		for (int i = 0; i < dimension; i++)
		{
			for (int j = 0; j < dimension; j++)
			{
				nMap[i][j] = map[i+1][j+1];
				nMap[i][j].setCoordinate(i, j);
			}
		}
		return nMap;
	}
}
