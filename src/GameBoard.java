public class GameBoard {

	Tile[][] map;
	private int rows;
	private int cols;

	public GameBoard(int row, int col) {
		rows = row;
		cols = col;
		map = new Tile[row][col];

		final long startTime = System.currentTimeMillis();
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r < rows; r++) {

				map[r][c] = new Tile();

			}
		}
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime));

	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;

	}

	public Tile getTileAt(int x, int y) {
		return map[x][y];
	}

}
