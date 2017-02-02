package cephalopod;

import java.util.Scanner;

public class GameState {
	private Board gameBoard;

	// Technically the Board Description needs to tell the GameState how many
	// moves do the game have.
	private Player xPlayer;
	private Player oPlayer;
	// private HumanPlayer oPlayer;

	private Scanner scanner = new Scanner(System.in);

	public void play() {
		int row, col;
		row = 0;
		col = 0;
		// Ordering the size of the board to play on
		System.out.println("Row: ");
		row = scanner.nextInt();
		System.out.println("Column: ");
		col = scanner.nextInt();
		gameBoard = new Board(row, col);

		// X move
		// xPlayer = new HumanPlayer(); // Declaring the kind of the type
		// xPlayer = new RandomPlayer(gameBoard);
		// xPlayer = new SmartPlayer(gameBoard);
		xPlayer = new MinimaxPlayer(gameBoard);
		xPlayer.setName("xPlayer");
		xPlayer.setMoveId(1);

		// Y move
		// oPlayer = new SmartPlayer(gameBoard); // Declaring the kind of the
		oPlayer = new HumanPlayer();
		// oPlayer = new RandomPlayer(gameBoard);
		// oPlayer = new MinimaxPlayer(gameBoard);
		oPlayer.setName("oPlayer");
		oPlayer.setMoveId(-1);
		System.out.println("xplayer: " + xPlayer.getClass().getName() + "\noPlayer: " + oPlayer.getClass().getName());

		// control (xmove)
		int moveID = xPlayer.getMoveId();

		while (!gameBoard.isFilled()) {
			gameBoard.drawBoard();
			int control = moveID;
			int xMove, yMove;

			do {
				if (control == xPlayer.getMoveId()) {
					if (xPlayer instanceof MinimaxPlayer) {
						xPlayer.setBoard(gameBoard);
					}
					xMove = xPlayer.getX();
					yMove = xPlayer.getY();

					// System.out.println("\nxPlayer moves:\nx: " + xMove +
					// "\ny: " + yMove);
					// int input = scanner.nextInt();
				} else {
					if (oPlayer instanceof MinimaxPlayer) {
						oPlayer.setBoard(gameBoard);
					}
					xMove = oPlayer.getX();
					yMove = oPlayer.getY();

					// System.out.println("oPlayer moves:\nx: " + xMove + "\ny:
					// " + yMove);
					// int input = scanner.nextInt();
				}

			} while (!gameBoard.isMoveLegal(xMove, yMove));
			// gameBoard.setUDLR(xMove, yMove);
			System.out.print("neighbours' information:\nUDLR/Capturing: ");
			for (int j = 0; j < 4; j++) {
				System.out.print(gameBoard.getUDLR(j) + "/" + ifCanCapture(j) + " ");
			}
			System.out.print("\nOccupied cells of neighbors: " + gameBoard.notEmptyUDLR()
					+ "\nTotal pip counts of neighbors: " + gameBoard.totalPipCount() + "\n");
			gameBoard.capturingPlacement(xMove, yMove, moveID);
			// Switch moves
			if (control == xPlayer.getMoveId()) {
				moveID = oPlayer.getMoveId();
			} else
				moveID = xPlayer.getMoveId();
		} // End While(notFilled) Loop
	}// End play()

	public boolean ifCanCapture(int i) {
		if (gameBoard.getUDLR(i) < 6 && gameBoard.getUDLR(i) > -6 && gameBoard.getUDLR(i) != 0
				&& gameBoard.notEmptyUDLR() >= 2)
			return true;
		return false;
	}
}// End Class