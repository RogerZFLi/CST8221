package cst8221.assignment.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import cst8221.assignment.view.ActionField;
import cst8221.assignment.view.MainWindow;
import cst8221.assignment.view.PlayField;

public class GameController {
	
	
	
	private static final MainWindow GAME_WINDOW = MainWindow.loadGame();
	private static GameController GAME_CONTROLLER = null;
	
	private int[] numberCounter;
	private int totalCounter;
	
	private GameController() {
		
	}
	
	public static GameController getController() {
		if(GAME_CONTROLLER == null)
			GAME_CONTROLLER = new GameController();
		return GAME_CONTROLLER;
	}
	
	/**
	 * Method Name: resetGame
	 * Purpose: Method resetGame() is used if user wants to reset the entries during the game. 
	 * Algorithm: On playField calls method reload() with appropriate vars and resets the game. 
	 */
	public void resetGame() {
		GAME_WINDOW.getPlayField().reload(GAME_WINDOW, GAME_WINDOW.getActionField().getDimSelected()==0?2:GAME_WINDOW.getActionField().getDimSelected());
		GAME_WINDOW.getPlayField().setNumSelected(null);
		GAME_WINDOW.getActionField().reset(); //uses reset() method to reset actionField
		for(int i = 0; i < numberCounter.length; i++) {
			numberCounter[i] = 0;
		}
		totalCounter = 0;
		
	}
	
	public void reloadPlayField() {
		GAME_WINDOW.getPlayField().removeAll();
		GAME_WINDOW.getPlayField().load(GAME_WINDOW, GAME_WINDOW.getActionField().getDimSelected());
		GAME_WINDOW.getPlayField().invalidate();
		GAME_WINDOW.getPlayField().validate();
		GAME_WINDOW.getPlayField().repaint();
	}
	
	public void resetActionField() {
		if (!GAME_WINDOW.getActionField().isPlayMode())//checks if it is play mode 
			return;
		GAME_WINDOW.getActionField().getPoint().setText("0");//sets points and time to 0 
		GAME_WINDOW.getActionField().getTime().setText("0");
		
		GAME_WINDOW.getActionField().resetTimer();
	}
	
	public void resetCounters(int dim) {
		numberCounter = new int[(int)Math.pow(dim, 2)];
		totalCounter = 0;
	}
	
	/**
	 * Method Name: loadProgress
	 * Purpose: Method loadProgress() is used to Load existing file as a configuration for the Sudoku game. 
	 * Algorithm: Uses JFileChooser to select the file, chooses the file and uses scanner to scan the details of the game, then sets all these details 
	 * to actual Sudoku game. Catches the errors. 
	 */
	public void loadProgress() {
		
		GAME_WINDOW.log("Loading progress....");
		GAME_WINDOW.setFromFile(true);
		GAME_WINDOW.getLogField().getPlayRadioButton().setSelected(true);
		//Open current project folder location to save file
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setDialogTitle("Specify a file to load");   
		 
		int userSelection = fileChooser.showSaveDialog(GAME_WINDOW);
		File fileToRead = null;
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToRead = fileChooser.getSelectedFile();
		    System.out.println("Save as file: " + fileToRead.getAbsolutePath());
		}
		//Read progress from file
		if(fileToRead!=null) {
			try (Scanner scanner = new Scanner(fileToRead)){
				int dimNumber = scanner.nextInt();
				scanner.nextLine();
				String level = scanner.nextLine();
				String point = scanner.nextLine();
				String time = scanner.nextLine();
				GAME_WINDOW.getActionField().getDim().setSelectedItem(dimNumber);
				GAME_WINDOW.getActionField().getLevel().setSelectedItem(level);
				GAME_WINDOW.getActionField().getPoint().setText(point);
				GAME_WINDOW.getActionField().getTime().setText(time);
				JButton[][] btns = GAME_WINDOW.getPlayField().getNumberJButtons();
				
				for(int i=0; i<btns.length; i++) {
					String line = null;
					if(scanner.hasNext()) {
						line = scanner.nextLine();
						System.out.println(line);
						String[] numbers = line.split(",");
						for(int j=0; j<btns[i].length; j++) {
							if(!numbers[j].isBlank()) {
								fillNumber(GAME_WINDOW, btns[i][j], numbers[j], dimNumber,true);
							}
						}
					}
				}
				GAME_WINDOW.log("Progress loaded....");
			} catch (IOException e) {
				GAME_WINDOW.log("Fail to load progress...");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method Name: loadMasked
	 * Purpose: Method loadMasked() is used to Load game with masked numbers based on difficulty level selected. 
	 * Algorithm: Uses JFileChooser to select the file, chooses the file and uses scanner to scan the details of the game, then sets all these details 
	 * to actual Sudoku game. Catches the errors. 
	 * @param difficulty Defines the difficulty level, easy, medium or hard
	 * @param dim the dimension to be loaded to
	 */
	public void loadMasked(String difficulty, int dim) {
		
		GAME_WINDOW.log("Loading game with " + difficulty + " mode...");
		double maskRate = 0;
		switch(difficulty) {
			case PlayField.EASY:
				maskRate = 0.25;
				
				break;
			case PlayField.MEDIUM:
				maskRate = 0.5;
				break;
			case PlayField.HARD:
				maskRate = 0.75;
				break;
			default:
				System.err.println("Error happened");
		}
		File fileToRead = new File("Level/" + dim);
		
		//Read progress from file
		if(fileToRead!=null) {
			try (Scanner scanner = new Scanner(fileToRead)){

				JButton[][] btns = GAME_WINDOW.getPlayField().getNumberJButtons();
				//generate musked buttons
				HashSet<JButton> maskedButtons = new HashSet<>();
				for(int i=0; i<dim; i++) {
					for(int j=0; j<dim; j++) {
						int maskCounter = 0;
						ArrayList<JButton> buttons = GAME_WINDOW.getPlayField().getDimBlocks()[i][j].getButtons();
						SecureRandom sr = new SecureRandom();
						int indexToMask = -1;
						do {
							indexToMask = sr.nextInt(dim*dim);
							if(maskedButtons.contains(buttons.get(indexToMask))) {
								continue;
							}
							maskedButtons.add(buttons.get(indexToMask));
							maskCounter++;
						}while((double)maskCounter/(dim*dim)<maskRate);	
					}
				}
				//load solution with masked numbers
				for(int i=0; i<btns.length; i++) {
					String line = null;
					if(scanner.hasNext()) {
						line = scanner.nextLine();
						String[] numbers = line.split(",");
						for(int j=0; j<btns[i].length; j++) {
							if(!numbers[j].isBlank()) {
								if(!maskedButtons.contains(btns[i][j]))
									fillNumber(GAME_WINDOW, btns[i][j], numbers[j], dim,true);
							}
						}
					}
				}
				
				GAME_WINDOW.log(difficulty + " mode loaded....");
			} catch (IOException e) {
				GAME_WINDOW.log("Fail to load " + difficulty + " mode...");
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Method Name: saveProgress
	 * Purpose: Void Method saveProgress() is used to save the current game progress to a specific file. 
	 * Algorithm: Using JFileChooser to create a new file and save to specific location. 
	 */
	public void saveProgress() {
		GAME_WINDOW.log("Saving progress....");
		StringBuilder sb = new StringBuilder();
		JButton[][] btns = GAME_WINDOW.getPlayField().getNumberJButtons();
		//uses getter methods to get the values. 
		sb.append(GAME_WINDOW.getActionField().getDimSelected());
		sb.append("\n");
		sb.append(GAME_WINDOW.getActionField().getLevel().getSelectedItem());
		sb.append("\n");
		sb.append(GAME_WINDOW.getActionField().getPoint().getText());
		sb.append("\n");
		sb.append(GAME_WINDOW.getActionField().getTime().getText());
		sb.append("\n");
		
		for(int i=0; i<btns.length; i++) {
			for(int j=0; j<btns.length; j++) {
				String numString = btns[i][j].getText();
				if(numString == null)
					sb.append(" ");
				else {
					sb.append(numString);
				}
				if(j<btns[i].length - 1)
					sb.append(",");
			}
			sb.append("\n");
		}
		
		//Open current project folder location to save file
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setDialogTitle("Specify a file to save");   
		 
		int userSelection = fileChooser.showSaveDialog(GAME_WINDOW);
		File fileToSave = null;
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = fileChooser.getSelectedFile();
		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
		//Save file
		if(fileToSave!=null) {
			try (FileWriter fw = new FileWriter(fileToSave)){
				fw.write(sb.toString());
				GAME_WINDOW.log("Progress saved....");
			} catch (IOException e) {
				GAME_WINDOW.log("Fail to save progress...");
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	/**
	 * Method Name: rand
	 * Purpose: Method rand() generate a random number and load as the dimension of the game. 
	 * Algorithm: Creates the object of SecureRandom, then uses newInt to return pseudorandom number and use it to set the dimension.  
	 */
	public void rand() {
		SecureRandom sRandom = new SecureRandom();
		int num = sRandom.nextInt(3) + 2; //sets int num to "random" number 
		GAME_WINDOW.getActionField().getDim().setSelectedItem(num);
		GAME_WINDOW.log("Randomly selected Dim: " + num + " ....");//adds the log info 
	}
	
	
	/**
	 * Method Name: fillNumber
	 * Purpose: Method fillNumber() is used to implement the functionality of the buttons to set the selected by user values for the game. 
	 * Algorithm: This method checks if button is not null and sets the appropriate number and color (cyan) for the specific button and increments counter. 
	 * After that it prints the message in the history panel. Then checks if the board is finished and plays the "complete" sound.
	 * Then checks if the step is completed and plays the "good job" sound. If only one is correct, plays "correct" sound.    
	 * @param window - parameter of class MainWindow 
	 * @param btn - button
	 * @param number - String parameter 
	 * @param dim - integer parameter of the dimension 
	 * @param muted - boolean parameter
	 */
	public void fillNumber(MainWindow window, JButton btn, String number, int dim, boolean muted) {
		int numRep = -1;
		String originalNumber = null;
		try {
			numRep = Integer.parseInt(number);
		}catch(NumberFormatException ex) {
			numRep = number.charAt(0) - 55;
		}
		
		if(btn.getText()!=null)	{//checks if button is not null 
			numberCounter[numRep-1]--;
			totalCounter--;
			originalNumber = btn.getText();
			revertToUncomplete(originalNumber);
		}
		btn.setText(number);
		if(!muted) btn.setBackground(Color.CYAN);//sets the background color 
		GAME_WINDOW.getPlayField().getCellsTakenMap().put(btn.getName(), number);
		
		numberCounter[numRep-1]++;//increments counter 
		totalCounter++;
		
		window.log(number + " was put to Row "+ btn.getName().charAt(0) + ", " + " Col " + btn.getName().charAt(1) +"...");//displays logs 
		String soundName = null;
		if(totalCounter == (int) (Math.pow(dim, 2)) * (int) (Math.pow(dim, 2))) {//checks if the game is completed 
			soundName = "complete.wav"; 
			if(!muted) complete();//calls complete method 
			ActionField.setTimerStop(true);
		}else if(numberCounter[numRep-1]  == dim * dim) {//checks if the whole step is completed 
			soundName = "goodjob.wav";
			if(!muted) stepComplete(number);
		}else
			soundName = "correct.wav";  
		if(muted) return;
		//https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}catch(Exception ex) {
			System.err.println("Error happened");
		}
	}
	
	
	/**
	 * Method Name: stepComplete
	 * Purpose: Method stepComplete() is used to set background color to Blue if the one digit is finished.
	 * Algorithm: Method stepComplete() uses nested loops to check if one digit is finished and sets Background color to Blue. 
	 * @param number - 
	 */
	public void stepComplete(String number) {
		for(int i=0; i<GAME_WINDOW.getPlayField().getNumberJButtons().length; i++) {
			for(int j=0; j<GAME_WINDOW.getPlayField().getNumberJButtons()[i].length; j++) {
				if(GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].getText()!=null && GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].getText().equals(number)) {
					GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].setBackground(Color.BLUE);
				}
				
			}
		}
	}
	
	public void revertToUncomplete(String number) {
		for(int i=0; i<GAME_WINDOW.getPlayField().getNumberJButtons().length; i++) {
			for(int j=0; j<GAME_WINDOW.getPlayField().getNumberJButtons()[i].length; j++) {
				if(number!=null) {
					if(GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].getText()!=null && GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].getText().equals(number)) {
						GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].setBackground(Color.CYAN);
					}
				}
			}
		}
	}
	
	/**
	 * Method Name: complete
	 * Purpose: Method complete() is used to set background color to Pink and sets to not enabled mode if the game is finished.  
	 * Algorithm: Method complete() uses nested loops to check if the game is completed and sets Background color to Pink. 
	 */
	public void complete() {
		for(int i=0; i<GAME_WINDOW.getPlayField().getNumberJButtons().length; i++) {
			for(int j=0; j<GAME_WINDOW.getPlayField().getNumberJButtons()[i].length; j++) {
				GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].setBackground(Color.PINK);
				GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].setEnabled(false);;
			}
		}
		for(int i=0; i<GAME_WINDOW.getPlayField().getNumbersToSelect().length; i++) {
			GAME_WINDOW.getPlayField().getNumbersToSelect()[i].setBackground(Color.GREEN);
		}
	}


}
