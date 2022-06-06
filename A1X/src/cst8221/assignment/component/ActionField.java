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
 * This class is //TODO
 * 
 * @author Roger Li
 * @author Denys Savskyi
 * @version 1.0.0
 * @see
 * @see
 * @since
 *
 */
public class ActionField extends JPanel {

	/**
	 * 
	 */
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
	 * 
	 */
	public ActionField() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param window
	 */
	public ActionField(MainWindow window) {
		init(window);
	}

	/**
	 * 
	 * @param window
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
	 * 
	 */
	public void reset() {
		if (!isPlayMode)
			return;
		point.setText("0");
		time.setText("0");

		timer = 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getDimSelected() {
		return dimSelected;
	}

	/**
	 * 
	 * @param dimSelected
	 */
	public void setDimSelected(int dimSelected) {
		this.dimSelected = dimSelected;
	}

	/**
	 * 
	 * @return
	 */
	public JComboBox<String> getLevel() {
		return level;
	}

	/**
	 * 
	 * @param level
	 */
	public void setLevel(JComboBox<String> level) {
		this.level = level;
	}

	/**
	 * 
	 * @return
	 */
	public JTextField getPoint() {
		return point;
	}

	/**
	 * 
	 * @param point
	 */
	public void setPoint(JTextField point) {
		this.point = point;
	}

	/**
	 * 
	 * @return
	 */
	public JTextField getTime() {
		return time;
	}

	/**
	 * 
	 * @param time
	 */
	public void setTime(JTextField time) {
		this.time = time;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPlayMode() {
		return isPlayMode;
	}

	/**
	 * 
	 * @param isPlayMode
	 */
	public void setPlayMode(boolean isPlayMode) {
		this.isPlayMode = isPlayMode;
	}

	public JComboBox<Integer> getDim() {
		return dim;
	}

	public void setDim(JComboBox<Integer> dim) {
		this.dim = dim;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public JButton getLoadButton() {
		return loadButton;
	}

	public void setLoadButton(JButton loadButton) {
		this.loadButton = loadButton;
	}

	class SecondCounter extends TimerTask {

		@Override
		public void run() {
			while (true) {
				if (timerStop)
					break;
				time.setText(String.valueOf(timer++));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			timerStop = false;

		}

	}

	public SecondCounter getSecond() {
		return second;
	}

	public static boolean isTimerStop() {
		return timerStop;
	}

	public static void setTimerStop(boolean timerStop) {
		ActionField.timerStop = timerStop;
	}

	public static boolean isTimerReset() {
		return timerReset;
	}

	public static void setTimerReset(boolean timerReset) {
		ActionField.timerReset = timerReset;
	}

	public static boolean isPointReset() {
		return pointReset;
	}

	public static void setPointReset(boolean pointReset) {
		ActionField.pointReset = pointReset;
	}

	public void resetTimer() {
		timer = 0;
	}

}
