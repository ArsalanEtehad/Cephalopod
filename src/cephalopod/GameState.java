package cephalopod;

import java.util.Scanner;

public class GameState {
	private Board thisBoard;

	// Technically the Board Description needs to tell the GameState how many
	// moves do the game have.
	private Player xPlayer;
	private Player oPlayer;
	// private HumanPlayer oPlayer;

	private Scanner scanner = new Scanner(System.in);

	public void play() {
		int row, col;
		// Ordering the size of the board to play on
		System.out.println("Row: ");
		row = scanner.nextInt();
		System.out.println("Column: ");
		col = scanner.nextInt();

		// X move
		//xPlayer = new HumanPlayer(); // Declaring the kind of the player
		//xPlayer = new RandomPlayer(row, col);
		xPlayer = new SmartPlayer(row, col);
		xPlayer.setName("xPlayer");
		xPlayer.setMoveId(1);

		// Y move
		
		// oPlayer = new SmartPlayer(row,col); // Declaring the kind of the
		oPlayer = new HumanPlayer();
		//oPlayer = new RandomPlayer(row,col);
		oPlayer.setName("oPlayer");
		oPlayer.setMoveId(-1);
		System.out.println("xplayer: " + xPlayer.getClass().getName() + "\noPlayer: " + oPlayer.getClass().getName());
		thisBoard = new Board(row, col);

		// control (xmove)
		int moveID = xPlayer.getMoveId();

		while (!thisBoard.isFilled()) {
			thisBoard.drawBoard();
			int control = moveID;
			int xMove, yMove;

			do {
				if (control == xPlayer.getMoveId()) {
					if (xPlayer instanceof SmartPlayer) {
						((SmartPlayer) xPlayer).setMatrix(thisBoard.getMatrix());
					}

					xMove = xPlayer.getX();
					yMove = xPlayer.getY();
				} else {
					if (oPlayer instanceof SmartPlayer) {
						((SmartPlayer) oPlayer).setMatrix(thisBoard.getMatrix());
					}
					xMove = oPlayer.getX();
					yMove = oPlayer.getY();
				}
			} while (!thisBoard.isMoveLegal(xMove, yMove));

			thisBoard.setCell(xMove, yMove, moveID);
			thisBoard.setUDLR(xMove, yMove);
			System.out.print("neighbours' information:\nUDLR/Capturing: ");
			for (int j = 0; j < 4; j++) {
				System.out.print(thisBoard.getUDLR(j) + "/" + ifCanCapture(j)
						+ " ");
			}
			System.out.print("\nOccupied cells of neighbors: "
					+ thisBoard.notEmptyUDLR()
					+ "\nTotal pip counts of neighbors: "
					+ thisBoard.totalPipCount() + "\n");

			capture(xMove, yMove, moveID);

			// Switch moves
			if (control == xPlayer.getMoveId()) {
				moveID = oPlayer.getMoveId();
			} else
				moveID = xPlayer.getMoveId();

		}// End While(notFilled) Loop
	}// End play()

	public boolean ifCanCapture(int i) {
		if (thisBoard.getUDLR(i) < 6 && thisBoard.getUDLR(i) > -6
				&& thisBoard.getUDLR(i) != 0 && thisBoard.notEmptyUDLR() >= 2)
			return true;
		return false;
	}

	public void capture(int x, int y, int move) {
		int up = Math.abs(thisBoard.getUDLR(0));
		int down = Math.abs(thisBoard.getUDLR(1));
		int left = Math.abs(thisBoard.getUDLR(2));
		int right = Math.abs(thisBoard.getUDLR(3));

		if (thisBoard.notEmptyUDLR() == 4) {
			if (thisBoard.totalPipCount() <= 6) {
				clearDown(x, y);
				clearUp(x, y);
				clearLeft(x, y);
				clearRight(x, y);
				thisBoard.setCell(x, y, thisBoard.totalPipCount() * move);
			} else if (thisBoard.totalPipCount() > 6) {
				if (up + down + left <= 6 && up != 0 && down != 0 && left != 0) {
					clearDown(x, y);
					clearUp(x, y);
					clearLeft(x, y);
					thisBoard.setCell(x, y, (left + down + up) * move);
				} else if (down + left + right <= 6 && right != 0 && down != 0
						&& left != 0) {
					clearLeft(x, y);
					clearRight(x, y);
					clearDown(x, y);
					thisBoard.setCell(x, y, (left + down + right) * move);
				} else if (up + down + right <= 6 && right != 0 && down != 0
						&& down != 0) {
					clearDown(x, y);
					clearUp(x, y);
					clearRight(x, y);
					thisBoard.setCell(x, y, (up + down + right) * move);
				} else if (up + left + right <= 6 && right != 0 && down != 0
						&& down != 0) {
					clearUp(x, y);
					clearRight(x, y);
					clearLeft(x, y);
					thisBoard.setCell(x, y, (up + left + right) * move);
				} else if (up + down <= 6 && up != 0 && down != 0) {
					clearDown(x, y);
					clearUp(x, y);
					thisBoard.setCell(x, y, (up + down) * move);
				} else if (left + right <= 6 && left != 0 && right != 0) {
					clearLeft(x, y);
					clearRight(x, y);
					thisBoard.setCell(x, y, (left + right) * move);
				} else if (up + left <= 6 && up != 0 && left != 0) {
					clearUp(x, y);
					clearLeft(x, y);
					// capture value
					thisBoard.setCell(x, y, (left + up) * move);
				} else if (up + right <= 6 && up != 0 && right != 0) {
					clearUp(x, y);
					clearRight(x, y);
					thisBoard.setCell(x, y, (up + right) * move);
				} else if (down + left <= 6 && left != 0 && down != 0) {
					clearDown(x, y);
					clearLeft(x, y);
					thisBoard.setCell(x, y, (left + down) * move);

				} else if (down + right <= 6 && right != 0 && down != 0) {
					clearDown(x, y);
					clearRight(x, y);
					thisBoard.setCell(x, y, (down + right) * move);
				}
			}
		} else if (thisBoard.notEmptyUDLR() == 3) {
			if (thisBoard.totalPipCount() <= 6) {
				clearDown(x, y);
				clearUp(x, y);
				clearLeft(x, y);
				clearRight(x, y);
				thisBoard.setCell(x, y, thisBoard.totalPipCount() * move);
			} else if (thisBoard.totalPipCount() > 6) {
				if (up + down <= 6 && up != 0 && down != 0) {
					clearDown(x, y);
					clearUp(x, y);
					thisBoard.setCell(x, y, (up + down) * move);
				} else if (left + right <= 6 && left != 0 && right != 0) {
					clearLeft(x, y);
					clearRight(x, y);
					thisBoard.setCell(x, y, (left + right) * move);
				} else if (up + left <= 6 && up != 0 && left != 0) {
					clearUp(x, y);
					clearLeft(x, y);

					thisBoard.setCell(x, y, (left + up) * move);
				} else if (up + right <= 6 && up != 0 && right != 0) {
					clearUp(x, y);
					clearRight(x, y);
					thisBoard.setCell(x, y, (up + right) * move);
				} else if (down + left <= 6 && left != 0 && down != 0) {
					clearDown(x, y);
					clearLeft(x, y);
					thisBoard.setCell(x, y, (left + down) * move);
				} else if (down + right <= 6 && right != 0 && down != 0) {
					clearDown(x, y);
					clearRight(x, y);
					thisBoard.setCell(x, y, (down + right) * move);
				}
			}
		} else if (thisBoard.notEmptyUDLR() == 2) {
			if (thisBoard.totalPipCount() <= 6) {
				clearDown(x, y);
				clearUp(x, y);
				clearLeft(x, y);
				clearRight(x, y);
				thisBoard.setCell(x, y, thisBoard.totalPipCount() * move);
			}
		}
	}

	public void clearDown(int x, int y) {
		if ((x + 1) < thisBoard.getRow())
			thisBoard.setCell((x + 1), y, 0);
	}

	public void clearUp(int x, int y) {
		if (x > 0)
			thisBoard.setCell((x - 1), y, 0);
	}

	public void clearLeft(int x, int y) {
		if (y >= 1)
			thisBoard.setCell(x, (y - 1), 0);
	}

	public void clearRight(int x, int y) {
		if ((y + 1) < thisBoard.getCol())
			thisBoard.setCell(x, (y + 1), 0);
	}

}// End Class