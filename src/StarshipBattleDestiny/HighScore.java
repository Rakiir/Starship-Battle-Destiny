/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package StarshipBattleDestiny;

import java.io.Serializable;

public class HighScore  implements Serializable {
    private int score;
    private String name;
    
    public HighScore(String name, int score) {
        this.score = score;
        this.name = name;
    } //End HighScore constructor

    public int getScore() {
        return score;
    } //End getScore method

    public String getName() {
        return name;
    } //End getName method
    
} //End of HighScore Class