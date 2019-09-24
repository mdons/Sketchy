package Sketchy;

import java.util.ArrayList;

/**
 * Swaps two shapes' indexes in the _shapes ArrayList. This can be used to
 * undo or redo both the raising and the lowering of shapes.
 * @author mjdonnel
 *
 */
public class SwapIndexes extends Command {
	
	private int _i1;
	private int _i2;
	private ArrayList<SketchyShape> _shapes;
	
	public SwapIndexes(int i1, int i2, ArrayList<SketchyShape> shapes) {
		_i1 = i1;
		_i2 = i2;
		_shapes = shapes;
	}
	
	public void undo() {
		SketchyShape shape1 = _shapes.get(_i1);
		SketchyShape shape2 = _shapes.get(_i2);
		_shapes.set(_i1, shape2);
		_shapes.set(_i2, shape1);
	}
	
	public void redo() {
		this.undo();
	}

}
