package cephalopod;

import java.util.Random;

public class RandomPlayer extends Player {
	private int row;
	private int col;
	private Random rand = new Random();
	
	public RandomPlayer(int row,int col){
		this.row = row;
		this.col = col;
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
}