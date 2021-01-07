package game_of_life;

import game_of_life.Grid.Cell;
import game_of_life.Grid.Coordinate;

public class Main {

	public static void main(String[] args) {
		GameOfLife gof = new GameOfLife(8, 100);
//		gof.setAlive(new Coordinate(2, 3),
//				new Coordinate(3, 3), 
//				new Coordinate(4, 3),
//				new Coordinate(3, 7),
//				new Coordinate(3, 6),
//				new Coordinate(3, 5),
//				new Coordinate(3, 1),
//				new Coordinate(2, 7),
//				new Coordinate(2, 2),
//				new Coordinate(2, 3),
//				new Coordinate(4, 3));

		gof.setAlive(new Coordinate(2, 3), new Coordinate(2, 3), new Coordinate(3, 3), new Coordinate(3, 4),
				new Coordinate(3, 8), new Coordinate(4, 0), new Coordinate(4, 1), new Coordinate(4, 2),
				new Coordinate(3, 2), new Coordinate(3, 5), new Coordinate(6, 3));

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
						} else {
							if (aliveNeighboursSum == 3) {
								nexGen.setAlive(cell.getCoordinate(), true);
							}
						}
					}
				}
				grid = nexGen;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

		public void showGrid() {
			grid.show();
		}
	}

}
