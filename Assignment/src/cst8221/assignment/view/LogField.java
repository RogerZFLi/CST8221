/**
 * File name: Assignment1.java
 * Roger Li - 040896855 & Denys Savskyi - 041004781
 * Course  CST 8221 - JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: June 5, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose:  LogField.java is used to implement the functionality of the history panel. This field will display logs about every user action. 
 * Show possible errors. Allows you to set and get all the logs.
 */
package cst8221.assignment.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cst8221.assignment.controller.GameController;

/**
 * Class Name: LogField
 * Method List: getLogs(), setLogs(JTextArea logs), LogField(), LogField(MainWindow window), loadLogField(MainWindow window)
 * Constant List: serialVersionUID
 * Purpose: Class LogField is used to implement the functionality of the history panel. This field will display logs about every user action. 
 * Show possible errors. Allows you to set and get all the logs.
 * @author Roger Li
 * @author Denys Savskyi
 * @version Version 2 (2022-06-05)
 * @see "Import Swing Components: javax.swing.BorderFactory; javax.swing.BoxLayout; javax.swing.ButtonGroup; javax.swing.ImageIcon; javax.swing.JButton;
 * javax.swing.JDialog; javax.swing.JLabel; javax.swing.JPanel; javax.swing.JRadioButton; javax.swing.JScrollPane; javax.swing.JTextArea;"
 * @see "Extdents: JPanel, Package: cst8221.assignment.component;"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-14
 */
public class LogField extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextArea logs;
	private JRadioButton playRadioButton;

	/**
	 * Method Name: getLogs
	 * Purpose: Method getLogs() of class JTextArea gets the value of the variable logs 
	 * Algorithm: 
	 * @return logs - parameter that returns the actual value of the variable 
	 */
	public JTextArea getLogs() {
		return logs;
	}
	
	/**
	 * Method Name: setLogs
	 * Purpose: Setter method setLogs() takes the param and assigns it to the variable logs. 
	 * Algorithm: 
	 * @param logs - Parameter logs of class JTextArea - sets the variable. 
	 */
	public void setLogs(JTextArea logs) {
		this.logs = logs;
	}

	/**
	 * Method Name: LogField
	 * Purpose: Default constructor
	 * Algorithm: No arg constructor
	 */
	public LogField() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method Name: LogField
	 * Purpose: Method LogField(MainWindow window) is used to receive window as a parameter and load log field. 
	 * Algorithm: 
	 * @param window - parameter of class MainWindow
	 */
	public LogField(MainWindow window) {
		loadLogField(window);
	}

	/**
	 * Method Name: loadLogField
	 * Purpose: Loads the log text field (scroll panel) to log field pane
	 * Algorithm: Adds the log text field and mode radio buttons to log field pane
	 * @param window - The main window to display
	 */
	private void loadLogField(MainWindow window) {
		
		logs = new JTextArea();
		
		BufferedImage myPicture = null;
		
		try {
			File imageFile = new File("images/logo.png");
			if(imageFile.exists())
				myPicture = ImageIO.read(imageFile);
			else {
				myPicture = new BufferedImage(200, 120, Image.SCALE_SMOOTH);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		Image imgScaled = myPicture.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
		JButton logoButton = new JButton(new ImageIcon(imgScaled));
		
		final Image img = myPicture.getScaledInstance(600, myPicture.getHeight() * 600 / myPicture.getWidth(), Image.SCALE_SMOOTH);
//		final Dimension logoSizeDimension = new Dimension(600, myPicture.getHeight() * 600 / myPicture.getWidth());
		logoButton.addActionListener(e->{
			JDialog info = new JDialog(window);
			info.setTitle("Sudoku");
			info.setSize(600, 777);
			info.setPreferredSize(new Dimension(600, 777));
			info.setResizable(false);
			JPanel labelContainer = new JPanel();
			labelContainer.setLayout(new BoxLayout(labelContainer, BoxLayout.Y_AXIS));
			
			JLabel pic = new JLabel(new ImageIcon(img));
			labelContainer.add(pic);
			
			
			
			JPanel recordBoard = new JPanel();
			recordBoard.setLayout(new BoxLayout(recordBoard, BoxLayout.Y_AXIS));
			JPanel recordTitlePane = new JPanel();
			recordTitlePane.setSize(600, 30);
			JLabel recordTitleLabel = new JLabel("Glory Board - Top 10");
			recordTitlePane.add(recordTitleLabel);
			recordBoard.add(recordTitlePane);
			JPanel recordTopPane = new JPanel();
			recordTopPane.setSize(600, 30);
			recordTopPane.setLayout(new BoxLayout(recordTopPane, BoxLayout.X_AXIS));
			
			JLabel record1 = new JLabel("1: "+" 10000 ");
			JButton viewChampionButton = new JButton("View Champion's Solution");
			recordTopPane.add(record1);
			recordTopPane.add(viewChampionButton);
			recordBoard.add(recordTopPane);
			JLabel record2 = new JLabel("2: "+" 10000 ");
			recordBoard.add(record2);
			JLabel record3 = new JLabel("3: "+" 10000 ");
			recordBoard.add(record3);
			JLabel record4 = new JLabel("4: "+" 10000 ");
			recordBoard.add(record4);
			JLabel record5 = new JLabel("5: "+" 10000 ");
			recordBoard.add(record5);
			JLabel record6 = new JLabel("6: "+" 10000 ");
			recordBoard.add(record6);
			JLabel record7 = new JLabel("7: "+" 10000 ");
			recordBoard.add(record7);
			JLabel record8 = new JLabel("8: "+" 10000 ");
			recordBoard.add(record8);
			JLabel record9 = new JLabel("9: "+" 10000 ");
			recordBoard.add(record9);
			JLabel record10 = new JLabel("10: "+" 10000 ");
			recordBoard.add(record10);
			labelContainer.add(recordBoard);
			info.add(labelContainer);
//			info.setSize(logoSizeDimension);
			info.setVisible(true);
		});
		logoButton.setPreferredSize(new Dimension(180,120));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
		JPanel logoContainer = new JPanel();
		logoContainer.setLayout(new BoxLayout(logoContainer, BoxLayout.X_AXIS));
		logoContainer.setPreferredSize(new Dimension(180,120));
		logoContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoContainer.setAlignmentY(Component.CENTER_ALIGNMENT);
		logoContainer.add(logoButton);
		this.add(logoContainer);
		
		JPanel modePanel = new JPanel();
		modePanel.setLayout(new FlowLayout());
		JLabel modeLabel = new JLabel("Mode: ");
		JRadioButton designRadioButton = new JRadioButton("Design");
		designRadioButton.setSelected(true);
		designRadioButton.addActionListener(e->{
			if(designRadioButton.isSelected()) {
				ActionField.setTimerStop(true);
				window.getActionField().reset();
				window.getActionField().setPlayMode(false);
				window.getActionField().getPoint().setEnabled(false);
				window.getActionField().getTime().setEnabled(false);
				window.getActionField().getLevel().setSelectedIndex(0);
				window.getActionField().getLevel().setEnabled(false);
				window.getActionField().getSaveButton().setEnabled(false);
				window.getActionField().getLoadButton().setEnabled(false);
				GameController.getController().resetGame();
				
			}
		});
		
		playRadioButton = new JRadioButton("Play");
		playRadioButton.addChangeListener(e->{
			if(playRadioButton.isSelected()) {
				ActionField.setTimerStop(true);
				window.getActionField().reset();
				window.getActionField().setPlayMode(true);
				window.getActionField().getPoint().setEnabled(true);
				window.getActionField().getTime().setEnabled(true);
				window.getActionField().getLevel().setEnabled(true);
				window.getActionField().getSaveButton().setEnabled(true);
				window.getActionField().getLoadButton().setEnabled(true);
			}
		});
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(designRadioButton);
		buttonGroup.add(playRadioButton);
		modePanel.add(modeLabel);
		modePanel.add(designRadioButton);
		modePanel.add(playRadioButton);
		this.add(modePanel);
		
		JScrollPane logArea = new JScrollPane(logs);
		logArea.setPreferredSize(new Dimension(200, 300));
		this.add(logArea);
		
	}
	
	/**
	 * Method Name: getPlayRadioButton
	 * Purpose: Method getPlayRadioButton() is used to return the variable playRadioButton 
	 * Algorithm: 
	 * @return playRadioButton - returns parameter play radio button 
	 */
	public JRadioButton getPlayRadioButton() {
		return playRadioButton;
	}

	/**
	 * Method Name: setPlayRadioButton
	 * Purpose: Method setPlayRadioButton(JRadioButton playRadioButton) is used to set the radio button  
	 * Algorithm: 
	 * @param playRadioButton - parameter of class JRadioButton
	 */
	public void setPlayRadioButton(JRadioButton playRadioButton) {
		this.playRadioButton = playRadioButton;
	}

}
