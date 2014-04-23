package com.client.rendering;

import com.client.GameCanvas;
import com.shared.utils.FloatMatrix;
import com.shared.utils.Vector3;

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

	public float up(float delta) {
		// TODO Auto-generated method stub
		Vector3 temp = new Vector3((float)-Math.tan(Math.toRadians(yaw)) * getSign(yaw), getSign(yaw), 10/camZ);
		temp.normalize();
		camX += temp.x * delta;
		camY += temp.y * delta;
		return Vector3.dist(temp.x, temp.y);
	}

	public float down(float delta) {
		// TODO Auto-generated method stub
		Vector3 temp = new Vector3((float)-Math.tan(Math.toRadians(yaw)) * getSign(yaw) , getSign(yaw), 10/camZ);
		temp.left();
		temp.left();
		temp.normalize();
		camX += temp.x * delta;
		camY += temp.y * delta;
		return Vector3.dist(temp.x, temp.y);
	}

	public void left(float delta) {
		// TODO Auto-generated method stub
		Vector3 temp = new Vector3((float)-Math.tan(Math.toRadians(yaw)) * getSign(yaw), getSign(yaw), 10/camZ);
		temp.right(); // the map starts off backwards...
		temp.normalize();
		camX += temp.x * delta;
		camY += temp.y * delta;
		
	}

	public void right(float delta) {
		// TODO Auto-generated method stub
		Vector3 temp = new Vector3((float)-Math.tan(Math.toRadians(yaw)) * getSign(yaw), getSign(yaw), 10/camZ);
		temp.left();
		temp.normalize();
		camX += temp.x * delta;
		camY += temp.y * delta;
	}

	private float getSign(float yaw) {
		// TODO Auto-generated method stub
		yaw = (float) Math.cos(Math.toRadians(yaw));
		float toRet = yaw >= 0 ? 1 : -1;
		return toRet;
	}

	public void zoomIn() {
		// TODO Auto-generated method stub
		float delta = this.up(1.0f);
		camZ -= delta;
	}

	public void zoomOut() {
		// TODO Auto-generated method stub
		float delta = this.down(1.0f);
		camZ += delta;
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

	public void move(Vector3 moveVector) {
		// TODO Auto-generated method stub 
		moveVector.normalize();
		this.down(moveVector.y);
		this.right(moveVector.x);
	}
	
// This doesn't work	
//	public Vector3 getNormalLookAtVector() {
//		Vector3 vect = new Vector3(cameraMatrix[8], cameraMatrix[9], 0);
//		vect.normalize();
//		return vect;
//	}
}
