package Sketchy;

import java.util.ArrayList;

/**
 * Remakes a deleted shape, or redeletes it.
 * @author mjdonnel
 *
 */
public class DeleteShape extends Command {
	
	private int _index;
	private SketchyShape _shape;
	private ArrayList<SketchyShape> _shapes;
	
	public DeleteShape(int index, SketchyShape shape, ArrayList<SketchyShape> shapes) {
		_index = index;
		_shape = shape;
		_shapes = shapes;
	}
	
	public void undo() {
		_shapes.add(_index, _shape);
	}
	
	public void redo() {
		_shapes.remove(_index);
	}

}
