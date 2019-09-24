package Sketchy;

/**
 * Swaps a shape's rotation with its previous rotation.
 * @author mjdonnel
 *
 */
public class RotateShape extends Command {
	
	private SketchyShape _shape;
	private double _angle;
	
	public RotateShape(SketchyShape shape) {
		_shape = shape;
		_angle = _shape.getRotation();
	}
	
	public void undo() {
		double temp = _shape.getRotation();
		_shape.setRotation(_angle);
		_angle = temp;
	}
	
	public void redo() {
		this.undo();
	}

}
