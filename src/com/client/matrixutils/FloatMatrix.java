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
		FloatMatrix first = new FloatMatrix(2, 2);
		float [][] data = {	{1, 2}, 
							{3, 4}	};
		first.setData(data);
		
		float [][] data2 = {	{1, 2, 3}, 
								{4, 5, 6}	};

		System.out.printf("(%s, %s)\n", data2.length, data2[0].length);
		FloatMatrix second = new FloatMatrix(2, 3);
		second.setData(data2);
		
		System.out.println(first + "\n" + second);
		System.out.println(FloatMatrix.multiply(first, second));
	}
}
