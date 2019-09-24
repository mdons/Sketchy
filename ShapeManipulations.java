package Sketchy;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * ShapeManipulations is a JPanel responsible for raising shapes,
 * lowering shapes, filling shapes, and deleting shapes. All of
 * these actions are effected through calls to the DrawingPanel
 * methods. The four JButtons are arranged in a descending
 * GridLayout and have attached ActionListeners which make an
 * appropriate method call when a button is pressed.
 * 
 * @author mjdonnel
 *
 */
public class ShapeManipulations extends JPanel {
	
	private DrawingPanel _drawingPanel;
	
	public ShapeManipulations(DrawingPanel drawingPanel) {
		super();
		_drawingPanel = drawingPanel;
		
		JButton raiseButton = new JButton("RAISE");
		JButton lowerButton = new JButton("LOWER");
		JButton fillButton = new JButton("FILL");
		JButton deleteButton = new JButton("DELETE");
		
		raiseButton.addActionListener(new ButtonListener(0));
		lowerButton.addActionListener(new ButtonListener(1));
		fillButton.addActionListener(new ButtonListener(2));
		deleteButton.addActionListener(new ButtonListener(3));
		
		this.setLayout(new GridLayout(4,0));
		this.setBorder(new TitledBorder("Shape Manipulations"));
		this.add(raiseButton);
		this.add(lowerButton);
		this.add(fillButton);
		this.add(deleteButton);
	}
	
	/**
	 * The ButtonListener inner class takes in a channel to inform
	 * the ButtonListener which task it should perform when its button
	 * is pressed. 0 means raise shape, 1 means lower shape, 2 means
	 * fill shape, and 3 means delete shape.
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
			switch (_channel) {
			case 0:
				_drawingPanel.raiseShape();
				_drawingPanel.repaint();
				break;
			case 1:
				_drawingPanel.lowerShape();
				_drawingPanel.repaint();
				break;
			case 2:
				_drawingPanel.fillShape();
				_drawingPanel.repaint();
				break;
			case 3:
				_drawingPanel.deleteShape();
				_drawingPanel.repaint();
				break;
			}
		}
		
	}

}
