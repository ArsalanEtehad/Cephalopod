package cephalopod;

import java.util.Random;

public class SmartPlayer extends Player {

	protected int row, col, x, y;
	protected int matrix[][];
	protected int UDLR[];
	protected int totalPipCount;
	private Random rand = new Random();

	public SmartPlayer(int row, int col) {
		this.row = row;
		this.col = col;
		UDLR = new int[4];
		totalPipCount = 0;
	}

	public void setMatrix(int matrix[][]) {
		this.matrix = new int[row][col];
		this.matrix = matrix;
	}

	protected void analyseNextMove() {
		// Ensures the wishing Cell is empty and gets the highest totalPipCount
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (matrix[i][j] == 0) {
					setUDLR(i, j);
					if (totalPipCount() >= totalPipCount) {
						totalPipCount = totalPipCount();
						x = i;
						y = j;
					}
				} else {
					x = rand.nextInt(row);
					y = rand.nextInt(col);
				}
			}
		}
	}

	@Override
	public int getX() {
		analyseNextMove();
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	public void setUDLR(int x, int y) {
		// IGNORE THE 6s
		// RIGHT
		if (y == (col - 1)) {
			UDLR[3] = 0;
		} else {
			if (Math.abs(matrix[x][y + 1]) < 6)
				UDLR[3] = matrix[x][y + 1];
			else
				UDLR[3] = 0;
		}

		// LEFT
		if (y == 0) {
			UDLR[2] = 0;
		} else {
			if (Math.abs(matrix[x][y - 1]) < 6)
				UDLR[2] = matrix[x][y - 1];
			else
				UDLR[2] = 0;
		}
		// UP
		if (x == 0) {
			UDLR[0] = 0;
		} else {
			if (Math.abs(matrix[x - 1][y]) < 6)
				UDLR[0] = matrix[x - 1][y];
			else
				UDLR[0] = 0;
		}
		// DOWN
		if (x == (row - 1)) {
			UDLR[1] = 0;
		} else {
			if (Math.abs(matrix[x + 1][y]) < 6)
				UDLR[1] = matrix[x + 1][y];
			else
				UDLR[1] = 0;
		}
	}

	public int totalPipCount() {
		int absUDLR = 0;
		absUDLR = Math.abs(UDLR[0]) + Math.abs(UDLR[1]) + Math.abs(UDLR[2])
				+ Math.abs(UDLR[3]);
		return absUDLR;
	}

}
