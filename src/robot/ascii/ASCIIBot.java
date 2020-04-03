package robot.ascii;

import com.googlecode.lanterna.TextColor;

import control.RobotControl;
import robot.Robot;
import robot.ascii.impl.Arm;
import robot.ascii.impl.Bar;
import robot.ascii.impl.Block;
import robot.impl.RobotImpl;
import robot.impl.RobotInitException;

// ASCIIBot template code Programming 1 s2, 2017
// designed by Caspar, additional code by Ross
public class ASCIIBot extends AbstractASCIIBot implements Robot
{
	public static void main(String[] args)
	{
		new RobotControl().control(new ASCIIBot(), null, null);
	}

	// MUST CALL DEFAULT SUPERCLASS CONSTRUCTOR!
	public ASCIIBot()
	{
		super();
	}

	Bar bars[];
	Block blocks[];
	Arm a;
	
	@Override
	public void init(int[] barHeights, int[] blockHeights, int height, int width, int depth)
	{
		// in addition to validating init params this also sets up keyboard support for the ASCIIBot!
		try
		{
			RobotImpl.validateInitParams(this, barHeights, blockHeights, height, width, depth);
		}
		catch (RobotInitException e)
		{
			System.err.println(e.getMessage());
			System.exit(0);
		}

		// write init code here to place bars, blocks and initial arm position
		// suggest writing a separate method e.g. initImpl()
		bars = new Bar[barHeights.length];
		blocks = new Block[blockHeights.length];
		int maxRow = terminalFrame.getTerminalSize().getRows()-1;
		int leftStack = maxRow;
		int rightStack = maxRow;
		
		for (int i=0; i<barHeights.length; i++){
			Bar b = new Bar(barHeights[i], i+2);
			bars[i] = b;
		}

		for (int b=0; b<blockHeights.length; b++){
			int blockCol;
			int sourceHt;
			if (b%2 != 0){
				blockCol = 10;
				sourceHt = rightStack;
				rightStack -= blockHeights[b]; //adding (subtracting) block values after it is passed into the block class
			}
			else {
				blockCol = 1;
				sourceHt = leftStack;
				leftStack -= blockHeights[b];
			}
			Block c = new Block(b, blockHeights[b], blockCol, sourceHt);
			blocks[b] = c;
		}

		a = new Arm(height-1, width, depth, -1);
		
		draw();
		
		// call this to clear previous contents between each draw (i.e. after each robot move)
//		terminalFrame.flush();
//		delayAnimation();
	}

	//maxRow == 12, maxCol == 14

	public void draw(){
		delayAnimation();
		for (int i=0; i<bars.length; i++)
			bars[i].draw(terminalFrame);
		for (int j=0; j<blocks.length; j++)
			blocks[j].draw(terminalFrame);
		a.draw(terminalFrame);
		terminalFrame.flush();
	}
	
	@Override
	public void pick()
	{
		int maxRow = terminalFrame.getTerminalSize().getRows()-1;
		// implement methods to draw robot environment using your implementation of Arm.draw(), Bar.draw() etc.
		System.out.println("pick()");
		a.pick();
		for (int j=0; j<blocks.length; j++){
			int currentBlockPos = blocks[j].getBlockPos();
			int currentBlockCol = blocks[j].getBlockCol();
			if(maxRow-currentBlockPos+blocks[j].getBlockHeight() == a.getHeight()-a.getDepth() && currentBlockCol == a.getWidth()){
				a.setActiveBlock(blocks[j].getBlockIndex());
			}
		}
	}

	@Override
	public void drop()
	{
		System.out.println("drop()");
		a.drop();
	}

	@Override
	public void up()
	{
		System.out.println("up()");
		a.up();
		if (a.getActiveBlock() != -1)
			blocks[a.getActiveBlock()].up();
		draw();
	}

	@Override
	public void down()
	{
		System.out.println("down()");
		a.down();
		if (a.getActiveBlock() != -1)
			blocks[a.getActiveBlock()].down();
		draw();
	}

	@Override
	public void contract()
	{
		System.out.println("contract()");
		a.contract();
		if (a.getActiveBlock() != -1)
			blocks[a.getActiveBlock()].contract();
		draw();
	}

	@Override
	public void extend()
	{
		System.out.println("extend()");
		a.extend();
		if (a.getActiveBlock() != -1)
			blocks[a.getActiveBlock()].extend();
		draw();
	}

	@Override
	public void lower()
	{
		System.out.println("lower()");
		a.lower();
		if (a.getActiveBlock() != -1)
			blocks[a.getActiveBlock()].lower();
		draw();
	}

	@Override
	public void raise()
	{
		System.out.println("raise()");
		a.raise();
		if (a.getActiveBlock() != -1)
			blocks[a.getActiveBlock()].raise();
		draw();
	}
	
}
