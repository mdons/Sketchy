package Sketchy;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

/**
 * The Controls panel is a JPanel responsible for modifying the
 * drawing state. It contains a list of four mutually exclusive
 * JRadioButtons which will set the DrawingState appropriately
 * through their ActionListeners and the StateHolder association.
 * The radio buttons are listed in a descending GridLayout.
 * 
 * @author mjdonnel
 *
 */
public class Controls extends JPanel {
	
	private StateHolder _stateHolder;
	
	public Controls(StateHolder stateHolder) {
		super();
		_stateHolder = stateHolder;
		
		JRadioButton selectButton = new JRadioButton("SELECT SHAPE");
		JRadioButton penButton = new JRadioButton("DRAW WITH PEN");
		JRadioButton rectangleButton = new JRadioButton("DRAW RECTANGLE");
		JRadioButton ellipseButton = new JRadioButton("DRAW ELLIPSE");
		
		switch (Constants.INITSTATE) {
		case 0:
			selectButton.setSelected(true);
			break;
		case 1:
			penButton.setSelected(true);
			break;
		case 2:
			rectangleButton.setSelected(true);
			break;
		default:
			ellipseButton.setSelected(true);
		}
		
		ButtonGroup buttonGroup = new ButtonGroup(); // sets up mutual exclusivity
		buttonGroup.add(selectButton);
		buttonGroup.add(penButton);
		buttonGroup.add(rectangleButton);
		buttonGroup.add(ellipseButton);
		
		selectButton.addActionListener(new ButtonListener(0));
		penButton.addActionListener(new ButtonListener(1));
		rectangleButton.addActionListener(new ButtonListener(2));
		ellipseButton.addActionListener(new ButtonListener(3));
		
		this.setLayout(new GridLayout(4,0));
		this.setBorder(new TitledBorder("Controls"));
		this.add(selectButton);
		this.add(penButton);
		this.add(rectangleButton);
		this.add(ellipseButton);
	}
	
	/**
	 * ButtonListener is an ActionListener that will fire
	 * when a radio button is pressed. It receives an
	 * associated channel which is also the drawing state
	 * to which the ButtonListener is committed.
	 * 
	 * @author mjdonnel
	 *
	 */
	private class ButtonListener implements ActionListener {
		
		private int _channel;
		
		public ButtonListener(int channel) {
			_channel = channel;
		}
		
		public void actionPerformed(ActionEvent e) {
			_stateHolder.setState(_channel);
		}
		
	}

}
