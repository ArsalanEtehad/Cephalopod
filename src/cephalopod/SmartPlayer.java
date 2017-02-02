package cephalopod;

import java.util.Random;

public class SmartPlayer extends Player {

	protected int x, y;
	protected Board gameBoard;
	// private Random rand = new Random();

	public SmartPlayer(Board mainBoard) {
		gameBoard = new Board(mainBoard.getRow(), mainBoard.getCol());
		gameBoard.copyBoard(mainBoard);
	}

	protected void nextMove(Board gameBoard) {
		if (gameBoard.isFilled()) {
			return;
		}
		Board currentBoard = new Board(gameBoard.getRow(), gameBoard.getCol());
		currentBoard.copyBoard(gameBoard);
		double maxUtility = 0;// ????
		double minUtility = 0;
		double utility = 0;
		int maxX = -2;
		int maxY = -2;

		search: {
			for (int i = 0; i < currentBoard.getRow(); i++) {
				for (int j = 0; j < currentBoard.getCol(); j++) {
					Board maxBoard = new Board(currentBoard.getRow(), currentBoard.getCol());
					maxBoard.copyBoard(currentBoard);
					if (maxBoard.isMoveLegal(i, j)) {
						maxBoard.capturingPlacement(i, j, type);

						// maxBoard.drawBoard();
						utility = maxBoard.utility(type);
						maxX = i;
						maxY = j;

						if (maxBoard.isFilled()) {
							if (maxBoard.checkWinningStatus() == type) {
								maxUtility = maxBoard.getRow() * maxBoard.getCol() * 6;
								maxX = i;
								maxY = j;
								break search;
							}
						}

						// minUtility = -1 * opponentMove((type * -1),
						// maxBoard);
						if (utility > maxUtility) {
							maxUtility = utility;
							maxX = i;
							maxY = j;
						}
					}
				}
			}
		}
		this.x = maxX;
		this.y = maxY;
	}// End of nextMove()

	@Override
	public int getX() {
		nextMove(this.gameBoard);
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setBoard(Board board) {
		gameBoard.copyBoard(board);
	}
}