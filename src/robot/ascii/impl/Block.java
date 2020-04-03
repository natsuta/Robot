package robot.ascii.impl;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

public class Block implements Drawable
{
	private int blockIndex;
	private int blockHeight;
	private int blockCol;
	private int blockPos;
	
	public Block(int blockIndex, int blockHeight, int blockCol, int blockPos){
		this.blockIndex = blockIndex;
		this.blockHeight = blockHeight;
		this.blockCol = blockCol;
		this.blockPos = blockPos;
	}
	
	@Override
	public void draw(SwingTerminalFrame terminalFrame)
	{
		if (blockHeight == 1){
			terminalFrame.setBackgroundColor(TextColor.ANSI.YELLOW);
		}
		if (blockHeight == 2){
			terminalFrame.setBackgroundColor(TextColor.ANSI.RED);
		}
		if (blockHeight == 3){
			terminalFrame.setBackgroundColor(TextColor.ANSI.BLUE);
		}
		
		terminalFrame.setCursorPosition(blockCol, blockPos);
		for (int rowPos = blockPos; rowPos >= blockPos - blockHeight; rowPos--){
			terminalFrame.putCharacter(' ');
			terminalFrame.setCursorPosition(blockCol, rowPos);
		}
	}
	
	public void up()
	{
		blockPos--;
	}

	public void down()
	{
		blockPos++;
	}

	public void contract()
	{
		blockCol--;
	}

	public void extend()
	{
		blockCol++;
	}

	public void lower()
	{
		blockPos++;
	}

	public void raise()
	{
		blockPos--;
	}

	public int getBlockCol() {
		return blockCol;
	}

	public void setBlockCol(int blockCol) {
		this.blockCol = blockCol;
	}

	public int getBlockPos() {
		return blockPos;
	}

	public void setBlockPos(int blockPos) {
		this.blockPos = blockPos;
	}

	public int getBlockIndex() {
		return blockIndex;
	}

	public int getBlockHeight() {
		return blockHeight;
	}
	
}
