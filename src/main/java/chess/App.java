package chess;

import chess.pieces.Knight;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class App {

  public static void main(String[] args) throws Exception {
    System.out.println("Press enter to run");
    System.in.read();

    for (int size = 112; size > 96; size -= 4) {
      System.out.println("running board size " + size);
      boolean canReach = Check.whether(new Knight()).canReach(4, 4).from(0, 1).usingBoardSize(size).run();
      System.out.println(canReach
          ? "square IS reachable"
          : "square IS NOT reachable");
    }
  }

  /*
   * Fluent Interface / Builder pattern
   *
   * This is most useful when there are a lot of different optional
   * configuration options or you want to build an immutable object
   */
  static class Check {

    private Piece piece;
    private Square start;
    private Square destination;
    private int boardSize;

    static Check whether(Piece piece) {
      Check check = new Check();
      check.piece = piece;
      return check;
    }

    Check usingBoardSize(int size) {
      this.boardSize = size;
      return this;
    }

    Check canReach(int rank, int file) {
      this.destination = new Square(rank, file);
      return this;
    }

    Check from(int rank, int file) {
      start = new Square(rank, file);
      return this;
    }

    boolean run() {
      GameBoard board = new GameBoard(boardSize).set(start, piece);
      return isReachable(board, piece, destination);
    }
  }

  private static List<GameBoard> visitedBoardStates = new ArrayList<>();
  private static Deque<GameBoard> boardStatesToVisit = new ArrayDeque<>();

  static boolean isReachable(GameBoard startingBoard, Piece piece, Square destination) {
    boardStatesToVisit.push(startingBoard);
    while (!boardStatesToVisit.isEmpty()) {
      GameBoard nowVisiting = boardStatesToVisit.pop();
      if (nowVisiting.get(destination) == piece) {
        boardStatesToVisit.clear();
        return true;
      }
      findNewStatesToVisit(piece, nowVisiting);
    }
    return false;
  }

  private static void findNewStatesToVisit(Piece piece, GameBoard from) {
    for (GameBoard moveResult : piece.getValidMoves(from)) {
      if (shouldVisit(moveResult))
        boardStatesToVisit.add(moveResult);
    }
  }

  static boolean shouldVisit(GameBoard candidate) {
    if (visitedBoardStates.contains(candidate))
      return false;
    visitedBoardStates.add(candidate);
    return true;
  }
}
