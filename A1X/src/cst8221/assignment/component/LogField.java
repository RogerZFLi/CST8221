/**
 * 
 */
package cst8221.assignment.component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cst8221.assignment.window.MainWindow;

/**
 * @author Roger Li
 * @author Denys Savskyi
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


	private void loadLogField(MainWindow window) {
		
		logs = new JTextArea();
		
		BufferedImage myPicture = null;
		
		try {
			myPicture = ImageIO.read(new File("logo.png"));
			myPicture.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image imgScaled = myPicture.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
		JLabel picLabel = new JLabel(new ImageIcon(imgScaled));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(picLabel);
		JPanel modePanel = new JPanel();
		modePanel.setLayout(new FlowLayout());
		JLabel modeLabel = new JLabel("Mode: ");
		JRadioButton designRadioButton = new JRadioButton("Design");
		designRadioButton.addChangeListener(e->{
			if(designRadioButton.isSelected()) {
				window.getActionField().setPlayMode(false);
				window.getActionField().getPoint().setEnabled(false);
				window.getActionField().getTime().setEnabled(false);
				window.getActionField().getLevel().setEnabled(false);
				
			}
		});
		
		JRadioButton playRadioButton = new JRadioButton("Play");
		playRadioButton.addChangeListener(e->{
			if(designRadioButton.isSelected()) {
				window.getActionField().setPlayMode(true);
				window.getActionField().getPoint().setEnabled(true);
				window.getActionField().getTime().setEnabled(true);
				window.getActionField().getLevel().setEnabled(true);
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
