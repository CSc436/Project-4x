package com.client;

import com.client.matrixutils.FloatMatrix;

public class Camera {
	private float[] cameraMatrix;
	private float camX = GameCanvas.GRID_WIDTH/2 * -1, camY = GameCanvas.GRID_WIDTH * -1, camZ = 20.0f;
	
	public Camera() {
		makeCameraMatrix();
	}
	
	public void makeCameraMatrix() {
		// 4.71238898
		//.785398163
		cameraMatrix = FloatMatrix.createCameraMatrix(0.0f,
				3.14159f +  .785398163f, 0.0f, 45,
				GameCanvas.WIDTH / GameCanvas.HEIGHT, 0.1f, 1000000f)
				.columnWiseData();
	}
	
	public float[] getCameraMatrix() {
		return cameraMatrix;
	}
	
	public float getX() {
		return camX;
	}
	
	public float getY() {
		return camY;
	}
	
	public float getZ() {
		return camZ;
	}

	public void up(float delta) {
		// TODO Auto-generated method stub
		camY += delta;
	}

	public void down(float delta) {
		// TODO Auto-generated method stub
		camY -= delta;
	}

	public void left(float delta) {
		// TODO Auto-generated method stub
		camX += delta;
	}

	public void right(float delta) {
		// TODO Auto-generated method stub
		camX -= delta;
	}

	public void zoomIn() {
		// TODO Auto-generated method stub
		camZ -= 1.0f;
		camY += 1.0f;
	}

	public void zoomOut() {
		// TODO Auto-generated method stub
		camZ += 1.0f;
		camY -= 1.0f;
	}

	public void rotateLeft() {
		// TODO Auto-generated method stub
		
	}

	public void rotateRight() {
		// TODO Auto-generated method stub
		
	}

	public void defaultPosition() {
		// TODO Auto-generated method stub
		camX = GameCanvas.GRID_WIDTH/2 * -1;
		camY = GameCanvas.GRID_WIDTH * -1; 
		camZ = 20.0f;
	}
}
