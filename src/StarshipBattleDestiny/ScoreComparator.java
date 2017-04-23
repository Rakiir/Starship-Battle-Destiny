/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarshipBattleDestiny;

import java.util.Comparator;

public class ScoreComparator implements Comparator<HighScore> {

    public int compare(HighScore score1, HighScore score2) {

        int sc1 = score1.getScore();
        int sc2 = score2.getScore();

        if (sc1 > sc2) {
            return -1;
        } else if (sc1 < sc2) {
            return +1;
        } else {
            return 0;
        }
    }
} //End of ScoreComparator Class
