package gremlins.model;

import gremlins.App;
import processing.core.PApplet;
import processing.core.PImage;

public class Wizard extends Shape {
    private boolean moveLeft;


    public PImage left, right, up, down;


    public Wizard(PApplet pApplet) {
        super(20, 20);
        right = pApplet.loadImage(pApplet.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        left = pApplet.loadImage(pApplet.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        up = pApplet.loadImage(pApplet.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        down = pApplet.loadImage(pApplet.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        setSprite(right);

    }

    public void tick() {
        // If moveLeft is true, move left by decrementing x
        if (moveLeft) {
            this.x--;
        } else {
            // Move right by incrementing x
            this.x++;
        }
    }

    /**
     * Called in App when the left key is pressed.
     */
    public void pressLeft() {
        setSprite(left);
    }

    /**
     * Called in App when the right key is pressed.
     */
    public void pressRight() {
        setSprite(right);
    }


    /**
     * Called in App when the up key is pressed.
     */
    public void pressUp() {
        this.setSprite(up);
    }

    /**
     * Called in App when the down key is pressed.
     */
    public void pressDown() {
        this.setSprite(down);
    }


    public void draw(App app, int x, int y) {
        this.x = x;
        this.y = y;
        draw(app);

    }
}
