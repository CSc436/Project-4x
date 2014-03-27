package com.client;

import com.client.matrixutils.FloatMatrix;
import com.client.utils.Vector3;

public class Camera {
	private float[] cameraMatrix;
	private float camX = GameCanvas.GRID_WIDTH/2 * -1, camY = GameCanvas.GRID_WIDTH * -1, camZ = 20.0f;
	private float yaw = 0.0f, pitch = 225.0f, roll = 0.0f;
	private static float DELTA_ROTATE = 0.5f;
	
	public Camera() {
		makeCameraMatrix();
	}
	
	public void makeCameraMatrix() {
		cameraMatrix = FloatMatrix.createCameraMatrix((float)Math.toRadians(yaw),
				(float)Math.toRadians(pitch), (float)Math.toRadians(roll), 45,
				(float)GameCanvas.WIDTH / (float)GameCanvas.HEIGHT, 0.1f, 1000000f)
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
		Vector3 temp = new Vector3((float)-Math.tan(Math.toRadians(yaw)) * getSign(yaw), getSign(yaw), 0);
		temp.normalize();
		camX += temp.x;
		camY += temp.y;
	}

	public void down(float delta) {
		// TODO Auto-generated method stub
		Vector3 temp = new Vector3((float)-Math.tan(Math.toRadians(yaw)) * getSign(yaw) , getSign(yaw), 0);
		temp.left();
		temp.left();
		temp.normalize();
		camX += temp.x;
		camY += temp.y;
	}

	public void left(float delta) {
		// TODO Auto-generated method stub
		Vector3 temp = new Vector3((float)-Math.tan(Math.toRadians(yaw)) * getSign(yaw), getSign(yaw), 0);
		temp.right(); // the map starts off backwards...
		temp.normalize();
		camX += temp.x;
		camY += temp.y;
		
	}

	public void right(float delta) {
		// TODO Auto-generated method stub
		Vector3 temp = new Vector3((float)-Math.tan(Math.toRadians(yaw)) * getSign(yaw), getSign(yaw), 0);
		temp.left();
		temp.normalize();
		camX += temp.x;
		camY += temp.y;
	}

	private float getSign(float yaw) {
		// TODO Auto-generated method stub
		yaw = (float) Math.cos(Math.toRadians(yaw));
		float toRet = yaw >= 0 ? 1 : -1;
		return toRet;
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
	
// This doesn't work	
//	public Vector3 getNormalLookAtVector() {
//		Vector3 vect = new Vector3(cameraMatrix[8], cameraMatrix[9], 0);
//		vect.normalize();
//		return vect;
//	}
}
