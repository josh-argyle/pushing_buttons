Pushing Buttons is a puzzle game in which the player is tasked with depressing a set of buttons, each marked by a shape,
in one of a selection of colours. Each non-depressed button, when pressed, depresses every other button of a shared shape
marking and colour.Similarly, any matching buttons that were already depressed become non-depressed. The goal is to have
all buttons on the board depressed.

The point of this readme is to describe the input parameters. Case is irrelevant, however if spelling deviates from that
written here the program will exit with an indication of what part of your data to check. Do not add blank lines to the
file.

To run the program, enter the grid information into ButtonInput.txt. Check Grids.txt for an example. Once the grid details
have been entered, compile and run Extract.java. This will read the grid in from the txt file, and then spit out the
first solution it found of the shortest length solution it could find. To count the number of solutions for any given
grid, run the program once, then change the value of the knownBest variable in Extract.java to the length of the
shortest solution the program returned. This will count every solution of that length that it finds. Note this may not
be exclusive, but you can increase the number of iterations the program runs to increase the likelihood that it will
find every solution.

State:  pushed (for buttons that are depressed)
        unpushed (for buttons that are not)

Colour: green
        red
        cyan
        blue
        orange
        yellow
        purple

Shape:  square
        triangle
        circle
        star
        heart
        diamond
        cross