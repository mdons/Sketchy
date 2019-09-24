package Sketchy;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * My top level object instantiates two subcomponent JPanels DrawingPanel and ControlPanel.
 * These objects are added to the MainPanel in a BorderLayout. Additionally, this class
 * contains two Holders. StateHolder is responsible for setting and getting the program state
 * (0 for select state, 1 for draw state, 2 for make rectangle state, and 3 for make ellipse
 * state). Additionally, ColorHolder is responsible for setting and getting the user selected
 * color. The ColorHolder is initialized with the default color defined in the constants class.
 * Both the DrawingPanel and the ControlPanel receive associations to the Holders.
 * 
 * @author mjdonnel
 *
 */
public class MainPanel extends JPanel {
	
	public MainPanel() {
		super();
		this.setLayout(new BorderLayout());
		
		Color startingColor = new Color(Constants.INITCOLOR_RED, Constants.INITCOLOR_GREEN, Constants.INITCOLOR_BLUE);
		ColorHolder colorHolder = new ColorHolder(startingColor);
		StateHolder stateHolder = new StateHolder(Constants.INITSTATE);
		
		DrawingPanel drawingPanel = new DrawingPanel(stateHolder, colorHolder);
		ControlPanel controlPanel = new ControlPanel(stateHolder, colorHolder, drawingPanel);
		
		this.add(drawingPanel, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.WEST);
	}

}
