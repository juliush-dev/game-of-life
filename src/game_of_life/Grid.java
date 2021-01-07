package game_of_life;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Grid {
	static class Coordinate {
		private int row, col;

		public Coordinate(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public void setcol(int value) {
			this.col = value;
		}

		public void setrow(int value) {
			this.row = value;
		}

		public int getcol() {
			return this.col;
		}

		public int getrow() {
			return this.row;
		}

		@Override
		public String toString() {
			return String.format("(row:%s, col:%s)", row, col);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + col;
			result = prime * result + row;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coordinate other = (Coordinate) obj;
			if (col != other.col)
				return false;
			if (row != other.row)
				return false;
			return true;
		}
		
		
	}

	static enum Directions {
		N("N"), S("S"), W("W"), E("E"), NW("NW"), NE("NE"), SW("SW"), SE("SE");

		String val;

		Directions(String val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return val.length() == 2 ? val : (val += " ");
		}
	}

	static class Cell {
		private static final String LIVE = "x";
		private static final String DEAD = " ";
		private Coordinate co;
		private boolean isAlive;
		private Map<Directions, Coordinate> neighbours;

		public Cell(Coordinate co) {
			this.co = co;
			isAlive = false;
			neighbours = new HashMap<>();
		}

		public Coordinate getCoordinate() {
			return this.co;
		}

		public Map<Directions, Coordinate> getNeighbours() {
			return neighbours;
		}

		private String defaultStr() {
			String str = "--Cell--";
			str += "Cordinate: " + co + "\n";
			str += "Alive: " + isAlive + "\n";
			str += "NeighboursSize: " + neighbours.size() + "\n";
			return str;
		}
		public String toString(boolean showNeighbours) {
			String str = defaultStr();
			if (showNeighbours) {
				str += "Neighbours: \n";
				for (Directions dirc : neighbours.keySet()) {
					str += String.format("\t%s-> %s\n", dirc, neighbours.get(dirc));
				}
			}
			return str;
		}
		
		@Override
		public String toString() {
			return defaultStr();
		}

		public String getState() {
			String str = isAlive ? LIVE : DEAD;
			return str;
		}

		private void setAlive(boolean val) {
			isAlive = val;
		}

		public boolean isAlive() {
			return isAlive;
		}

	}

	int size;
	int inner = 0;
	Cell[][] cells;
	String grid = "";
	int gridLineLength = 0;
	int gridLength = 0;

	public Grid() {
	}

	public Grid(int size) {
		this.size = size;
		inner = this.size - 1;
		cells = new Cell[size][size];
		initGrid();
		gridLineLength = 2 * size + 2;
		gridLength = gridLineLength * size;
	}

	private void initGrid() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				Cell cell = new Cell(new Coordinate(row, col));
				initiateNeighboursOf(cell);
				cells[row][col] = cell;
				grid += ((col == inner) ? ("|" + cell.getState() + "|\n") : ("|" + cell.getState()));
			}
		}
	}

	public void updateWith(Cell cell) {
		int at = indexOnGrid(cell);
		String[] splitedGrid = grid.split("|");
		splitedGrid[at] = cell.getState();
		grid = String.join("", splitedGrid);
	}
	
	public int indexOnGrid(Cell cell) {
		return cell.getCoordinate().getrow() * gridLineLength + 2 * cell.getCoordinate().getcol() + 1;
	}

	public void show() {
		System.out.println(grid);
	}

	private void initiateNeighboursOf(Cell cell) {
		for (Directions dirc : Directions.values()) {
			Coordinate co = computeCoordinateWith(dirc, cell.getCoordinate());
			if (!isOutOfBound(co)) {
				cell.getNeighbours().put(dirc, co);
			}
		}
	}

	public boolean isOutOfBound(Coordinate co) {
		return co.getcol() > inner || co.getrow() > inner || co.getcol() < 0 || co.getrow() < 0;
	}

	private Coordinate computeCoordinateWith(Directions dirc, Coordinate ref) {
		int col = ref.getcol(), row = ref.getrow();
		int colDecrem = col - 1;
		int rowDecrem = row - 1;
		int colIncrem = col + 1;
		int rowIncrem = row + 1;
		switch (dirc) {
		case NW -> {
			col = colDecrem;
			row = rowDecrem;
		}
		case N -> row = rowDecrem;
		case NE -> {
			col = colIncrem;
			row = rowDecrem;
		}
		case W -> col = colDecrem;
		case E -> col = colIncrem;
		case SW -> {
			col = colDecrem;
			row = rowIncrem;
		}
		case S -> row = rowIncrem;
		case SE -> {
			col = colIncrem;
			row = rowIncrem;
		}
		}
		return new Coordinate(row, col);
	}

	public void setAlive(Coordinate co, boolean val) {
		Cell cell = cells[co.getrow()][co.getcol()];
		cell.setAlive(val);
		updateWith(cell);
//		show();
	}
	
	public Cell[][] getCells(){
		return cells;
	}
	
	public void showAllAlive() {
		for(Cell[] row : cells) {
			for(Cell c : row) {
				if(c.isAlive) {
					System.out.println(c);
				}
			}
		}
	}

}