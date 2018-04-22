package chess;

import java.util.List;

public interface Piece {

    List<GameBoard> getValidMovesFrom(GameBoard board);
}
