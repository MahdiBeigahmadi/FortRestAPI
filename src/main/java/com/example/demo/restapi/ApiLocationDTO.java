package com.example.demo.restapi;

import com.example.demo.models.Cell;
import com.example.demo.models.Coordinate;
import com.example.demo.models.GameBoard;

/**
 * DTO class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiLocationDTO {
    private int row;
    private int col;

    private int id;

    public ApiLocationDTO () {
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }
    public void setEnemyNumberAtCell(int id){
        this.id = id;
    }
    public void setEnemyShot(){
        new Cell(true, id).makeHasBeenShot();
        new GameBoard().recordUserShot(new Coordinate(row, col));
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
