package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Manage the game Fort Defense game state.
 */
public class Game {

    private final ScoreTracker enemyScoreTracker = new ScoreTracker();
    private final GameBoard board = new GameBoard();
    private final List<Enemy> enemies = new ArrayList<>();
    private List<Integer> latestEnemyDamages;
    private boolean lastPlayerShotHit;
    public Game(){}
    public Game(int numEnemies) {
        IntStream.range(1, numEnemies + 1)
                .forEach(enemyNum -> enemies.add(new Enemy(board, enemyNum)));
    }

    public boolean hasUserWon() {
        return enemies.stream().allMatch(Enemy::isFortDestroyed);
    }

    public boolean hasUserLost() {
        return enemyScoreTracker.hasWon();
    }

    public int getEnemyPoints() {
        return enemyScoreTracker.getScore();
    }

    public void recordPlayerShot(Coordinate cell) {
        board.recordUserShot(cell);
        lastPlayerShotHit = board.cellContainsEnemy(cell);
    }

    public boolean didLastPlayerShotHit() {
        return lastPlayerShotHit;
    }

    public Cell getCellState(Coordinate cell) {
        return board.getCellState(cell);
    }

    public void fireEnemyShots() {
        latestEnemyDamages = new ArrayList<>();
        enemies.stream()
                .mapToInt(Enemy::getShotDamage)
                .filter(dmg -> dmg > 0)
                .forEach(dmg ->
                    {
                        enemyScoreTracker.addScore(dmg);
                        latestEnemyDamages.add(dmg);
                    });
    }

    public int[] getLatestEnemyDamages() {
        if (latestEnemyDamages == null) {
            return new int[0];
        }
        return latestEnemyDamages.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
