/**
 * 
 */
package cst8221.assignment.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.security.SecureRandom;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import cst8221.assignment.component.ActionField;
import cst8221.assignment.component.LogField;
import cst8221.assignment.component.Menu;
import cst8221.assignment.component.PlayField;

/**
 * @author Roger Li
 * @author Denys Savskyi
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
		this("Sudoku (Roger Li)");
	}

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public MainWindow(String title) throws HeadlessException {
		super(title);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(800, 705);
		setMinimumSize(new Dimension(800, 705));
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
		playField.reload(this, 2);
		actionField.reset();
		logField.getLogs().append("Resetting game... \n");
	}
	
	public void loadProgress() {
		getLogField().getLogs().append("Loading progress....");
	}
	

	public void saveProgress() {
		getLogField().getLogs().append("Saving progress....");
	}
	
	public void rand() {
		SecureRandom sRandom = new SecureRandom();
		int num = sRandom.nextInt(3) + 2;
		this.getPlayField().reload(this, num);
	}

	public PlayField getPlayField() {
		return playField;
	}

	public void setPlayField(PlayField playField) {
		this.playField = playField;
	}

	public ActionField getActionField() {
		return actionField;
	}

	public void setActionField(ActionField actionField) {
		this.actionField = actionField;
	}

	public LogField getLogField() {
		return logField;
	}

	public void setLogField(LogField logField) {
		this.logField = logField;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}