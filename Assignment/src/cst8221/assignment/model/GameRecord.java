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



public class GameRecord implements Serializable{
	
	private static final long serialVersionUID = 6180990269034121879L;
	/**
	 * 
	 */

	private static final String RECORD_FILE_PATH = "records/record.rcd";
	private Progress topSolution;
	private ArrayList<Integer> top10Scores;
	private boolean breakRecord;
	
	public GameRecord() {
		breakRecord = false;
		topSolution = null;
		top10Scores = new ArrayList<>();
		
	}
	
	public GameRecord loadRecord() {
		File recordFile = new File(RECORD_FILE_PATH);
		GameRecord r = null;
		if(recordFile.exists()) {
			try (FileInputStream fis = new FileInputStream(recordFile);
				     ObjectInputStream ois = new ObjectInputStream(fis)) {
				r = (GameRecord)ois.readObject();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			r = new GameRecord();
		}
		System.out.println("Record list: ");
		r.getTop10Scores().forEach(i->System.out.println(i));
		return r;
	}
	
	public void updateRecord(Progress p) {
		if(this.top10Scores.size()>=10 && this.top10Scores.get(9) > p.getPoint()) return;
		else {
			if(this.top10Scores.size()>0 && p.getPoint() > this.top10Scores.get(0)) {
				breakRecord = true;
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
			
			Collections.sort(this.top10Scores);
			Collections.reverse(this.top10Scores);
			saveRecord();
			
		}
		
		
	}
	
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

	public Progress getTopSolution() {
		return topSolution;
	}

	public void setTopSolution(Progress topSolution) {
		this.topSolution = topSolution;
	}

	public ArrayList<Integer> getTop10Scores() {
		return top10Scores;
	}

	public void setTop10Scores(ArrayList<Integer> top10Scores) {
		this.top10Scores = top10Scores;
	}

	public boolean isBreakRecord() {
		return breakRecord;
	}

	public void setBreakRecord(boolean breakRecord) {
		this.breakRecord = breakRecord;
	}
}
