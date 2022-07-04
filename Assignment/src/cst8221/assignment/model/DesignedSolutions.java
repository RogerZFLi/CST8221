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


public class DesignedSolutions implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final DesignedSolutions SOLUTIONS = null;
	
	private static SortedSet<Progress> solutions2D;
	private static SortedSet<Progress> solutionsD;
	private static SortedSet<Progress> solutions4D;
	
	public static DesignedSolutions getSolutions() {
		if (SOLUTIONS == null) {
			if(readSolutions()) {
				
			}
			
		}
		return SOLUTIONS;
	}

	private static boolean readSolutions() {
		// TODO Auto-generated method stub
		return false;
	}

	public static void updateSolutions(Progress p) {
		
	}
	
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
