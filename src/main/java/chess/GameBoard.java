package chess;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

    private final Map<Square, Piece> pieces; // almost all positions were null, use map to only store existing (sparse)
    private final int size;

    public GameBoard(int size) {
        this.size = size;
        this.pieces = new HashMap<>();
    }

    private GameBoard(GameBoard original) {
        this.size = original.size;
        this.pieces = new HashMap<>(original.pieces); // copy map to avoid changing original
    }

    public int getSize() {
        return size;
    }

    public Piece get(Square position) {
        return pieces.get(position);
    }

    public Square find(Piece piece) {
        // can't get by value from map, loop over entries instead, not a problem here since always single entry
        // to be even more efficient, use bimap (two maps), or change everything to use one-way get only
        for (Map.Entry<Square, Piece> squarePieceEntry : pieces.entrySet()) {
            if (squarePieceEntry.getValue().equals(piece))
                return squarePieceEntry.getKey();
        }
        return null;
    }

    public GameBoard set(Square position, Piece piece) {
        GameBoard copy = new GameBoard(this);
        copy.pieces.put(position, piece);
        return copy;
    }

    public GameBoard move(Square from, Square to) {
        if (from.equals(to))
            return this;
        GameBoard copy = new GameBoard(this);
        copy.pieces.put(to, copy.pieces.get(from));
        copy.pieces.remove(from);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        GameBoard gameBoard = (GameBoard) o;
        return pieces.equals(gameBoard.pieces);
    }

    @Override
    public int hashCode() {
        return pieces.hashCode();
    }
}
