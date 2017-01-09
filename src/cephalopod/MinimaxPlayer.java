package cephalopod;

public class MinimaxPlayer extends SmartPlayer {

	public MinimaxPlayer(int row, int col) {
		super(row, col);
	}

	@Override
	protected void analyseNextMove() {
		System.out.println("MinimaxPlayer's analyseNextMove()\n");
		boolean[][] possibleMoves = new boolean[row][col];

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if(matrix[i][j] == 0){
					possibleMoves[i][j] = true; 
				}else
					possibleMoves[i][j] = false;
			}
		}
	}
}
