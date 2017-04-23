/*
 * These are the laucher which is the player's character.  They have a height, width, location
 * along the x-axis, a location along the y-axis, and a translucent rectangle around them.
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
class Launcher extends Rectangle {

    private final int launcherWidth = 70;
    private final int launcherHeight = 103;
    protected static final Image LAUNCHER_IMG = new Image("file:starshipDestiny.png");

    public Launcher() {
        this.setWidth(launcherWidth);
        this.setHeight(launcherHeight);

        //Load and set image of the player's launcher.
        ImageView viewLauncher = new ImageView();
        try {
            viewLauncher.setImage(LAUNCHER_IMG);
            viewLauncher.setFitWidth(launcherWidth);
            viewLauncher.setFitHeight(launcherHeight); //70 x 103
            this.setFill(new ImagePattern(LAUNCHER_IMG));
        } catch (Exception e) {
            System.out.println("Unable to load Launcher image.");
            System.exit(0);
        }
    } //End Constructor

    @Override
    public String toString() {
        return ("A launcher translates along the X-axis " + getX() + " and fires shots up the Y-axis of " + getY()
                + " and has a width of " + getWidth() + " and a height of " + getHeight());
    }

} //End of StarshipDestiny Class