package com.example.demo.models.textui;

import com.example.demo.models.*;
import com.example.demo.restapi.ApiGameDTO;

import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * Handle the text-based display and keyboard based interaction for
 * running the Fortress Defense game.
 */
public class TextUI {
    public static final char SYMBOL_FOG = '~';
    public static final char SYMBOL_FORT = 'X';
    public static final char SYMBOL_MISS = ' ';
    public static final char SYMBOL_NOTHING = '.';


    private final Game game;

    public TextUI(ApiGameDTO game) {
        this.game = game;
    }

    public void playGame() {
        displayWelcome();
        displayBoard(false);
        while (gameRunning()) {
            doPlayerShot();
            doEnemyShots();
            displayBoard(false);
        }
        doWonOrLost();
    }

    private void displayWelcome() {
        System.out.println("------------------------");
        System.out.println("Welcome to Fort Defense!");
        System.out.println("by Brian Fraser");
        System.out.println("------------------------");
        System.out.println();
    }

    private boolean gameRunning() {
        return !game.hasUserWon() && !game.hasUserLost();
    }

    public void displayBoard(boolean revealBoard) {
        System.out.println();
        System.out.println("Game Board:");

        // Print column headings:
        System.out.printf("%5s", "");
        for (int col = 0; col < GameBoard.NUMBER_COLS; col++) {
            System.out.printf("%3d", col + 1);
        }
        System.out.println();

        // Print rows:
        for (int row = 0; row < GameBoard.NUMBER_ROWS; row++) {
            System.out.printf("%5c", 'A' + row);
            for (int col = 0; col < GameBoard.NUMBER_COLS; col++) {
                Coordinate coord = new Coordinate(row, col);

                Cell cell = game.getCellState(coord);
                char symbol = convertCellStateToSymbol(cell, revealBoard);
                System.out.printf("%3c", symbol);
            }
            System.out.println();
        }

        // Print structural strength left:
        System.out.printf("Opponents points: %d / %d.%n",
                game.getEnemyPoints(), ScoreTracker.MAX_SCORE);

        if (revealBoard) {
            System.out.println("(Lower case fort letters are where you shot.)");
        }
    }

    public char convertCellStateToSymbol(Cell state, boolean revealBoard) {
        if (state.isHidden() && !revealBoard) {
            return SYMBOL_FOG;
        } else if (state.hasFort()) {
            if (revealBoard) {
                if (state.hasBeenShot()) {
                    return (char)(state.getFortNumberAtCell() + 'a' - 1);
                } else {
                    return (char)(state.getFortNumberAtCell() + 'A' - 1);
                }
            } else {
                return SYMBOL_FORT;
            }
        } else if (state.hasBeenShot()) {
            return SYMBOL_MISS;
        } else {
            return SYMBOL_NOTHING;
        }
    }

    private void doPlayerShot() {
        Coordinate cell = getPlayerMove();
        game.recordPlayerShot(cell);
        if (game.didLastPlayerShotHit()) {
            System.out.println("HIT!");
        } else {
            System.out.println("Miss.");
        }
    }

    private Coordinate getPlayerMove() {
        while (true) {
            System.out.print("Enter your move: ");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            try {
                return new Coordinate(input);
            } catch (InvalidParameterException exception) {
                System.out.println("Invalid target. Please enter a coordinate such as D10.");
            }
        }
    }

    private void doEnemyShots() {
        game.fireEnemyShots();
        int[] damages = game.getLatestEnemyDamages();

        for (int i = 0; i < damages.length; i++) {
            System.out.printf("Opponent #%d of %d shot you for %d points!%n",
                    i + 1,
                    damages.length,
                    damages[i]);
        }
    }

    private void doWonOrLost() {
        if (game.hasUserWon()) {
            System.out.println("Congratulations! You won!");
        } else if (game.hasUserLost()) {
            System.out.println("I'm sorry, your fort is all wet! They win!");
        } else {
            assert false;
        }
        displayBoard(true);
    }
}
