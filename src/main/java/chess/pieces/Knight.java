package chess.pieces;

import chess.GameBoard;
import chess.Piece;
import chess.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight implements Piece {

  @Override
  public List<GameBoard> getValidMoves(GameBoard from) {
    Square myPosition = from.find(this);
    List<Square> allMoves = Arrays.asList(
        // some moves are missing. that's ok
        myPosition.move(2, 1),
        myPosition.move(2, -1),
        myPosition.move(-2, -1),
        myPosition.move(-2, 1));

    int boardSize = from.getSize();
    List<GameBoard> positions = new ArrayList<>(4);
    for (Square newPosition : allMoves) {
      if (newPosition.isInBounds(boardSize))
        positions.add(from.move(myPosition, newPosition));
    }
    return positions;
  }
}
