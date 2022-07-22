/**
 * File name: GameRecord.java
 * Roger Li - 040896855 & Denys Savskyi - 041004781
 * Course  CST 8221 - JAP, Lab Section: 302
 * Assignment: A22
 * Professor: Paulo Sousa
 * Date: July 22, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose: GameRecord.java was created to manage the game record for the game of Sudoku. 
 * Uses various methods to get, set and update record, top 10 players and break record. As well as load.  
 */
package cst8221.assignment.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Class Name: GameRecord
 * Method List:  GameRecord(),loadRecord(), updateRecord(Progress p), saveRecord(), getTopSolution(), 
 * setTopSolution(Progress topSolution),getTop10Scores(), setTop10Scores(ArrayList top10Scores), isBreakRecord(), setBreakRecord(boolean breakRecord)
 * Constant List: serialVersionUID
 * Purpose: Сlass GameRecord was created to manage the game record for the game of Sudoku. 
 * Uses various methods to get, set and update record, top 10 players and break record. As well as load.  
 * @author Roger Li
 * @author Denys Savskyi
 *
 * @version Version 2 (2022-06-05)
 * @see "import java.io.File;java.io.FileInputStream;java.io.FileOutputStream;java.io.IOException;java.io.ObjectInputStream;
 * java.io.ObjectOutputStream;java.io.Serializable;java.util.ArrayList;java.util.Collections;"
 * @see "Implements: Serializable, Package: cst8221.assignment.model;"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-14
 */
public class GameRecord implements Serializable{
	
	/**
	 * sets serialVersionUID 
	 */
	
	private static final long serialVersionUID = 6180990269034121879L;


	private static final String RECORD_FILE_PATH = "records/record.rcd";
	private Progress topSolution;
	private ArrayList<Integer> top10Scores;
	private boolean breakRecord;
	
	/**
	 * Default constructor 
	 */
	public GameRecord() {
		breakRecord = false;
		topSolution = null;
		top10Scores = new ArrayList<>();
		
	}
	
	/**
	 * Method Name: loadRecord
	 * Purpose: Method loadRecord() of GameRecord loads record from file and adds it. 
	 * @return r - returns r of GameRecord 
	 */
	public GameRecord loadRecord() {
		File recordFile = new File(RECORD_FILE_PATH);//file object
		GameRecord r = null;
		if(recordFile.exists()) {//chceks if recordFiles exists 
			try (FileInputStream fis = new FileInputStream(recordFile);
				     ObjectInputStream ois = new ObjectInputStream(fis)) {
				r = (GameRecord)ois.readObject();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			r = new GameRecord();//creates new object 
		}
		System.out.println("Record list: ");
		r.getTop10Scores().forEach(i->System.out.println(i));
		return r;
	}
	
	/**
	 * Method Name: updateRecord
	 * Purpose: Method updateRecord() was created to update the record of players Sudoku. 
	 * @param p - var p of Progress   
	 */
	public void updateRecord(Progress p) {
		if(this.top10Scores.size()>=10 && this.top10Scores.get(9) > p.getPoint()) return;
		else {
			if(this.top10Scores.size()>0 && p.getPoint() > this.top10Scores.get(0)) {
				breakRecord = true;//record break sets to true 
				this.topSolution = p;
				this.top10Scores.add(0, this.topSolution.getPoint());
				if(this.top10Scores.size()>10) this.top10Scores.remove(10);
			}else {
				if(this.top10Scores.size()<10 || this.top10Scores.get(9)<p.getPoint()) {
					this.top10Scores.add(p.getPoint());
					Collections.sort(this.top10Scores);
					Collections.reverse(this.top10Scores);
					if(this.top10Scores.size()>10) this.top10Scores.remove(10);
				}
			}
			
			Collections.sort(this.top10Scores);//sors the top scores 
			Collections.reverse(this.top10Scores);
			saveRecord();
			
		}
		
		
	}
	
	/**
	 * Method Name: saveRecord
	 * Purpose: Method saveRecord() was created to save the current record of the game  
	 */
	public void saveRecord() {
		File fileToSave = new File(RECORD_FILE_PATH);
		try {
			fileToSave.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Save file
		if(fileToSave.exists()) {
			try (FileOutputStream fos = new FileOutputStream(fileToSave);
				     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(this);
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * Method Name: getTopSolution
	 * Purpose: Method getTopSolution() was created to get the best solution 
	 * @return topSolution - return the top solution 
	 */
	public Progress getTopSolution() {
		return topSolution;
	}

	/**
	 * Method Name: setTopSolution
	 * Purpose: Method setTopSolution() was created to set the best solution 
	 * @param topSolution - receives the top solution 
	 */
	public void setTopSolution(Progress topSolution) {
		this.topSolution = topSolution;
	}

	/**
	 * Method Name: getTop10Scores
	 * Purpose: Method getTop10Scores() was created to get the 10 best solutions 
	 * @return top10Scores - return the top 10 solutions 
	 */
	public ArrayList<Integer> getTop10Scores() {
		return top10Scores;
	}

	/**
	 * Method Name: setTop10Scores
	 * Purpose: Method setTop10Scores() was created to set the 10 best solutions
	 * @param top10Scores - receives the 10 top solutions 
	 */
	public void setTop10Scores(ArrayList<Integer> top10Scores) {
		this.top10Scores = top10Scores;
	}
	
	/**
	 * Method Name: isBreakRecord
	 * Purpose: Method isBreakRecord() was created to validate if record was broken 
	 * @return breakRecord - return the record breaker  
	 */
	public boolean isBreakRecord() {
		return breakRecord;
	}

	/**
	 * Method Name: setBreakRecord
	 * Purpose: Method setBreakRecord() was created to set the break record 
	 * @param breakRecord - receives the break record
	 */
	public void setBreakRecord(boolean breakRecord) {
		this.breakRecord = breakRecord;
	}
}
