/**
 * Course  CST 8221 – JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: May 29, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose: //TODO
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
 * This class is //TODO
 * @author Roger Li
 * @author Denys Savskyi
 * @version
 * @see
 * @since
 *
 */
public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container contentPane;
	private PlayField playField;
	private ActionField actionField;
	private LogField logField;
	private Menu menu;
	

	/**
	 * @throws HeadlessException
	 */
	public MainWindow() throws HeadlessException {
		this("Sudoku (Roger Li, Denys Savasyi)");
	}

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public MainWindow(String title) throws HeadlessException {
		super(title);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(700, 700);
		setMinimumSize(new Dimension(700, 700));
		setResizable(false);
		
		loadRootPane();
		pack();
		setVisible(true);
	}
	
	public void loadRootPane() {
		contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		menu = new Menu(this);
		playField = new PlayField(this);
		logField = new LogField(this);
		actionField = new ActionField(this);
		contentPane.add(menu, BorderLayout.PAGE_START);
		contentPane.add(playField, BorderLayout.LINE_START);
		contentPane.add(logField, BorderLayout.LINE_END);
		contentPane.add(actionField, BorderLayout.PAGE_END);
		
		
	}

	public void resetGame() {
		playField.reload(this, actionField.getDimSelected()==0?2:actionField.getDimSelected());
		playField.setNumSelected(null);
		actionField.reset();
		log("Resetting game...");
	}
	
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
	 * ***************************************************
	 * Method Name: saveProgress
	 * Purpose: to save the current game progress to a file
	 * Algorithm: using JFileChooser to create a new file and save to specific location
	 *****************************************************
	 **/
	
	public void saveProgress() {
		log("Saving progress....");
		StringBuilder sb = new StringBuilder();
		JButton[][] btns = getPlayField().getNumberJButtons();
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
	 * generate a random number and load as the dimension of the game
	 */
	public void rand() {
		SecureRandom sRandom = new SecureRandom();
		int num = sRandom.nextInt(3) + 2;
		this.getActionField().getDim().setSelectedItem(num);
		log("Randomly selected Dim: " + num + " ....");
	}
	/**
	 * 
	 * @return
	 */
	public PlayField getPlayField() {
		return playField;
	}
	/**
	 * 
	 * @param playField
	 */
	public void setPlayField(PlayField playField) {
		this.playField = playField;
	}

	/**
	 * 
	 * @return
	 */
	public ActionField getActionField() {
		return actionField;
	}

	/**
	 * 
	 * @param actionField
	 */
	public void setActionField(ActionField actionField) {
		this.actionField = actionField;
	}

	/**
	 * 
	 * @return
	 */
	public LogField getLogField() {
		return logField;
	}
	/**
	 * 
	 * @param logField
	 */
	public void setLogField(LogField logField) {
		this.logField = logField;
	}
	
	/**
	 * 
	 * @return
	 */
	public Menu getMenu() {
		return menu;
	}
	
	/**
	 * 
	 * @param menu
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	/**
	 * 
	 * @param msg
	 */
	public void log(String msg) {
		getLogField().getLogs().append(msg);
		getLogField().getLogs().append("\n");
	}
}