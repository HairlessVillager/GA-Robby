public class Robby {
	public static final int PICK_UP_CAN_SCORE = +10;
	public static final int PICK_UP_EMPTY_SCORE = -1;
	public static final int CRASH_WALL_SCORE = -5;
	private int score = 0;
	private Gene gene = null;
	private Board board = null;
	private int x = 0;
	private int y = 0;

	public Robby() {
		;
	}

	public void init(Gene gene, Board board) {
		score = 0;
		x = 0;
		y = 0;
		this.gene = gene;
		this.board = board;
	}

	public int getScore() {
		return score;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void step() {
		step(getSensor());
	}

	private RobbySensor getSensor() {
		RobbySensor sensor = new RobbySensor();
		sensor.north = board.getCell(x + 0, y - 1);
		sensor.south = board.getCell(x + 0, y + 1);
		sensor.east = board.getCell(x + 1, y + 0);
		sensor.west = board.getCell(x - 1, y + 0);
		sensor.currentSite = board.getCell(x + 0, y + 0);
		return sensor;
	}

	private void step(RobbySensor sensor) {
		step(gene.getAction(sensor));
	}

	private void step(Action action) {
		switch(action) {
			case MOVE_NORTH :
				tryMove(0, -1);
				break;
			case MOVE_SOUTH :
				tryMove(0, +1);
				break;
			case MOVE_EAST :
				tryMove(+1, 0);
				break;
			case MOVE_WEST :
				tryMove(-1, 0);
				break;
			case STAY_PUT :
				break;
			case PICK_UP :
				tryPickUp();
				break;
			case MOVE_RANDOM :
				tryMoveRandom();
				break;
			default :
				break;
		}
	}

	private void tryMove(int dx, int dy) {
		x += dx;
		y += dy;

		if(board.isOutOfBoard(x, y)) {
			x -= dx;
			y -= dy;
			score += CRASH_WALL_SCORE;
		}
	}

	private void tryPickUp() {
		if(board.getCell(x, y) == CellStatus.CAN) {
			score += PICK_UP_CAN_SCORE;
			board.cleanCell(x, y);
		} else {
			score += PICK_UP_EMPTY_SCORE;
		}
	}

	private void tryMoveRandom() {
		int i = (int) (Math.random() * 4.0);
		switch(i) {
			case 0 :
				tryMove(-1, 0);
				break;
			case 1 :
				tryMove(+1, 0);
				break;
			case 2 :
				tryMove(0, -1);
				break;
			case 3 :
				tryMove(0, +1);
				break;
			default :
				tryMove(-1, 0);
				break;
		}
	}

	public static void main(String[] args) {
		Robby robby = new Robby();
		Board board = Board.createRandom(0.5);
		robby.init(Gene.create("055352156255322254350001250151353155151355255354264501252655250252055253054066300356351152253354562356236341151152564152602220353640023050334545050602006055620605353355154255201254354100510155150151153636154214210214261433221624643410256422133"), board);
		for(int i = 0; i < 200; i++) {
			robby.step();
			for(int x = 0; x < board.width; x++) {
				for(int y = 0; y < board.height; y++) {
					if(x == robby.x && y == robby.y) {
						System.out.print('O');
					} else {
						if(board.getCell(x, y) == CellStatus.EMPTY) {
							System.out.print('.');
						} else{
							System.out.print('#');
						}
					}
				}
				System.out.print('\n');
			}
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			try {
				Thread.sleep(1000);
			} catch(Exception ex) {
				;
			}
		}
	}
}
