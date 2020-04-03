package robot.ascii.impl;

import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

public interface Drawable
{
	// set robot speed to 0 initially? (for testing only)
	public static final boolean START_PAUSED=false;
	
	public abstract void draw(SwingTerminalFrame terminalFrame);

}
