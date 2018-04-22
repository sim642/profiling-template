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

      Square start = new Square(0, 1);
      Square destination = new Square(4, 4);
      boolean canReach = isReachable(size, start, destination);
      System.out.println(canReach
          ? "square IS reachable"
          : "square IS NOT reachable");
    }
  }

  public static boolean isReachable(int size, Square start, Square destination) {
    Piece piece = new Knight();
    GameBoard board = new GameBoard(size).set(start, piece);
    return isReachable(board, piece, destination);
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
