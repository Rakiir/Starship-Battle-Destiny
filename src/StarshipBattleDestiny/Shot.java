/*
 * These are the shots fired by the player.  They have a height, width, location
 * along the x-axis, a y-axis which they travel straight on, and a translucent rectangle around them.
 */
package StarshipBattleDestiny;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Thomas Caltabellotta
 * Section 12
 * TA Katrina Scott
 * Prof. Vanessa Starkey
 */
public class Shot extends Rectangle {

    private final int shotWidth = 18;
    private final int shotHeight = 134;
    protected final static Image SHOT_IMG = new Image("file:destinyLaser.png");

    public Shot() {

        this.setWidth(shotWidth);
        this.setHeight(shotHeight);

        //Loads and sets the image of the shots.
        ImageView viewShot = new ImageView();
        try {
            viewShot.setImage(SHOT_IMG);
            viewShot.setFitWidth(shotWidth);
            viewShot.setFitHeight(shotHeight); //17.5 x 134.2
            this.setFill(new ImagePattern(SHOT_IMG));
        } catch (Exception e) {
            System.out.println("Unable to load Shot image.");
            System.exit(0);
        }
    }

    @Override
    public String toString() {
        return ("A shot that moves along the vertical axis " + getY() + " from the position of " + getX()
                + " with a width of " + getWidth() + " and a height of " + getHeight());
    }
} //End of Shot Class