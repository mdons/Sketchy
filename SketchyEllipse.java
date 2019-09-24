package Sketchy;

import java.awt.geom.Ellipse2D;
import cs015.fnl.SketchySupport.FileIO;

/**
 * See SketchyShape for explanation.
 * @author mjdonnel
 *
 */
public class SketchyEllipse extends SketchyShape {
	
	public SketchyEllipse() {
		super(new Ellipse2D.Double());
	}
	
	public void writeName(FileIO fileIO) {
		fileIO.writeString("ellipse");
	}

}
