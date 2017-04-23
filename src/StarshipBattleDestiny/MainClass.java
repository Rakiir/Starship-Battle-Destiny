/*
 * This game uses JavaFX to play a simple shooter game.
 * The player is the launcher at the bottom of the screen and moves from left to right using either the A & D or LEFT & RIGHT keys.
 * When the player hits the space bar a shot is created at the launcher's current location that moves directly upward.
 * There are two types of enemies the user is shooting at:  large and small enemies.
 * When the user hits an enemy the points go up and when they reach 1000 points they win, but if they miss 10 times the player loses the game.
 * In additon when the enemy is hit the explosion graphic appears briefly and the enemy dissapears.
 */
package StarshipBattleDestiny;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import static javafx.scene.input.KeyCode.H;
import static javafx.scene.input.KeyCode.Q;
import static javafx.scene.input.KeyCode.SPACE;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Thomas Caltabellotta
 * Section 12
 * TA Katrina Scott
 * Prof. Vanessa Starkey
 */
public class MainClass extends Application {

    final static int GAME_WIDTH = 1000;
    final static int GAME_HEIGHT = 650;
    Scene scene;

    @Override
    public void start(Stage primaryStage) {
        
        GamePane pane = new GamePane();
        scene = new Scene(pane, GAME_WIDTH, GAME_HEIGHT);
        
        //CacheManager cacheManager = new CacheManager();
        //if (cacheManager.isListEmpty() == true) {
            //cacheManager.addCacheInfo("Stormcloak7", "GC12345", "N 39 11.111   W 084 11.111");
        //    pane.mainCacheControl();
        //}
        
        pane.playerInstructionsPane();
        pane.setTextField();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {

                //When the space bar is pressed the fire method is called firing another shot.
                if (ke.getCode() == SPACE) {
                    pane.fire();
                }
                //When the Ctrl-Q pressed the endGame method is called ending the game and updating the high score.
                if (ke.isControlDown() == true && ke.getCode() == Q) {
                    try {
                        GamePane.endGame();
                    } catch (IOException ex) {
                        Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //A secret command method for the admin to use so I change the GC Code, Final Coordinates, & Admin Password when Ctrl-Alt-T-C is pressed.
                if (ke.isControlDown() == true && ke.isAltDown() == true && ke.getCode() == T) {
                    try {
                        GamePane.mainCacheControl();
                    } catch (IOException ex) {
                        //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Cannot call MainCacheControl:  " + ex);
                    }
                }
                //A secret command method for the admin to view the current high scores without changing them when Ctrl-Alt-H-S is pressed.
                if (ke.isControlDown() == true && ke.isAltDown() == true && ke.getCode() == H) {
                    GamePane.showHighScores();
                }
            }
        }); //End EventHandler
        

        primaryStage.setTitle("Starship Battle:  Destiny v6.0.3");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false); //Prevents the user from changing the size of the stage or making it fullscreen.
    } //End start method

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    } //End main method
    
} //End of MainClass Class