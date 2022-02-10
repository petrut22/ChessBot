import java.util.ArrayList;

public class Rook extends Piece{

    Rook(String color, int x, int y) {
        super(color, x, y);
    }

    public Rook copy() {
        return new Rook(this.color, this.position.getX(), this.position.getY());
    }
    @Override
    ArrayList<Move> possibleMoves() {
        int[] destX = {1, -1, 0, 0};
        int[] destY = {0, 0, 1, -1};

        return getMoves(destX, destY);
    }

    @Override
    void move() {

    }
}
