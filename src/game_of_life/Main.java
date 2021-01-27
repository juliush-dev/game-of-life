package game_of_life;

import game_of_life.Grid.Cell;
import game_of_life.Grid.Coordinate;

public class Main {

	public static void main(String[] args) {

		GameOfLife gof = new GameOfLife(15);
		char dead = Cell.DEAD.charAt(0);
		char live = Cell.LIVE.charAt(0);
		char empty = Cell.EMPTY.charAt(0);
		int generation = 100;
		char[][] template = { 
				{ live, live, dead, dead, dead}, 
				{ live, dead, live, dead, dead, empty, dead}, 
				{ live, dead, dead, dead, live, empty, dead, empty, dead, live },
				{ live, dead, live, empty, dead, dead, live, dead },
				{ empty, live, dead, empty },
				{ live, live, dead, live, live, live, live, dead, dead, live, live, live, live, dead },
				{ live, dead, dead, live, dead, dead, dead, empty, dead, empty, live },
				{empty, dead, dead, live, dead},
				{empty,  empty, dead, empty, dead, live, dead, live, dead, dead, dead, live, live},
				{ dead, dead, live, dead, dead, dead, dead, empty, dead, live, dead, live, dead, live},
				{ live, live, dead, live, dead, empty, dead, empty, live },
				{ dead, dead, dead, empty, dead, empty, dead, live, dead, live, dead, empty, dead, empty },
				{ live, live, dead, live, live, live, live, dead, dead, live, live, dead, empty },
				{ live, dead, dead, empty, dead, empty, live, empty, dead, dead, live, dead, live },
				{ dead, live, empty, dead, dead, dead, empty, dead, empty, empty, empty, live, dead },
				{ live, live, dead, live, dead, empty, dead, empty, live, live, dead, live, dead, dead, dead },
				{ dead, dead, dead, live, live, empty, dead, live, empty, dead, empty, dead, live, live },
				{ dead, dead, live, dead, live, dead, dead, empty, dead, empty, empty, empty, live, dead },
				{ live, dead, empty, live, live, live, dead, live, live, dead, live, dead, dead, dead },
				{ dead, dead, dead, live, empty, dead, empty, dead, live, live },
				{dead, live, live, dead, empty },
				{ live, live, dead, live, dead, empty, dead, dead, dead },
				{ dead, dead, dead, empty, dead, live, live },
				{ dead, dead, live, dead, live, dead, dead, empty, dead, empty, empty, empty, live, dead },
				{ live, dead, empty, live, dead, dead, dead },
				{ dead, dead, live, live },
				{ live, live, dead, empty }
		};
		gof.gameOfLife(template, generation);
		gof.play();
	}

	static class GameOfLife {
		private int generation = 0;
		private int gridSize = 0;
		private Grid grid = null;

		public GameOfLife(int gridSize, int generation) {
			this.gridSize = gridSize;
			grid = new Grid(this.gridSize);
			this.generation = generation;
		}

		public GameOfLife(int gridSize) {
			this.gridSize = gridSize;
			grid = new Grid(this.gridSize);
		}

		public void setAlive(Coordinate... coords) {
			for (Coordinate co : coords) {
				if (!grid.isOutOfBound(co)) {
					System.out.println(co + " is valid");
					grid.setAlive(co, true);
				} else {
					System.out.println(co + " isn't valid");
				}
			}
		}

		public void setAlive(char[][] template) {
			int row = 0, col = 0;

			for (char[] rowChar : template) {
				if (row == grid.size)
					break;
				for (char colChar : rowChar) {
					if (col == grid.size)
						break;
					Coordinate co = new Coordinate(row, col);
					if (!grid.isOutOfBound(co)) {
						if (Cell.DEAD.charAt(0) == colChar)
							grid.setAlive(co, false);
						else if (Cell.LIVE.charAt(0) == colChar)
							grid.setAlive(co, true);
					}
					col++;
				}
				col = 0;
				row++;
			}
		}

		private int getAliveNeighboursOfSize(Cell cell) {
			int aliveNeighbours = 0;
			for (Coordinate c : cell.getNeighbours().values()) {
				Cell neighbour = grid.getCells()[c.getrow()][c.getcol()];
				if (neighbour.isAlive()) {
					aliveNeighbours += 1;
				}
			}
			return aliveNeighbours;
		}

		public void play() {
			System.out.println("Game start");
			for (int i = 0; i < generation; i++) {
				System.out.println("Generation: " + i);
				showGrid();
				Grid nexGen = new Grid(gridSize);
				for (Cell[] cells : grid.getCells()) {
					for (Cell cell : cells) {
						int aliveNeighboursSum = getAliveNeighboursOfSize(cell);
						if (cell.isAlive()) {
							if (aliveNeighboursSum < 2 || aliveNeighboursSum > 3) {
								nexGen.setAlive(cell.getCoordinate(), false);
							} else {
								nexGen.setAlive(cell.getCoordinate(), true);
							}
						} else if (aliveNeighboursSum == 3) {
							nexGen.setAlive(cell.getCoordinate(), true);
						} else {
							nexGen.setAlive(cell.getCoordinate(), false);
						}
					}
				}
				grid = nexGen;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

		public void showGrid() {
			grid.show();
		}

		public void gameOfLife(char[][] template, int generation) {
			this.generation = generation;
			setAlive(template);
		}
	}

}
