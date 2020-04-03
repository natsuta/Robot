package control;

import robot.Robot;

//Robot Assignment for Programming 1 s2 2017
//Adapted by Caspar and Ross from original Robot code in written by Dr Charles Thevathayan
//Sherman Wong s3656264
public class RobotControl implements Control
{
	// we need to internally track where the arm is
	private int height = Control.INITIAL_HEIGHT;
	private int width = Control.INITIAL_WIDTH;
	private int depth = Control.INITIAL_DEPTH;

	private int[] barHeights;
	private int[] blockHeights;

	private Robot robot;

	// called by RobotImpl
	@Override
	public void control(Robot robot, int barHeightsDefault[], int blockHeightsDefault[])
	{
		this.robot = robot;

		// some hard coded init values you can change these for testing
		this.barHeights = new int[]
		{ 3, 4, 1, 5, 2, 3, 2, 6 };
		this.blockHeights = new int[]
		{ 3, 2, 1, 2, 1, 1, 2, 2, 1, 1, 2, 1, 2, 3 };
		//note that they alternate between left/right columns
		//odd numbers of blocks may cause errors

		// FOR SUBMISSION: uncomment following 2 lines
//			this.barHeights = barHeightsDefault;
//			this.blockHeights = blockHeightsDefault;

		//Splitting the blockHeights array into two
		int blockCount;
		if (blockHeights.length % 2 == 0)
			blockCount = blockHeights.length/2;
		else
			blockCount = (blockHeights.length/2)+1;
		
		int leftBlockHeights[] = new int[blockCount];
		int rightBlockHeights[] = new int[blockCount];
		int leftBlockTotal = 0;
		int rightBlockTotal = 0;
		for (int b=0; b < blockHeights.length; b++){
			int index = 0;
			//because index starts from zero, every number divisible by 2 (after 0) will represent the left column.
			if (b%2 != 0){
				rightBlockHeights[index] = this.blockHeights[b];
				rightBlockTotal += this.blockHeights[b];
			}	
			else {
				leftBlockHeights[index] = this.blockHeights[b];
				leftBlockTotal += this.blockHeights[b];
			}
			index++;
		}
		
		//arrays for bartotals
		int barTotals[] = new int[10];
		barTotals[0] = leftBlockTotal;
		for (int b=1; b <= barHeights.length; b++){
			barTotals[b] = this.barHeights[b-1];
		}
		barTotals[9] = rightBlockTotal;
		for (int b=0; b <= barHeights.length; b++){
			System.out.print(barTotals[b] + " ");
		}
		
		// initialise the robot
		robot.init(this.barHeights, this.blockHeights, height, width, depth);

		// a simple private method to demonstrate how to control robot
		// note use of constant from Control interface
		int ht = INITIAL_HEIGHT;
		
		// ADD ASSIGNMENT PART A METHOD CALL(S) HERE		
		int col = 2;
		int depth;
		boolean reversi = false;
		System.out.println(blockHeights.length - 1);
		
		int starting;
		boolean oddStart = false; //Special protocol for odd numbered block array lengths
		boolean oddEnd = false;
		boolean emergFinish = false; //Same as above
		if (blockHeights.length % 2 != 0){
			oddStart = true;
			oddEnd = true;
			System.out.println("Odd numbered block array");
			starting = blockHeights.length - 1;
		}
		else
			starting = blockHeights.length - 2;
		
		for (int c=starting; c>=0; c--){
			//height optimisation
			int highest = 0;
			
			for (int s=0; s<barTotals.length; s++) {
				if (highest < barTotals[s]){
					highest	= barTotals[s];
					ht = highest + 1;
				}
			}
			System.out.println("Current height: " + this.height);
			System.out.println("Height target: " + ht);
			if (this.height < ht){
				heightRaise(ht);
			}
			else if (this.height > ht){
				heightLower(ht);
			}
			
			//depth calculation			
			depth = this.height - barTotals[col-1] - this.blockHeights[c] - 1;
			System.out.println();
			System.out.println("Block number: " + c);
			System.out.println("Current size of block: " + this.blockHeights[c]);
			System.out.println("Target column: " + col);
			System.out.println("Current bar height: " + barTotals[col-1]);
			System.out.println("Target depth: " + depth);

			// clearance calculation
			int clearance = 0;
			if (c % 2 != 0)
				for (int s=barHeights.length-1; s>=col-2; s--) {
					if (barHeights[s] > clearance)
						clearance = barHeights[s];				
				}
			else
				for (int s=0; s<col-1; s++) {
					if (barHeights[s] > clearance)
						clearance = barHeights[s];				
				}
			
			System.out.println("Highest block: " + clearance);
			clearance += this.blockHeights[c] + 1;
			System.out.println("Current height: " + this.height);
			System.out.println("Height target: " + clearance);
			
			if (c % 2 != 0){
				extendToWidth(Control.SRC2_COLUMN);
				descend(ht - barTotals[9]-1);
				robot.pick();
				raise(0);
				if (this.height < clearance){
					heightRaise(clearance);
				}
				else if (this.height > clearance && barTotals[9] < clearance){
					heightLower(clearance);
				}
				ht = clearance;
				depth = this.height - barTotals[col-1] - this.blockHeights[c] - 1;
				System.out.println("New height: " + ht);
				barTotals[9] -= this.blockHeights[c];
				contractToWidth(col);
				descend(depth);
				robot.drop();
				raise(0);
				barTotals[col-1] += this.blockHeights[c];
				barHeights[col-2] += this.blockHeights[c];
				c -= 2;
			}
			else {
				contractToWidth(Control.SRC1_COLUMN);
				descend(ht - barTotals[0]-1);
				robot.pick();
				raise(0);
				if (this.height < clearance){
					heightRaise(clearance);
				}
				else if (this.height > clearance && barTotals[0] < clearance){
					heightLower(clearance);
				}
				ht = clearance;
				depth = this.height - barTotals[col-1] - this.blockHeights[c] - 1;
				System.out.println("Current height: " + ht);
				barTotals[0] -= this.blockHeights[c];
				extendToWidth(col);
				descend(depth);
				robot.drop();
				raise(0);
				barTotals[col-1] += this.blockHeights[c];
				barHeights[col-2] += this.blockHeights[c];
				
				if (oddStart == true){
					oddStart = false;
				}
				else {				
					c += 2;
				}
			}
			
			//Emergency Finish protocol
			if (emergFinish == true)
				break;
			if (c < 0 && oddEnd == true) { // in case c ends up at -1
				c = 1;
				emergFinish = true; //should go back to loop one more time
			}

			//changing direction protocol
			if (reversi == false)
				col++;
			else
				col--;
			
			if (col == 10){
				reversi = true;
				col = 9;
			}
			else if (col == 2){
				reversi = false;
				col = 3;
			}
		}
		
	} //end of this block

	// simple example method to help get you started
	private void extendToWidth(int width)
	{
		while (this.width < width)
		{
			robot.extend();
			this.width++;
		}
	}

	// WRITE THE REST OF YOUR METHODS HERE!
	
	private void contractToWidth(int width){
		while (this.width > width)
		{
			robot.contract();
			this.width--;
		}
	}
	
	private void descend(int depth){ 
		while (this.depth < depth){
			if (depth == 0)
				break;
			robot.lower();
			this.depth++;
		}
	}
	
	private void raise(int depth) {
		while (this.depth > depth){
			if (this.depth == 0)
				break;
			robot.raise();
			this.depth--;
		}
	}
	
	private void heightLower (int height){
		while (this.height > height){
			if (this.height == height)
				break;
			robot.down();
			this.height--;
		}
	}
	
	private void heightRaise (int height){
		while (this.height < height){
			robot.up();
			this.height++;
		}
	}
	
}
