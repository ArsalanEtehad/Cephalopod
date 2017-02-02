package cephalopod;

import java.util.Scanner;

public class Board {
	private int[][] matrix;
	private int UDLR[] = new int[4];
	private int rowSize;
	private int colSize;
	private int p1Counter, p2Counter;

	public void copyBoard(Board Board) {
		for (int i = 0; i < Board.getRow(); i++) {
			for (int j = 0; j < Board.getCol(); j++) {
				// System.out.println(Board.getMatrix(i, j));
				matrix[i][j] = Board.getMatrix(i, j);
			}
		}

		// matrix = Board.getMatrix();
		this.rowSize = Board.getRow();
		this.colSize = Board.getCol();
		this.UDLR = Board.UDLR;
	}

	public Board(int row, int col) {
		matrix = new int[row][col];
		this.rowSize = row;
		this.colSize = col;
		initializeMatrix();
		initializeUDLR();
	}// End Constructor(int,int)

	public void initializeMatrix() {
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				matrix[i][j] = 0;
			}
		}
	}// End initializeMatrix()

	public void initializeUDLR() {
		for (int i = 0; i < 4; i++) {
			UDLR[i] = 0;
		}
	}// End initializeUDLR()0.

	public void drawBoard() {// It's based on the Monospaced Font in order for
								// best appearance
		for (int j = 0; j < colSize; j++) {
			System.out.print("   " + (j + 1));
		}
		System.out.print("\n");

		for (int i = 0; i < rowSize; i++) {
			System.out.print(" ");
			for (int j = 0; j < colSize; j++) {
				System.out.print(" ---");
			}
			System.out.print("\n");

			System.out.print(i + 1);
			for (int j = 0; j < colSize; j++) {
				System.out.print("|");
				if (matrix[i][j] == 0) {
					System.out.print("   ");
				} else if (matrix[i][j] < 0) {
					System.out.print(" " + matrix[i][j]);
				} else {
					System.out.print(" " + matrix[i][j] + " ");
				}
			}
			System.out.println("|");
		}
		System.out.print(" ");
		for (int j = 0; j < colSize; j++) {
			System.out.print(" ---");
		}
		System.out.print(" \n\n");
	}// End drawBoard()

	public void setCellDirectly(int x, int y, int capture) {
		matrix[x][y] = capture;

		if (capture < 0) {
			p2Counter++;
		} else if (capture > 0)
			p1Counter++;
	}// End setMove()

	public boolean isFilled() {
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				if (matrix[i][j] == 0) {
					return false; // If there is any blank
				}
			}
		}
		checkWinningStatus();
		return true; // if all filled
	}// End ifFilled

	public void scanUDLR(int x, int y) {
		UDLR[0] = matrix[x - 1][y];
		UDLR[1] = matrix[x + 1][y];
		UDLR[2] = matrix[x][y - 1];
		UDLR[3] = matrix[x][y + 1];
	}

	public boolean isMoveLegal(int x, int y) { // args X and Y Axis of the
												// wishing Cell to play on
		if (rowSize <= x || colSize <= y || x < 0 || y < 0) {
			// System.out.print("Position (" + (x + 1) + ", " + (y + 1) + ") is
			// out of range!\n");
			return false;
		} else if (!(matrix[x][y] == 0)) {
			// System.out.print("Position (" + (x + 1) + ", " + (y + 1) + ") has
			// been occupied!\n");
			return false;
		} else
			return true;
	}

	public int checkWinningStatus() {
		if (p1Counter < p2Counter) {
			System.out.print("Player 2(oPlayer [-1] ) is the winner!");
			return -1;
		} else if (p2Counter < p1Counter) {
			System.out.print("Player 1(xPlayer [+1]) is the winner!");
			return +1;
		} else {
			System.out.print("DRAW! NO WINNER");
			return 0;
		}
	}

	public void setUDLR(int x, int y) {
		if (y == colSize - 1) {
			UDLR[3] = 0;
		} else {
			UDLR[3] = matrix[x][y + 1];
		}
		if (y == 0) {
			UDLR[2] = 0;
		} else {
			UDLR[2] = matrix[x][y - 1];
		}
		if (x == 0) {
			UDLR[0] = 0;
		} else {
			UDLR[0] = matrix[x - 1][y];
		}
		if (x == rowSize - 1) {
			UDLR[1] = 0;
		} else {
			UDLR[1] = matrix[x + 1][y];
		}
	}

	public int totalPipCount() {

		int absUDLR = 0;
		absUDLR = Math.abs(UDLR[0]) + Math.abs(UDLR[1]) + Math.abs(UDLR[2]) + Math.abs(UDLR[3]);
		return absUDLR;
	}

	public double utility(int playerType) {
		int total = 0;
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				total += matrix[i][j];
			}
		}
		return total * playerType;
	}

	public int getUDLR(int i) {
		return UDLR[i];
	}

	public int notEmptyUDLR() {
		int occupiedCells = 0;

		for (int i = 0; i < 4; i++) {
			if (UDLR[i] != 0)
				occupiedCells++;
		}
		return occupiedCells;
	}

	int getRow() {
		return rowSize;
	}

	int getCol() {

		return colSize;
	}

	public int getMatrix(int i, int j) {
		// System.out.print(i + " , " + j);
		return matrix[i][j];
	}

	public int[] getUDLR() {
		return UDLR;
	}

	public void capturingPlacement(int x, int y, int playerType) {
		setCellDirectly(x, y, playerType);
		setUDLR(x, y);
		int up = Math.abs(getUDLR(0));
		int down = Math.abs(getUDLR(1));
		int left = Math.abs(getUDLR(2));
		int right = Math.abs(getUDLR(3));

		if (notEmptyUDLR() == 4) {
			if (totalPipCount() <= 6) {
				clearDown(x, y);
				clearUp(x, y);
				clearLeft(x, y);
				clearRight(x, y);
				setCellDirectly(x, y, totalPipCount() * playerType);
			} else if (totalPipCount() > 6) {
				if (up + down + left <= 6 && up != 0 && down != 0 && left != 0) {
					clearDown(x, y);
					clearUp(x, y);
					clearLeft(x, y);
					setCellDirectly(x, y, (left + down + up) * playerType);
				} else if (down + left + right <= 6 && right != 0 && down != 0 && left != 0) {
					clearLeft(x, y);
					clearRight(x, y);
					clearDown(x, y);
					setCellDirectly(x, y, (left + down + right) * playerType);
				} else if (up + down + right <= 6 && right != 0 && down != 0 && down != 0) {
					clearDown(x, y);
					clearUp(x, y);
					clearRight(x, y);
					setCellDirectly(x, y, (up + down + right) * playerType);
				} else if (up + left + right <= 6 && right != 0 && down != 0 && down != 0) {
					clearUp(x, y);
					clearRight(x, y);
					clearLeft(x, y);
					setCellDirectly(x, y, (up + left + right) * playerType);
				} else if (up + down <= 6 && up != 0 && down != 0) {
					clearDown(x, y);
					clearUp(x, y);
					setCellDirectly(x, y, (up + down) * playerType);
				} else if (left + right <= 6 && left != 0 && right != 0) {
					clearLeft(x, y);
					clearRight(x, y);
					setCellDirectly(x, y, (left + right) * playerType);
				} else if (up + left <= 6 && up != 0 && left != 0) {
					clearUp(x, y);
					clearLeft(x, y);
					// capture value
					setCellDirectly(x, y, (left + up) * playerType);
				} else if (up + right <= 6 && up != 0 && right != 0) {
					clearUp(x, y);
					clearRight(x, y);
					setCellDirectly(x, y, (up + right) * playerType);
				} else if (down + left <= 6 && left != 0 && down != 0) {
					clearDown(x, y);
					clearLeft(x, y);
					setCellDirectly(x, y, (left + down) * playerType);

				} else if (down + right <= 6 && right != 0 && down != 0) {
					clearDown(x, y);
					clearRight(x, y);
					setCellDirectly(x, y, (down + right) * playerType);
				}
			}
		} else if (notEmptyUDLR() == 3) {
			if (totalPipCount() <= 6) {
				clearDown(x, y);
				clearUp(x, y);
				clearLeft(x, y);
				clearRight(x, y);
				setCellDirectly(x, y, totalPipCount() * playerType);
			} else if (totalPipCount() > 6) {
				if (up + down <= 6 && up != 0 && down != 0) {
					clearDown(x, y);
					clearUp(x, y);
					setCellDirectly(x, y, (up + down) * playerType);
				} else if (left + right <= 6 && left != 0 && right != 0) {
					clearLeft(x, y);
					clearRight(x, y);
					setCellDirectly(x, y, (left + right) * playerType);
				} else if (up + left <= 6 && up != 0 && left != 0) {
					clearUp(x, y);
					clearLeft(x, y);

					setCellDirectly(x, y, (left + up) * playerType);
				} else if (up + right <= 6 && up != 0 && right != 0) {
					clearUp(x, y);
					clearRight(x, y);
					setCellDirectly(x, y, (up + right) * playerType);
				} else if (down + left <= 6 && left != 0 && down != 0) {
					clearDown(x, y);
					clearLeft(x, y);
					setCellDirectly(x, y, (left + down) * playerType);
				} else if (down + right <= 6 && right != 0 && down != 0) {
					clearDown(x, y);
					clearRight(x, y);
					setCellDirectly(x, y, (down + right) * playerType);
				}
			}
		} else if (notEmptyUDLR() == 2) {
			if (totalPipCount() <= 6) {
				clearDown(x, y);
				clearUp(x, y);
				clearLeft(x, y);
				clearRight(x, y);
				setCellDirectly(x, y, totalPipCount() * playerType);
			}
		}
	}

	public void clearDown(int x, int y) {
		if ((x + 1) < getRow())
			setCellDirectly((x + 1), y, 0);
	}

	public void clearUp(int x, int y) {
		if (x > 0)
			setCellDirectly((x - 1), y, 0);
	}

	public void clearLeft(int x, int y) {
		if (y >= 1)
			setCellDirectly(x, (y - 1), 0);
	}

	public void clearRight(int x, int y) {
		if ((y + 1) < getCol())
			setCellDirectly(x, (y + 1), 0);
	}

}// End Board Class