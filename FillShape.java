package Sketchy;

import java.awt.Color;

/**
 * Swaps a shape's color with its previous color.
 * @author mjdonnel
 *
 */
public class FillShape extends Command {
	
	private Color _prevColor;
	private SketchyShape _shape;
	
	public FillShape(Color prevColor, SketchyShape shape) {
		_prevColor = prevColor;
		_shape = shape;
	}
	
	public void undo() {
		Color temp = _shape.getFillColor();
		_shape.setFillColor(_prevColor);
		_prevColor = temp;
	}
	
	public void redo() {
		this.undo();
	}

}
