package cst8221.assignment.model;

public class Record {
	
	private static final String RECORD_FILE_PATH = "records/record.txt";
	private Solution topSolution;
	private boolean recordBroken;
	private String[] top10Scores;
	
	public Record() {
		// TODO Auto-generated constructor stub
	}
	
	public void readRecord() {
		
	}
	
	public void loadRecord() {
		
		
	}

	public Solution getTopSolution() {
		return topSolution;
	}

	public void setTopSolution(Solution topSolution) {
		this.topSolution = topSolution;
	}

	public boolean isRecordBroken() {
		return recordBroken;
	}

	public void setRecordBroken(boolean recordBroken) {
		this.recordBroken = recordBroken;
	}

	public String[] getTop10Scores() {
		return top10Scores;
	}

	public void setTop10Scores(String[] top10Scores) {
		this.top10Scores = top10Scores;
	}

}
