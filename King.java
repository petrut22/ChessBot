import java.util.ArrayList;

public class King extends Piece{

    King(String color, int x, int y) {
        super(color, x, y);
    }

    public King copy() {
        return new King(this.color, this.position.getX(), this.position.getY());
    }

    // positions for the king that do not cause check
    @Override
    ArrayList<Move> possibleMoves() {
        int[] destX = {1, -1, 0, 0, 1, 1, -1, -1};
        int[] destY = {0, 0, 1, -1, 1, -1, 1, -1};

        ArrayList<Move> moves = new ArrayList<>();
        ChessTable board = Main.table;
        int xdest; // x for final destination of a move
        int ydest; // y for final destination of a move

        for (int i = 0; i < destX.length; i++) {
            xdest = position.getX() + destX[i];
            ydest = position.getY() + destY[i];
            if (board.coordinatesValid(xdest, ydest)) {
                ChessTable chessAux = new ChessTable(board, new Move(position, new Position(xdest, ydest)));
                if (!chessAux.checkKingSah(new Position(xdest, ydest))) {
                    if (board.pieceFromPosition(xdest, ydest) != null) {
                        if (board.pieceFromPosition(xdest, ydest).color != this.color) {
                            Move m = new Move(position, new Position(xdest, ydest));
                            moves.add(m);
                        }
                    } else {
                        Move m = new Move(position, new Position(xdest, ydest));
                        moves.add(m);
                    }
                }


            }
        }
        if (moves.size() == 0)
            return null;
        return moves;
    }

    @Override
    void move() {

    }
}
