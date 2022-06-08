package cst8221.assignment.component;

import java.util.ArrayList;

import javax.swing.JButton;

public class DimBlock {
	
	private ArrayList<JButton> buttons;

	
	public DimBlock() {
		// TODO Auto-generated constructor stub
	}
	
	public DimBlock(int dim) {
		buttons = new ArrayList<>();
	}
	
	public void addButton(JButton button) {
		buttons.add(button);
	}

	public ArrayList<JButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<JButton> buttons) {
		this.buttons = buttons;
	}
	
	
}
