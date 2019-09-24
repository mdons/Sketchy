package Sketchy;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Operations is a JPanel responsible for undoing, redoing,
 * saving, and loading All of these actions are effected through
 * calls to the DrawingPanel methods. The four JButtons are
 * arranged in a descending GridLayout and have attached
 * ActionListeners which make an appropriate method call when a
 * button is pressed.
 * 
 * @author mjdonnel
 *
 */
public class Operations extends JPanel {
	
	private DrawingPanel _drawingPanel;
	
	public Operations(DrawingPanel drawingPanel) {
		super();
		_drawingPanel = drawingPanel;
		
		JButton undoButton = new JButton("UNDO");
		JButton redoButton = new JButton("REDO");
		JButton saveButton = new JButton("SAVE");
		JButton loadButton = new JButton("LOAD");
		
		undoButton.addActionListener(new ButtonListener(0));
		redoButton.addActionListener(new ButtonListener(1));
		saveButton.addActionListener(new ButtonListener(2));
		loadButton.addActionListener(new ButtonListener(3));
		
		this.setLayout(new GridLayout(4,0));
		this.setBorder(new TitledBorder("Operations"));
		this.add(undoButton);
		this.add(redoButton);
		this.add(saveButton);
		this.add(loadButton);
	}
	
	/**
	 * The ButtonListener inner class takes in a channel to inform
	 * the ButtonListener which task it should perform when its button
	 * is pressed. 0 means undo, 1 means redo, 2 means save, and 3
	 * means load.
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
				_drawingPanel.undo();
				break;
			case 1:
				_drawingPanel.redo();
				break;
			case 2:
				_drawingPanel.save();
				break;
			case 3:
				_drawingPanel.load();
				break;
			}
		}
		
	}

}
