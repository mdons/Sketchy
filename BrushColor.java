package Sketchy;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * BrushColor is a JPanel responsible for modifying the current
 * color based upon user input. It contains three JSliders for
 * modifying RGB values, and three JLabels for displaying these
 * values. It also contains instances of SliderListener, which
 * notice changes in RGB values, and passes them on to the
 * ColorHolder association. The sliders and labels are displayed
 * in a descending GridLayout.
 * 
 * @author mjdonnel
 *
 */
public class BrushColor extends JPanel {
	
	private ColorHolder _colorHolder;
	private JLabel _redLabel;
	private JLabel _greenLabel;
	private JLabel _blueLabel;
	
	public BrushColor(ColorHolder colorHolder) {
		super();
		_colorHolder = colorHolder;
		
		JSlider redSlider = new JSlider(0, 255, Constants.INITCOLOR_RED);
		JSlider greenSlider = new JSlider(0, 255, Constants.INITCOLOR_GREEN);
		JSlider blueSlider = new JSlider(0, 255, Constants.INITCOLOR_BLUE);
		
		/*
		 * New SliderListeners receive associations for their channel
		 * (whether they are to modify red, green, or blue), and for
		 * the slider to which they are added.
		 */
		redSlider.addChangeListener(new SliderListener(0, redSlider));
		greenSlider.addChangeListener(new SliderListener(1, greenSlider));
		blueSlider.addChangeListener(new SliderListener(2, blueSlider));
		
		_redLabel = new JLabel("Red: " + Constants.INITCOLOR_RED);
		_greenLabel = new JLabel("Green: " + Constants.INITCOLOR_GREEN);
		_blueLabel = new JLabel("Blue: " + Constants.INITCOLOR_BLUE);
		
		this.setLayout(new GridLayout(6,0));		
		this.setBorder(new TitledBorder("Brush Color"));
		this.add(_redLabel);
		this.add(redSlider);
		this.add(_greenLabel);
		this.add(greenSlider);
		this.add(_blueLabel);
		this.add(blueSlider);
	}
	
	/**
	 * The SliderListener will modify the appropriate value
	 * of ColorHolder's Color based upon the channel to
	 * which it is assigned (0 for red, 1 for green, and 2
	 * for blue). The appropriate label is changed as well.
	 * 
	 * @author mjdonnel
	 *
	 */
	private class SliderListener implements ChangeListener {
		
		private int _channel;
		private JSlider _slider;
		
		public SliderListener(int channel, JSlider slider) {
			_channel = channel;
			_slider = slider;
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			int value = _slider.getValue();
			Color currentColor = _colorHolder.getColor();
			Color newColor;
			
			switch (_channel) {
			case 0:
				newColor = new Color(value, currentColor.getGreen(), currentColor.getBlue());
				_redLabel.setText("Red: " + value);
				break;
			case 1:
				newColor = new Color(currentColor.getRed(), value, currentColor.getBlue());
				_greenLabel.setText("Green: " + value);
				break;
			default:
				newColor = new Color(currentColor.getRed(), currentColor.getGreen(), value);
				_blueLabel.setText("Blue: " + value);
			}
			
			_colorHolder.setColor(newColor);
		}
		
	}

}
