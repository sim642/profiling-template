package chess;

import java.util.Arrays;

public class GameBoard {

  private final Piece[][] pieces;
  private final int size;

  public GameBoard(int size) {
    this.size = size;
    this.pieces = new Piece[size][size];
  }

  private GameBoard(GameBoard original) {
    this.size = original.size;
    this.pieces = new Piece[size][];
    for (int i = 0; i < size; i++) {
      pieces[i] = original.pieces[i].clone();
    }
  }

  public int getSize() {
    return size;
  }

  public Piece get(Square position) {
    return pieces[position.rank][position.file];
  }

  public Square find(Piece piece) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (pieces[i][j] == piece)
          return new Square(i, j);
      }
    }
    return null;
  }

  public GameBoard set(Square position, Piece piece) {
    GameBoard copy = new GameBoard(this);
    copy.pieces[position.rank][position.file] = piece;
    return copy;
  }

  public GameBoard move(Square from, Square to) {
    if (from.equals(to))
      return this;
    GameBoard copy = new GameBoard(this);
    copy.pieces[to.rank][to.file] = copy.pieces[from.rank][from.file];
    copy.pieces[from.rank][from.file] = null;
    return copy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    GameBoard gameBoard = (GameBoard) o;
    // there's nothing wrong with deepEquals unless it's called too many times
    return Arrays.deepEquals(pieces, gameBoard.pieces); 
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(pieces);
  }
}
