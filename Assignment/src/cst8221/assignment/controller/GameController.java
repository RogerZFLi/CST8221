package cst8221.assignment.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import cst8221.assignment.model.GameRecord;
import cst8221.assignment.model.Progress;
import cst8221.assignment.view.ActionField;
import cst8221.assignment.view.GameSplash;
import cst8221.assignment.view.MainWindow;
import cst8221.assignment.view.PlayField;

public class GameController extends JFrame{
	
	
	
	/**
	 * default serial id
	 */
	private static final long serialVersionUID = 1L;
	private static final MainWindow GAME_WINDOW = MainWindow.loadGame(); 
	private static GameController GAME_CONTROLLER = null;
	private static Progress progress;
	private static GameRecord record;
	private static int[] numberCounter;
	private static int totalCounter;
	
	/**
	 * default constructor
	 */
	private GameController() {
		
	}
	/**
	 * Method Name: start
	 *
	 * Purpose: To start the Game with default configuration 
	 * Algorithm: Display the Game window by invoking GameController instance and initialize progress and record instance
	 * 
	 */
	public static void start() {
		GameController.getController();
		progress = new Progress();
		numberCounter = new int[(int)Math.pow(2, 2)];
		totalCounter = 0;
		record = new GameRecord();
	}
	/**
	 * Method Name: getController
	 *
	 * Purpose: To instantiate GameController instance with Singleton DP
	 * Algorithm: Singleton DP
	 * @return the GameController instance
	 */
	public static GameController getController() {
		if(GAME_CONTROLLER == null)
			GAME_CONTROLLER = new GameController();
		return GAME_CONTROLLER;
	}
	
	/**
	 * Method Name: resetGame
	 * Purpose: Method resetGame() is used if user wants to reset the entries during the game. 
	 * Algorithm: On playField calls method reload() with appropriate vars and resets the game. 
	 * 
	 */
	public void resetGame() {
		progress.resetProgress(GAME_WINDOW.getActionField().getDimSelected()==0?2:GAME_WINDOW.getActionField().getDimSelected());
		reloadPlayField();
		resetActionField(); //uses reset() method to reset actionField
		for(int i = 0; i < numberCounter.length; i++) {
			numberCounter[i] = 0;
		}
		totalCounter = 0;
		
	}
	/**
	 * Method Name: reloadPlayField
	 * Purpose: To reload window with selected dimension. 
	 * Algorithm: remove all components on PlayField and reset, reload selected number to null and re-paint PlayField
	 * 
	 */
	public void reloadPlayField() {
		GAME_WINDOW.getPlayField().removeAll();
		GAME_WINDOW.getPlayField().load(GAME_WINDOW, GAME_WINDOW.getActionField().getDimSelected());
		GAME_WINDOW.getPlayField().setNumSelected(null);
		GAME_WINDOW.getPlayField().invalidate();
		GAME_WINDOW.getPlayField().validate();
		GAME_WINDOW.getPlayField().repaint();
	}
	/**
	 * Method Name: reloadActionField
	 * Purpose: To reset ActionField components configurations. 
	 * Algorithm: remove all components on ActionField and reset, reload selected number to null and re-paint PlayField
	 * 
	 */
	public void resetActionField() {
		if (!GAME_WINDOW.getActionField().isPlayMode())//checks if it is play mode 
			return;
		GAME_WINDOW.getActionField().getPoint().setText("0");//sets points and time to 0 
		GAME_WINDOW.getActionField().getTime().setText("0");
		
		GAME_WINDOW.getActionField().resetTimer();
	}
	/**
	 * Method Name: resetCounters
	 * Purpose: To reset the counter of the same number/char and the total numbers/chars filled to the play field 
	 * Algorithm: reset numberCounter and totalCounter to 0
	 * @param dim current dimension number
	 */
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
		JFileChooser fileChooser = new JFileChooser("./progress");
		fileChooser.setDialogTitle("Specify a file to load");   
		 
		int userSelection = fileChooser.showSaveDialog(GAME_WINDOW);
		File fileToRead = null;
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToRead = fileChooser.getSelectedFile();
		    System.out.println("Load from file: " + fileToRead.getAbsolutePath());
		}
		//Read progress from file
		if(fileToRead!=null) {
			try (FileInputStream fis = new FileInputStream(fileToRead);
				     ObjectInputStream ois = new ObjectInputStream(fis)) {
				Progress progress = (Progress)ois.readObject();
				GAME_WINDOW.getActionField().getDim().setSelectedItem(progress.getDim());
				GAME_WINDOW.getActionField().getPoint().setText(String.valueOf(progress.getPoint()));
				GAME_WINDOW.getActionField().getTime().setText(progress.getTime());
				JButton[][] btns = GAME_WINDOW.getPlayField().getNumberJButtons();
				
				for(int i=0; i<btns.length; i++) {
						for(int j=0; j<btns[i].length; j++) {
							
							if(progress.getNumbers()[i][j]!=null) {
								fillNumber(GAME_WINDOW, btns[i][j], progress.getNumbers()[i][j], progress.getDim(),true);
							}
						}
				}
				GAME_WINDOW.log("Progress loaded....");
			}catch(Exception ioe) {
				GAME_WINDOW.log("Fail to load progress....");
			}
		}
		GAME_WINDOW.setFromFile(false);
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
		File fileToRead = new File("designs/" + dim);
		
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
		
		
		//Open current project folder location to save file
		JFileChooser fileChooser = new JFileChooser("./progress");
		fileChooser.setDialogTitle("Specify a file to save");   
		 
		int userSelection = fileChooser.showSaveDialog(GAME_WINDOW);
		File fileToSave = null;
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = fileChooser.getSelectedFile();
		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
		//Save file
		if(fileToSave!=null) {
			try (FileOutputStream fos = new FileOutputStream(fileToSave);
				     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(progress);
			}catch(IOException ioe) {
				GAME_WINDOW.log("Fail to save progress...");
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
		if(!muted) {
			btn.setBackground(Color.CYAN);//sets the background color 
			btn.setForeground(Color.BLACK);
		}
		GAME_WINDOW.getPlayField().getCellsTakenMap().put(btn.getName(), number);
		GAME_WINDOW.getPlayField().dimBlockButtonBelong(btn).addFilledChars(number);
		numberCounter[numRep-1]++;//increments counter 
		totalCounter++;
		
		window.log(number + " was put to Row "+ btn.getName().charAt(0) + ", " + " Col " + btn.getName().charAt(1) +"...");//displays logs 
		String soundName = null;
		if(totalCounter == (int) (Math.pow(dim, 2)) * (int) (Math.pow(dim, 2))) {//checks if the game is completed 
			soundName = "sounds/complete.wav"; 
			if(!muted) complete();//calls complete method 
			ActionField.setTimerStop(true);
		}else if(numberCounter[numRep-1]  == dim * dim) {//checks if the whole step is completed 
			soundName = "sounds/goodjob.wav";
			if(!muted) stepComplete(number);
		}else
			soundName = "sounds/correct.wav";  
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
	/**
	 * Method: revertToUncomplete
	 * Purpose: to change back the color of buttons from complete status to incomplete status
	 * @param number the number/char to be fill to current button
	 */
	public void revertToUncomplete(String number) {
		for(int i=0; i<GAME_WINDOW.getPlayField().getNumberJButtons().length; i++) {
			for(int j=0; j<GAME_WINDOW.getPlayField().getNumberJButtons()[i].length; j++) {
				if(number!=null) {
					if(GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].getText()!=null && GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].getText().equals(number)) {
						GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].setBackground(Color.CYAN);
						GAME_WINDOW.getPlayField().getNumberJButtons()[i][j].setForeground(Color.BLACK);
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
		record = record.loadRecord();
		record.updateRecord(progress);
		if(record.isBreakRecord()) {
			addToModel(record.getTopSolution());
		}
		//TODO: add splash pane here
		popupSplash("images/sudoku_winner.png", 2000);
		
		
	}
	/**
	 * Method: addToModel
	 * Purpose: to add current progress to the game model
	 * Algorithm: //TODO
	 * @param p
	 */
	public void addToModel(Progress p) {
		
	}
	/**
	 * getter of progress
	 * @return progress
	 */
	public static Progress getProgress() {
		return progress;
	}
	/**
	 * setter of progress
	 * @param progress progress to be assigned to progress field
	 */
	public static void setProgress(Progress progress) {
		GameController.progress = progress;
	}
	
	/**
	 * Method: setColor
	 * Purpose: to set color for all the buttons in play field
	 * Algorithm: iterates every dim block and set the color of all the buttons in each dim block. 
	 * @param color the color to be set to all the buttons in play field.
	 */
	public static void setColor(Color color) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		int dim = GAME_WINDOW.getActionField().getDimSelected();
		for(int i=0; i<dim; i++)
			for(int j=0; j<dim; j++)
				if(i%2==0 && j%2==0|| i%2!=0 && j%2!=0){
					for(JButton btn: GAME_WINDOW.getPlayField().getDimBlocks()[i][j].getButtons()) {
						btn.setBackground(new Color(b,r,g));
						Color c = btn.getBackground();
						//https://stackoverflow.com/questions/4672271/reverse-opposing-colors
						double y = (299 * c.getRed() + 587 * c.getGreen() + 114 * c.getBlue()) / 1000;
						btn.setForeground(y >= 128 ? Color.black : Color.white);
					}
				}else {
					for(JButton btn: GAME_WINDOW.getPlayField().getDimBlocks()[i][j].getButtons())
						btn.setBackground(color);
				}
	}
	/**
	 * Method: currentColor
	 * Purpose: to get the color of the buttons in the first dimension block on the play field
	 * Algorithm:  GAME_WINDOW.getPlayField().getDimBlocks()[0][0].getButtons().get(0).getBackground()
	 * @return the color of the buttons in the first dimension block
	 */
	public static Color currentColor() {
		return GAME_WINDOW.getPlayField().getDimBlocks()[0][0].getButtons().get(0).getBackground();
	}
	/**
	 * Method: popupSplash
	 * Purpose: to display a splash screen with specific picture and last for a specific duration
	 * Algorithm: instantiate a GameSplash object
	 * @param path the location of picture to display to the splash screen
	 * @param ms the time that the splash screen to last
	 */
	public static void popupSplash(String path, long ms) {
		new GameSplash(path, ms);
	}
	
	


}
