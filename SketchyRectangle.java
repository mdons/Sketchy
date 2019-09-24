package Sketchy;

import java.awt.geom.Rectangle2D;
import cs015.fnl.SketchySupport.FileIO;

/**
 * See SketchyShape for explanation.
 * @author mjdonnel
 *
 */
public class SketchyRectangle extends SketchyShape {
	
	public SketchyRectangle() {
		super(new Rectangle2D.Double());
	}
	
	public void writeName(FileIO fileIO) {
		fileIO.writeString("rectangle");
	}

}
