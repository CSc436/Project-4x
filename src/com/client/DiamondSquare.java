/*
 * Gabe Kishi - DiamondSquare
 * Procedurally generates terrain using a height map produced by
 * the Diamond Squares algorithm
 * 		http://en.wikipedia.org/wiki/Diamond-square_algorithm
 */

package com.client;

import java.util.Random;

public class DiamondSquare {
	
	private static Random rand = new Random();
	
	public static float[][] DSGen(int SIZE, long seed, float roughness){
		// size is the nearest power of 2 that fully contains SIZE plus 1
		int size = (1 << (int)Math.ceil(Math.log(SIZE)/Math.log(2))) + 1; 
		
		// seed random number generator
		rand.setSeed(seed);
		float[][] noise = new float[size][size];
		
		// initialize the corner values
		noise[0][0] = rand.nextFloat();
		noise[size-1][0] = rand.nextFloat();
		noise[0][size-1] = rand.nextFloat();
		noise[size-1][size-1] = rand.nextFloat();
		
		// begin Diamond Squares iteration
		dsIter(noise, size, roughness);
		float [] rng = range(noise);
		scale(noise, rng[0],  rng[1]);
		return noise;
	}
	
	private static void dsIter(float[][] noise, int size, float roughness){
		int sideLength = size - 1;
		int sizeMinus = size - 1;
		float rough = roughness, avg;
		
		while(sideLength > 1){
			int halfLen = sideLength/2;
			
			// squares
			for (int x = 0; x < size - 1; x += sideLength)
				for (int y = 0; y < size - 1; y += sideLength){
					avg = (noise[x][y] + noise[x + sideLength][y] + noise[x][y + sideLength] 
							+ noise[x + sideLength][y + sideLength]) / 4.0f;
					avg += 2 * rough * rand.nextFloat() - rough;
					noise[x + halfLen][y + halfLen] = avg;
				}
			
			float N, E, S, W;
			
			// diamonds
			for (int x = 0; x < size - 1; x+= halfLen)
				for (int y = (x + halfLen) % sideLength; y < size - 1; y += sideLength){
					W = noise[(x - halfLen+sizeMinus)%sizeMinus][y];
					E = noise[(x + halfLen) % sizeMinus][y];
					N = noise[x][(y - halfLen + sizeMinus)%sizeMinus];
					S = noise[x][(y + halfLen)%sizeMinus];
					
					avg = (N + E + S + W)/ 4.0f;
					avg += 2 * rough * rand.nextFloat() - rough;
					noise[x][y] = avg;
				    if(x == 0)  noise[size-1][y] = avg;
				    if(y == 0)  noise[x][size-1] = avg;
				}

			sideLength /= 2;
			rough *= .8;
		}
	}
	
	// generates noise for ice caps (this is kludgy)
	public static float[][] genCapNoise(int SIZE, long seed){
		float[][] noise = new float[SIZE][SIZE];
		rand.setSeed(seed);
		
		int MIN_HEIGHT = SIZE/8;
		int VARIANCE = MIN_HEIGHT/4;

		int[] temp = new int[SIZE];
		int[] topCap = new int[SIZE], bottomCap = new int[SIZE];
		
		for (int x = 0; x < SIZE; x++)
			temp[x] = rand.nextInt(VARIANCE);
	
		for (int x = 0; x < SIZE; x++)
			topCap[x] = temp[x]/2 + temp[(x+1) % SIZE]/4 
			+ temp[(x - 1 + SIZE)%SIZE]/4;
		for (int i = 0; i < 6; i++){
			for (int x = 0; x < SIZE; x++)
				temp[x] = topCap[x]/2 + topCap[(x+1) % SIZE]/4 
				+ topCap[(x - 1 + SIZE)%SIZE]/4;
			
			for (int x = 0; x < SIZE; x++)
				topCap[x] = temp[x]/2 + temp[(x+1) % SIZE]/4 
				+ temp[(x - 1 + SIZE)%SIZE]/4;
		}
		
		for (int x = 0; x < SIZE; x++)
			temp[x] = rand.nextInt(VARIANCE);
	
		for (int x = 0; x < SIZE; x++)
			bottomCap[x] = temp[x]/2 + temp[(x+1) % SIZE]/4 
			+ temp[(x - 1 + SIZE)%SIZE]/4;
		for (int i = 0; i < 6; i++){
			for (int x = 0; x < SIZE; x++)
				temp[x] = bottomCap[x]/2 + bottomCap[(x+1) % SIZE]/4 
				+ bottomCap[(x - 1 + SIZE)%SIZE]/4;
			
			for (int x = 0; x < SIZE; x++)
				bottomCap[x] = temp[x]/2 + temp[(x+1) % SIZE]/4 
				+ temp[(x - 1 + SIZE)%SIZE]/4;
		}
		
		for (int x = 0; x < SIZE; x++){
			for (int y = 0; y < topCap[x] + MIN_HEIGHT; y++)
				noise[x][y] = 1.0f;
			for (int y = (SIZE-1 - MIN_HEIGHT) - bottomCap[x]; y < SIZE; y++)
				noise[x][y] = 1.0f;
		}
		
		return noise;
		
	}
	
	// returns a point representing the range of values in the array (used in scaling)
	public static float[] range(float[][] arr){
		float max = 0f, min = 0f;
		for (int x = 0; x < arr.length; x++)
			for (int y = 0; y < arr[0].length; y++){
				if (x == 0 && y == 0){
					min = arr[x][y]; 
					max = min;
				}
				else{
					if (min > arr[x][y])
						min = arr[x][y];
					if (max < arr[x][y])
						max = arr[x][y];
				}
			}
		return new float[] {min, max};
	}
	
	// scales array such that the minimum value goes to 0.0, and the max to 1.0
	public static void scale(float[][] result, float min, float max){
		int width = result.length;
		int height = result[0].length;
		float nmax = max-min;
		
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++){
				result[x][y] -= min;
				result[x][y] /= nmax;
			}
	}
}
