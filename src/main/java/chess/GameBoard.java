package chess;

import java.util.Arrays;

public class GameBoard {

    private final Piece[] pieces;
    private final int size;

    public GameBoard(int size) {
        this.size = size;
        this.pieces = new Piece[size * size];
    }

    private GameBoard(GameBoard original) {
        this.size = original.size;
        this.pieces = original.pieces.clone();
    }

    public int getSize() {
        return size;
    }

    public Piece get(Square position) {
        return pieces[position.rank * size + position.file];
    }

    public Square find(Piece piece) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == piece)
                return new Square(i / size, i % size);
        }
        return null;
    }

    public GameBoard set(Square position, Piece piece) {
        GameBoard copy = new GameBoard(this);
        copy.pieces[position.rank * size + position.file] = piece;
        return copy;
    }

    public GameBoard move(Square from, Square to) {
        if (from.equals(to))
            return this;
        GameBoard copy = new GameBoard(this);
        copy.pieces[to.rank * size + to.file] = copy.pieces[from.rank * size + from.file];
        copy.pieces[from.rank * size + from.file] = null;
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        GameBoard gameBoard = (GameBoard) o;
        return Arrays.equals(pieces, gameBoard.pieces);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(pieces);
    }
}
