package Sketchy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import cs015.fnl.SketchySupport.FileIO;

/**
 * The CurvedLine class represents a curved line as a collection of smaller, straight lines.
 * These line segments are stored in the ArrayList _line. The CurvedLine can add segments to
 * itself, paint/draw itself, save itself, and load itself.
 * 
 * @author mjdonnel
 *
 */
public class CurvedLine {
	
	private ArrayList<Line2D.Double> _line;
	
	public CurvedLine() {
		_line = new ArrayList<Line2D.Double>();
	}
	
	/**
	 * As the mouse is dragged, it will pass its old and new locations to the CurvedLine through
	 * the addSegment method, which will create a new Line2D.Double between the points. This line
	 * segment is then added to the list array.
	 * 
	 * @param prev
	 * @param curr
	 */
	public void addSegment(Point prev, Point curr) {
		Line2D.Double newSegment = new Line2D.Double(prev.x, prev.y, curr.x, curr.y);
		_line.add(newSegment);
	}
	
	/**
	 * Paint uses 2D graphics to draw all the line segments in the curved line.
	 * 
	 * @param g
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		for (Line2D.Double lineSegment: _line) {
			g2.draw(lineSegment);
		}
	}
	
	/**
	 * This method writes the line and all of its segments to a file.
	 * 
	 * @param fileIO
	 */
	public void save(FileIO fileIO) {
		fileIO.writeString("line");
		for (Line2D.Double lineSegment: _line) {
			fileIO.writeString("lineSegment");
			fileIO.writeDouble(lineSegment.x1);
			fileIO.writeDouble(lineSegment.y1);
			fileIO.writeDouble(lineSegment.x2);
			fileIO.writeDouble(lineSegment.y2);
		}
		fileIO.writeString("endOfLine"); //informs a load method when to stop reading line segments
	}
	
	/**
	 * This method reads the line and all of its segments from a file.
	 * 
	 * @param fileIO
	 */
	public void load(FileIO fileIO) {
		boolean moreData = fileIO.hasMoreData();
		while (moreData) { // reads and generates line segments and an "endOfLine" is read
			String string = fileIO.readString();
			switch (string) {
			case "lineSegment":
				double x1 = fileIO.readDouble();
				double y1 = fileIO.readDouble();
				double x2 = fileIO.readDouble();
				double y2 = fileIO.readDouble();
				Line2D.Double lineSegment = new Line2D.Double(x1, y1, x2, y2);
				_line.add(lineSegment);
				break;
			case "endOfLine":
				moreData = false;
				break;
			}
		}
	}

}
