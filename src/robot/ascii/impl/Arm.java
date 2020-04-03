package robot.ascii.impl;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

import robot.RobotMovement;

public class Arm implements RobotMovement, Drawable
{
	private int height;
	private int width;
	private int depth;
	private int activeBlock;
	
	public Arm (int height, int width, int depth, int activeBlock){
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.activeBlock = activeBlock;
	}
	
	@Override
	public void draw(SwingTerminalFrame terminalFrame)
	{
		int maxRow = terminalFrame.getTerminalSize().getRows()-1;
		terminalFrame.setBackgroundColor(TextColor.ANSI.WHITE);
		
		for (int i=maxRow; i>maxRow-height; i--){
			terminalFrame.setCursorPosition(0, i);
			terminalFrame.putCharacter(' ');
		}
		
		for (int w=0; w<=width; w++){
			terminalFrame.setCursorPosition(w, maxRow-height);
			terminalFrame.putCharacter(' ');
		}
		
		for (int d=maxRow-height; d<=maxRow-height+depth; d++){
			terminalFrame.setCursorPosition(width, d);
			terminalFrame.putCharacter(' ');
		}
		
	}

	@Override
	public void pick()
	{
		//automatically does the setActiveBlock method
	}

	@SuppressWarnings("null")
	@Override
	public void drop()
	{
		this.activeBlock = -1;
	}

	@Override
	public void up()
	{
		height++;
		
	}

	@Override
	public void down()
	{
		height--;
	}

	@Override
	public void contract()
	{
		width--;
	}

	@Override
	public void extend()
	{
		width++;
	}

	@Override
	public void lower()
	{
		depth++;
	}

	@Override
	public void raise()
	{
		depth--;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getDepth(){
		return depth;
	}

	public int getActiveBlock() {
		return activeBlock;
	}

	public void setActiveBlock(int activeBlock) {
		this.activeBlock = activeBlock;
	}
	
}
