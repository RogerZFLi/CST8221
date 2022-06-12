/**
 * File name: DimBlock.java
 * Roger Li - 040896855 & Denys Savskyi - 041004781
 * Course  CST 8221 - JAP, Lab Section: 302
 * Assignment: A12
 * Professor: Paulo Sousa
 * Date: June 10, 2022
 * Compiler: Eclipse IDE for Java Developers - Version: 2022-03 (4.23.0)
 * Purpose:File DimBlock.java is used to group the blocks of buttons that belong to the same dimension (e.g. dim=2, buttons at row 0, 1 and col 0, 1 will be grouped) with respect to Sudoku game logic. 
 */
package cst8221.assignment.component;

import java.util.ArrayList;

import javax.swing.JButton;

/**
 * Class Name: DimBlock
 * Method List: DimBlock(), DimBlock(int dim), addButton(JButton button), ArrayList getButtons(), setButtons(ArrayList buttons)
 * Constant List: 
 * Purpose: The class DimBlock is used to group the blocks of buttons that belong to the same dimension (e.g. dim=2, buttons at row 0, 1 and col 0, 1 will be grouped) with respect to Sudoku game logic.
 * @author Roger Li
 * @author Denys Savskyi
 *
 * @version Version 2 (2022-06-10)
 * @see "Import Swing Components: javax.swing.JButton;  java.util.ArrayList;"
 * @see "Package: cst8221.assignment.component;"
 * @since JDK 18.0.1.1
 * @since JRE JavaSE-14
 */
public class DimBlock {
	
	private ArrayList<JButton> buttons;

	/**
	 * Method Name: DimBlock
	 * Purpose: Default constructor DimBlock() 
	 * Algorithm:  
	 */
	public DimBlock() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Method Name: DimBlock
	 * Purpose: Constructor DimBlock() creates the Array List variable buttons 
	 * Algorithm:  
	 * @param dim - integer dim parameter that has a dimension value 
	 */
	public DimBlock(int dim) {
		buttons = new ArrayList<>();
	}
	
	/**
	 * Method Name: getButtons
	 * Purpose: Method getButtons() gets the value of the variable buttons
	 * Algorithm: add given JButton to buttons
	 * @param button the button to be added to the dim block
	 */
	public void addButton(JButton button) {
		buttons.add(button);//add given JButton to buttons
	}
	
	/**
	 * Method Name: getButtons
	 * Purpose: Method getButtons() gets the value of the variable buttons
	 * Algorithm:  
	 * @return buttons - parameter that returns the actual value of the variable 
	 */
	public ArrayList<JButton> getButtons() {
		return buttons;
	}
	
	/**
	 * Method Name: setButtons
	 * Purpose: Setter method setButtons() takes the param and assigns it to the variable buttons.
	 * Algorithm: 
	 * @param buttons - parameter that is used to assign the attribute.
	 */
	public void setButtons(ArrayList<JButton> buttons) {
		this.buttons = buttons;
	}
	
	
}
