package gremlins;

import gremlins.model.Gremlin;
import gremlins.model.Stone;
import gremlins.model.Wall;
import gremlins.model.Wizard;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.*;
import java.util.Scanner;


public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    public static final int FPS = 60;

    public static final Random randomGenerator = new Random();

    public String configPath;

    public PImage brickwall;
    public PImage stonewall;
    public PImage gremlin;
    public PImage slime;
    public PImage fireball;
//    public PImage wizardImg;

    Wizard wizard;


    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    public void setup() {
        frameRate(FPS);

        initMap();

        // Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));

        wizard = new Wizard(this);


        JSONObject conf = loadJSONObject(new File(this.configPath));


    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {
        moveWizardWhenPress();
    }

    private void moveWizardWhenPress() {
        int xTemp = wizard.getX(),yTemp=wizard.getY();

        if (this.keyCode == 37) {
            xTemp-=2;
            this.wizard.pressLeft();
        } else if (this.keyCode == 39) {
            xTemp += 2;
            this.wizard.pressRight();
        } else if (this.keyCode == 38) {
            this.wizard.pressUp();
            yTemp -=2;

        } else if (this.keyCode == 40) {
            this.wizard.pressDown();
            yTemp +=2;
        }

        // check colision
        Rectangle wizardRect  = new Rectangle(xTemp,yTemp,SPRITESIZE, SPRITESIZE);

        boolean isCollision=false;
        for(Wall wall : listWall)
        {
            Rectangle rectWal = new Rectangle(wall.getX(),wall.getY(),SPRITESIZE, SPRITESIZE);

            isCollision = wizardRect.intersects(rectWal);
            if(isCollision)
                break;

        }

//         wizzard can move when no collision
        if(!isCollision)
        {
            this.wizard.draw(this,xTemp,yTemp);
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased() {

    }


    /**
     * Draw all elements in the game by current frame.
     */

    boolean isFirst = true;

    List<Gremlin> listGremlin = new ArrayList<>();
    List<Wall> listWall = new ArrayList<>();


    public void draw() {
        background(255, 191, 128);

        if (isFirst) {
            drawMap();
        }


        listWall.forEach(wall -> {
            wall.draw(this);
        });

        wizard.draw(this);


        listGremlin.forEach(gremlin1 -> {
            gremlin1.draw(this);
        });


        isFirst = false;


    }

    private void drawMap() {
        for (int y = 0; y < listBrick.size(); y++) {
            for (int x = 0; x < listBrick.get(y).length(); x++) {
                char b = listBrick.get(y).charAt(x);
                if (b == 'X') {
                    Stone stone = new Stone(x*SPRITESIZE,y*SPRITESIZE);
                    stone.setSprite(stonewall);
                    stone.draw(this);
                    listWall.add(stone);
                } else if (b == 'B') {
                    Wall wall = new Wall(x*SPRITESIZE,y*SPRITESIZE);
                    wall.setSprite(brickwall);
                    wall.draw(this);
                    listWall.add(wall);

                } else if (b == 'W') {
                    wizard.draw(this, x * SPRITESIZE, y * SPRITESIZE);

                } else if (b == 'G') {
                    Gremlin gremlin1 = new Gremlin(x * SPRITESIZE, y * SPRITESIZE);
                    gremlin1.setSprite(gremlin);
                    gremlin1.draw(this);
                    listGremlin.add(gremlin1);

                }

            }

        }


    }

    ArrayList<String> listBrick = new ArrayList();

    private void initMap() {

        try {
            File myObj = new File("level1.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                listBrick.add(data);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {

        PApplet.main("gremlins.App");
    }
}
