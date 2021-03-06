/**
 * File name: Assignment1.java
 * Roger Li - 040896855 & Denys Savskyi - 041004781
 * Course  CST 8221 - JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: June 5, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose:  Menu.java is used to create functional components such as Game and Help Menus inside the Sudoku game in a menu bar. 
 * Menu provides user-friendly way to let the user pick options of the game.
 */
package cst8221.assignment.view;

import java.awt.Color;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cst8221.assignment.controller.GameController;

/**
 * Class Name: Menu
 * Method List: Menu(), Menu(MainWindow window), loadGameMenu(MainWindow window), loadHelpMenu(MainWindow window), load(MainWindow window)
 * Constant List: serialVersionUID
 * Purpose: Class Menu is used to create functional components such as Game and Help Menus inside the Sudoku game in a menu bar. 
 * Menu provides user-friendly way to let the user pick options of the game.  
 * @author Roger Li
 * @author Denys Savskyi
 * @version Version 2 (2022-06-05)
 * @see "Import Swing Components: javax.swing.BoxLayout; javax.swing.JDialog; javax.swing.JLabel; javax.swing.JMenu; javax.swing.JMenuBar;
 * javax.swing.JMenuItem; javax.swing.JPanel;"
 * @see "Extdents: JMenuBar, Package: cst8221.assignment.component;"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-14
 */
public class Menu extends JMenuBar {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Method Name: Menu
	 * Purpose: The default constructor of Menu class. 
	 * Algorithm:  
	 */
	public Menu() {
		
	}
	
	/**
	 * Method Name: Menu
	 * Purpose: Parameterized constructor is used to load the game with the window parameter. 
	 * Algorithm: 
	 * @param window - parameter of MainWindow class 
	 */
	public Menu(MainWindow window) {
		load(window);
	}
	
	/**
	 * Method Name: loadGameMenu
	 * Purpose:Method loadGameMenu() creates Game menu in the menu bar section with the list of in-game options for friendly-user experience. 
	 * Algorithm: Creates JMenu and JMenuItem objects and sets the specific Action Listener on every button with the appropriate method. 
	 * If user would click "New" option from the list, the loadRootPane() method would be called. And The new game would start. 
	 * The similar process for the rest of the options. 
	 * @param window - 
	 */
	public void loadGameMenu(MainWindow window) {
		
		JMenu game = new JMenu("Game");
		Icon newIcon = new ImageIcon(new ImageIcon("images/new.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		
		JMenuItem newGame = new JMenuItem("New");
		newGame.setIcon(newIcon);
		newGame.addActionListener(e->{
			GameController.getController().resetGame();
		});
		game.add(newGame);
		JMenuItem open = new JMenuItem("Open");
		Icon openIcon = new ImageIcon(new ImageIcon("images/open.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		open.setIcon(openIcon);
		//load the saved progress file, save function as Load button
		open.addActionListener(e->{
			GameController.getController().loadProgress();
			GameController.popupSplash("images/sudoku_cfg.png", 1000);
			window.getLogField().getLogs().append("Opening a saved progress...\n");
		});
		game.add(open);
		JMenuItem save = new JMenuItem("Save");
		Icon saveIcon = new ImageIcon(new ImageIcon("images/save.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		save.setIcon(saveIcon);
		save.addActionListener(e->{
			GameController.getController().saveProgress();
			GameController.popupSplash("images/sudoku_cfg.png", 1000);
			window.getLogField().getLogs().append("Saving progress...\n");
		});
		game.add(save);
		JMenuItem reset = new JMenuItem("Reset");
		Icon resetIcon = new ImageIcon(new ImageIcon("images/reset.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		reset.setIcon(resetIcon);
		//clear the play field
		reset.addActionListener(e->{
			
			GameController.getController().resetGame();
			window.getLogField().getLogs().append("Reseting game...\n");
			GameController.popupSplash("images/sudoku_cfg.png", 1000);
		});
		game.add(reset);
		JMenuItem exit = new JMenuItem("Exit");
		Icon exitIcon = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		exit.setIcon(exitIcon);
		exit.addActionListener(e->window.dispose());
		game.add(exit);
		this.add(game);
	}
	
	/**
	 * Method Name: loadHelpMenu
	 * Purpose: Method loadHelpMenu() creates Help menu in the menu bar section with the detailed information about this application. 
	 * Algorithm: Creates JMenu object and JMenuItem object "About", then creates Action Listener with info window inside. 
	 * Creates JPanel and sets layout with labels. Sets the size. 
	 * @param window - parameter of MainWindow class. 
	 */
	public void loadHelpMenu(MainWindow window) {
		JMenu help = new JMenu("Help");//creates the object of JMenu and JMenuItem
		JMenuItem changeColor = new JMenuItem("Change Color");
		Icon colorPickerIcon = new ImageIcon(new ImageIcon("images/color_dropper.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		changeColor.setIcon(colorPickerIcon);
		changeColor.addActionListener(e->{
			Color initialcolor= GameController.currentColor();    
			Color color=JColorChooser.showDialog(this,"Select a color",initialcolor);  
			GameController.setColor(color);
			GameController.popupSplash("images/sudoku_cfg.png", 1000);
		});
		help.add(changeColor);
		JMenuItem about = new JMenuItem("About");
		Icon infoIcon = new ImageIcon(new ImageIcon("images/info.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		about.setIcon(infoIcon);
		about.addActionListener(e->{
			GameController.popupSplash("images/sudoku_about.png", 1000);
//			JDialog info = new JDialog(window);
//			info.setTitle("About");
//			JPanel labelContainer = new JPanel();
//			labelContainer.setLayout(new BoxLayout(labelContainer, BoxLayout.Y_AXIS));
//			//sets JLable with different info about the Sudoku game 
//			JLabel name = new JLabel("Sudoku");
//			JLabel version = new JLabel("Version: 1.0.0");
//			JLabel author = new JLabel("By: Roger Li - 040896855, Denys Savskyi - 041004781");
//			labelContainer.add(name);
//			labelContainer.add(version);
//			labelContainer.add(author);
//			info.add(labelContainer);
//			info.setSize(new Dimension(300, 100));//sets the size of the frame 
//			info.setVisible(true);
			window.log("About this program...");
		});
		help.add(about);
		this.add(help);
		
	}
	
	/**
	 * Method Name: load
	 * Purpose: Method load() is used to load game and help menus in the menu bar section. 
	 * Algorithm: 
	 * @param window - parameter of MainWindow class
	 */
	public void load(MainWindow window) {
		
		loadGameMenu(window);
		loadHelpMenu(window);
	}

}
