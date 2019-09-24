package Sketchy;

import javax.swing.JFrame;

/**
 * This is the main class, where your program will start.
 * Note that this is an application, where the main method calls 
 * the App constructor.
 *
 * This class instantiates a new JFrame and my top level object,
 * a MainPanel. It then adds the MainPanel to the frame.
 *
 * @author mjdonnel
 * Did you discuss your design with another student?
 * No
 * If so, list their login here:
 * N/A
 */

public class App {

	public App() {
		JFrame frame = new JFrame("Sketchy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainPanel panel = new MainPanel();
		frame.add(panel);
		frame.pack();
		
		frame.setVisible(true);
	}


	/* You don't need to touch this part. */
	public static void main(String[] argv) {
		new App();
	}

}
