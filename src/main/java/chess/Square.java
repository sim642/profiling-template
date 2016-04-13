package chess;

public class Square {
  public final int rank, file;

  public Square(int rank, int file) {
    this.rank = rank;
    this.file = file;
  }

  public Square move(int rankDelta, int fileDelta) {
    return new Square(rank + rankDelta, file + fileDelta);
  }

  public boolean isInBounds(int boardSize) {
    return rank >= 0 && file >= 0 && rank < boardSize && file < boardSize;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Square square = (Square) o;
    return rank == square.rank && file == square.file;
  }

  @Override
  public int hashCode() {
    return 31 * rank + file;
  }
}
