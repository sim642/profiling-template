package chess;

import chess.pieces.Knight;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Press enter to run");
        System.in.read();

        for (int size = 112; size > 96; size -= 4) {
            System.out.println("running board size " + size);

            Square start = new Square(0, 1);
            Square destination = new Square(4, 4);
            boolean canReach = isReachableByKnight(size, start, destination);
            System.out.println(canReach
                    ? "square IS reachable"
                    : "square IS NOT reachable");
        }
    }

    public static boolean isReachableByKnight(int size, Square start, Square destination) {
        Piece piece = new Knight();
        GameBoard board = new GameBoard(size).set(start, piece);
        return isReachable(board, piece, destination);
    }

    private static Set<GameBoard> visitedBoardStates = new HashSet<>();
    private static Deque<GameBoard> boardStatesToVisit = new ArrayDeque<>();

    private static boolean isReachable(GameBoard startingBoard, Piece piece, Square destination) {
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

    private static void findNewStatesToVisit(Piece piece, GameBoard board) {
        for (GameBoard moveResult : piece.getValidMovesFrom(board)) {
            if (shouldVisit(moveResult))
                boardStatesToVisit.add(moveResult);
        }
    }

    private static boolean shouldVisit(GameBoard candidate) {
        if (visitedBoardStates.contains(candidate))
            return false;
        visitedBoardStates.add(candidate);
        return true;
    }
}
