package cst8221.assignment.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Class Name: GameSplash
 * Method List: GameSplash(String path), GameSplash(String path, long ms), paint(Graphics g)
 * Constant List: serialVersionUID
 * Purpose: The class GameSplash defines the splash screen to display at different conditions
 * @author Roger Li
 * @author Denys Savskyi
 *
 * @version Version 2 (2022-06-10)
 * @see "Import Swing Components: javax.swing.JButton;  java.util.ArrayList;"
 * @see "Package: cst8221.assignment.view;"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-15
 */
//https://www.c-sharpcorner.com/article/developing-a-splash-screen-using-java/
public class GameSplash extends JFrame {
	
	private static final long serialVersionUID = 1L;
	Image splashScreen;
	ImageIcon imageIcon;

	/**
	 * Constructor taking the parameter representing the location of the picture to display on the splash screen
	 * The display duration of splash screen set to 5 seconds (5000 milliseconds)
	 * @param path location of the picture to display on the splash screen
	 */
	public GameSplash(String path) {
		this(path, 5000);
	}
	/**
	 * Constructor taking the parameter representing the location of the picture to display on the splash screen
	 * The display duration of splash screen set to ms milliseconds
	 * @param path location of the picture to display on the splash screen
	 * @param ms duration the splash screen to display
	 */
	public GameSplash(String path, long ms) {
		splashScreen = Toolkit.getDefaultToolkit().getImage(path);
		// Create ImageIcon from Image
		imageIcon = new ImageIcon(splashScreen);
		// Set JWindow size from image size
		setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
		// Get current screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Get x coordinate on screen for make JWindow locate at center
		int x = (screenSize.width - getSize().width) / 2;
		// Get y coordinate on screen for make JWindow locate at center
		int y = (screenSize.height - getSize().height) / 2;
		// Set new location for JWindow
		setLocation(x, y);
		
		// Make JWindow visible
		setVisible(true);
		paint(getGraphics());
		setAlwaysOnTop(true);
		try {
			// Make JWindow appear for 10 seconds before disappear
			Thread.sleep(ms);
			dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * paints the splash screen with selected picture
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(splashScreen, 0, 0, this);
	}

}
