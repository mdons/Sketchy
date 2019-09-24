package Sketchy;

import java.awt.Color;

/**
 * ColorHolder is responsible for setting and getting
 * the user selected color.
 * 
 * @author mjdonnel
 *
 */
public class ColorHolder {
	
	private Color _currentColor;
	
	public ColorHolder(Color newColor) {
		_currentColor = newColor;
	}
	
	public Color getColor() {
		return _currentColor;
	}
	
	public void setColor(Color newColor) {
		_currentColor = newColor;
	}

}
