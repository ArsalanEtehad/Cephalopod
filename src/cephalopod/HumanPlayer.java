package cephalopod;

import java.util.Scanner;

public class HumanPlayer extends Player {
	private Scanner scanner = new Scanner(System.in);

	@Override
	public int getX() {
		System.out.print("\n" + name + ":\nx: ");
		int x = scanner.nextInt();
		return --x;
	}

	@Override
	public int getY() {
		System.out.print("y: ");
		int y = scanner.nextInt();
		return --y;
	}
 }