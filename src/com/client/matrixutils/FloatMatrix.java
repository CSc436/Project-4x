package com.client.matrixutils;

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
	private static FloatMatrix createTransformationMatrix(	float roll, float pitch, float yaw, 
													float x, float y, float z){
		float cosRoll = (float) Math.cos(roll); 
		float sinRoll = (float) Math.sin(roll);
		float cosPitch = (float) Math.cos(pitch); 
		float sinPitch = (float) Math.sin(pitch);
		float cosYaw = (float) Math.cos(yaw); 
		float sinYaw = (float) Math.sin(yaw);
		
		float[][] data = {
				{cosYaw * cosPitch,	cosYaw * sinPitch * sinRoll	- sinYaw * cosRoll,	cosYaw * sinPitch * cosRoll + sinYaw * sinRoll, -x  },
				{sinYaw * cosPitch,	sinYaw * sinPitch * sinRoll + cosYaw * cosRoll, sinYaw * sinPitch * cosRoll - cosYaw * sinRoll, -y  },
				{- sinPitch,		cosPitch * sinRoll,								cosPitch * cosRoll,								-z  },
				{0.0f,				0.0f,											0.0f,											1.0f}
		};
		return new FloatMatrix(data);
	}
	
	private static FloatMatrix createPerspectiveMatrix(int fieldOfViewVertical,
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
											float x,	float y,	float z,
											int fov, float aspectRatio, float near, float far){
		
		return FloatMatrix.multiply(createPerspectiveMatrix(fov, aspectRatio, near, far), createTransformationMatrix(roll, pitch, yaw, x, y, z));
	}
	
	public float [] columnWiseData(){
		float [] result = new float[ROWS * COLS];
		int i = 0;
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
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
/*		FloatMatrix first = new FloatMatrix(2, 2);
		float [][] data = {	{1, 2}, 
							{3, 4}	};
		first.setData(data);
		
		float [][] data2 = {	{1, 2, 3}, 
								{4, 5, 6}	};

		System.out.printf("(%s, %s)\n", data2.length, data2[0].length);
		FloatMatrix second = new FloatMatrix(2, 3);
		second.setData(data2);
		
		System.out.println(first + "\n" + second);
		System.out.println(FloatMatrix.multiply(first, second));*/
		
		System.out.println(FloatMatrix.createCameraMatrix(0.0f, 0.0f, 0.0f, 0.0f,0.0f, 0.0f, 60, 16.0f/9.0f, 1.0f, 1000.0f).columnWiseData());
	}
}
