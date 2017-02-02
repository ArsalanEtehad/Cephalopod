package cephalopod;

public abstract class Player {
	protected int type;
	protected String name;

	public void setName(String name) {
		this.name = name;
	}

	public void setMoveId(int moveId) {
		this.type = moveId;
	}

	public int getMoveId() {
		return type;
	}

	public abstract int getX();

	public abstract int getY();

	public void setRange(int x, int y) {
	}

	public void availabilityReciever() {
	}

	public abstract void setBoard(Board board);
}