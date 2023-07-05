import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Extract {
    // How many buttons are in the grid.
    static int numberOfButtonsInGrid;
    // Flag to log everything for debug.
    static boolean log = false;
    // A number set by the user to limit how many buttons should be pressed before restart.
    static int maxButtonPresses = 50;
    // The algorithm's current lowest number of presses for a successful solution.
    static int currentBest = maxButtonPresses;
    // A grid representation to hold the representation of the lowest length. Lets the main() method check if there was
    // a successful solution.
    static Button[] bestGrid = new Button[0];
    // The master grid to copy. Is read in and created from the ButtonInput.txt file.
    static Button[] masterGrid = new Button[0];
    // ArrayList to hold the moves used to get the first lowest length solution.
    static ArrayList<String> best = new ArrayList<>();
    // How many iterations to run the program over.
    static final int ITERATIONS = 100000;
    // A number shown to be the known current best. When changed, will only log the solutions of this length to the
    // allSolutions.txt file, giving us an accurate count of the number of solutions of this size.
    static int knownBest = 10;

    public static void main(String[] args) throws IOException {
        // Open the allSolutions.txt file to write our solutions of known fastest length to.
        FileWriter fw = new FileWriter(new File("src/allSolutions.txt"));
        masterGrid = readGridFromTxt();
        // For the declared number of iterations, run the handler.
        for (int i = 0; i < ITERATIONS; i++) {
            if (i % 1000000 == 0 && i != 0) System.out.println("Iteration:" + i);
            currentBest = maxButtonPresses;
            handler(fw);
            if (currentBest > maxButtonPresses) {
                System.out.println("New best: " + maxButtonPresses);
            }
        }
        // If there was a result that meets the criteria, print out the length and moves taken.
        if (bestGrid.length > 0 && areAllPressed(bestGrid)) {
            System.out.println("\nShortest solution: " + maxButtonPresses + " moves.");
            printAllMoves(best);
        } else {
            System.out.println("No solution found.");
        }
        fw.close();
        // Count the number of solutions found.
        solutionCounter();
    }

    // Makes a new copy of the masterGrid for use in the current iteration.
    private static Button[] makeGrid() {
        numberOfButtonsInGrid = masterGrid.length;
        Button[] grid = new Button[masterGrid.length];
        for (int i = 0; i < numberOfButtonsInGrid; i++) {
            Button b = new Button(masterGrid[i]);
            grid[i] = b;
        }

        return grid;
    }

    // Handler for the program.
    private static void handler(FileWriter fw) throws IOException {
        Random random = new Random();
        ArrayList<String> inList = new ArrayList<>();
        Button[] grid = makeGrid();

        int count = maxButtonPresses;
        int inCount = 0;

        // While the buttons arent pushed, and we havnt pressed an unreasonable amount of buttons.
        while (!areAllPressed(grid) && count > 0) {
            log("While loop");

            // Get the unpushed buttons.
            ArrayList<Button> availableNums = new ArrayList<>();
            for (int i = 0; i < numberOfButtonsInGrid; i++) {
                if (!grid[i].state) {
                    availableNums.add(grid[i]);
                }
            }
            // Pick one of them, and push it.
            int choice = random.nextInt(availableNums.size());
            if (!availableNums.get(choice).state) {
                Button b = new Button(availableNums.get(choice));
                pressButton(grid, b);
                inCount++;
                inList.add(printPushingButtonInfo(b));
            }
            count--;
        }

        // If we get a new fastest solution, record it.
        if (areAllPressed(grid) && (inCount < maxButtonPresses)) {
            maxButtonPresses = inCount;
            best = inList;
            bestGrid = Arrays.copyOf(grid, grid.length);
            for (String b : inList) {
                fw.write(b + "\t");
            }
        }
        // If we get an equally fast solution, write it to the allSolutions.txt file.
        if (areAllPressed(grid) && inCount == knownBest) {
            for (String b : inList) {
                fw.write(b + "\t");
            }
            fw.write("\n");
        }
    }

    // Prints the resulting moves list ArrayList.
    private static void printAllMoves(ArrayList<String> input) {
        for (String s : input) {
            System.out.println(s);
        }
    }

    // Prints a buttons current state.
    private static String printPushingButtonInfo(Button input) {
        log("printButton");
        String shape = "";
        if (input.shape == 0) shape = "circle";
        else if (input.shape == 1) shape = "diamond";
        else if (input.shape == 2) shape = "star";
        else if (input.shape == 3) shape = "triangle";
        else if (input.shape == 4) shape = "square";
        else if (input.shape == 5) shape = "cross";
        else if (input.shape == 6) shape = "heart";
        String colour = "";
        if (input.colour == 0) colour = "red";
        else if (input.colour == 1) colour = "green";
        else if (input.colour == 2) colour = "yellow";
        else if (input.colour == 3) colour = "orange";
        else if (input.colour == 4) colour = "blue";
        else if (input.colour == 5) colour = "purple";
        else if (input.colour == 6) colour = "cyan";

        return  ("Push: " + colour + " " + shape);
    }

    private static Button[] pressButton(Button[] grid, Button pushedButton) {
        log("pressButton");
        // Copy the button to avoid changing by referral.
        Button thePressedOne = new Button(pushedButton);
        // For each of the buttons.
        for (int i = 0; i < numberOfButtonsInGrid; i++) {
            // If the button exists, and it shares a shape or colour with the input button, change its state.
            if (grid[i] != null && (thePressedOne.shape == grid[i].shape || thePressedOne.colour == grid[i].colour)) {
                grid[i].state = !grid[i].state;
            }
        }
        return grid;
    }

    // Prints a buttons current state.
    private static void printButtonState(Button input) {
        log("printButton");
        String shape = "";
        if (input.shape == 0) shape = "circle";
        else if (input.shape == 1) shape = "diamond";
        else if (input.shape == 2) shape = "star";
        else if (input.shape == 3) shape = "triangle";
        else if (input.shape == 4) shape = "square";
        else if (input.shape == 5) shape = "cross";
        else if (input.shape == 6) shape = "heart";
        String colour = "";
        if (input.colour == 0) colour = "red";
        else if (input.colour == 1) colour = "green";
        else if (input.colour == 2) colour = "yellow";
        else if (input.colour == 3) colour = "orange";
        else if (input.colour == 4) colour = "blue";
        else if (input.colour == 5) colour = "purple";
        else if (input.colour == 6) colour = "cyan";
        String state = "";
        if (input.state) state = "pushed";
        else state = "not pushed";

        System.out.println(colour + " " + shape + " is " + state);
    }

    // Checks if all the buttons are pushed.
    private static boolean areAllPressed(Button[] grid) {
        log("areAllPressed");
        for (int i = 0; i < numberOfButtonsInGrid; i++) {
            if (!grid[i].state) {
                return false;

            }
        }
        return true;
    }

    // For logging,
    private static void log(String input) {
        if (log) System.out.println(input);
    }

    // Read all solutions from allSolutions.txt into a HashSet, and returns the number of novel results.
    private static void solutionCounter() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/allSolutions.txt"));
        HashSet<String> solutions = new HashSet<String>();
        while (scanner.hasNextLine()) {
            solutions.add(scanner.nextLine());
        }
        System.out.println("\nFound solutions of length " + knownBest + ": " + solutions.size());
    }

    // Makes the grid from the input .txt file.
    private static Button[] readGridFromTxt() throws IOException {
        Scanner scanner = new Scanner(new File("src/ButtonInput.txt"));

        ArrayList<Button> buttons = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim().toLowerCase();
            String[] split = input.split(" ");
            if (split.length != 3) {
                System.err.println("Bad input length, check data and try again.");
                System.exit(0);
            }
            // Append the state.
            boolean state = false;
            if (split[0].equals("pushed")) state = true;
            // Append the colour.
            int colour = 0;
            if ("red".equals(split[1])) colour = 0;
            else if ("green".equals(split[1])) colour = 1;
            else if ("yellow".equals(split[1])) colour = 2;
            else if ("orange".equals(split[1])) colour = 3;
            else if ("blue".equals(split[1])) colour = 4;
            else if ("purple".equals(split[1])) colour = 5;
            else if ("cyan".equals(split[1])) colour = 6;
            else {
                System.err.println("Bad colour input, check data and try again.");
                System.exit(0);
            }
            int shape = 0;
            if ("circle".equals(split[2])) shape = 0;
            else if ("diamond".equals(split[2])) shape = 1;
            else if ("star".equals(split[2])) shape = 2;
            else if ("triangle".equals(split[2])) shape = 3;
            else if ("square".equals(split[2])) shape = 4;
            else if ("cross".equals(split[2])) shape = 5;
            else if ("heart".equals(split[2])) shape = 6;
            else {
                System.err.println("Bad shape input, check data and try again.");
                System.exit(0);
            }
            Button b = new Button(state, colour, shape);
            buttons.add(b);
        }
        Button[] grid = new Button[buttons.size()];
        for (int i = 0; i < buttons.size(); i++) {
            grid[i] = buttons.get(i);
        }
        return grid;
    }
}
