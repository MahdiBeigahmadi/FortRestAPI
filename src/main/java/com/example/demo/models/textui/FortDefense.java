package com.example.demo.models.textui;

import com.example.demo.models.Enemy;
import com.example.demo.models.Game;
import com.example.demo.restapi.ApiGameDTO;

/**
 * Launch the Fort Defense game (text-mode).
 */
public class FortDefense {
    private static final int DEFAULT_NUMBER_FORTS = 5;

    public static void main(String[] args) {
        // Determine # forts in Game
        int numForts;
        if (args.length == 0) {
            numForts = DEFAULT_NUMBER_FORTS;
        } else {
            numForts = Integer.parseInt(args[0]);
        }

        // Check if cheating:
        boolean cheating = false;
        if (args.length >= 2) {
            if (args[1].trim().compareToIgnoreCase("--cheat") == 0) {
                cheating = true;
            }
        }

        // Play game
        try {
            Game game = new Game(numForts);
            TextUI ui = new TextUI((ApiGameDTO) game);

            if (cheating) {
                ui.displayBoard(true);
                System.out.println();
                System.out.println();
            }

            System.out.println("Starting game with " + numForts + " forts.");
            ui.playGame();
        } catch (Enemy.UnableToCreateEnemyException e) {
            System.out.println("Error: Unable to place " + numForts + " on the board.");
            System.out.println("       Try running game again with fewer forts.");
        }
    }
}
