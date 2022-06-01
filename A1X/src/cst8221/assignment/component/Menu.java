/**
 * Course  CST 8221 – JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: May 29, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose: //TODO
 */
package cst8221.assignment.component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import cst8221.assignment.window.MainWindow;


/**
 * This class is //TODO
 * @author Roger Li
 * @author Denys Savskyi
 * @version
 * @see
 * @since
 *
 */
public class Menu extends JMenuBar {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public Menu() {
		
	}
	
	/**
	 * 
	 * @param window
	 */
	public Menu(MainWindow window) {
		load(window);
	}
	
	/**
	 * 
	 * @param window
	 */
	public void loadGameMenu(MainWindow window) {
		
		JMenu game = new JMenu("Game");
		
		JMenuItem newGame = new JMenuItem("New");
		newGame.addActionListener(e->{
			window.loadRootPane();
		});
		game.add(newGame);
		JMenuItem open = new JMenuItem("Open");
		//load the saved progress file, save function as Load button
		open.addActionListener(e->{
			window.loadProgress();
			window.getLogField().getLogs().append("Opening a saved progress...\n");
		});
		game.add(open);
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(e->{
			window.saveProgress();
			window.getLogField().getLogs().append("Saving progress...\n");
		});
		game.add(save);
		JMenuItem reset = new JMenuItem("Reset");
		//clear the play field
		reset.addActionListener(e->{
			
			window.resetGame();
			window.getLogField().getLogs().append("Reseting game...\n");
		});
		game.add(reset);
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(e->window.dispose());
		game.add(exit);
		this.add(game);
	}
	
	/**
	 * 
	 * @param window
	 */
	public void loadHelpMenu(MainWindow window) {
		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(e->{
			JDialog info = new JDialog(window);
			info.setTitle("About");
			JPanel labelContainer = new JPanel();
			labelContainer.setLayout(new BoxLayout(labelContainer, BoxLayout.Y_AXIS));
			JLabel name = new JLabel("Sudoku");
			JLabel version = new JLabel("Version: 1.0.0");
			JLabel author = new JLabel("By: Roger Li - 040896855");
			labelContainer.add(name);
			labelContainer.add(version);
			labelContainer.add(author);
			info.add(labelContainer);
			info.setSize(new Dimension(300, 100));
			info.setVisible(true);
			window.getLogField().getLogs().append("About this program...\n");
		});
		help.add(about);
		this.add(help);
		
	}
	/**
	 * 
	 * @param window
	 */
	public void load(MainWindow window) {
		
		loadGameMenu(window);
		loadHelpMenu(window);
	}

}
