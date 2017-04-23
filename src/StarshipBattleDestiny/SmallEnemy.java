/*
 * These are the smaller of the two enemies.  They have a height, width, location
 * along the x-axis and y-axis, and a translucent rectangle around them.
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
class SmallEnemy extends Rectangle {
    
    private final int smallEnemyWidth = 60;
    private final int smallEnemyHeight = 60;
    protected final static Image SMALL_ENEMY_IMG = new Image("file:kinoDrone.png");
     
    public SmallEnemy() {
        this.setWidth(smallEnemyWidth);
        this.setHeight(smallEnemyHeight);
        
        //Loads and sets the SmallEnemy's image.
        ImageView viewSmallEnemy = new ImageView();
        try {
           viewSmallEnemy.setImage(SMALL_ENEMY_IMG);
           viewSmallEnemy.setFitWidth(smallEnemyWidth); 
           viewSmallEnemy.setFitHeight(smallEnemyHeight); //60 x 60
           this.setFill(new ImagePattern(SMALL_ENEMY_IMG));
        }
        catch(Exception e) {
            System.out.println("Unable to load SmallEnemy image.");
            System.exit(0);
        } 
        
    } //End Constructor  
   
    @Override
    public String toString() {
        return ("A small enemy originates from the Y-axis of " + getY() + " and moves left to right along " + getX() +
                " with a width of " + getWidth() + " and a height of " + getHeight());
    }
} //End SmallEnemy Class