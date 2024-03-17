package com.example.demo.restapi;

import com.example.demo.controllers.GameController;
import com.example.demo.models.Cell;
import com.example.demo.models.Coordinate;
import com.example.demo.models.Game;
import com.example.demo.models.GameBoard;
import com.example.demo.models.textui.TextUI;

import java.io.Serializable;

/**
 * DTO class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 * from the data stored in the model, or required by the model.
 */
public class ApiBoardDTO {
    public static int row;
    public static int col;

   // celState[row][col] = {"fog", "hit", "fort", "miss", "field"}
    public static String[][] cellStates;

    public ApiBoardDTO() {
    }
    public String getCellState() {
        String word = new GameBoard().getCellState(new Coordinate(row , col) ).toString();
        switch (word) {
            case "fog" -> {
                return "fog";
            }
            case "hit" -> {
                return "hit";
            }
            case "fort" -> {
                return "fort";
            }
            case "miss" -> {
                return "miss";
            }
            case "field" -> {
                return "field";
            }
        }
        return "FAILED TO GET STATE OF THE CELL, THE GAME BOARD IS EMPTY, " +
                "PLEASE TRY TO PLAY SOME GAME BEFORE TRYING TO GET THE STATE OF THE CELL";
    }
}
