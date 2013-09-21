package com.njd.life;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Grid {

	private HashSet<Location>	grid		= new HashSet<Location>();

	private boolean				paused		= false;
	private int					tileWidth	= 10;
	private int					tileHeight	= 10;

	public Grid() {
	}

	public void clear() {
		grid = new HashSet<Location>();
	}
	
	public void pause() {
		paused = true;
	}

	public void unpause() {
		paused = false;
	}

	public void add(int col, int row) {
		grid.add(Location.valueOf(col, row));
	}

	public void remove(int col, int row) {
		grid.add(Location.valueOf(col, row));
	}

	public void render(Graphics2D g, int width, int height) {
		g.setColor(Color.black);
		for (Location l : grid) {
			renderLocation(g, l);
		}
	}

	public void step() {
		if (!paused) {
			HashSet<Location> newGrid = new HashSet<Location>();
			HashSet<Location> checkForNewLife = new HashSet<Location>();

			for (Location l : grid) {
				int n = aliveNeighbors(grid, l);
				if (n == 2 || n == 3) {
					newGrid.add(l);
				}
				checkForNewLife.addAll(Arrays.asList(Location.neighbors(l)));
			}

			// create new life
			for (Location l : checkForNewLife) {
				if (!grid.contains(l)) {
					// no life yet
					int n = aliveNeighbors(grid, l);
					if (n == 3) {
						newGrid.add(l);
					}
				}
			}

			grid = newGrid;
		}
	}

	public void renderLocation(Graphics2D g, Location l) {
		g.fillRect(l.getCol() * tileWidth - tileWidth / 2, l.getRow()
				* tileHeight - tileHeight / 2, tileWidth, tileHeight);
	}

	public int aliveNeighbors(HashSet<Location> grids, Location l) {
		Location[] neighbors = Location.neighbors(l);
		int count = 0;
		for (Location neighbor : neighbors) {
			if (grids.contains(neighbor)) {
				++count;
			}
		}
		return count;
	}

	public void togglePause() {
		paused = !paused;
	}

	public boolean isPaused() {
		return paused;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

}
