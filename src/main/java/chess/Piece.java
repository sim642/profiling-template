package chess;

public interface Piece {

  Iterable<GameBoard> getValidMoves(GameBoard from);

}
