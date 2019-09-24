package Sketchy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import cs015.fnl.SketchySupport.FileIO;

/**
 * DrawingPanel is the largest and most important class in Sketchy. It is here where shapes and lines
 * are created and deleted, undone and redone, in the blink of an eye. But seriously, DrawingPanel
 * stores all the displayed SketchyShapes and CurvedLines in ArrayLists called _shapes and _lines,
 * respectively. It also keeps track of the _currentShape and _currentLine for various operations.
 * Finally, DrawingPanel keeps an _undoStack and a _redoStack as a record of commands. The many
 * methods of DrawingPanel will perform most operations using these key variables. See methods for
 * more specific descriptions.
 * 
 * DrawingPanel itself is just a JPanel with an attached MouseAdapter, which is called during both
 * mouse clicks and mouse movement.
 * 
 * @author mjdonnel
 *
 */
public class DrawingPanel extends JPanel {
	
	private FileIO _fileIO;
	private StateHolder _stateHolder;
	private ColorHolder _colorHolder;
	private ArrayList<CurvedLine> _lines;
	private ArrayList<SketchyShape> _shapes;
	private Stack<Command> _undoStack;
	private Stack<Command> _redoStack;
	private SketchyShape _currentShape;
	private CurvedLine _currentLine;
	
	public DrawingPanel(StateHolder stateHolder, ColorHolder colorHolder) {
		super();
		_fileIO = new FileIO(); // called for save/load interactions
		_stateHolder = stateHolder; // association to StateHolder
		_colorHolder = colorHolder; // association to ColorHolder
		_lines = new ArrayList<CurvedLine>(); // contains all CurvedLines to be displayed after repaint
		_shapes = new ArrayList<SketchyShape>(); // contains all SketchyShapes to be displayed after repaint
		_undoStack = new Stack<Command>(); // record of previous actions/commands
		_redoStack = new Stack<Command>(); // record of undone actions/commands
		_currentShape = null;
		_currentLine = null;
		
		Dimension drawingPanelDimensions = new Dimension(Constants.DRAWINGPANEL_WIDTH, Constants.DRAWINGPANEL_HEIGHT); // initial dimensions stored in Constants
		this.setPreferredSize(drawingPanelDimensions);
		this.setSize(drawingPanelDimensions);
		this.setBackground(Color.WHITE);
		
		MyMouseAdapter myMouseAdapter = new MyMouseAdapter(this); // MouseAdapter receives an association to DrawingPanel
		this.addMouseListener(myMouseAdapter);
		this.addMouseMotionListener(myMouseAdapter);
		
		this.setFocusable(true);
		this.grabFocus();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (SketchyShape shape: _shapes) {
			shape.paint(g); // displays every shape in _shapes
		}
		for (CurvedLine line: _lines) {
			line.paint(g); // displays every line in _lines (see CurvedLine's paint method for more info)
		}
	}
	
	/**
	 * Takes in the position of a mouse press. Clears the current shape, then searches the list of
	 * shapes for a shape which has been clicked. If a new current shape is found, its border is
	 * changed to black. All other borders are set to their shape's fill color.
	 * 
	 * @param click
	 */
	public void selectShape(Point click) {
		_currentShape = null;
		for (int i=_shapes.size()-1; i>=0; i--) { // selects the top most shape when shapes overlap
			SketchyShape shape = _shapes.get(i);
			if (shape.contains(click) && (_currentShape == null)) { // will not select more than one current shape when shapes overlap
				_currentShape = shape;
				_currentShape.setBorderColor(Color.BLACK);
			}
			else {
				shape.setBorderColor(shape.getFillColor());
			}
		}
	}
	
	/**
	 * Convenient method which resets the current shape's border and releases it.
	 */
	public void clearShape() {
		if (_currentShape != null) {
			_currentShape.setBorderColor(_currentShape.getFillColor());
			_currentShape = null;
		}
	}
	
	/**
	 * Creates a new CurvedLine and makes it the current line. Line segments are added in
	 * another method.
	 */
	public void makeCurvedLine() {
		_currentLine = new CurvedLine();
		_lines.add(_currentLine);
	}
	
	/**
	 * Takes in the position of a mouse press. Creates a new rectangle or ellipse at that location
	 * depending on the current drawing state. Sets the shapes color according to the ColorHolder.
	 * Adds the shape to the list of shapes to be displayed. Finally, creates a record of this
	 * operation through the Command, MakeShape. This Command is added to the undo stack and the
	 * redo stack is cleared.
	 * 
	 * @param loc
	 */
	public void makeSketchyShape(Point loc) {
		switch (_stateHolder.getState()) {
		case 2:
			_currentShape = new SketchyRectangle();
			break;
		case 3:
			_currentShape = new SketchyEllipse();
			break;
		}
		_currentShape.setBorderWidth(Constants.BORDER_WIDTH);
		_currentShape.setLocation(loc.x, loc.y);
		_currentShape.setColor(_colorHolder.getColor());
		_shapes.add(_currentShape);
		
		Command makeShape = new MakeShape(_currentShape, _shapes);
		_undoStack.push(makeShape);
		_redoStack = new Stack<Command>();
	}
	
	/**
	 * Takes in the previous position of the mouse and the current position of the mouse as the
	 * mouse is dragged. Resizes the current shape accordingly. Takes rotation into account using
	 * the rotatePoint method.
	 * 
	 * @param prev
	 * @param curr
	 */
	public void resizeShape(Point prev, Point curr) {
		double width = _currentShape.getWidth();
		double height = _currentShape.getHeight();
		double locX = _currentShape.getX();
		double locY = _currentShape.getY();
		Point center = _currentShape.getCenter();
		double theta = _currentShape.getRotation();
		
		Point rotatedPrev = this.rotatePoint(prev, center, theta);
		Point rotatedCurr = this.rotatePoint(curr, center, theta);
		
		int dX = rotatedCurr.x - rotatedPrev.x; // change in x, accounting for rotation
		int dY = rotatedCurr.y - rotatedPrev.y; // change in y, accounting for rotation
		
		if (rotatedCurr.x < center.x) {
			dX = -dX;
		}
		if (rotatedCurr.y < center.y) {
			dY = -dY;
		}
		
		_currentShape.setLocation(locX - dX, locY - dY); // top left corner expands by dX, dY
		_currentShape.setSize(width + 2 * dX, height + 2 * dY); // bottom right corner expands by dX, dY
	}
	
	 /**
	  * The helper method was given as pseudocode in the assignment. This method rotates a given point
	  * around another given point using a given angle. The point is then returned to the resize method,
	  * which called it.
	  * 
	  * @param pointToRotate
	  * @param rotateAround
	  * @param angle
	  * @return
	  */
	private Point rotatePoint(Point pointToRotate, Point rotateAround, double angle) {
		double sine = Math.sin(angle/180*Math.PI);
		double cosine = Math.cos(angle/180*Math.PI);
		Point rotatedPoint = new Point(pointToRotate.x-rotateAround.x, pointToRotate.y-rotateAround.y);
		rotatedPoint = new Point((int) (rotatedPoint.x*cosine+rotatedPoint.y*sine), (int) (-rotatedPoint.x*sine+rotatedPoint.y*cosine));
		rotatedPoint = new Point(rotatedPoint.x+rotateAround.x, rotatedPoint.y+rotateAround.y);
		return rotatedPoint;
	}
	
	/**
	 * Takes in the previous position of the mouse and the current position of the mouse as the
	 * mouse is dragged. Moves the current shape accordingly.
	 * 
	 * @param prev
	 * @param curr
	 */
	public void moveShape(Point prev, Point curr) {
		int dX = curr.x - prev.x;
		int dY = curr.y - prev.y;
		double locX = _currentShape.getX();
		double locY = _currentShape.getY();
		_currentShape.setLocation(locX + dX, locY + dY);
	}
	
	/**
	 * Takes in the previous position of the mouse and the current position of the mouse as the
	 * mouse is dragged. Rotates the current shape accordingly.
	 * 
	 * @param prev
	 * @param curr
	 */
	public void rotateShape(Point prev, Point curr) {
		Point center = _currentShape.getCenter();
		double prevTheta = _currentShape.getRotation();
		double dTheta = (Math.atan2(curr.y-center.y, curr.x-center.x) - Math.atan2(prev.y-center.y, prev.x-center.x)) / Math.PI * 180;
		_currentShape.setRotation(prevTheta + dTheta);
	}
	
	/**
	 * Raises the current shape by moving it one position back in the shapes ArrayList so it
	 * will be painted later. The old occupant is swapped into the forward spot. Finally,
	 * a swapIndexes command keeping a record of the swapped indexes is pushed onto the undo
	 * stack and the redo stack is cleared.
	 */
	public void raiseShape() {
		int i = _shapes.indexOf(_currentShape);
		if (i<_shapes.size()-1) {
		SketchyShape temp = _shapes.get(i+1);
		_shapes.set(i, temp);
		_shapes.set(i+1, _currentShape);
		
		Command swapIndexes = new SwapIndexes(i, i+1, _shapes);
		_undoStack.push(swapIndexes);
		_redoStack = new Stack<Command>();
		}
	}
	
	/**
	 * See raiseShape method. Nearly identical, but the current shape is swapped forward in
	 * the ArrayList.
	 */
	public void lowerShape() {
		int i = _shapes.indexOf(_currentShape);
		if (i>0) {
		SketchyShape temp = _shapes.get(i-1);
		_shapes.set(i, temp);
		_shapes.set(i-1, _currentShape);
		
		Command swapIndexes = new SwapIndexes(i, i-1, _shapes);
		_undoStack.push(swapIndexes);
		_redoStack = new Stack<Command>();
		}
	}
	
	/**
	 * Changes the current shape's fill color to the current color. The shapes old color
	 * is saved and stored in a FillShape command, which is pushed onto the undo stack. The
	 * redo stack is cleared.
	 */
	public void fillShape() {
		if (_currentShape != null) {
			Color oldColor = _currentShape.getFillColor();
			_currentShape.setFillColor(_colorHolder.getColor());
			
			Command fillShape = new FillShape(oldColor, _currentShape);
			_undoStack.push(fillShape);
			_redoStack = new Stack<Command>();
		}
	}
	
	/**
	 * This method removes the current shape by deleting its reference in the shapes ArrayList
	 * and as the current shape. Prior to that, however, the shape and its index are stored
	 * in a DeleteShape command, which is added to the undo stack. The redo stack is cleared.
	 */
	public void deleteShape() {
		if (_currentShape != null) {
			int index = _shapes.indexOf(_currentShape);
			
			Command deleteShape = new DeleteShape(index, _currentShape, _shapes);
			_undoStack.push(deleteShape);
			_redoStack = new Stack<Command>();
			
			_shapes.remove(index);
			this.clearShape();
		}
	}
	
	/**
	 * If there is a command on the undo stack, it's removed, its undo method is called,
	 * and it's pushed onto the redo stack.
	 */
	public void undo() {
		if (!_undoStack.empty()) {
			Command lastCommand = _undoStack.pop();
			lastCommand.undo();
			_redoStack.push(lastCommand);
			this.repaint();
		}
	}
	
	/**
	 * If there is a command on the redo stack, it's removed, its redo method is called,
	 * and it's pushed onto the undo stack.
	 */
	public void redo() {
		if (!_redoStack.empty()) {
			Command nextCommand = _redoStack.pop();
			nextCommand.redo();
			_undoStack.push(nextCommand);
			this.repaint();
		}
	}
	
	/**
	 * The save method opens a file to be written to, tells all the displayed shapes to
	 * write themselves, tell all the displayed lines to write themselves, and closes
	 * the file.
	 */
	public void save() {
		String filename = FileIO.getFileName(true, this);
		if (filename != null) {
			_fileIO.openWrite(filename);
			for (SketchyShape shape: _shapes) {
				shape.save(_fileIO);
			}
			for (CurvedLine line: _lines) {
				line.save(_fileIO);
			}
			_fileIO.closeWrite();
		}
	}
	
	/**
	 * The load method resets the lists, stacks, and current objects. It then reads a
	 * string to determine what object to load next. It creates that object, then tells
	 * the object to load itself. Finally, the method closes the file.
	 */
	public void load() {
		String filename = FileIO.getFileName(false, this);
		if (filename != null) {
			_lines = new ArrayList<CurvedLine>();
			_shapes = new ArrayList<SketchyShape>();
			_undoStack = new Stack<Command>();
			_redoStack = new Stack<Command>();
			_currentShape = null;
			_currentLine = null;
			
			_fileIO.openRead(filename);
			while (_fileIO.hasMoreData()) {
				String string = _fileIO.readString();
				switch (string) {
				case "ellipse":
					SketchyEllipse ellipse = new SketchyEllipse();
					ellipse.load(_fileIO);
					_shapes.add(ellipse);
					break;
				case "rectangle":
					SketchyRectangle rectangle = new SketchyRectangle();
					rectangle.load(_fileIO);
					_shapes.add(rectangle);
					break;
				case "line":
					CurvedLine line = new CurvedLine();
					line.load(_fileIO);
					_lines.add(line);
					break;
				}
			}
			_fileIO.closeRead();
			this.repaint();
		}
	}
	
	/**
	 * The MyMouseAdapter class is responsible for a lot of user input. Its mousePressed
	 * method is called when a mouse button is depressed, its mouseReleased method is called
	 * when a mouse button is released, and its mouseDragged method is called when the mouse
	 * is dragged to a new location.
	 * 
	 * The MouseAdapter must know a lot of context in order to execute the appropriate actions.
	 * If a shape is selected, dragging it with the left mouse button will move it, dragging it
	 * with the middle mouse button will resize it, and dragging it will the right mouse button
	 * will rotate it. In the draw lines state, pressing the mouse creates a new CurvedLine,
	 * dragging the mouse adds segments to it, and releasing the mouse means that the line is
	 * finalized and can be stored in a command. If drawing rectangles or ellipses, pressing the
	 * mouse generates a new shape and dragging the mouse resizes that shape.
	 * 
	 * @author mjdonnel
	 *
	 */
	private class MyMouseAdapter extends MouseInputAdapter {
		
		private DrawingPanel _drawingPanel;
		private Point _prevPoint;
		
		public MyMouseAdapter(DrawingPanel drawingPanel) {
			_drawingPanel = drawingPanel;
			_prevPoint = null;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			_prevPoint = e.getPoint();
			
			switch (_stateHolder.getState()) {
			case 0: // select state/mode
				_drawingPanel.selectShape(_prevPoint);
				if(_currentShape != null) {
					switch (e.getModifiers()) {
					case MouseEvent.BUTTON1_MASK: // if a move is initiated with the left mouse button
						Command moveShape = new MoveShape(_currentShape); // store the shape's initial position in the MoveShape command
						_undoStack.push(moveShape);
						_redoStack = new Stack<Command>();
						break;
					case MouseEvent.BUTTON3_MASK: // if a rotation is initiated with the right mouse button
						Command rotateShape = new RotateShape(_currentShape); // store the shape's initial rotation in the RotateShape command
						_undoStack.push(rotateShape);
						_redoStack = new Stack<Command>();
						break;
					case MouseEvent.BUTTON2_MASK: // if a resize is initiated with the middle mouse button
						Command resizeShape = new ResizeShape(_currentShape); // store the shape's initial dimensions and location in the ResizeShape command
						_undoStack.push(resizeShape);
						_redoStack = new Stack<Command>();
					}
				}
				break;
			case 1: // draw line state/mode
				_drawingPanel.clearShape(); // unselect shape
				_drawingPanel.makeCurvedLine(); // make a new current line
				break;
			default: // make shape states/modes
				_drawingPanel.clearShape(); // unselect shape
				_drawingPanel.makeSketchyShape(_prevPoint); // make a new shape (method knows which shape to make)
				break;
			}
			
			_drawingPanel.repaint();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			Point currPoint = e.getPoint(); // most up-to-date mouse location
			switch (_stateHolder.getState()) {
			case 0:
				if (_currentShape != null) { // if a shape is selected
					switch (e.getModifiers()) {
					case MouseEvent.BUTTON1_MASK:
						_drawingPanel.moveShape(_prevPoint, currPoint); // move the shape as the left mouse button is dragged
						break;
					case MouseEvent.BUTTON3_MASK:
						_drawingPanel.rotateShape(_prevPoint, currPoint); // rotates the shape as the right mouse button is dragged
						break;
					case MouseEvent.BUTTON2_MASK:
						_drawingPanel.resizeShape(_prevPoint, currPoint); // resizes the shape as the middle mouse button is dragged
						break;
					}
				}
				break;
			case 1:
				_currentLine.addSegment(_prevPoint, currPoint); // adds segments to the current CurvedLine as the mouse is dragged
				break;
			default:
				_drawingPanel.resizeShape(_prevPoint, currPoint); // resizes a new shape as the mouse is dragged
				break;
			}
			
			_prevPoint = currPoint; // current mouse location is set as the previous mouse location for the next time this method is called
			_drawingPanel.repaint();
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (_stateHolder.getState() == 1) {
				Command drawLine = new DrawLine(_currentLine, _lines); // once all line segments have been added to a line, it is preserved in a command
				_undoStack.push(drawLine);
				_redoStack = new Stack<Command>();
			}
		}
		
	}
	
}
