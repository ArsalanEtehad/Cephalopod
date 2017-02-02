package cephalopod;

import java.util.Random;

public class RandomPlayer extends Player {
	private int row;
	private int col;
	private Random rand = new Random();
	
	public RandomPlayer(Board mainBoard){
		
		this.row = mainBoard.getRow();
		this.col = mainBoard.getCol();
	}
	
	
	@Override
	public int getX(){
		System.out.print("\n" + name + ":\nx: ");
		int x = rand.nextInt(row);
		return x;
	}
	
	@Override
	public int getY(){
		System.out.print("y: ");
		int y = rand.nextInt(col);
		return y;
	}


	@Override
	public void setBoard(Board board) {
		// TODO Auto-generated method stub
		
	}
}