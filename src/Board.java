public class Board {
	public final static int width = 10;
	public final static int height = 10;
	private CellStatus[][] value;

	private Board() {
		value = new CellStatus[width][height];
	}

	static public Board createRandom(double probabilityOfCan) {
		Board board = new Board();
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				board.value[i][j] = Math.random() < probabilityOfCan ? CellStatus.CAN : CellStatus.EMPTY;
			}
		}
		return board;
	}

	public CellStatus getCell(int x, int y) {
		if(isOutOfBoard(x, y)) {
			return CellStatus.WALL;
		} else {
			return value[x][y];
		}
	}

	public void cleanCell(int x, int y) {
		if(!isOutOfBoard(x, y)) {
			value[x][y] = CellStatus.EMPTY;
		}
	}

	public void placeFlag(int x, int y) {
		if(!isOutOfBoard(x, y)) {
			value[x][y] = CellStatus.FLAG;
		}
	}

	public boolean isOutOfBoard(int x, int y) {
		if(0 <= x && x < width
				&& 0 <= y && y < height) {
			return false;
		} else {
			return true;
		}
	}
}
