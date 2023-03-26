package sudoku.domain;

import java.io.Serializable;

public class Game implements Serializable {
    private final GameState gameState;
    private final int[][] gridState;

    public static final int GRID_BOUNDARY = 9;

    public Game() {
    }

    public Game(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int[][] getGridState() {
        return GameUtilities.copyToNewArray(gridState);
    }
}
