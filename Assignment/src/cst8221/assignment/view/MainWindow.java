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
package cst8221.assignment.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Class Name: MainWindow Method List: MainWindow(), MainWindow(String title),
 * loadRootPane(), resetGame(), loadProgress() saveProgress(), rand(), PlayField
 * getPlayField(), setPlayField(PlayField playField),ActionField
 * getActionField(), setActionField(ActionField actionField), LogField
 * getLogField(), setLogField(LogField logField), Menu getMenu(), setMenu(Menu
 * menu), log(String msg). Constant List: serialVersionUID. Purpose: Class
 * MainWindow sets a specific size to the application. Fills the frames with
 * different components and executes the functionality of these components.
 * Allows the user to load and save specific Sudoku game configurations, reset
 * and randomize the game.
 * 
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
	/**
	 * implements singleton DP
	 */
	private static MainWindow GAME_WINDOW = null;
	// private variables of different classes
	private Container contentPane;
	private PlayField playField;
	private ActionField actionField;
	private LogField logField;
	private Menu menu;
	private boolean isFromFile;
	public static final String START_SPLASH_PATH = "images/splash.jpg";

	/**
	 * Method Name: MainWindow Purpose: Class default constructor MainWindow that
	 * sets frame name. Algorithm:
	 * 
	 * @throws HeadlessException - trown if called in the env that does not support
	 *                           devices such as mouse, keyboard.
	 */
	private MainWindow() throws HeadlessException {
		this("Sudoku (Roger Li, Denys Savskyi)");
	}

	/**
	 * Method Name: MainWindow Purpose: Constructor with parameter sets size,
	 * minimal size and makes it not resizeble. Algorithm:
	 * 
	 * @param title - MainWindow receives title as parameter.
	 * @throws HeadlessException - trown if called in the env that does not support
	 *                           devices such as mouse, keyboard.
	 */
	private MainWindow(String title) throws HeadlessException {
		super(title);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(700, 700);// sets size
		setMinimumSize(new Dimension(700, 700));
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Get x coordinate on screen for make JWindow locate at center
		int x = (screenSize.width - getSize().width) / 2;
		// Get y coordinate on screen for make JWindow locate at center
		int y = (screenSize.height - getSize().height) / 2;
		// Set new location for JWindow
		setLocation(x, y);
		new GameSplash(START_SPLASH_PATH);
		loadRootPane();// calls loadRootPane method
		this.pack(); // sizes the frame so all its objects are at their correct size.
		setVisible(true);
	}
	/**
	 * Method: loadGame
	 * Purpose: to instantiate MainWindow object with Singleton DP
	 * @return GAME_WINDOW
	 */
	public static MainWindow loadGame() {
		if (GAME_WINDOW == null) {
			GAME_WINDOW = new MainWindow();
		}
		return GAME_WINDOW;
	}

	/**
	 * Method Name: loadRootPane Purpose: Method loadRootPane() is used to set the
	 * objects of the game and use the add() function to add them. Algorithm: Gets
	 * the content pane and sets the layout, calls add() method to add components.
	 */
	private void loadRootPane() {
		contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		menu = new Menu(this);
		playField = new PlayField(this);
		logField = new LogField(this);
		actionField = new ActionField(this);
		// adds the components to the BorderLayout to the relative positioning constants
		contentPane.add(menu, BorderLayout.PAGE_START);
		contentPane.add(playField, BorderLayout.LINE_START);
		contentPane.add(logField, BorderLayout.LINE_END);
		contentPane.add(actionField, BorderLayout.PAGE_END);

	}

	/**
	 * Method Name: getPlayField Purpose: Method getPlayField() of class PlayField
	 * gets the value of the variable playField Algorithm:
	 * 
	 * @return playField - parameter that returns the actual value of the variable
	 */
	public PlayField getPlayField() {
		return playField;
	}

	/**
	 * Method Name: setPlayField Purpose: Setter method setPlayField() takes the
	 * param and assigns it to the variable playField. Algorithm:
	 * 
	 * @param playField - Parameter playField of class PlayField - sets the
	 *                  variable.
	 */
	public void setPlayField(PlayField playField) {
		this.playField = playField;
	}

	/**
	 * Method Name: getActionField Purpose: Method getActionField() of class
	 * ActionField gets the value of the variable actionField Algorithm:
	 * 
	 * @return actionField - parameter that returns the actual value of the variable
	 */
	public ActionField getActionField() {
		return actionField;
	}

	/**
	 * Method Name: setActionField Purpose: Setter method setActionField() takes the
	 * param and assigns it to the variable actionField. Algorithm:
	 * 
	 * @param actionField - Parameter actionField of class ActionField - sets the
	 *                    variable.
	 */
	public void setActionField(ActionField actionField) {
		this.actionField = actionField;
	}

	/**
	 * Method Name: getLogField Purpose: Method getLogField() of class LogField gets
	 * the value of the variable logField Algorithm:
	 * 
	 * @return logField - contains the value of the variable logField
	 */
	public LogField getLogField() {
		return logField;
	}

	/**
	 * Method Name: setLogField Purpose: Setter method setLogField() takes the param
	 * and assigns it to the variable logField. Algorithm:
	 * 
	 * @param logField - Parameter logField of class LogField - sets the variable.
	 */
	public void setLogField(LogField logField) {
		this.logField = logField;
	}

	/**
	 * Method Name: getMenu Purpose: Method getMenu() of class Menu gets the value
	 * of the variable menu Algorithm:
	 * 
	 * @return menu - parameter that contains the value of the variable menu
	 */

	public Menu getMenu() {
		return menu;
	}

	/**
	 * Method Name: setMenu Purpose: Setter method setMenu() takes the param and
	 * assigns it to the variable menu. Algorithm:
	 * 
	 * @param menu - Parameter menu of class Menu - sets the variable.
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * Method Name: log Purpose: Method log() adds log information. Algorithm:
	 * 
	 * @param msg - this parameter receives within log() method and displays.
	 */
	public void log(String msg) {
		getLogField().getLogs().append(msg);
		getLogField().getLogs().append("\n");
	}
	
	/**
	 * getter of fromFile
	 * @return value of fromFile
	 */
	public boolean isFromFile() {
		return isFromFile;
	}

	/**
	 * setter of fromFile
	 * 
	 * @param isFromFile  value to be assigned to fromFile
	 */
	public void setFromFile(boolean isFromFile) {
		this.isFromFile = isFromFile;
	}

	
}