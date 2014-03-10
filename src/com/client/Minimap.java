package com.client;

import com.client.matrixutils.FloatMatrix;

public class Minimap {
	
	private float[] cameraMatrix;
	private int WIDTH;
	private int HEIGHT;
	
	public static void renderMinimap() {
		
	}
	
	private void makeCameraMatrix() {
		cameraMatrix = FloatMatrix.createCameraMatrix(0.0f,
				3.14159f + .785398163f, 0.0f, 45,
				(float) WIDTH / (float) HEIGHT, 0.1f, 1000000f)
				.columnWiseData();
	}

}
