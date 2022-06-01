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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cst8221.assignment.window.MainWindow;

/**
 * This class is //TODO
 * @author Roger Li
 * @author Denys Savskyi
 * @version 1.0.0
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
		this.setLayout(new FlowLayout());
		JLabel dimLabel = new JLabel("Dim: ");
		this.add(dimLabel);
		
		dim.addItem(2);
		dim.addItem(3);
		dim.addItem(4);
		dim.addActionListener(e->{
			window.getPlayField().setNumSelected(null);
			dimSelected = (Integer)dim.getSelectedItem();
			window.getPlayField().reload(window, dimSelected);
			window.log("Reloading...");
			window.log("Dimension number set to :" + dimSelected + "\n");
			
		});
		this.add(dim);
		
		JLabel levelLabel = new JLabel("Level: ");
		level = new JComboBox<>();
		level.addItem("Select");
		level.addItem(PlayField.EASY);
		level.addItem(PlayField.MEDIUM);
		level.addItem(PlayField.HARD);
		level.addActionListener(e->{
			if(isPlayMode) {
				window.getPlayField().setDifficulty((String)level.getSelectedItem());
				window.log("Set level to '" + level.getSelectedItem() + "'....");
			}
		});
		
		this.add(levelLabel);
		this.add(level);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(e->window.saveProgress());
		this.add(saveButton);
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(e->window.loadProgress());
		this.add(loadButton);
		JButton randButton = new JButton("Rand");
		randButton.addActionListener(e->window.rand());
		this.add(randButton);
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(e->window.resetGame());
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
		time = new JTextField();
		time.setEditable(false);
		time.setText("0");
		time.setPreferredSize(new Dimension(40, 20));
		this.add(time);
	}
	
	/**
	 * 
	 */
	public void reset() {
		if(!isPlayMode) return;
		point.setText("0");
		time.setText("0");
		level.setSelectedItem(0);
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
	
	
}


