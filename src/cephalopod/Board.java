package cephalopod;

public class Board {
	private int[][] matrix;
	private int UDLR[] = new int[4];
	private int row;
	private int col;
	private int p1Counter,p2Counter;

 	public Board(int row, int col) {
		this.row = row;
		this.col = col;
		matrix = new int[row][];
		for (int i = 0; i < row; i++) {
			matrix[i] = new int[col];
		}
		initializeMatrix();
		initializeUDLR();
	}// End Constructor(int,int)

	public void initializeMatrix() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
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
		for (int j = 0; j < col; j++) {
			System.out.print("   " + (j + 1));
		}
		System.out.print("\n");

		for (int i = 0; i < row; i++) {
			System.out.print(" ");
			for (int j = 0; j < col; j++) {
				System.out.print(" ---");
			}
			System.out.print("\n");

			System.out.print(i + 1);
			for (int j = 0; j < col; j++) {
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
		for (int j = 0; j < col; j++) {
			System.out.print(" ---");
		}
		System.out.print(" \n\n");
	}// End drawBoard()

	public void setCell(int x, int y, int capture) {
		matrix[x][y] = capture;
		
		if (capture < 0) {
			p2Counter++;
		} else if (capture > 0)
			p1Counter++;

	}// End setMove()

	public boolean isFilled() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (matrix[i][j] == 0) {
					return false; // If there is any blank
				}
			}
		}
		whoIsTheWinner();
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
		if (row <= x || col <= y || x < 0 || y < 0) {
			System.out.print("Position (" + (x + 1) + ", " + (y + 1)
					+ ") is out of range!\n");
			return false;
		} else if (!(matrix[x][y] == 0)) {
			System.out.print("Position (" + (x + 1) + ", " + (y + 1)
					+ ") has been occupied!\n");
			return false;
		} else
			return true;
	}

	// Dirty Block
	public void whoIsTheWinner() {
		if (p1Counter < p2Counter)
			System.out.print("Player 2(oPlayer [-1] ) is the winner!");
		else if (p2Counter < p1Counter)
			System.out.print("Player 1(xPlayer [+1]) is the winner!");
		else
			System.out.print("DRAW! NO WINNER");
	}

	
	public void setUDLR(int x, int y) {
		if (y == col - 1) {
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
		if (x == row - 1) {
			UDLR[1] = 0;
		} else {
			UDLR[1] = matrix[x + 1][y];
		}
	}

	public int totalPipCount() {
		int absUDLR = 0;
		absUDLR = Math.abs(UDLR[0]) + Math.abs(UDLR[1]) + Math.abs(UDLR[2])
				+ Math.abs(UDLR[3]);
		return absUDLR;
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
		int r = row;
		return r;
	}

	int getCol() {
		int c = col;
		return c;
	}
	
	public int[][] getMatrix(){
		return matrix;
	}
	
	public int[] getUDLR(){
		return UDLR;
	}

	//End of Dirty Block
	
	
	
}// End Board Class