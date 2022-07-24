/**
 * File name: Progress.java
 * Roger Li - 040896855 & Denys Savskyi - 041004781
 * Course  CST 8221 - JAP, Lab Section: 302
 * Assignment: A22
 * Professor: Paulo Sousa
 * Date: July 22, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose: Progress.java was created to manage the progress of the Sudoku game implementation.
 * This class helps to set, get dimension and numbers, time and points. 
 */
package cst8221.assignment.model;

import java.io.Serializable;

/**
 * Class Name: Progress
 * Method List:  Progress(), Progress(int dim), Progress(Progress p),Progress(int dim, int point, String time, String[][] numbers), 
 * updateProgress(int point, String time, int i, int j, String num), getDim(), setDim(int dim), getPoint(), setPoint(int point),
 * getTime(), setTime(String time), getNumbers(), setNumbers(String[][] numbers),resetProgress(int dim) 
 * Constant List: serialVersionUID
 * Purpose: Сlass Progress was created to manage the progress of the Sudoku game implementation.
 * This class helps to set, get dimension and numbers, time and points
 * @author Roger Li
 * @author Denys Savskyi
 *
 * @version Version 2 (2022-06-05)
 * @see "java.io.Serializable;"
 * @see "Implements: Serializable, Package: cst8221.assignment.model;"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-14
 */
public class Progress implements Serializable{

	/**
	 * default serial id
	 */
	private static final long serialVersionUID = 1L;
	private int dim;
	private int point;
	private String time;
	private String[][] numbers;
	
	/**
	 * Default constructor 
	 */
	public Progress() {
		this(2);
	}
	
	/**
	 * Parametrized Default constructor 
	 */
	public Progress(int dim) {
		this(dim,0,"0", new String[dim * dim][dim * dim]);
	}
	
	/**
	 * Parametrized Default constructor
	 * @param p - var p of Progress  
	 */
	public Progress(Progress p) {
		this(p.getDim(), p.getPoint(), p.getTime(), p.getNumbers());
	}
	
	/**
	 * Parametrized Default constructor
	 * @param dim - var dim of int  
	 * @param point - var point of int 
	 * @param time - var time of String 
	 * @param numbers - var numbers of String[][] 
	 */
	public Progress(int dim, int point, String time, String[][] numbers) {
		this.dim = dim;
		this.point = point;
		this.time = time;
		this.numbers = numbers;
	}
	
	
	/**
	 * Method Name: updateProgress
	 * Purpose: Method updateProgress() was created to update progress (point, time and numbers) of the game Sudoku.  
	 * @param point - var point of int
	 * @param time - var time of String 
	 * @param i - var time of int 
	 * @param j - var numbers of int 
	 * @param num - var num of String 
	 */
	public void updateProgress(int point, String time, int i, int j, String num) {
		this.point = point;
		this.time = time;
		this.numbers[i][j] = num;
	}
	
	/**
	 * Method Name: getDim
	 * Purpose: Method getDim() was created to get the dimension 
	 * @return dim - return the dimension 
	 */
	public int getDim() {
		return dim;
	}

	/**
	 * Method Name: setDim
	 * Purpose: Method setTopSolution() was created to set the dimension 
	 * @param dim - receives the dimension 
	 */
	public void setDim(int dim) {
		this.dim = dim;
	}

	/**
	 * Method Name: getPoint
	 * Purpose: Method getPoint() was created to get the points of the ssesion 
	 * @return point - return the points of the game  
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * Method Name: setPoint
	 * Purpose: Method setPoint() was created to set the points  
	 * @param point - receives the points
	 */
	public void setPoint(int point) {
		this.point = point;
	}

	/**
	 * Method Name: getTime
	 * Purpose: Method getTime() was created to get the time  
	 * @return time - return time of the game 
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Method Name: setTime
	 * Purpose: Method setTime() was created to set the time 
	 * @param time - receives the time  
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Method Name: getNumbers
	 * Purpose: Method getNumbers() was created to get the numbers of the game 
	 * @return numbers - return the numbers of the game  
	 */
	public String[][] getNumbers() {
		return numbers;
	}

	/**
	 * Method Name: setNumbers
	 * Purpose: Method setNumbers() was created to set the numbers of the game 
	 * @param numbers - receives the numbers 
	 */
	public void setNumbers(String[][] numbers) {
		this.numbers = numbers;
	}

	/**
	 * Method Name: resetProgress
	 * Purpose: Method resetProgress() was created to reset the points, time and points of the game. 
	 * @param dim - var dim of int  
	 */
	public void resetProgress(int dim) {
		this.dim = dim;
		this.point = 0;
		this.time = "0";
		this.numbers = new String[dim * dim][dim * dim];
		
	}
	

}
