/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarshipBattleDestiny;

import java.util.*;
import java.io.*;

public class HighScoreManager {

    private ArrayList<HighScore> savedHighScoresList;
    private static final String HIGHSCORE_FILE = "highScoreFile.txt";

    //Initialising an in and outputStream for working with the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public HighScoreManager() {
        //Initialising the savedHighScoresList-arraylist
        savedHighScoresList = new ArrayList<>();
    } //End HighScoreManager Constructor

    /**
     * This method gets the high scores as a string for output.
     * 
     * @return highScoreString
     */
    public String getHighscoreString() {
        String highscoreString = "";
        int max = 10;

        ArrayList<HighScore> scores;
        scores = getScores();

        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highscoreString += (i + 1) + ".\t" + scores.get(i).getName() + "\t\t" + scores.get(i).getScore() + "\n";
            i++;
        }
        return highscoreString;
    } //End getHighScoreString method
    
    /**
     * This method us a duplicate of the method getHighScoreString() with the only difference being that it 
     * uses spaces instead of tabs in the String that is returned because JOptionPane does not recognize tabs.
     * 
     * @return highScoreStringMsg
     */
    public String getHighscoreStringMsg() {
        String highscoreStringMsg = "";
        int max = 10;

        ArrayList<HighScore> scores;
        scores = getScores();

        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highscoreStringMsg += (i + 1) + ".   " + scores.get(i).getName() + "   " + scores.get(i).getScore() + "\n";
            i++;
        }
        return highscoreStringMsg;
    } //End getHighScoreStringMsg method

    /**
     * This method gets the scores by running the methods loadScoreFile, sortScores, and removeLowScores.
     * This gets the saved past names and  high scores from the file, sorts this information 
     * by score from highest score to lowest score, and then removes all scores but the 10 highest scores.
     * 
     * @return savedHighScoresList
     */
    public ArrayList<HighScore> getScores() {
        loadScoreFile();
        sortScores();
        removeLowScores();
        return savedHighScoresList;
    } //End getScores method

    /**
     * This method sorts the scores in the savedHighScoresList
     */
    private void sortScores() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(savedHighScoresList, comparator);
    } //End sort method

    /**
     * This method adds the saved high scores from the file to the ArrayList savedHighScoresList.
     * 
     * @param playerName
     * @param pastHighScore 
     */
    public void addScore(String playerName, int pastHighScore) {
        loadScoreFile();
        savedHighScoresList.add(new HighScore(playerName, pastHighScore));
        updateScoreFile();
    } //End addScore method
    
    /**
     * This method removes all the high scores with a score that is not one of 
     * the 10 highest from the ArrayList savedHighScoresList.
     */
    public void removeLowScores() {
        if (savedHighScoresList.size() > 10) {
            for (int i = 10; i < savedHighScoresList.size(); i++) {
                savedHighScoresList.remove(i);
            }
        }
    } //End removeLowScores method

    /**
     * This method loads the file that contains the saved high scores from the past times the game was played.
     */
    public void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
            savedHighScoresList = (ArrayList<HighScore>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Loading] FileNotFound Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Loading] IOException Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Loading] ClassNotFoundException Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Loading] IOException Error: " + e.getMessage());
            }
        }
    } //End loadScoreFile method
    
    

    /**
     * This method updates the save file with the new high scores.
     */
    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            outputStream.writeObject(savedHighScoresList);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FileNotFound Error: " + e.getMessage() + ", the program will try and make a new file.");
        } catch (IOException e) {
            System.out.println("[Update] IOException Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] IOException Error: " + e.getMessage());
            }
        }

    } //End updateScoreFile method
    
} //End of HighScoreManager Class