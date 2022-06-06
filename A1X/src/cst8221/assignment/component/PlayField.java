/**
 * File name: Assignment1.java
 * Roger Li - 040896855 & Denys Savskyi - 041004781
 * Course  CST 8221 - JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: June 5, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose:  PlayField.java is used to create a game space where the user can interact and play Sudoku. 
 * This area is filled with various buttons designed to make the game process easier for the user. Here the user can play and design new variations of Sudoku. 
 */
package cst8221.assignment.component;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;


import cst8221.assignment.window.MainWindow;

/**
 * Class Name: PlayField
 * Method List: PlayField(), PlayField(MainWindow window), reload(MainWindow window, int dimSelected), load(MainWindow window), load(MainWindow window, int dim),
 * load(MainWindow window, int dim, double hiddenRate), fillNumber(MainWindow window, JButton btn, String number, int dim, boolean muted), setDifficulty(String level),
 * stepComplete(String number), complete(), getNumSelected(), setNumSelected(String numSelected), getNumberJButtons(), setNumberJButtons(JButton[][] numberJButtons)
 * Constant List: serialVersionUID, HARD, MEDIUM, EASY
 * Purpose: The PlayField class is used to create a game space where the user can interact and play Sudoku. 
 * This area is filled with various buttons designed to make the game process easier for the user. Here the user can play and design new variations of Sudoku. 
 * @author Roger Li
 * @author Denys Savskyi 
 * @version Version 2 (2022-06-05)
 * @see "Extdents: JPanel, Package: cst8221.assignment.component." 
 * @see "Import Swing and Sound Components: javax.sound.sampled.AudioInputStream; javax.sound.sampled.AudioSystem; javax.sound.sampled.Clip; javax.swing.BorderFactory;
 * javax.swing.JButton; javax.swing.JPanel;"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-14
 */
public class PlayField extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final String HARD = "Hard";
	public static final String MEDIUM = "Medium";
	public static final String EASY = "Easy";
	
	private JButton[][] numberJButtons;
	private JButton[] numbersToSelect;
	private Map<String, String> cellsTakenMap;//creates Map with keys and objects String
	private String numSelected;
	private static Map<Integer, String> rowColRepStringMap;//creates Map with Integer keys and objects String
	private int[] numberCounter;
	private int totalCounter;
	

	/**
	 * Method Name: PlayField
	 * Purpose: The default constructor of PlayField class
	 * Algorithm: 
	 */
	public PlayField() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method Name: PlayField
	 * Purpose: Constructor PlayField is used to set size of the window and load it. 
	 * Algorithm: 
	 * @param window - parameter of MainWindow class 
	 */
	public PlayField(MainWindow window) {
		setSize(590, 680);
		setPreferredSize(new Dimension(590, 680));
		load(window);
	}

	/**
	 * Method Name: reload
	 * Purpose: Method reload is used to create window with selected dimension if reloaded. 
	 * Algorithm: 
	 * @param window - parameter of MainWindow class
	 * @param dimSelected - integer parameter of selected simension 
	 */
	public void reload(MainWindow window, int dimSelected) {
		this.removeAll();
		load(window, dimSelected);
		this.invalidate();
		this.validate();
		this.repaint();
		window.log("Game reloading... ");
	}
	
	/**
	 * Method Name: load
	 * Purpose: Method load(MainWindow window) is used to load the game with the default second dimension. 
	 * Algorithm: 
	 * @param window - parameter of MainWindow class. 
	 */
	public void load(MainWindow window) {
		load(window, 2);
	}
	
	/**
	 * Method Name: load
	 * Purpose: Method load(MainWindow window, int dim) is used to load the game with the selected dim and hidrate. 
	 * Algorithm: 
	 * @param window - parameter of MainWindow class. 
	 * @param dim - integer parameter of selected dimension 
	 */
	public void load(MainWindow window, int dim) {
		load(window, dim, 1);
	}
	
	/**
	 * Method Name: load
	 * Purpose: This method is used to set Grid Layout with the selected dimension. Create all the buttons for the game 
	 * Algorithm: Sets preferred size and layout with respect to selected dimension, creates HashMap and counter.
	 * Then uses for loop to go through and put the values to the HashMap. Uses nested for loops to go through it.
	 * Then create button objects and set name by getting the values from HashMap. Add Action Listener. 
	 * Create for loop to go through and print keys. Checks if the user is selecting the correct location. 
	 * If yes (incorrect) then sets the flag to false and prints the error. If correct, calls fillNumber method with the appropriate values. 
	 * 
	 * The next for loop is used to configure the buttons that user is going to use to fill the values. Creates the buttons with the numbers and letter if needed. 
	 * And creates Action Listener. And uses Yellow color for selected button and Green color for the rest of the buttons with the help of for loop. 
	 * @param window - parameter of class MainWindow
	 * @param dim - integer parameter for the dimension 
	 * @param hiddenRate - double parameter for hidden rate 
	 */
	public void load(MainWindow window, int dim, double hiddenRate) {  //TODO
		this.setPreferredSize(new Dimension(590, 680));
		
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new GridLayout((int) (Math.pow(dim, 2)) + 1, (int) Math.pow(dim, 2), 1, 1));//sets Grid layouts with respect to selected dimension 
		numberJButtons = new JButton[(int) (Math.pow(dim, 2))][(int) (Math.pow(dim, 2))];//creates buttons 
		numbersToSelect = new JButton[(int) (Math.pow(dim, 2))];//creates buttons for user to select 
		cellsTakenMap = new HashMap<>();
		rowColRepStringMap = new HashMap<>();
		numberCounter = new int[(int)Math.pow(dim, 2)];
		totalCounter = 0;
		
		for(int i=0; i<(int) (Math.pow(dim, 2)); i++) {
			if(i>9) {
				rowColRepStringMap.put(i, Character.toString('A' + i - 10));//puts to HasMap if number is larger then 9 (letters) 
			}else {
				rowColRepStringMap.put(i, String.valueOf(i));//puts number to HashMap
			}
		}
		for (int i = 0; i < (int) (Math.pow(dim, 2)); i++) {
			for (int j = 0; j < (int) (Math.pow(dim, 2)); j++) {
				
				JButton btn = new JButton();
				btn.setPreferredSize(new Dimension(300 / (int) (Math.pow(dim, 2))-1, 300 / (int) (Math.pow(dim, 2))-1));
				
				btn.setName(rowColRepStringMap.get(i) + rowColRepStringMap.get(j));
				btn.setText(null);
				btn.setMargin(new Insets(0, 0, 0, 0));
				btn.addActionListener(e -> {
					
					if (numSelected!=null) {
						
							
						boolean available = true;
						for(String k : cellsTakenMap.keySet()) {
							System.out.println(k);
							if(cellsTakenMap.get(k).equals(numSelected) && (k.charAt(0) == btn.getName().charAt(0)|| k.charAt(1) == btn.getName().charAt(1))) {
								available = false;
								window.log("Invalid try...");
								//https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator
								String soundName = "wrong.wav";  
								try {
									AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
									Clip clip = AudioSystem.getClip();
									clip.open(audioInputStream);
									clip.start();
								}catch(Exception ex) {
									System.err.println("Error happened");
								}
								break;
							}
						}
						if(available) {
							fillNumber(window, btn, numSelected, dim, false);
						}
					}
				});
				numberJButtons[i][j] = btn;
				this.add(btn);
			}
		}
		
		for (int i = 0; i < (int) (Math.pow(dim, 2)); i++) {
			for (int j = 0; j < (int) (Math.pow(dim, 2)); j++) {
				if(((i/dim)%2==0 &&(j/dim)%2==0 || (i/dim)%2!=0 && (j/dim)%2!=0)&& i%dim == 0 && j%dim == 0 ) {
					for(int n=0; n<dim; n++) {
						for(int m=0; m<dim; m++) {
							numberJButtons[i+n][j + m].setBackground(new Color(220, 240, 244));
						}
					}
				}
			}
		}
		
		for(int i=1; i<=(int) (Math.pow(dim, 2));i++) {
			JButton btn = new JButton(String.valueOf(i<10?i : Character.toString((char)('A' + (i - 10)))));
			btn.setPreferredSize(new Dimension(300 / (int) (Math.pow(dim, 2))-1, 300 / (int) (Math.pow(dim, 2))-1));
			btn.setBackground(Color.GREEN);
			btn.setMargin(new Insets(0, 0, 0, 0));
			btn.addActionListener(e->{
				btn.setBackground(Color.YELLOW);
				numSelected = btn.getText();
				for(JButton b: numbersToSelect) {
					if(!b.equals(btn)) {
						b.setBackground(Color.GREEN);
					}
				}
				window.log("Selected number: " + numSelected);
			});
			numbersToSelect[i-1] = btn;
			this.add(btn);
		}
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
		try {
			numRep = Integer.parseInt(number);
		}catch(NumberFormatException ex) {
			numRep = number.charAt(0) - 55;
		}
		
		if(btn.getText()!=null)	{//checks if button is not null 
			numberCounter[numRep-1]--;
			totalCounter--;
		}
		btn.setText(number);
		btn.setBackground(Color.CYAN);//sets the background color 
		cellsTakenMap.put(btn.getName(), numSelected);
		
		numberCounter[numRep-1]++;//increments counter 
		totalCounter++;
		
		window.log(number + " was put to Row "+ btn.getName().charAt(0) + ", " + " Col " + btn.getName().charAt(1) +"...");//displays logs 
		String soundName = null;
		if(totalCounter == (int) (Math.pow(dim, 2)) * (int) (Math.pow(dim, 2))) {//checks if the game is completed 
			soundName = "complete.wav"; 
			complete();//calls complete method 
			ActionField.setTimerStop(true);
		}else if(numberCounter[numRep-1]  == dim * dim) {//checks if the whole step is completed 
			soundName = "goodjob.wav";
			stepComplete(numSelected);
		}
		else
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
	 * Method Name: setDifficulty
	 * Purpose: Method setDifficulty() is used to set specific level of difficulty selected bu the user. 
	 * Algorithm: 
	 * @param level - String parameter level of difficulty selected by user. 
	 */
	public void setDifficulty(String level) {  //TODO
		switch(level) {
		case HARD:
			
			break;
		case MEDIUM:
			break;
		case EASY:
			break;
		default:
			System.err.println("Error happens");
		}
	}
	
	/**
	 * Method Name: stepComplete
	 * Purpose: Method stepComplete() is used to set background color to Blue if the one digit is finished.
	 * Algorithm: Method stepComplete() uses nested loops to check if one digit is finished and sets Background color to Blue. 
	 * @param number - 
	 */
	public void stepComplete(String number) {
		for(int i=0; i<numberJButtons.length; i++) {
			for(int j=0; j<numberJButtons[i].length; j++) {
				if(numberJButtons[i][j].getText()!=null && numberJButtons[i][j].getText().equals(number)) {
					numberJButtons[i][j].setBackground(Color.BLUE);
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
		for(int i=0; i<numberJButtons.length; i++) {
			for(int j=0; j<numberJButtons[i].length; j++) {
				numberJButtons[i][j].setBackground(Color.PINK);
				numberJButtons[i][j].setEnabled(false);;
			}
		}
	}
	
	/**
	 * Method Name: getNumSelected
	 * Purpose: Method getNumSelected() of class ActionField gets the value of the variable numSelected
	 * Algorithm: 
	 * @return numSelected - parameter that returns the actual value of the variable 
	 */
	public String getNumSelected() {
		return numSelected;
	}

	/**
	 * Method Name: setNumSelected
	 * Purpose: Setter method setNumSelected() takes the param and assigns it to the variable numSelected.
	 * Algorithm: 
	 * @param numSelected - Parameter menu of class String - sets the variable.
	 */
	public void setNumSelected(String numSelected) {
		this.numSelected = numSelected;
	}

	/**
	 * Method Name: getNumberJButtons
	 * Purpose: Method getNumberJButtons() of class ActionField gets the value of the variable numberJButtons
	 * Algorithm: 
	 * @return numberJButtons - parameter that returns the actual value of the variable 
	 */
	public JButton[][] getNumberJButtons() {
		return numberJButtons;
	}

	/**
	 * Method Name: setNumberJButtons
	 * Purpose: Setter method setNumberJButtons() takes the param and assigns it to the variable numberJButtons.
	 * Algorithm: 
	 * @param numberJButtons - Parameter menu of class JButton - sets the variable.
	 */
	public void setNumberJButtons(JButton[][] numberJButtons) {
		this.numberJButtons = numberJButtons;
	}
	
	

}
