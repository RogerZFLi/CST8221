/**
 * Course  CST 8221 – JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: May 29, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose: //TODO
 */
package cst8221.assignment.component;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
public class PlayField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton[][] numberJButtons;
	private JButton[] numbersToSelect;
	private Map<String, String> cellsTakenMap;
	private String numSelected;
	private static Map<Integer, String> rowColRepStringMap;
	private int[] numberCounter;
	private int totalCounter;
	

	/**
	 * 
	 */
	public PlayField() {
		// TODO Auto-generated constructor stub
	}

	public PlayField(MainWindow window) {
		setSize(window.getWidth() * 625 / 800, window.getHeight());
		load(window);
	}
	/**
	 * 
	 * @param dimSelected
	 */
	public void reload(MainWindow window, int dimSelected) {
		this.removeAll();
		load(window, dimSelected);
		this.invalidate();
		this.validate();
		this.repaint();
		window.getLogField().getLogs().append("Game reloading... \n");
	}
	/**
	 * 
	 */
	public void load(MainWindow window) {
		load(window, 2);
	}
	/**
	 * 
	 * @param dim
	 */
	public void load(MainWindow window, int dim) {
		this.setPreferredSize(new Dimension(window.getWidth() * 400/640, window.getHeight() * 15/360));
		
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new GridLayout((int) (Math.pow(dim, 2)) + 1, (int) Math.pow(dim, 2), 1, 1));
		numberJButtons = new JButton[(int) (Math.pow(dim, 2))][(int) (Math.pow(dim, 2))];
		numbersToSelect = new JButton[(int) (Math.pow(dim, 2))];
		cellsTakenMap = new HashMap<>();
		rowColRepStringMap = new HashMap<>();
		numberCounter = new int[(int)Math.pow(dim, 2)];
		totalCounter = 0;
		for(int i=0; i<(int) (Math.pow(dim, 2)); i++) {
			if(i>9) {
				rowColRepStringMap.put(i, Character.toString('A' + i - 10));
			}else {
				rowColRepStringMap.put(i, String.valueOf(i));
			}
		}
		for (int i = 0; i < (int) (Math.pow(dim, 2)); i++) {
			for (int j = 0; j < (int) (Math.pow(dim, 2)); j++) {
				
				JButton btn = new JButton();
				btn.setPreferredSize(new Dimension(300 / (int) (Math.pow(dim, 2))-1, 300 / (int) (Math.pow(dim, 2))-1));
				
				btn.setName(rowColRepStringMap.get(i) + rowColRepStringMap.get(j));
				btn.setText(null);
				btn.setMargin(new Insets(0, 0, 0, 0));
				btn.addActionListener(e -> {
					
					if (numSelected!=null) {
						int numRep = -1;
						if(numSelected.charAt(0) >= 10)
							numRep = numSelected.charAt(0) - 55;
						else
							numRep = Integer.parseInt(numSelected);
						boolean available = true;
						for(String k : cellsTakenMap.keySet()) {
							System.out.println(k);
							if(cellsTakenMap.get(k).equals(numSelected) && (k.charAt(0) == btn.getName().charAt(0)|| k.charAt(1) == btn.getName().charAt(1))) {
								available = false;
								window.getLogField().getLogs().append("Invalid try...\n");
								//https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator
								String soundName = "wrong.wav";  
								try {
									AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
									Clip clip = AudioSystem.getClip();
									clip.open(audioInputStream);
									clip.start();
								}catch(Exception ex) {
									System.err.println("Error happened");
								}
								break;
							}
						}
						if(available) {
							if(btn.getText()!=null)	{
								numberCounter[numRep-1]--;
								totalCounter--;
							}
							btn.setText(numSelected);
							btn.setBackground(Color.CYAN);
							cellsTakenMap.put(btn.getName(), numSelected);
							
							numberCounter[numRep-1]++;
						
							totalCounter++;
							window.getLogField().getLogs().append(numSelected + " was put to Row "+ btn.getName().charAt(0) + ", " + " Col " + btn.getName().charAt(1) +"...\n");
							String soundName = null;
							if(totalCounter == (int) (Math.pow(dim, 2)) * (int) (Math.pow(dim, 2))) {
								soundName = "complete.wav"; 
								complete();
							}else if(numberCounter[numRep-1]  == dim * dim) {
								soundName = "goodjob.wav";
								stepComplete(numSelected);
							}
							else
								soundName = "correct.wav";  
							//https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator
							try {
								AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
								Clip clip = AudioSystem.getClip();
								clip.open(audioInputStream);
								clip.start();
							}catch(Exception ex) {
								System.err.println("Error happened");
							}
						}
					}
				});
				numberJButtons[i][j] = btn;
				this.add(btn);
			}
		}
		
		for (int i = 0; i < (int) (Math.pow(dim, 2)); i++) {
			for (int j = 0; j < (int) (Math.pow(dim, 2)); j++) {
				if(((i/dim)%2==0 &&(j/dim)%2==0 || (i/dim)%2!=0 && (j/dim)%2!=0)&& i%dim == 0 && j%dim == 0 ) {
					for(int n=0; n<dim; n++) {
						for(int m=0; m<dim; m++) {
							numberJButtons[i+n][j + m].setBackground(new Color(220, 240, 244));
						}
					}
				}
			}
		}
		
		for(int i=1; i<=(int) (Math.pow(dim, 2));i++) {
			JButton btn = new JButton(String.valueOf(i<10?i : Character.toString((char)('A' + (i - 10)))));
			btn.setPreferredSize(new Dimension(300 / (int) (Math.pow(dim, 2))-1, 300 / (int) (Math.pow(dim, 2))-1));
			btn.setBackground(Color.GREEN);
			btn.setMargin(new Insets(0, 0, 0, 0));
			btn.addActionListener(e->{
				btn.setBackground(Color.YELLOW);
				numSelected = btn.getText();
				for(JButton b: numbersToSelect) {
					if(!b.equals(btn)) {
						b.setBackground(Color.GREEN);
					}
				}
				window.getLogField().getLogs().append("Selected number: " + numSelected + "\n");
			});
			numbersToSelect[i-1] = btn;
			this.add(btn);
		}
	}
	
	public void setDifficulty(String level) {
		
	}
	
	public void stepComplete(String number) {
		for(int i=0; i<numberJButtons.length; i++) {
			for(int j=0; j<numberJButtons[i].length; j++) {
				if(numberJButtons[i][j].getText()!=null && numberJButtons[i][j].getText().equals(number)) {
					numberJButtons[i][j].setBackground(Color.BLUE);
				}
			}
		}
	}
	
	public void complete() {
		for(int i=0; i<numberJButtons.length; i++) {
			for(int j=0; j<numberJButtons[i].length; j++) {
				numberJButtons[i][j].setBackground(Color.PINK);
				numberJButtons[i][j].setEnabled(false);;
			}
		}
	}
	
	public String getNumSelected() {
		return numSelected;
	}

	public void setNumSelected(String numSelected) {
		this.numSelected = numSelected;
	}

}
