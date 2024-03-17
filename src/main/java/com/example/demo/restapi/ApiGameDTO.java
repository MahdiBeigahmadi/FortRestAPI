package com.example.demo.restapi;

/**
 * DTO class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
import com.example.demo.models.*;
public class ApiGameDTO extends Game {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int opponentPoints;
    public long numActiveOpponentForts;

    // Amount of points that the opponents scored on the last time they fired.
    // If opponents have not yet fired, then it should be an empty array (0 size).
    public int[] lastOpponentPoints;

    public ApiGameDTO() {
        Game enemyScores = new Game();
        lastOpponentPoints = new int[]{enemyScores.getEnemyPoints()};
    }
    public ApiGameDTO(int gameNumber, boolean isGameWon, boolean isGameLost, int opponentPoints, long numActiveOpponentForts) {
        this.gameNumber = gameNumber;
        this.isGameLost = isGameLost;
        this.isGameWon = isGameWon;
        this.opponentPoints = opponentPoints;
        this.numActiveOpponentForts = numActiveOpponentForts;
    }


    public boolean hasUserLost() {
        return isGameLost;
    }

    public int getEnemyPoints() {
        return opponentPoints;
    }
}
