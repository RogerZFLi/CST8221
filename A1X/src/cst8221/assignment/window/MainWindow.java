/**
 * File name: MainWindow.java
 * Roger Li - 040896855 & Denys Savskyi - 041004781
 * Course  CST 8221 - JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: June 5, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose: sets a specific size to the application. Fills the frames with different components and executes the functionality of these components. 
 * Allows the user to load and save specific Sudoku game configurations, reset and randomize the game. 
 */
package cst8221.assignment.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import cst8221.assignment.component.ActionField;
import cst8221.assignment.component.LogField;
import cst8221.assignment.component.Menu;
import cst8221.assignment.component.PlayField;

/**
 * Class Name: MainWindow 
 * Method List: MainWindow(), MainWindow(String title), loadRootPane(), resetGame(), loadProgress()
 * saveProgress(), rand(), PlayField getPlayField(), setPlayField(PlayField playField),ActionField getActionField(), 
 * setActionField(ActionField actionField), LogField getLogField(), setLogField(LogField logField), 
 * Menu getMenu(), setMenu(Menu menu), log(String msg).
 * Constant List: serialVersionUID. 
 * Purpose: Class MainWindow sets a specific size to the application. Fills the frames with different components and executes the functionality of these components. 
 * Allows the user to load and save specific Sudoku game configurations, reset and randomize the game. 
 * @author Roger Li
 * @author Denys Savskyi
 * @version Version 2 (2022-06-05)
 * @see "Package: cst8221.assignment.window. Imports Swing components: javax.swing.JButton; javax.swing.JFileChooser; javax.swing.JFrame; javax.swing.WindowConstants;"
 * @see "Extends: JFrame"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-14
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	//private variables of different classes 
	private Container contentPane;
	private PlayField playField;
	private ActionField actionField;
	private LogField logField;
	private Menu menu;
	
	/**
	 * Method Name: MainWindow
	 * Purpose: Class default constructor MainWindow that sets frame name. 
	 * Algorithm: 
	 * @throws HeadlessException - trown if called in the env that does not support devices such as mouse, keyboard. 
	 */
	public MainWindow() throws HeadlessException {
		this("Sudoku (Roger Li, Denys Savasyi)");
	}

	/**
	 * Method Name: MainWindow
	 * Purpose: Constructor with parameter sets size, minimal size and makes it not resizeble.  
	 * Algorithm: 
	 * @param title - MainWindow receives title as parameter. 
	 * @throws HeadlessException - trown if called in the env that does not support devices such as mouse, keyboard. 
	 */
	public MainWindow(String title) throws HeadlessException {
		super(title);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(700, 700);//sets size 
		setMinimumSize(new Dimension(700, 700));
		setResizable(false);
		
		loadRootPane();//calls loadRootPane method 
		pack(); //sizes the frame so all its objects are at their correct size. 
		setVisible(true);
	}
	
	/**
	 * Method Name: loadRootPane
	 * Purpose: Method loadRootPane() is used to set the objects of the game and use the add() function to add them. 
	 * Algorithm: Gets the content pane and sets the layout, calls add() method to add components. 
	 */
	public void loadRootPane() {
		contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		menu = new Menu(this);
		playField = new PlayField(this);
		logField = new LogField(this);
		actionField = new ActionField(this);
		//adds the components to the BorderLayout to the relative positioning constants
		contentPane.add(menu, BorderLayout.PAGE_START);
		contentPane.add(playField, BorderLayout.LINE_START);
		contentPane.add(logField, BorderLayout.LINE_END);
		contentPane.add(actionField, BorderLayout.PAGE_END);
		
	}
	
	/**
	 * Method Name: resetGame
	 * Purpose: Method resetGame() is used if user wants to reset the entries during the game. 
	 * Algorithm: On playField calls method reload() with appropriate vars and resets the game. 
	 */
	public void resetGame() {
		playField.reload(this, actionField.getDimSelected()==0?2:actionField.getDimSelected());
		playField.setNumSelected(null);
		actionField.reset(); //uses reset() method to reset actionField
		
	}
	
	/**
	 * Method Name: loadProgress
	 * Purpose: Method loadProgress() is used to Load existing file as a configuration for the Sudoku game. 
	 * Algorithm: Uses JFileChooser to select the file, chooses the file and uses scanner to scan the details of the game, then sets all these details 
	 * to actual Sudoku game. Catches the errors. 
	 */
	public void loadProgress() {
		
		log("Loading progress....");
		
		//Open current project folder location to save file
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setDialogTitle("Specify a file to load");   
		 
		int userSelection = fileChooser.showSaveDialog(this);
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
				getActionField().getDim().setSelectedItem(dimNumber);
				getActionField().getLevel().setSelectedItem(level);
				getActionField().getPoint().setText(point);
				getActionField().getTime().setText(time);
				JButton[][] btns = getPlayField().getNumberJButtons();
				
				for(int i=0; i<btns.length; i++) {
					String line = null;
					if(scanner.hasNext()) {
						line = scanner.nextLine();
						System.out.println(line);
						String[] numbers = line.split(",");
						for(int j=0; j<btns[i].length; j++) {
							if(!numbers[j].isBlank()) {
								this.getPlayField().fillNumber(this, btns[i][j], numbers[j], dimNumber,true);
							}
						}
					}
				}
				log("Progress loaded....");
			} catch (IOException e) {
				log("Fail to load progress...");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method Name: loadMasked
	 * Purpose: Method loadMasked() is used to Load game with masked numbers based on difficulty level selected. 
	 * Algorithm: Uses JFileChooser to select the file, chooses the file and uses scanner to scan the details of the game, then sets all these details 
	 * to actual Sudoku game. Catches the errors. 
	 */
	public void loadMasked(String difficulty, int dim) {
		
		log("Loading game ...");
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

				JButton[][] btns = getPlayField().getNumberJButtons();
				//generate musked buttons
				HashSet<JButton> maskedButtons = new HashSet<>();
				for(int i=0; i<dim; i++) {
					for(int j=0; j<dim; j++) {
						int maskCounter = 0;
						ArrayList<JButton> buttons = this.getPlayField().getDimBlocks()[i][j].getButtons();
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
									this.getPlayField().fillNumber(this, btns[i][j], numbers[j], dim,true);
							}
						}
					}
				}
				
				log(difficulty + " mode loaded....");
			} catch (IOException e) {
				log("Fail to load " + difficulty + " mode...");
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
		log("Saving progress....");
		StringBuilder sb = new StringBuilder();
		JButton[][] btns = getPlayField().getNumberJButtons();
		//uses getter methods to get the values. 
		sb.append(getActionField().getDimSelected());
		sb.append("\n");
		sb.append(getActionField().getLevel().getSelectedItem());
		sb.append("\n");
		sb.append(getActionField().getPoint().getText());
		sb.append("\n");
		sb.append(getActionField().getTime().getText());
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
		 
		int userSelection = fileChooser.showSaveDialog(this);
		File fileToSave = null;
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = fileChooser.getSelectedFile();
		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		}
		//Save file
		if(fileToSave!=null) {
			try (FileWriter fw = new FileWriter(fileToSave)){
				fw.write(sb.toString());
				log("Progress saved....");
			} catch (IOException e) {
				log("Fail to save progress...");
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
		this.getActionField().getDim().setSelectedItem(num);
		log("Randomly selected Dim: " + num + " ....");//adds the log info 
	}
	
	
	/**
	 * Method Name: getPlayField
	 * Purpose: Method getPlayField() of class PlayField gets the value of the variable playField
	 * Algorithm: 
	 * @return playField - parameter that returns the actual value of the variable 
	 */
	public PlayField getPlayField() {
		return playField;
	}
	
	
	/**
	 * Method Name: setPlayField
	 * Purpose: Setter method setPlayField() takes the param and assigns it to the variable playField.
	 * Algorithm: 
	 * @param playField - Parameter playField of class PlayField - sets the variable. 
	 */
	public void setPlayField(PlayField playField) {
		this.playField = playField;
	}

	/**
	 * Method Name: getActionField
	 * Purpose: Method getActionField() of class ActionField gets the value of the variable actionField
	 * Algorithm: 
	 * @return actionField - parameter that returns the actual value of the variable 
	 */
	public ActionField getActionField() {
		return actionField;
	}

	/**
	 * Method Name: setActionField
	 * Purpose: Setter method setActionField() takes the param and assigns it to the variable actionField.
	 * Algorithm: 
	 * @param actionField - Parameter actionField of class ActionField - sets the variable. 
	 */
	public void setActionField(ActionField actionField) {
		this.actionField = actionField;
	}

	/**
	 * Method Name: getLogField
	 * Purpose: Method getLogField() of class LogField gets the value of the variable logField 
	 * Algorithm: 
	 * @return logField - contains the value of the variable logField
	 */
	public LogField getLogField() {
		return logField;
	}
	
	/**
	 * Method Name: setLogField
	 * Purpose: Setter method setLogField() takes the param and assigns it to the variable logField. 
	 * Algorithm: 
	 * @param logField - Parameter logField of class LogField - sets the variable. 
	 */
	public void setLogField(LogField logField) {
		this.logField = logField;
	}
	
	/**
	 * Method Name: getMenu
	 * Purpose: Method getMenu() of class Menu gets the value of the variable menu 
	 * Algorithm: 
	 * @return menu - parameter that contains the value of the variable menu
	 */
	
	public Menu getMenu() {
		return menu;
	}
	
	/**
	 * Method Name: setMenu
	 * Purpose: Setter method setMenu() takes the param and assigns it to the variable menu. 
	 * Algorithm: 
	 * @param menu - Parameter menu of class Menu - sets the variable. 
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	/**
	 * Method Name: log
	 * Purpose: Method log() adds log information. 
	 * Algorithm: 
	 * @param msg - this parameter receives within log() method and displays. 
	 */
	public void log(String msg) {
		getLogField().getLogs().append(msg);
		getLogField().getLogs().append("\n");
	}
}