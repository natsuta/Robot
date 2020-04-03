package robot.ascii.impl;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

public class Bar implements Drawable
{
	private int barHeight;
	private int barPos;
	
	public Bar(int barHeight, int barPos){
		this.barHeight = barHeight;
		this.barPos = barPos;
	}
	
	@Override
	public void draw(SwingTerminalFrame terminalFrame)
	{
		int maxRow = terminalFrame.getTerminalSize().getRows()-1;		
		terminalFrame.setForegroundColor(TextColor.ANSI.BLACK);
		terminalFrame.setBackgroundColor(TextColor.ANSI.GREEN);
		
		int half = barHeight / 2;

		int count = 0;
		for (int rowPos = maxRow; rowPos > maxRow - barHeight; rowPos--){
			terminalFrame.setCursorPosition(barPos, rowPos);
			terminalFrame.putCharacter(count==half ? (char)('0'+barHeight) : ' ');
			count++;
			}
	}
}
