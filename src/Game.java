public class Game {
	Board board = null;
	Robby robby = null;

	public Game() {
		;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setRobby(Robby robby) {
		this.robby = robby;
	}

	public void run(int steps) {
		while(steps -- > 0) {
			robby.step();
		}
	}
}
