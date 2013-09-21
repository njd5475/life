package com.njd.life;

public class Location {

	private int	row;
	private int	col;
	private int	hashCode;

	protected Location(int col, int row) {
		this.row = row;
		this.col = col;
		//use the MD5 hash code of a unique string
		this.hashCode = String.format("%dx%d", col,row).hashCode();
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}
	
	public String toString() {
		return String.format("(%d, %d)", col, row);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Location) {
			Location l = (Location) o;
			return l.hashCode() == hashCode;
		}
		return false;
	}
	
	public static Location valueOf(int col, int row) {
		return new Location(col, row);
	}

	public static Location[] neighbors(Location l) {
		int col = l.getCol();
		int row = l.getRow();
		return new Location[] { valueOf(col + 1, row),
				valueOf(col + 1, row + 1), valueOf(col, row + 1),
				valueOf(col - 1, row), valueOf(col - 1, row - 1),
				valueOf(col, row - 1), valueOf(col + 1, row - 1),
				valueOf(col - 1, row + 1) };
	}
	
	public static void main(String...args) {
		if(valueOf(32,27).hashCode() == valueOf(32, 27).hashCode()) {
			System.out.println("Hashcode uniq to row col values");
		}
	}
}
