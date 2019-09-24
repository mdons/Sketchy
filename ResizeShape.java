package Sketchy;

/**
 * Swaps a shape's location and dimensions with its previous location and dimensions.
 * @author mjdonnel
 *
 */
public class ResizeShape extends Command {
	
	private SketchyShape _shape;
	private double _x;
	private double _y;
	private double _width;
	private double _height;
	
	public ResizeShape(SketchyShape shape) {
		_shape = shape;
		_x = _shape.getX();
		_y = _shape.getY();
		_width = _shape.getWidth();
		_height = _shape.getHeight();
	}
	
	public void undo() {
		double tempX = _shape.getX();
		double tempY = _shape.getY();
		double tempWidth = _shape.getWidth();
		double tempHeight = _shape.getHeight();
		
		_shape.setLocation(_x, _y);
		_shape.setSize(_width, _height);
		
		_x = tempX;
		_y = tempY;
		_width = tempWidth;
		_height = tempHeight;
	}
	
	public void redo() {
		this.undo();
	}

}
