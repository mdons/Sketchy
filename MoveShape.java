package Sketchy;

/**
 * Swaps a shape's location with its previous location.
 * @author mjdonnel
 *
 */
public class MoveShape extends Command {
	
	private SketchyShape _shape;
	private double _prevX;
	private double _prevY;
	
	public MoveShape(SketchyShape shape) {
		_shape = shape;
		_prevX = _shape.getX();
		_prevY = _shape.getY();
	}
	
	public void undo() {
		double tempX = _shape.getX();
		double tempY = _shape.getY();
		_shape.setLocation(_prevX, _prevY);
		_prevX = tempX;
		_prevY = tempY;
	}
	
	public void redo() {
		this.undo();
	}

}
