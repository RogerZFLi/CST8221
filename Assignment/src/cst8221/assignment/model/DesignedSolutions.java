/**
 * File name: DesignedSolutions.java
 * Roger Li - 040896855 & Denys Savskyi - 041004781
 * Course  CST 8221 - JAP, Lab Section: 302
 * Assignment: A22
 * Professor: Paulo Sousa
 * Date: July 22, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose: DesignedSolutions.java was created to manage the solutions for the game of Sudoku. 
 * Uses various methods to get, read and update solutions for the Sudoku game implementation. 
 */
package cst8221.assignment.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.SortedSet;

import javax.swing.JButton;

/**
 * Class Name: DesignedSolutions
 * Method List:  getSolutions(),readSolutions(),  updateSolutions(Progress p), readSolutions(int dim)
 * Constant List: serialVersionUID
 * Purpose: Сlass DesignedSolutions was created to manage the solutions for the game of Sudoku. 
 * Uses various methods to get, read and update solutions for the Sudoku game implementation. 
 * @author Roger Li
 * @author Denys Savskyi
 *
 * @version Version 2 (2022-06-05)
 * @see "import java.io.File;java.io.FileInputStream;java.io.IOException;java.io.ObjectInputStream;
 * java.io.Serializable;java.security.SecureRandom;java.util.ArrayList;java.util.HashSet;java.util.Scanner;java.util.SortedSet;javax.swing.JButton;"
 * @see "Implements: Serializable, Package: cst8221.assignment.model;"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-14
 */
public class DesignedSolutions implements Serializable {
	
	/**
	 * default serial id
	 */
	private static final long serialVersionUID = 1L;
	
	private static final DesignedSolutions SOLUTIONS = null;
	
	private static SortedSet<Progress> solutions2D;
	private static SortedSet<Progress> solutions3D;
	private static SortedSet<Progress> solutions4D;
	
	/**
	 * Method Name: getSolutions
	 * Purpose: Method getSolutions() to get the solution of teh Sudoku game 
	 * @return SOLUTIONS - if solutions is not null 
	 */
	public static DesignedSolutions getSolutions() {
		if (SOLUTIONS == null) {
			if(readSolutions()) {
				
			}
			
		}
		return SOLUTIONS;
	}

	/**
	 * Method Name: readSolutions
	 * Purpose: Method readSolutions()  
	 * @return false - false 
	 */
	private static boolean readSolutions() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Method Name: updateSolutions
	 * Purpose: Method updateSolutions() was created to update the solution 
	 * @param p - Progress var 
	 */
	public static void updateSolutions(Progress p) {
		
	}
	
	/**
	 * Method Name: readSolutions
	 * Purpose: Method readSolutions() was created to read from file and read the solution from the file to the Sudoku game. 
	 * Algorithm: Method readSolutions() creates File var for file to and read teh solution from teh file. 
	 * @param dim - int param 
	 */
	public static boolean readSolutions(int dim) {
		File fileToRead = new File("designs/" + dim);
		
		//Read progress from file
		if(fileToRead!=null) {
			try (FileInputStream fis = new FileInputStream(fileToRead);
				     ObjectInputStream ois = new ObjectInputStream(fis)) {
				//solutions = (ArrayList<Progress>)ois.readObject();
				
			}catch(Exception ioe) {
				ioe.printStackTrace();
			}
		}else {
			//solutions = new ArrayList<Progress>();
		}
		return false;

	}

}
