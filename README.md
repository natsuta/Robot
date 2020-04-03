# Robot
For Programming 1 Semester 2 2017  
Code checked April 2020

# Background
There are two parts to this project; the first was to code the logic for a robotic arm program that would be run using a Java GUI and the second was to take that logic and replicate the GUI using the Lanterna terminal emulator.

Starter code was provided for half of the project; the rest of the implementation is in the following files:

- control.RobotControl.java
- robot.ascii.ASCIIBot.java
- robot.ascii.impl.Arm.java
- robot.ascii.impl.Bar.java
- robot.ascii.impl.Block.java

# How to run
- Import the repo to Eclipse as a project.
- To run the GUI, run `lib/RobotImpl.jar` as a Java application.
- To run the terminal emulator, run `ASCIIBot.java` in `src/robot.ascii`.

# Notes
- The `RobotControl.java` program has problems when the `blockHeights` array has an odd number of values or when the `barHeights` array has less than 8 values.
- You can also use the following keyboard keys to manually control the robot:

| Robot Command | Key(s)         |
|---------------|----------------|
| up()          | Page Up or [   |
| down()        | Page Down or ] |
| extend()      | Right Arrow    |
| contract()    | Left Arrow     |
| raise()       | Up Arrow       |
| lower()       | Down Arrow     |
| pick()        | Home or P      |
| drop()        | End or D       |
| Speed up (1)  | NumPad +       |
| Slow down (1) | NumPad -       |
| Speed up (5)  | NumPad *       |
| Slow down (5) | NumPad /       |
