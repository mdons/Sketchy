package Sketchy;

import java.util.ArrayList;

/**
 * Removes a recently made shape or remakes it.
 * @author mjdonnel
 *
 */
public class MakeShape extends Command {
	
	private SketchyShape _shape;
	private ArrayList<SketchyShape> _shapes;
	
	public MakeShape(SketchyShape shape, ArrayList<SketchyShape> shapes) {
		_shape = shape;
		_shapes = shapes;
	}
	
	public void undo() {
		_shapes.remove(_shape);
	}
	
	public void redo() {
		_shapes.add(_shape);
	}

}
