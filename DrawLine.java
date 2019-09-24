package Sketchy;

import java.util.ArrayList;

/**
 * Undoes or redoes making a CurvedLine.
 * @author mjdonnel
 *
 */
public class DrawLine extends Command {
	
	private CurvedLine _line;
	private ArrayList<CurvedLine> _lines;
	
	public DrawLine(CurvedLine line, ArrayList<CurvedLine> lines) {
		_line = line;
		_lines = lines;
	}
	
	public void undo() {
		_lines.remove(_line);
	}
	
	public void redo() {
		_lines.add(_line);
	}

}
