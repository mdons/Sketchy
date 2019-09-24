package Sketchy;

import java.awt.Color;

import cs015.fnl.SketchySupport.FileIO;
/**
 * SketchyShape is an abstract class nearly identical to a support code Shape, but with
 * added save and load functionality. Additionally, whether this shape is a rectangle or
 * ellipse will be decided in the constructors of its children (SketchyEllipse and
 * SketchyRectangle). Because the children are different shapes, each must have their own
 * method for writing their name. This is done through the abstract writeName method.
 * Otherwise, all SketchyShapes save and load the same way.
 * 
 * @author mjdonnel
 *
 */
public abstract class SketchyShape extends cs015.prj.Shape.Shape {
	
	public SketchyShape(java.awt.geom.RectangularShape shape) {
		super(shape);
	}
	
	/**
	 * Writes the properties of this shape to a file.
	 * @param fileIO
	 */
	public void save(FileIO fileIO) {
		this.writeName(fileIO);
		fileIO.writeDouble(super.getX());
		fileIO.writeDouble(super.getY());
		fileIO.writeDouble(super.getWidth());
		fileIO.writeDouble(super.getHeight());
		fileIO.writeDouble(super.getRotation());
		Color color = super.getFillColor();
		fileIO.writeInt(color.getRed());
		fileIO.writeInt(color.getGreen());
		fileIO.writeInt(color.getBlue());
	}
	/**
	 * Reads and sets the properties of this shape from a file.
	 * @param fileIO
	 */
	public void load(FileIO fileIO) {
		this.setBorderWidth(Constants.BORDER_WIDTH);
		double x = fileIO.readDouble();
		double y = fileIO.readDouble();
		this.setLocation(x, y);
		double width = fileIO.readDouble();
		double height = fileIO.readDouble();
		this.setSize(width, height);
		double rotation = fileIO.readDouble();
		this.setRotation(rotation);
		int red = fileIO.readInt();
		int green = fileIO.readInt();
		int blue = fileIO.readInt();
		Color color = new Color(red, green, blue);
		this.setColor(color);
	}
	
	abstract public void writeName(FileIO fileIO);

}
