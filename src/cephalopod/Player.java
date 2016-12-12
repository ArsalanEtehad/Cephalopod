package cephalopod;

public class Player {
	protected int moveId;
	protected String name;

	public void setName(String name){
		this.name = name;
	}
	public void setMoveId(int moveId) {
		this.moveId = moveId;
	}

	public int getMoveId() {
		return moveId;
	}

	public int getX() {
		return (Integer) null;
	}

	public int getY() {
		return (Integer) null;
	}

	public void setRange(int x, int y) {
	}

	public void availabilityReciever() {
	}
}