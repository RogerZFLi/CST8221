package cst8221.assignment.model;

import java.io.Serializable;

public class Progress implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dim;
	private int point;
	private String time;
	private String[][] numbers;
	
	public Progress() {
		this(2);
	}
	
	public Progress(int dim) {
		this(dim,0,"0", new String[dim * dim][dim * dim]);
	}
	
	public Progress(Progress p) {
		this(p.getDim(), p.getPoint(), p.getTime(), p.getNumbers());
	}
	
	public Progress(int dim, int point, String time, String[][] numbers) {
		this.dim = dim;
		this.point = point;
		this.time = time;
		this.numbers = numbers;
	}
	
	

	public void updateProgress(int point, String time, int i, int j, String num) {
		this.point = point;
		this.time = time;
		this.numbers[i][j] = num;
	}
	
	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String[][] getNumbers() {
		return numbers;
	}

	public void setNumbers(String[][] numbers) {
		this.numbers = numbers;
	}

	public void resetProgress(int dim) {
		this.dim = dim;
		this.point = 0;
		this.time = "0";
		this.numbers = new String[dim * dim][dim * dim];
		
	}
	

}
