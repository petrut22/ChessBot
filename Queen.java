import java.util.ArrayList;

public class Queen extends Piece{

    Queen(String color, int x, int y) {
        super(color, x, y);
    }

    public Queen copy() {
        return new Queen(this.color, this.position.getX(), this.position.getY());
    }

    @Override
    ArrayList<Move> possibleMoves() {
        int[] destX = {1, -1, 0, 0, 1, 1, -1, -1};
        int[] destY = {0, 0, 1, -1, 1, -1, 1, -1};

        return getMoves(destX, destY);
    }

    @Override
    void move() {

    }
}
