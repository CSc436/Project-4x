package com.shared.utils;

public class FloatMatrix {
	private float [][] data;
	private final int ROWS, COLS;
	
	private static class InvalidMatrixIndexException extends RuntimeException{}
	private static class InvalidMatrixDimensionsException extends RuntimeException{}
	
	public FloatMatrix(int rows, int cols){
		ROWS = rows;
		COLS = cols;
		data = new float[ROWS][COLS];
	}
	
	public FloatMatrix(float [][] data){
		ROWS = data.length;
		COLS = data[0].length;
		this.data = data;
	}
	
	public void setData(float[][] data){
		if (data.length != ROWS && data[0].length != COLS)
			throw new InvalidMatrixDimensionsException();
		else
			this.data = data;
	}
	
	public boolean valid(int r, int c){
		return (r >= 0 && r < ROWS && c >= 0 && c < COLS);
	}
	
	public void setValueAt(int r, int c, float val){
		if (valid(r, c))
			data[r][c] = val;
		else
			throw new InvalidMatrixIndexException();
	}
	
	public float get(int r, int c){
		if (valid(r, c))
			return data[r][c];
		else
			throw new InvalidMatrixIndexException();
	}
	
	public static FloatMatrix multiply (FloatMatrix a, FloatMatrix b){	
		FloatMatrix result = null;
		if (a.COLS != b.ROWS)
			throw new InvalidMatrixDimensionsException();
		else{
			result = new FloatMatrix(a.ROWS, b.COLS);
			
			for (int r = 0; r < a.ROWS; r++)
				for (int c = 0; c < b.COLS; c++)
					for (int i = 0; i < a.COLS; i++)
						result.data[r][c] += a.data[r][i] * b.data[i][c];
		}
		return result;
	}
	
	public static FloatMatrix flipXZMatrix(){
		float [][] data = {
				{0.0f,	0.0f,	1.0f,	0.0f},
				{0.0f,	1.0f,	0.0f,	0.0f},
				{1.0f,	0.0f,	0.0f,	0.0f},
				{0.0f,	0.0f,	0.0f,	1.0f}
		};
		return new FloatMatrix(data);
	}
	
	/**
	 * Creates a transformation matrix
	 * <p>
	 * Creates a {@link FloatMatrix} representing a series of
	 * transformations: Roll -> Pitch -> Yaw -> Translation
	 * <p>
	 * 
	 * @param roll	angle of roll in radians
	 * @param pitch	angle of pitch in radians
	 * @param yaw	angle of yaw in radians
	 * @param x		x component of translation
	 * @param y		y component of translation
	 * @param z		z component of translation
	 * @return		a {@link FloatMatrix} transformation
	 */
	public static FloatMatrix createRotationMatrix(float roll, float pitch, float yaw){
		float sinA = (float)Math.sin(roll); float cosA = (float)Math.cos(roll);
		float sinB = (float)Math.sin(yaw); float cosB = (float)Math.cos(yaw);
		float sinY = (float)Math.sin(pitch); float cosY = (float)Math.cos(pitch);
		
		float[][] data = {
				{cosA*cosB,		cosA*sinB*sinY - sinA*cosY,		cosA*sinB*cosY + sinA*sinY, 	0.0f},
				{sinA*cosB,		sinA*sinB*sinY + cosA*cosY,		sinA*sinB*cosY - cosA*sinY, 	0.0f},
				{-sinB,			cosB*sinY,						cosB*cosY,					 	0.0f},
				{0.0f,			0.0f,							0.0f,						 	1.0f}
		};
		
		return new FloatMatrix(data);
	}
	
	public static FloatMatrix createPerspectiveMatrix(int fieldOfViewVertical,
			float aspectRatio, float nearPlane, float farPlane) {
		float top = nearPlane
				* (float) Math.tan(fieldOfViewVertical * Math.PI / 360.0);
		float bottom = -top;
		float left = bottom * aspectRatio;
		float right = top * aspectRatio;

		float X = 2 * nearPlane / (right - left);
		float Y = 2 * nearPlane / (top - bottom);
		float A = (right + left) / (right - left);
		float B = (top + bottom) / (top - bottom);
		float C = -(farPlane + nearPlane)
				/ (farPlane - nearPlane);
		float D = -2 * farPlane * nearPlane
				/ (farPlane - nearPlane);

		float[][] data = new float[][] {
								{ X, 		0.0f, 	A, 	0.0f }, 
								{ 0.0f, 	Y, 		B, 	0.0f }, 
								{ 0.0f, 	0.0f, 	C, 	-1.0f}, 
								{ 0.0f, 	0.0f, 	D, 	0.0f }};
		
		return new FloatMatrix(data);
	}
	
	public static FloatMatrix createCameraMatrix(	float roll, float pitch, float yaw,
													int fov, float aspectRatio, float near, float far){
		
		return FloatMatrix.multiply(createRotationMatrix(roll, pitch, yaw), createPerspectiveMatrix(fov, aspectRatio, near, far));
	}
	
	public static FloatMatrix translationMatrix(float x, float y, float z){
		float[][] data= {
				{1.0f,	0.0f,	0.0f,	x},
				{0.0f,	1.0f,	0.0f,	y},
				{0.0f,	0.0f,	1.0f,	z},
				{0.0f,	0.0f,	0.0f,	1.0f}
		};
		
		return new FloatMatrix(data);
	}
	
	public static FloatMatrix rotMatrixX(float angle){ // pitch
		float sinAngle = (float)Math.sin(angle);
		float cosAngle = (float)Math.cos(angle);
		
		float[][] data = {
				{1.0f,	0.0f,		0.0f, 		0.0f},
				{0.0f,	cosAngle, -sinAngle, 	0.0f},
				{0.0f,	sinAngle, cosAngle, 	0.0f},
				{0.0f,	0.0f,		0.0f,		1.0f}
		};
		
		return new FloatMatrix(data);
	}
	
	public static FloatMatrix rotMatrixY(float angle){ // yaw
		float sinAngle = (float)Math.sin(angle);
		float cosAngle = (float)Math.cos(angle);
		
		float[][] data = {
				{cosAngle,	0.0f,	sinAngle, 	0.0f},
				{0.0f,	 	1.0f,	0.0f,		0.0f},
				{-sinAngle,	0.0f,	cosAngle,	0.0f},
				{0.0f,		0.0f,	0.0f,		1.0f}
		};
		
		return new FloatMatrix(data);
	}
	
	public static FloatMatrix rotMatrixZ(float angle){ // roll
		float sinAngle = (float)Math.sin(angle);
		float cosAngle = (float)Math.cos(angle);
		
		float[][] data = {
				{cosAngle,	-sinAngle, 	0.0f,	0.0f},
				{sinAngle,	cosAngle, 	0.0f,	0.0f},
				{0.0f,		0.0f,		1.0f,	0.0f},
				{0.0f,		0.0f,		0.0f,	1.0f}
		};
		
		return new FloatMatrix(data);
	}
	
	public float [] columnWiseData(){
		float [] result = new float[ROWS * COLS];
		int i = 0;
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				result[i++] = data[r][c];
		return result;
	}
	
	public float[] rowWiseData(){
		float [] result = new float[ROWS * COLS];
		int i = 0;
		for (int c = 0; c < COLS; c++)
			for (int r = 0; r < ROWS; r++)
				result[i++] = data[r][c];
		return result;
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("[ ");
		for (int r = 0; r < ROWS; r++){
			if (r != 0)
				str.append("  ");
			for (int c = 0; c < COLS; c++){
				str.append(data[r][c]);
				str.append(c + 1 == COLS ? (r + 1 != ROWS ? "\n" : "") : "\t");
			}
		}
		str.append("]\n");
		
		return str.toString();	
	}
	
	public static void main(String[] args){
		System.out.println(FloatMatrix.createCameraMatrix(0.0f, 0.0f, 0.0f, 60, 16.0f/9.0f, 1.0f, 1000.0f));
	}
}
