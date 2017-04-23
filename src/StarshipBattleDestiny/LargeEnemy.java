/*
 * These are the large enemies.  They have a height, width, location on the 
 * x-axis and y-axis, and a translucent rectangle around them.
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
class LargeEnemy extends Rectangle {

    private final int largeEnemyWidth = 182;
    private final int largeEnemyHeight = 102;
    protected static final Image LARGE_ENEMY_IMG = new Image("file:enemyAsgard.png");
    
    public LargeEnemy() {
        
        this.setWidth(largeEnemyWidth);
        this.setHeight(largeEnemyHeight);
        
        //Loads and sets the LargeEnemy's image.
        ImageView viewLargeEnemy = new ImageView();
        try {
            viewLargeEnemy.setImage(LARGE_ENEMY_IMG);
            viewLargeEnemy.setFitWidth(largeEnemyWidth);
            viewLargeEnemy.setFitHeight(largeEnemyHeight); //182 x 102
            this.setFill(new ImagePattern(LARGE_ENEMY_IMG));
        }
        catch(Exception e) {
            System.out.println("Unable to load LargeENemy image.");
            System.exit(0);       
        } 
    } //End Constructor

    @Override
    public String toString() {
        return ("A small enemy originates from the Y-axis of " + getY() + " and moves left to right along " + getX() +
                " with a width of " + getWidth() + " and a height of " + getHeight());
    }
} //End of LargeEnemy Class