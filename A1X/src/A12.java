import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class A12 {
	
	private static JFrame mainFrame;
	private static JMenuBar menu;
	private static Container contentPane;
	private static JPanel playField;
	private static JPanel actionField;
	private static JPanel logField;
	private static String numSelected;
	private static int dimSelected;
	
	private static JTextField pointJTextField;
	private static JTextField timeField;
	private static JTextArea logs;
	
	private static JButton[][] numberJButtons;
	private static JButton[] numbersToSelect;
	private static Map<String, String> cellsTakenMap;
	
	private static void init() {
		mainFrame = new JFrame("Sudoku (Roger Li)");
		mainFrame.setSize(640, 360);
		
		System.out.println(mainFrame.getWidth() + " " + mainFrame.getHeight());
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		loadRootPane();
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	
	
	private static void loadRootPane() {
		contentPane = mainFrame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		loadMenu();
		loadPlayField();
		loadLogField();
		loadActionField();
		contentPane.add(menu, BorderLayout.PAGE_START);
		contentPane.add(logField, BorderLayout.LINE_END);
		contentPane.add(actionField, BorderLayout.PAGE_END);
		
		
	}
	
	private static void loadMenu() {
		menu = new JMenuBar();
//		menu.setPreferredSize(new Dimension(mainFrame.getWidth(), mainFrame.getHeight() * 15/360));
		JMenu game = new JMenu("Game");
		JMenu help = new JMenu("Help");
		JMenuItem newGame = new JMenuItem("New");
		newGame.addActionListener(e->{
			loadRootPane();
		});
		game.add(newGame);
		JMenuItem open = new JMenuItem("Open");
		//load the saved progress file, save function as Load button
		open.addActionListener(e->{
			loadProgress();
			logs.append("Opening a saved progress...\n");
		});
		game.add(open);
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(e->saveProgress());
		game.add(save);
		JMenuItem reset = new JMenuItem("Reset");
		//clear the play field
		reset.addActionListener(e->{
			
			resetGame();
			logs.append("Reseting game...\n");
		});
		game.add(reset);
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(e->mainFrame.dispose());
		game.add(exit);
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(e->{
			JDialog info = new JDialog(mainFrame);
			info.setTitle("About");
			JLabel msg = new JLabel("Sudoku\nVersion: 1.0.0\nBy: Roger Li - 040896855");

			info.add(msg);
			info.add(msg);
			info.add(msg);
			info.setSize(new Dimension(300, 100));
			info.setVisible(true);
			logs.append("About this program...\n");
		});
		help.add(about);
		menu.add(game);
		menu.add(help);
		
		
	}





	private static void loadPlayField() {
		loadPlayField(2);
		
	}
	
	private static void loadPlayField(Integer dimSelected) {
		if(playField!=null) contentPane.remove(playField);
		playField = null;
		playField = new JPanel();
		
		playField.setPreferredSize(new Dimension(mainFrame.getWidth() * 400/640, mainFrame.getHeight() * 15/360));
		playField.setMinimumSize(new Dimension(320, 380));
		playField.setMaximumSize(new Dimension(320, 380));
		
		playField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		playField.setLayout(new GridLayout((int) (Math.pow(dimSelected, 2)) + 1, (int) Math.pow(dimSelected, 2), 1, 1));
		numberJButtons = new JButton[(int) (Math.pow(dimSelected, 2))][(int) (Math.pow(dimSelected, 2))];
		numbersToSelect = new JButton[(int) (Math.pow(dimSelected, 2))];
		cellsTakenMap = new HashMap<>();
		for (int i = 0; i < (int) (Math.pow(dimSelected, 2)); i++) {
			for (int j = 0; j < (int) (Math.pow(dimSelected, 2)); j++) {
				
				JButton btn = new JButton();
				btn.setPreferredSize(new Dimension(300 / (int) (Math.pow(dimSelected, 2))-1, 300 / (int) (Math.pow(dimSelected, 2))-1));
				btn.setName(String.valueOf(i)+String.valueOf(j));
				btn.setText(null);
				btn.addActionListener(e -> {
					
					if (numSelected!=null) {
						boolean available = true;
						for(String k : cellsTakenMap.keySet()) {
							if(cellsTakenMap.get(k).equals(numSelected) && (k.charAt(0) == btn.getName().charAt(0)|| k.charAt(1) == btn.getName().charAt(1))) {
								available = false;
								logs.append("Invalid try...\n");
								break;
							}
						}
						if(available) {
							btn.setText(numSelected);
							btn.setBackground(Color.CYAN);
							cellsTakenMap.put(btn.getName(), numSelected);
							logs.append(numSelected + " was put to Row "+ btn.getName().charAt(0) + ", " + " Col " + btn.getName().charAt(1) +"...\n");
						}
					}
				});
				numberJButtons[i][j] = btn;
				playField.add(btn);
				
				contentPane.add(playField, BorderLayout.LINE_START);
			}
		}
		
		for(int i=1; i<=(int) (Math.pow(dimSelected, 2));i++) {
			JButton btn = new JButton(String.valueOf(i<10?i : Character.toString((char)('A' + (i - 10)))));
			btn.setPreferredSize(new Dimension(300 / (int) (Math.pow(dimSelected, 2))-1, 300 / (int) (Math.pow(dimSelected, 2))-1));
			btn.setBackground(Color.GREEN);
			btn.addActionListener(e->{
				btn.setBackground(Color.YELLOW);
				numSelected = btn.getText();
				for(JButton b: numbersToSelect) {
					if(!b.equals(btn)) {
						b.setBackground(Color.GREEN);
						
					}
				}
				logs.append("Selected number: " + numSelected + "\n");
			});
			numbersToSelect[i-1] = btn;
			playField.add(btn);
		}
	}
	
	private static void loadLogField() {
		if(logField!=null) logField = null;
		logField = new JPanel();
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
		logField.setLayout(new BoxLayout(logField, BoxLayout.Y_AXIS));
		logField.add(picLabel);
		JScrollPane logArea = new JScrollPane(logs);
		logArea.setPreferredSize(new Dimension(200, 300));
		logField.add(logArea);
		
	}

	private static void loadActionField() {
		actionField = new JPanel();
		actionField.setLayout(new FlowLayout());
		JLabel dimLabel = new JLabel("Dim: ");
		actionField.add(dimLabel);
		JComboBox<Integer> dim = new JComboBox<>();
		dim.addItem(2);
		dim.addItem(3);
		dim.addItem(4);
		dim.addActionListener(e->{
			numSelected = null;
			dimSelected = (Integer)dim.getSelectedItem();
			
			reloadPlayField();
			logs.append("Dimension number set to :" + dimSelected + "\n");
			
		});
		actionField.add(dim);
		
		JButton saveButton = new JButton("Save");
		actionField.add(saveButton);
		JButton loadButton = new JButton("Load");
		actionField.add(loadButton);
		JButton randButton = new JButton("Rand");
		actionField.add(randButton);
		JButton resetButton = new JButton("Reset");
		actionField.add(resetButton);
		JLabel pointLabel = new JLabel("Points: ");
		actionField.add(pointLabel);
		pointJTextField = new JTextField();
		pointJTextField.setEditable(false);
		pointJTextField.setText("0");
		pointJTextField.setPreferredSize(new Dimension(40, 20));
		actionField.add(pointJTextField);
		JLabel timeLabel = new JLabel("Time: ");
		actionField.add(timeLabel);
		timeField = new JTextField();
		timeField.setEditable(false);
		timeField.setText("0");
		timeField.setPreferredSize(new Dimension(40, 20));
		actionField.add(timeField);
				
	}
	
	private static void reloadPlayField() {
		loadPlayField(dimSelected);
		mainFrame.invalidate();
		mainFrame.validate();
		mainFrame.repaint();
		logs.append("Game reloading... \n");
	}

	private static void saveProgress() {
		logs.append("Saving progress... \n");
	}

	private static void loadProgress() {
		logs.append("Loading progress... \n");
		
	}
	
	private static void resetGame() {
		reloadPlayField();
		logs.append("Resetting game... \n");
		
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				init();
				
			}
		});
		
//		JFrame mainWindow = new JFrame("Sudoku (Roger Li)");
//		mainWindow.setSize(new Dimension(600, 400));
//		mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		Container pane = mainWindow.getContentPane();
//		pane.setLayout(new BorderLayout());
//		JPanel gridJPanel = new JPanel();
//		gridJPanel.setLayout(new GridLayout(3, 4));
//		
//		for(int i=1; i<10; i++) {
//			JButton btnButton = new JButton("Button"+ i);
//			gridJPanel.add(btnButton);
//		}
//		pane.add(gridJPanel, BorderLayout.CENTER);
//		JPanel actionJPanel = new JPanel();
//		JPanel logPanel = new JPanel();
//		actionJPanel.setLayout(new FlowLayout());
//		JComboBox<Integer> dim = new JComboBox<>();
//		dim.addItem(2);
//		dim.addItem(3);
//		dim.addItem(4);
//		dim.addActionListener(e->{
//			
//		});
//		JLabel dimLabel = new JLabel("Dim: ");
//		actionJPanel.add(dimLabel);
//		actionJPanel.add(dim);
//		actionJPanel.add(new JButton("Save"));
//		actionJPanel.add(new JButton("Load"));
//		actionJPanel.add(new JButton("Rand"));
//		actionJPanel.add(new JButton("Reset"));
//		JLabel pointLabel = new JLabel("Points: ");
//		JTextField pointJTextField = new JTextField();
//		pointJTextField.setEditable(false);
//		pointJTextField.setText("0");
//		pointJTextField.setPreferredSize(new Dimension(40, 20));
//		JTextField timeField = new JTextField();
//		timeField.setEditable(false);
//		timeField.setText("0");
//		timeField.setPreferredSize(new Dimension(40, 20));
//		
//		JLabel timeLabel = new JLabel("Time: ");
//		actionJPanel.add(pointLabel);
//		actionJPanel.add(pointJTextField);
//		actionJPanel.add(timeLabel);
//		actionJPanel.add(timeField);
//		pane.add(actionJPanel, BorderLayout.PAGE_START); 
//		JTextArea logs = new JTextArea();
//		logs.setEditable(false);
//		logs.setPreferredSize(new Dimension(200, 200));
//		
//		JScrollPane logJScrollPane = new JScrollPane(logs);
//		logJScrollPane.setPreferredSize(new Dimension(200, 300));
//		logPanel.add(logJScrollPane);
//		
//		pane.add(logPanel, BorderLayout.LINE_END);
//		mainWindow.setVisible(true);

	}
	
	

}
