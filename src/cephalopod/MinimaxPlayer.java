package cephalopod;

import java.util.Scanner;

public class MinimaxPlayer extends SmartPlayer {

	public MinimaxPlayer(Board mainBoard) {
		super(mainBoard);
	}// end of Constructor()

	private Scanner scanner = new Scanner(System.in);

	@Override
	protected void nextMove(Board gameBoard) {
		if (gameBoard.isFilled()) {
			return;
		}
		Board currentBoard = new Board(gameBoard.getRow(), gameBoard.getCol());
		currentBoard.copyBoard(gameBoard);
		double maxUtility = -1 * Double.MAX_VALUE;
		int maxX = -2;
		int maxY = -2;

		search: {
			for (int i = 0; i < currentBoard.getRow(); i++) {
				for (int j = 0; j < currentBoard.getCol(); j++) {
					Board maxBoard = new Board(currentBoard.getRow(), currentBoard.getCol());
					maxBoard.copyBoard(currentBoard);
					if (maxBoard.isMoveLegal(i, j)) {
						maxBoard.capturingPlacement(i, j, type);
						if (maxBoard.isFilled()) {
							if (maxBoard.checkWinningStatus() == type) {
								maxUtility = Double.MAX_VALUE;
								maxX = i;
								maxY = j;
								break search;
							}
						}

						double minUtility = -1 * opponentMove(type * -1, maxBoard);
						// System.out.println("minUtility: " + minUtility + " &
						// MaxUtility: " + maxUtility);
						if (minUtility > maxUtility) {
							maxUtility = minUtility;
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

	// returns the minimum utility each step

	protected double opponentMove(int playerType, Board maxBoard) {
		double maxUtility = -1 * gameBoard.getRow() * gameBoard.getCol() * 6 - 1;
		Board maxCurrentBoard = new Board(maxBoard.getRow(), maxBoard.getCol());
		maxCurrentBoard.copyBoard(maxBoard);
		search: {
			for (int i = 0; i < maxBoard.getRow(); i++) {
				for (int j = 0; j < maxBoard.getCol(); j++) {

					Board minBoard = new Board(maxCurrentBoard.getRow(), maxCurrentBoard.getCol());
					minBoard.copyBoard(maxCurrentBoard);

					if (minBoard.isMoveLegal(i, j)) {
						minBoard.capturingPlacement(i, j, playerType);
						// minBoard.drawBoard();

						if (minBoard.isFilled()) {
							if (minBoard.checkWinningStatus() == playerType) {
								maxUtility = minBoard.getRow() * minBoard.getCol() * 6;
							}
							break search;
						}
						double utility = minBoard.utility(playerType);

						if (utility > maxUtility) {
							maxUtility = utility;

						}
					}
				}
			}

		}
		return maxUtility;
	}

	@Override
	public int getX() {
		nextMove(this.gameBoard);
		return x;
	}// End of getX()

	@Override
	public int getY() {
		return y;
	}// End of getY()

}// End of MinimaxPlayer Classes