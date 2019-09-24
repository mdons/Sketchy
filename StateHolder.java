package Sketchy;

/**
 * StateHolder is responsible for setting and getting
 * the program state (0 for select state, 1 for draw
 * state, 2 for make rectangle state, and 3 for make
 * ellipse state).
 * 
 * @author mjdonnel
 *
 */
public class StateHolder {
	
	private int _state;
	
	public StateHolder(int initState) {
		_state = initState;
	}
	
	public int getState() {
		return _state;
	}
	
	public void setState(int newState) {
		_state = newState;
	}

}
