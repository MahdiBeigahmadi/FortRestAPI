package com.example.demo.controllers;

import com.example.demo.models.textui.TextUI;
import com.example.demo.restapi.ApiBoardDTO;
import com.example.demo.restapi.ApiGameDTO;
import com.example.demo.restapi.ApiLocationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

/* GameController class
 * GameController.java
 *
 * Class Description: It is a Controller class that manages
 * interactions between front-end and back-end
 * Class Invariant:
 *
 * Author: Mahdi Beigahmadi
 * Student ID: 301570853
 * Last modified: March. 2024
 */

@RestController
@RequestMapping("/api")
public class GameController {
    public static final ArrayList<ApiGameDTO> games = new ArrayList<>();

    public GameController() {
        games.add(new ApiGameDTO(1, true, false, 1200, 10));
        games.add(new ApiGameDTO(2, true, false, 1100, 9));
        games.add(new ApiGameDTO(3, false, true, 900, 8));
        games.add(new ApiGameDTO(4, false, true, 8200, 7));
        games.add(new ApiGameDTO(5, false, false, 700, 6));

    }

    @GetMapping("/about")
    public String getMyName() {
        return "MahdiBeigahmadi";
    }

    @GetMapping("/games")
    public ApiGameDTO getApiGameDtoObjects() {
        return new ApiGameDTO();
    }

    @PostMapping("/games")
    public ResponseEntity<ArrayList<ApiGameDTO>> createNewGame() {
        try {
            games.add(new ApiGameDTO(6, true, false, 600, 5));
            games.add(new ApiGameDTO(7, true, false, 500, 4));
            games.add(new ApiGameDTO(8, false, true, 400, 3));
            games.add(new ApiGameDTO(9, true, true, 300, 2));
            games.add(new ApiGameDTO(10, false, false, 200, 1));

            return ResponseEntity.status(201).body(games);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e);
        }
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<ApiGameDTO> getOpponentById(@PathVariable("id") int id) {
        if (id < 1 || id > games.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with this ID not found");
        }
        try {
            return ResponseEntity.status(201).body(games.get(id));
        } catch (IndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with this ID not found", e);
        }
    }


    @GetMapping("/games/{id}/board")
    public ResponseEntity<String> getStateOfTheBoard(@PathVariable("id") int id) {
        try {
            String boardState = new ApiBoardDTO().getCellState();
            return new ResponseEntity<>(boardState, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with that ID not Found.");
        }
    }


    @PostMapping("/games/{id}/moves")
    public ResponseEntity<?> makeMove(@RequestBody ApiLocationDTO moves, @PathVariable("id") int id) {
        try {
            ApiLocationDTO location = new ApiLocationDTO();
            location.setCol(moves.getCol());
            location.setRow(moves.getRow());
            location.setEnemyNumberAtCell(id);
            location.setEnemyShot();
            return new ResponseEntity<>("YOU HAVE MADE YOUR MOVE SUCCESSFULLY", HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with this ID not found", e);
        }
    }

    @PostMapping("/games/{id}/cheatstate")
    public ResponseEntity<?> enableCheatMode(@PathVariable("id") int id, @RequestBody String cheatCode) {
        if (!"SHOW_ALL".equals(cheatCode)) {
            return new ResponseEntity<>("Invalid cheat code.", HttpStatus.BAD_REQUEST);
        }
        ApiGameDTO game = games.get(id);
        if (game == null) {
            return new ResponseEntity<>("Game not found.", HttpStatus.NOT_FOUND);
        }
        try {
            new TextUI(game).displayBoard(true);
            return new ResponseEntity<>("YOU HAVE ENABLED THE CHEATING MODE SUCCESSFULLY", HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("An error occurred while enabling cheat mode.", HttpStatus.BAD_REQUEST);
        }
    }
}
