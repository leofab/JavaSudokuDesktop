package sudoku;

import sudoku.domain.Game;

import java.io.IOException;

public interface IStorage {
    void updateGameData(Game game) throws IOException;
    Game getGameData() throws IOException;
}
