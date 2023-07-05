// Button class.
public class Button {
    // State represents pushed (true) or unpushed (false).
    boolean state;
    int colour;
    int shape;

    public Button(Button input) {
        this.state = input.state;
        this.colour = input.colour;
        this.shape = input.shape;
    }
    public Button(boolean isPushed, int colour, int shape) {
        this.state = isPushed;
        this.colour = colour;
        this.shape = shape;
    }
}