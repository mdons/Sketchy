package Sketchy;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * ControlPanel is the JPanel to the left of the DrawingPanel.
 * It contains sub-panels which contain the actual controls for
 * Sketchy. In order to effect user actions, this panel passes
 * on the required association to the appropriate sub-panel.
 * 
 * @author mjdonnel
 *
 */
public class ControlPanel extends JPanel {
	
	public ControlPanel(StateHolder stateHolder, ColorHolder colorHolder, DrawingPanel drawingPanel) {
		super();
		this.setLayout(new GridLayout(4,0));
		this.add(new Controls(stateHolder)); // Controls panel modifies state
		this.add(new BrushColor(colorHolder)); // BrushColor panel modifies current color
		this.add(new ShapeManipulations(drawingPanel)); // ShapeManipulations runs DrawingPanel methods
		this.add(new Operations(drawingPanel)); // Operations runs DrawingPanel methods
	}

}
