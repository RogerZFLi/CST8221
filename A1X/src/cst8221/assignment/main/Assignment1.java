/**
 * File name: Assignment1.java
 * Roger Li - 040896855 & Denys Savskyi - 041004781
 * Course  CST 8221 - JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: June 5, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose: Assignment1.java is used to invoke the GUI game of Sudoku 
 */
package cst8221.assignment.main;

import java.awt.EventQueue;

import cst8221.assignment.window.MainWindow;


/**
 * Class Name: Assignment1
 * Method List: main()
 * Constant List: N/A
 * Purpose: Class Assignment1 is used to invoke the GUI game of Sudoku 
 * @author Roger Li
 * @author Denys Savskyi 
 * @version Version 2 (2022-06-05)
 * @see "Package: cst8221.assignment.main; Import: java.awt.EventQueue;"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-14
 */
public class Assignment1 { 
	
	/**
	 * Method Name: main
	 * Purpose: the start point of the program
	 * Algorithm: launch the program by calling invokelater() method of EventQueue
	 * @param args - command line interactive parameter
	 */
	public static void main(String[] args) {
		//invoke the game GUI
		EventQueue.invokeLater(()->new MainWindow());

	}
	
	

}
