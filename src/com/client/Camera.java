package com.client;

import com.client.matrixutils.FloatMatrix;

public class Camera {
	private float[] cameraMatrix;
	private float camX = GameCanvas.GRID_WIDTH/2 * -1, camY = GameCanvas.GRID_WIDTH * -1, camZ = 20.0f;
	private float yaw = 0.0f, pitch = 225.0f, roll = 0.0f;
	private static float DELTA_ROTATE = 0.5f;
	
	public Camera() {
		makeCameraMatrix();
	}
	
	public void makeCameraMatrix() {
		// 4.71238898
		//.785398163
		cameraMatrix = FloatMatrix.createCameraMatrix((float)Math.toRadians(yaw),
				(float)Math.toRadians(pitch), (float)Math.toRadians(roll), 45,
				GameCanvas.WIDTH / GameCanvas.HEIGHT, 0.1f, 1000000f)
				.columnWiseData();
	}
	
	public float[] getCameraMatrix() {
		makeCameraMatrix(); // update the camera matrix
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
		camX -= delta * Math.sin(yaw * Math.PI / 180.0);
	    camX -= delta * Math.sin(roll * Math.PI / 180.0);
	    camY += delta * Math.cos(pitch * Math.PI / 180.0);
	    camY += delta * Math.cos(roll * Math.PI / 180.0);
	}

	public void down(float delta) {
		// TODO Auto-generated method stub
		camX += delta * Math.sin(yaw * Math.PI / 180.0);
	    camX += delta * Math.sin(roll * Math.PI / 180.0);
	    camY -= delta * Math.cos(pitch * Math.PI / 180.0);
	    camY -= delta * Math.cos(roll * Math.PI / 180.0);
	}

	public void left(float delta) {
		// TODO Auto-generated method stub
		camX -= delta * Math.cos(yaw * Math.PI / 180.0);
	    camX -= delta * Math.cos(roll * Math.PI / 180.0);
	    camY += delta * Math.sin(pitch * Math.PI / 180.0);
	    camY += delta * Math.sin(roll * Math.PI / 180.0);
	}

	public void right(float delta) {
		// TODO Auto-generated method stub
		camX -= delta * Math.cos(yaw * Math.PI / 180.0);
	    camX -= delta * Math.cos(roll * Math.PI / 180.0);
	    camY += delta * Math.sin(pitch * Math.PI / 180.0);
	    camY += delta * Math.sin(roll * Math.PI / 180.0);
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
		yaw -= DELTA_ROTATE;
	}

	public void rotateRight() {
		// TODO Auto-generated method stub
		yaw += DELTA_ROTATE;
	}

	public void defaultPosition() {
		// TODO Auto-generated method stub
		camX = GameCanvas.GRID_WIDTH/2 * -1;
		camY = GameCanvas.GRID_WIDTH * -1; 
		camZ = 20.0f;
	}
}
