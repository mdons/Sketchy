package Sketchy;

/**
 * This abstract class mandates that its children, which store user actions,
 * must be able to undo and redo their actions.
 * 
 * @author mjdonnel
 *
 */
public abstract class Command {
	
	abstract public void undo();
	
	abstract public void redo();

}
