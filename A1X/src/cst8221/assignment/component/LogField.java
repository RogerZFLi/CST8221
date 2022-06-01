/**
 * Course  CST 8221 – JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: May 29, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose: //TODO
 */
package cst8221.assignment.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
public class LogField extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea logs;

	public JTextArea getLogs() {
		return logs;
	}

	public void setLogs(JTextArea logs) {
		this.logs = logs;
	}

	/**
	 * 
	 */
	public LogField() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param window
	 */
	public LogField(MainWindow window) {
		loadLogField(window);
	}

	/**
	 * 
	 * @param window
	 */
	private void loadLogField(MainWindow window) {
		
		logs = new JTextArea();
		
		BufferedImage myPicture = null;
		
		try {
			myPicture = ImageIO.read(new File("logo.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		Image imgScaled = myPicture.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
		JButton logoButton = new JButton(new ImageIcon(imgScaled));
		final Image img = myPicture.getScaledInstance(600, myPicture.getHeight() * 600 / myPicture.getWidth(), Image.SCALE_SMOOTH);
		final Dimension logoSizeDimension = new Dimension(600, myPicture.getHeight() * 600 / myPicture.getWidth());
		logoButton.addActionListener(e->{
			JDialog info = new JDialog(window);
			info.setTitle("Sudoku");
			JPanel labelContainer = new JPanel();
			JLabel author = new JLabel(new ImageIcon(img));
			labelContainer.add(author);
			info.add(labelContainer);
			info.setSize(logoSizeDimension);
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
		designRadioButton.addChangeListener(e->{
			if(designRadioButton.isSelected()) {
				ActionField.setTimerStop(true);
				window.getActionField().reset();
				window.getActionField().setPlayMode(false);
				window.getActionField().getPoint().setEnabled(false);
				window.getActionField().getTime().setEnabled(false);
				window.getActionField().getLevel().setEnabled(false);
				window.getActionField().getSaveButton().setEnabled(false);
				window.getActionField().getLoadButton().setEnabled(false);
				
			}
		});
		
		JRadioButton playRadioButton = new JRadioButton("Play");
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

}
