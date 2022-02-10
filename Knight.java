import java.util.ArrayList;

public class Knight extends Piece{

    Knight(String color, int x, int y) {
        super(color, x, y);
    }

    public Knight copy() {
        return new Knight(this.color, this.position.getX(), this.position.getY());
    }
    @Override
    ArrayList<Move> possibleMoves() {

        ArrayList<Move> moves = new ArrayList<Move>();
        int[] destX = {2, 2, -2, -2, 1, -1, 1, -1};
        int[] destY = {1, -1, 1, -1, 2, 2, -2, -2};
        int xdest; // x for final destination of a move
        int ydest; // y for final destination of a move
        ChessTable board = Main.table;

        for (int i = 0; i < destX.length; i++)
            for (int j = 0; j < 8; j++) {
                xdest = position.getX() + j * destX[i];
                ydest = position.getY() + j * destY[i];
                if (board.isSquareAvailable(this, xdest, ydest)) {
                    Move m = new Move(position, new Position(xdest, ydest));
                    moves.add(m);
                }
            }

        return moves;
    }

    @Override
    void move() {

    }
}
