/**
 * This method adds objects to the pane, controls all animations for adding and
 * moving enemies, adding and moving shots, and checking for collisions.
 * NOTE:  All methods for winning the game, losing the game, and such are to be
 * implemented in future versions ;)
 */
package StarshipBattleDestiny;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.JOptionPane;

class GamePane extends Pane {

    private Timeline animation;
    ArrayList<LargeEnemy> largeList = new ArrayList<>();
    ArrayList<SmallEnemy> smallList = new ArrayList<>();
    public static ArrayList<Shot> shotList = new ArrayList<>(1);
    static double GAME_WIDTH = 1000;
    static double GAME_HEIGHT = 650;
    public static int gameOverCounter = 0;
    public static int playerPointCounter = 0;
    int addToPlayerScore;
    int addToMisses;
    public static Launcher launcher;
    Rectangle topOfGame;
    static String playerName;
    final Text showScore;
    

    /**
     * This constructor for the GamePane calls the method that adds the
     * launcher, calls the method that adds the invisible object for the top of
     * the game, and houses the animations that control when a new enemy is
     * added, the rate that each SmallEnemy is moved, the rate that each
     * LargeEnemy is moved, the rate that each Shot is moved, and how often the
     * game will check for a collision.
     */
    public GamePane() {
        //Adds a launcher
        addLauncher();
        addTopOfGame();
        showScore = new Text();
        this.getChildren().add(showScore);

        //Animation to call new enemies every 10 seconds.
        animation = new Timeline(new KeyFrame(Duration.seconds(5), e -> addEnemy()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        //Animation to move SmallEnemies
        animation = new Timeline(new KeyFrame(Duration.millis(3), e -> moveSmallEnemy()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        //Animation to move Large Enemies
        animation = new Timeline(new KeyFrame(Duration.millis(5), e -> moveLargeEnemy()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        //Animation to move Shots
        animation = new Timeline(new KeyFrame(Duration.millis(1), e -> moveShot()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        this.setStyle("-fx-background-image: url(\"file:purpleSpaceBackground.jpeg\");-fx-background-size: cover");
    } //End GamePane Constructor

    /**
     * This method adds a JOptionPane window informing the player of the
     * instructions of how to play the game.
     */
    public static void playerInstructionsPane() {
        playerName = JOptionPane.showInputDialog("Please enter your name:");
        String blankName = "";
        
        while (playerName.equalsIgnoreCase(blankName)) {
            playerName = JOptionPane.showInputDialog("You cannot leave your name blank.\n"
                    + "Please enter your name:");
        } //end blankName while loop check
        while (playerName.trim().length() < 3) {
            int nameTrimLength = playerName.trim().length();
            if (nameTrimLength == 0) {
                playerName = JOptionPane.showInputDialog("Your name cannot be all whitespace characters.\n"
                    + "Please enter your name:");
            } //end if statement check for if the user inputs all whitespaces
            else if (nameTrimLength == 1 || nameTrimLength == 2) {
                playerName = JOptionPane.showInputDialog("Player's name must be 3 characters or more in length (not including whitespaces).\n"
                    + "Please enter your name:");
            } //end else-if statement check for if the name is less than 3 characters long
            
        } //end whitespace name while loop check
        while (playerName.length() < 3) {
            playerName = JOptionPane.showInputDialog("Player's name must be 3 characters or more in length.\n"
                    + "Please enter your name:");
        } //end name length while loop check
        
        String playerInstructionsMsg = "You are the captain of the Starship Destiny at the bottom of the screen.\n"
                + "Fire your Forward Guns at the enemy ships above you with the Space Bar.\n"
                + "The larger Asgard Ships are worth 10 points, while the smaller Kino Drones are worth twice that at 20 points.\n"
                + "It takes 500 points to win but only 10 misses to lose.\n\n"
                + "Winning the game earns you the coordinates to your enemy's base.\n\n"
                + "You can press Control-Q at any time to either surrender or show your enemies mercy.\n"
                + "This will also save your score and add it to the high scores, assuming it is one.\n"
                + "\n"
                + "Good Luck!";
        JOptionPane.showMessageDialog(null, playerInstructionsMsg, "How To Play", JOptionPane.INFORMATION_MESSAGE);
    } //end playerInstructionsPane method

    /**
     * When this method is called it begins playing the animations.
     */
    public void play() {
        animation.play();
    } //End play method

    /**
     * When this method is called it stops playing the animations.
     */
    public void stop() {
        animation.stop();
    } //End stop method
    
    /**
     * When this method is called it pauses the animations that are playing.
     */
    public void pause() {
        animation.pause();
    } //End pause method

    /**
     * This method adds an invisible rectangle to the top that ends the game
     * when it is hit 10 times... (aka: when the player misses 10 times).
     */
    public void addTopOfGame() {
        topOfGame = new Rectangle(); //Set slightly above game view so shot will be offscreen before it dissapears.
        topOfGame.setX(0);
        topOfGame.setY(0);
        topOfGame.setHeight(10);
        topOfGame.setWidth(1000);
        topOfGame.setFill(Color.TRANSPARENT);
        this.getChildren().add(topOfGame);
        addToMisses = 1;
    } //End addTopOfGame methpd

    /**
     * This method adds a new Launcher to the pane.
     */
    public void addLauncher() {
        launcher = new Launcher();
        launcher.setWidth(70);
        launcher.setHeight(103);
        launcher.setX((GAME_WIDTH * 0.5) - (launcher.getWidth() * 0.5) + 10);
        launcher.setY(GAME_HEIGHT - 100);
        this.getChildren().add(launcher);
    } //End addLauncher method

    /**
     * This method adds new enemies. It has a random variable that generates a
     * number between 0 and 100. If this random variable is less than 60 a new
     * LargeEnemy is created and added to the pane, and if the random variable
     * is higher than 60 it crates a new SmallEnemy and adds it to the pane.
     * This means that there is a 60% chance of a new LargeEnemy being created
     * and a 40% chance of a new SmallEnemy being created.
     */
    public void addEnemy() {
        int random = (int) (0 + Math.random() * 100);
        if (random < 60) { //60:40 chance LargeEnemy:SmallEnemy
            addToPlayerScore = 10;
            LargeEnemy newLargeEnemy = new LargeEnemy();
            newLargeEnemy.setWidth(182);
            newLargeEnemy.setHeight(102);
            newLargeEnemy.setX(0);
            newLargeEnemy.setY(60 + Math.random() * 200);
            newLargeEnemy.setVisible(true);
            largeList.add(newLargeEnemy);
            this.getChildren().add(newLargeEnemy);
            //countdownToLargeCollisionCheck();
        } //End boolean if statement to add a newLargeEnemy
        else {
            addToPlayerScore = 20;
            SmallEnemy newSmallEnemy = new SmallEnemy();
            newSmallEnemy.setX(0);
            newSmallEnemy.setY(60 + Math.random() * 250);
            newSmallEnemy.setWidth(60);
            newSmallEnemy.setHeight(60);
            newSmallEnemy.setVisible(true);
            smallList.add(newSmallEnemy);
            this.getChildren().add(newSmallEnemy);
            //countdownToSmallCollisionCheck();
        } //End boolean else statemet to add a newSmallEnemy
    } //End addEnemy method

    /**
     * This method adds a new Shot to the screen. It is called whenever the
     * player hits the Space Bar.
     */
    public void fire() {
        if (shotList.isEmpty()) {
            Shot newShot = new Shot();
            newShot.setX(GAME_WIDTH * 0.5);
            newShot.setY(launcher.getY() - launcher.getHeight() - 30);
            newShot.setWidth(18);
            newShot.setHeight(134);
            newShot.setVisible(true);
            shotList.add(newShot);
            this.getChildren().add(newShot);
        }
        else {
            //Don't add a new shot.
        }
    } //End fire method

    /**
     * This method sets how much each SmallEnemy will be moved each time the
     * animation timer calls the method, as well as removing the SmallEnemy if
     * it's x-axis value is greater than or equal to the width of the game + the
     * width of a SmallEnemy.
     */
    public void moveSmallEnemy() {
        for (int i = 0; i < smallList.size(); i++) {
            SmallEnemy moveSmall = smallList.get(i);
            double x = moveSmall.getX();
            double smallDX = 1;
            double removeSmall = GAME_WIDTH + moveSmall.getWidth();
            if (x >= removeSmall) {
                smallList.remove(i);
            } //end boolean if statement to change direction
            x += smallDX;
            moveSmall.setX(x);
        } //end for loop
    } //End moveSmallEnemy method

    /**
     * This method sets how much each LargeEnemy will be moved each time the
     * animation timer calls the method, as well as removing the LargeEnemy if
     * it's x-axis value is greater than or equal to the width of the game + the
     * width of a LargeEnemy.
     */
    public void moveLargeEnemy() {
        for (int i = 0; i < largeList.size(); i++) {
            LargeEnemy moveLarge = largeList.get(i);
            double x = moveLarge.getX();
            double largeDX = 1;
            double removeLarge = GAME_WIDTH + moveLarge.getWidth();
            if (x >= removeLarge) {
                largeList.remove(i);
            } //end boolean if statement to change direction
            x += largeDX;
            moveLarge.setX(x);
        } //end for loop
    } //End moveLargeEnemy method

    /**
     * This method sets how much each Shot will be moved each time the animation
     * timer calls the method.
     */
    public void moveShot() {
        if (shotList.isEmpty()) {
            return;
        }
        for (int i = 0; i < shotList.size(); i++) {
            Shot shot = shotList.get(i);
            double y = shot.getY();
            double dy = 0.21;
            y -= dy;
            shot.setY(y);
        } //End for loop
        collisionCheckTimeline();
    } //End moveShot method
    
    /**
     * When this method is called it runs the animations to check for collisions
     * with each SmallEnemy, each LargeEnemy, and with the invisible object at
     * the top of the game.
     */
    public void collisionCheckTimeline() {
        animation = new Timeline(new KeyFrame(Duration.millis(1), e -> checkCollisionSmall()));
        animation.setCycleCount(1);
        animation.play();   
        
        animation = new Timeline(new KeyFrame(Duration.millis(1), e -> checkCollisionLarge()));
        animation.setCycleCount(1);
        animation.play();
        
        animation = new Timeline(new KeyFrame(Duration.millis(1), e -> checkTopCollision()));
        animation.setCycleCount(1);
        animation.play();
    } //End smallCollisionTimeline method
    
    /**
     * This method checks for collisions with each SmallEnemy.
     */
    public void checkCollisionSmall() {
        //This segment of code checks for collisions with each SmallEnemy.
        for (int i = 0; i < shotList.size(); i++) {
            Shot tempShot = shotList.get(i);
            tempShot.setVisible(true);
            for (int j = 0; j < smallList.size(); j++) {
                SmallEnemy tempSmall = smallList.get(i);
                tempSmall.setVisible(true);
                if (tempShot.isVisible() && tempSmall.isVisible()) {
                    if (tempShot.getBoundsInLocal().intersects(tempSmall.getBoundsInLocal())) {
                        tempShot.setVisible(false);
                        tempSmall.setVisible(false);
                        smallList.remove(j);
                        shotList.remove(i);
                        setScore();
                    } //End inner boolean if statement
                } //End of outer boolean if statement
            } //End inner for loop
        } //End outer for loop
    } //End checkCollisionSmall method
    
    /**
     * This method checks for collisions with each LargeEnemy.
     */
    public void checkCollisionLarge() {
        //This segment of code checks for collisions with each LargeEnemy.
        for (int i = 0; i < shotList.size(); i++) {
            Shot tempShot = shotList.get(i);
            tempShot.setVisible(true);
            for (int j = 0; j < largeList.size(); j++) {
                LargeEnemy tempLarge = largeList.get(j);
                tempLarge.setVisible(true);
                if (tempShot.isVisible() && tempLarge.isVisible()) {
                    if (tempShot.getBoundsInParent().intersects(tempLarge.getBoundsInParent())) {
                        tempShot.setVisible(false);
                        tempLarge.setVisible(false);
                        largeList.remove(j);
                        shotList.remove(i);
                        setScore();
                    } //End inner boolean if statement
                } //End outer boolean if statement
            } //End inner for loop
        } //End outer for loop
    } //End checkCollisionLarge method
    
    /**
     * This method checks for a collision with the invisible rectangle at the 
     * top of the screen.
     */
    public void checkTopCollision() {        
        //This segment of code checks for collisions with the invisible topOfGame Rectangle object.
        for (int i = 0; i < shotList.size(); i++) {
            Shot tempShot = shotList.get(i);
            tempShot.setVisible(true);
            if (tempShot.isVisible() && topOfGame.isVisible()) {
                if (tempShot.getBoundsInParent().intersects(topOfGame.getBoundsInParent())) {
                    tempShot.setVisible(false);
                    shotList.remove(i);
                    setMisses();
                } //end inner boolean if statement
            } //end outer boolean if statement
        } //end for loop
    } //End checkTopCollision method

    /**
     * This method updates the counter for the player's points.
     */
    public void setScore() {
        //int pointsToWin = 500;
        playerPointCounter += addToPlayerScore;
        System.out.println("Score updated:  " + playerPointCounter);
        setTextField();
        //If the player gets a point amount within the given range the winGame and stop method are called.
        if (playerPointCounter > 499 && playerPointCounter < 511) { 
            try {
                winGame();
            } catch (IOException ex) {
                Logger.getLogger(GamePane.class.getName()).log(Level.SEVERE, null, ex);
            }
            stop();
        }
    } //End setScore method
    
    /**
     * This method updates the counter for the player's misses.
     */
    public void setMisses() {
        int missesToLose = 10;
        gameOverCounter += addToMisses;
        System.out.println("Misses Updated:  " + gameOverCounter);
        setTextField();
        //If the player gets the number of points designated then the loseGame method is called.
        if (gameOverCounter == missesToLose) {
            loseGame();
        }
        while(gameOverCounter == missesToLose) {
            stop();
        }
    } //End setMisses method
    
    /**
     * This method updates the Text object with the new score information, removes
     * the old TextField from the Pane, and adds the new Text object with the
     * updated score to the pane. It is called every time a collision is
     * detected and the player's score counter or miss counter is updated.
     */
    public void setTextField() {
        showScore.setText("Score:  " + String.valueOf(playerPointCounter)
            + "\nMisses:  " + String.valueOf(gameOverCounter));
        showScore.setLayoutX(25);
        showScore.setLayoutY(35);
        showScore.setFill(Color.WHITE);
        //Font family, weight, font size
        showScore.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD, 20));
    } //End setTextField method

    /**
     * This method determines if the player loses the game. The player loses the
     * game when they miss a target 10 times. When the game is lost a
     * JOptionPane window displays telling them the game is over and how many
     * points they had when they lost. This method also stops all animations.
     */
    public static void loseGame() {    
        String playerLosesMsg = "You Missed 10 times and Lost!\n\n"
            + "Your Score:  " + playerPointCounter;
        JOptionPane.showMessageDialog(null, playerLosesMsg, "Game Over!", JOptionPane.INFORMATION_MESSAGE);
        mainScoreControl();
        System.exit(10);
    } //End loseGame method
    
    /**
     * This method determines if the player wins the game. The player wins the
     * game when they earn 500 points. When the game is won a JOptionPane
     * window displays telling them they won the game, how many points they
     * had at the end, how many misses they had, and the coordinates for the
     * final location.
     */
    public static void winGame() throws IOException {
        CacheManager cacheManager = new CacheManager();
        String finalCoords, cacheGcCode;
        
        finalCoords = cacheManager.readCoordinates();
        cacheGcCode = cacheManager.readGcCode();
        
        String playerWinsMsg = "You Win!\n\n"
            + "Your Score:  " + playerPointCounter + "\n"
            + "Misses:  " + gameOverCounter + "\n\n"
            + "The final coords for " + cacheGcCode + " are \n" + finalCoords;
        JOptionPane.showMessageDialog(null, playerWinsMsg, "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        //mainScoreControl(); REMOVED
    } //End winGame method
    
    /**
     * When the player presses the "P" button this method is called.
     * It displays one of two messages based on how many points the player has
     * at the time it is pressed.  One message the player accepts their loss and
     * for the other the game accepts their loss.  Either way the player's
     * point total and total number of misses are displayed and the
     * mainScoreControl method is called.
     */
    public static void endGame() throws IOException {
        if (playerPointCounter <= 499) {
            String surrenderMsg = "Your surrender has been accepted, Captain!\n\n"
            + "Your Score:  " + playerPointCounter + "\n"
            + "Misses:  " + gameOverCounter;
            JOptionPane.showMessageDialog(null, surrenderMsg, "Surrender Accepted", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            CacheManager cacheManager = new CacheManager();
            String finalCoords, cacheGcCode;
            
            finalCoords = cacheManager.readCoordinates();
            cacheGcCode = cacheManager.readGcCode();
            
            String endGameMsg = "We accept our defeat... for now!\n\n"
            + "Your Score:  " + playerPointCounter + "\n"
            + "Misses:  " + gameOverCounter + "\n \n"
            + "The final coords for " + cacheGcCode + " are \n" + finalCoords;
            JOptionPane.showMessageDialog(null, endGameMsg, "Game Over!", JOptionPane.INFORMATION_MESSAGE);
        }  
        mainScoreControl();
        //System.exit(1);
    } //End endGame method
    
    /**
     * This method creates a new instance of HighScoreManager, adds the player's
     * score to the ArrayList of high scores in the HSM Class via its addScore
     * method, and outputs that information in a JOptionPane window.
     */
    public static void mainScoreControl() {
        HighScoreManager highScoreManager = new HighScoreManager();
        highScoreManager.addScore(playerName,playerPointCounter);
        System.out.print(highScoreManager.getHighscoreString());
        
        String highScoreMsg = highScoreManager.getHighscoreStringMsg();
        JOptionPane.showMessageDialog(null, highScoreMsg, "High Scores", JOptionPane.INFORMATION_MESSAGE);
        System.exit(1);
    } //End mainScoreControl method
    
    /**
     * Called when Ctrl-Alt-H is pressed so the admin can view the high scores without accidentally effecting anything.
     */
    public static void showHighScores() {
        HighScoreManager highScoreManager = new HighScoreManager();
        String highScoreMsg = highScoreManager.getHighscoreStringMsg();
        JOptionPane.showMessageDialog(null, highScoreMsg, "High Scores", JOptionPane.INFORMATION_MESSAGE); 
    } //End showHighScores method
     
    /**
     * Called when Ctrl-Alt-T is pressed so the admin can update the cache info.
     * @throws IOException 
     */
    public static void mainCacheControl() throws IOException {     
        CacheManager cacheManager = new CacheManager();
        String newGcCode;
        String newCoordinates;
        String newAdminPassword;
        String userEnteredPassword;
        
        userEnteredPassword = JOptionPane.showInputDialog("Please Enter Your Admin Password:  ");
        String adminPasswordOnFile = cacheManager.readAdminPassword();
        
        if (userEnteredPassword.equals(adminPasswordOnFile)) {
           newGcCode = cacheManager.changeGcCode();
           newCoordinates = cacheManager.changeCoordinates();
           newAdminPassword = cacheManager.changeAdminPassword();
           
           cacheManager.saveGcCode(newGcCode);
           cacheManager.saveCoordinates(newCoordinates);
           cacheManager.saveAdminPassword(newAdminPassword);
           
           cacheManager.cacheUpdateReview();
        }
        else {
            String adminPasswordErrorMsg = "Admin password has been entered unsuccessfully. \n\n"
                    + "Terminating program.";
            JOptionPane.showMessageDialog(null, adminPasswordErrorMsg, "[ADMIN] Password Entry Error", JOptionPane.INFORMATION_MESSAGE);
            System.exit(666);
        }
        System.exit(1);
    } //End mainScoreControl method
  
} //End of GamePane Class