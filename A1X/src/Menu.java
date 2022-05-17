import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar {
	JMenu game;
	JMenu help;
	JMenuItem newGame;
	JMenuItem open;
	JMenuItem save;
	JMenuItem reset;
	JMenuItem exit;
	
	public Menu() {
		loadGameMenu();
		loadHelpMenu();
		this.add(game);
		this.add(help);
		
	}
	
	public void loadGameMenu() {
		game = new JMenu("Game");
		newGame = new JMenuItem();
		newGame.addActionListener(e->{
		});
	}
	
	void loadHelpMenu() {
		// TODO Auto-generated method stub
		
	}
}
