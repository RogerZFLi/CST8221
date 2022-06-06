/**
 * File name: Assignment1.java
 * Roger Li - 040896855 & Denys Savskyi - 041004781
 * Course  CST 8221 - JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: June 5, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose: ActionField.java was created in order to design the necessary components for the action field of the Sudoku game. 
 * This class implements such components as: JButton Save, Load, Rand and Reset. The points and game time are also implemented with the help of JLabels.
 * In this part of the application, the user can see most of the Sudoku game settings.
 */
package cst8221.assignment.component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cst8221.assignment.window.MainWindow;

/**
 * Class Name: ActionField
 * Method List: ActionField(), ActionField(MainWindow window), init(MainWindow window), reset(), getDimSelected(), setDimSelected(int dimSelected), 
 * getLevel(), setLevel(JComboBox level), getPoint(), setPoint(JTextField point),getTime(), setTime(JTextField time), isPlayMode(), 
 * setPlayMode(boolean isPlayMode), getDim() , setDim(JComboBox dim), getSaveButton(), setSaveButton(JButton saveButton), getLoadButton(), setLoadButton(JButton loadButton) 
 * Constant List: serialVersionUID
 * Purpose: Сlass ActionField was created in order to design the necessary components for the action field of the Sudoku game. 
 * This class implements such components as: JButton Save, Load, Rand and Reset. The points and game time are also implemented with the help of JLabels.
 * In this part of the application, the user can see most of the Sudoku game settings.
 * @author Roger Li
 * @author Denys Savskyi
<<<<<<< HEAD
 * @version 1.0.0
 * @see
 * @see
 * @since
 *
=======
 * @version Version 2 (2022-06-05)
 * @see "Import Swing Components: javax.swing.JButton; javax.swing.JComboBox; javax.swing.JLabel; javax.swing.JPanel; javax.swing.JTextField;"
 * @see "Extdents: JPanel, Package: cst8221.assignment.component;"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-14
>>>>>>> roger/master
 */
public class ActionField extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int dimSelected;
	private JComboBox<Integer> dim = new JComboBox<>();
	private JComboBox<String> level;
	private JTextField point;
	private JTextField time;
	private boolean isPlayMode;
	private JButton saveButton, loadButton;
	private static long timer;
	private SecondCounter second;
	private static boolean timerStop;
	private static boolean timerReset;
	private static boolean pointReset;

	/**
	 * Method Name: ActionField
	 * Purpose: The default constructor of ActionField Class. 
	 * Algorithm:  
	 */
	public ActionField() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method Name: ActionField
	 * Purpose: Parameterized constructor of ActionField class is used to initialize values with received window variable. 
	 * Algorithm: 
	 * @param window - parameter of MainWindow class. 
	 */
	public ActionField(MainWindow window) {
		init(window);
	}

	/**
	 * Method Name: init
	 * Purpose: 
	 * Algorithm: 
	 * @param window - parameter of MainWindow class.
	 */
	public void init(MainWindow window) {
		timer = 0;

		this.setLayout(new FlowLayout());
		JLabel dimLabel = new JLabel("Dim: ");
		this.add(dimLabel);
		time = new JTextField();
		time.setEditable(false);
		time.setText("0");
		time.setPreferredSize(new Dimension(40, 20));

		dim.addItem(2);
		dim.addItem(3);
		dim.addItem(4);
		dim.addActionListener(e -> {
			window.getPlayField().setNumSelected(null);
			dimSelected = (Integer) dim.getSelectedItem();
			window.getPlayField().reload(window, dimSelected);
			window.log("Reloading...");
			window.log("Dimension number set to :" + dimSelected);

		});

		this.add(dim);
		dimSelected = (Integer) dim.getSelectedItem();
		JLabel levelLabel = new JLabel("Level: ");
		Timer t = new Timer();
		level = new JComboBox<>();
		level.addItem("Select");
		level.addItem(PlayField.EASY);
		level.addItem(PlayField.MEDIUM);
		level.addItem(PlayField.HARD);
		level.addActionListener(e -> {
			
			timerStop = false;
			reset();
			if (!level.getSelectedItem().equals("Select")) {
				second = new SecondCounter();
				t.schedule(second, 1000);

				window.getPlayField().setDifficulty((String) level.getSelectedItem());
				window.log("Set level to '" + level.getSelectedItem() + "....");
			}

		});

		this.add(levelLabel);
		this.add(level);

		saveButton = new JButton("Save");
		saveButton.addActionListener(e -> window.saveProgress());
		this.add(saveButton);
		loadButton = new JButton("Load");
		loadButton.addActionListener(e -> window.loadProgress());
		this.add(loadButton);
		JButton randButton = new JButton("Rand");
		randButton.addActionListener(e -> window.rand());
		this.add(randButton);
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(e -> {
			window.resetGame();
			level.setSelectedIndex(0);
			timerStop = true;
			window.log("Resetting game...");
		});
		this.add(resetButton);
		JLabel pointLabel = new JLabel("Points: ");
		this.add(pointLabel);
		point = new JTextField();
		point.setEditable(false);
		point.setText("0");
		point.setPreferredSize(new Dimension(40, 20));
		this.add(point);
		JLabel timeLabel = new JLabel("Time: ");
		this.add(timeLabel);
		this.add(time);
	}

	/**
	 * Method Name: reset
	 * Purpose: Method reset() is used to reset time and points of the current game. 
	 * Algorithm: 
	 */
	public void reset() {
		if (!isPlayMode)//checks if it is play mode 
			return;
		point.setText("0");//sets points and time to 0 
		time.setText("0");

		timer = 0;
	}

	/**
	 * Method Name: getDimSelected
	 * Purpose: Method getDimSelected() of class Menu gets the value of the variable dimSelected
	 * Algorithm:  
	 * @return dimSelected - parameter that returns the actual value of the variable 
	 */
	public int getDimSelected() {
		return dimSelected;
	}

	/**
	 * Method Name: setDimSelected
	 * Purpose: Setter method setPlayField() takes the param and assigns it to the variable playField.
	 * Algorithm: 
	 * @param dimSelected - parameter that is used to assign the attribute.
	 */
	public void setDimSelected(int dimSelected) {
		this.dimSelected = dimSelected;
	}

	/**
	 * Method Name: getLevel
	 * Purpose: Method getLevel() of class Menu gets the value of the variable level
	 * Algorithm: 
	 * @return level - parameter that returns the actual value of the variable 
	 */
	public JComboBox<String> getLevel() {
		return level;
	}

	/**
	 * Method Name: setLevel
	 * Purpose: Setter method setLevel() takes the param and assigns it to the variable level.
	 * Algorithm: 
	 * @param level - parameter that is used to assign the attribute.
	 */
	public void setLevel(JComboBox<String> level) {
		this.level = level;
	}

	/**
	 * Method Name: getPoint
	 * Purpose: Method getPoint() of class Menu gets the value of the variable point
	 * Algorithm: 
	 * @return point - parameter that returns the actual value of the variable 
	 */
	public JTextField getPoint() {
		return point;
	}

	/**
	 * Method Name: setPoint
	 * Purpose: Setter method setPoint() takes the param and assigns it to the variable point.
	 * Algorithm: 
	 * @param point - parameter that is used to assign the attribute.
	 */
	public void setPoint(JTextField point) {
		this.point = point;
	}

	/**
	 * Method Name: getTime
	 * Purpose: Method getTime() of class Menu gets the value of the variable time
	 * Algorithm: 
	 * @return time - parameter that returns the actual value of the variable 
	 */
	public JTextField getTime() {
		return time;
	}

	/**
	 * Method Name: setTime
	 * Purpose: Setter method setTime() takes the param and assigns it to the variable time.
	 * Algorithm: 
	 * @param time - parameter that is used to assign the attribute.
	 */
	public void setTime(JTextField time) {
		this.time = time;
	}

	/**
	 * Method Name: isPlayMode
	 * Purpose: Method isPlayMode() returns true if it is play mode and returns false if it is not. 
	 * Algorithm: 
	 * @return isPlayMode - return true or false  
	 */
	public boolean isPlayMode() {
		return isPlayMode;
	}

	/**
	 * Method Name: setPlayMode
	 * Purpose: Setter method setPlayMode() takes the param and assigns it to the variable isPlayMode.
	 * Algorithm: 
	 * @param isPlayMode - parameter that is used to assign the attribute.
	 */
	public void setPlayMode(boolean isPlayMode) {
		this.isPlayMode = isPlayMode;
	}

	/**
	 * Method Name: getDim
	 * Purpose: Method getDim() of class Menu gets the value of the variable dim
	 * Algorithm: 
	 * @return dim - parameter that returns the actual value of the variable 
	 */
	public JComboBox<Integer> getDim() {
		return dim;
	}

	/**
	 * Method Name: setDim
	 * Purpose: Setter method setDim() takes the param and assigns it to the variable dim.
	 * Algorithm: 
	 * @param dim - parameter that is used to assign the attribute.
	 */
	public void setDim(JComboBox<Integer> dim) {
		this.dim = dim;
	}

	/**
	 * Method Name: getSaveButton
	 * Purpose: Method getSaveButton() of class Menu gets the value of the variable saveButton
	 * Algorithm: 
	 * @return saveButton - parameter that returns the actual value of the variable 
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/**
	 * Method Name: setSaveButton
	 * Purpose: Setter method setSaveButton() takes the param and assigns it to the variable saveButton.
	 * Algorithm: 
	 * @param saveButton - parameter that is used to assign the attribute.
	 */
	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	/**
	 * Method Name: getLoadButton
	 * Purpose: Method getLoadButton() of class Menu gets the value of the variable loadButton
	 * Algorithm: 
	 * @return loadButton - parameter that returns the actual value of the variable 
	 */
	public JButton getLoadButton() {
		return loadButton;
	}

	/**
	 * Method Name: setLoadButton
	 * Purpose: Setter method setLoadButton() takes the param and assigns it to the variable loadButton.
	 * Algorithm: 
	 * @param loadButton - parameter that is used to assign the attribute.
	 */
	public void setLoadButton(JButton loadButton) {
		this.loadButton = loadButton;
	}

	
	/**
	 * Class Name: SecondCounter
	 * Method List: run(), getSecond(), isTimerStop(), setTimerStop(boolean timerStop), isTimerReset(), setTimerReset(boolean timerReset),
	 * isPointReset(), setPointReset(boolean pointReset), resetTimer() 
	 * Purpose: Class SecondCounter extends TimerTask and is used to reset, set and get the values for the timer for the Sudoku game implementation. 
	 * @author Roger Li
	 * @author Denys Savskyi 
	 * @version Version 2 (2022-06-05)
	 * @see "Import Swing Components: javax.swing.JButton; javax.swing.JComboBox; javax.swing.JLabel; javax.swing.JPanel; javax.swing.JTextField;"
	 * @see "Extdents: TimerTask, Package: cst8221.assignment.component;"
	 * @since JDK 18.0.1.1
	 * @since JRE JavaSE-14
	 */
	class SecondCounter extends TimerTask {

		/**
		 * Method Name: run
		 * Purpose: Method run() uses while loop to get the timer to work. 
		 * Algorithm: Uses while loop and checks if timerStop true or false. If it is turned off, then break. 
		 * Else add one second to the timer after 1 second sleep. 
		 */
		@Override
		public void run() {
			while (true) {
				if (timerStop)
					break;
				time.setText(String.valueOf(timer++));//adds one to the timer count 
				try {
					Thread.sleep(1000);//sleep 1 sec 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			timerStop = false;

		}

	}

	/**
	 * Method Name: getSecond
	 * Purpose: Method getSecond() of class Menu gets the value of the variable second
	 * Algorithm: 
	 * @return second - parameter that returns the actual value of the variable 
	 */
	public SecondCounter getSecond() {
		return second;
	}

	/**
	 * Method Name: isTimerStop
	 * Purpose: Method isTimerStop() uses to determine whether or not timer is running. 
	 * Algorithm: 
	 * @return timerStop - return true or false. 
	 */
	public static boolean isTimerStop() {
		return timerStop;
	}

	/**
	 * Method Name: setTimerStop
	 * Purpose: Setter method setTimerStop() takes the param and assigns it to the variable timerStop.
	 * Algorithm: 
	 * @param timerStop - parameter that is used to assign the attribute.
	 */
	public static void setTimerStop(boolean timerStop) {
		ActionField.timerStop = timerStop;
	}

	/**
	 * Method Name: isTimerReset
	 * Purpose: Method isTimerReset() uses to determine whether or not timer was reset. 
	 * Algorithm: 
	 * @return timerReset - return true or false. 
	 */
	public static boolean isTimerReset() {
		return timerReset;
	}

	/**
	 * Method Name: setTimerReset
	 * Purpose: Setter method setTimerReset() takes the param and assigns it to the variable timerReset.
	 * Algorithm: 
	 * @param timerReset - parameter that is used to assign the attribute.
	 */
	public static void setTimerReset(boolean timerReset) {
		ActionField.timerReset = timerReset;
	}

	/**
	 * Method Name: isPointReset
	 * Purpose: Method isPointReset() uses to determine whether or not points were reset.
	 * Algorithm: 
	 * @return pointReset - return true or false. 
	 */
	public static boolean isPointReset() {
		return pointReset;
	}

	/**
	 * Method Name: setPointReset
	 * Purpose: Setter method setPointReset() takes the param and assigns it to the variable pointReset.
	 * Algorithm: 
	 * @param pointReset - parameter that is used to assign the attribute. 
	 */
	public static void setPointReset(boolean pointReset) {
		ActionField.pointReset = pointReset;
	}

	/**
	 * Method Name: resetTimer
	 * Purpose: Method resetTimer() is used to set the value of timer to 0. 
	 * Algorithm: 
	 */
	public void resetTimer() {
		timer = 0;
	}

}
