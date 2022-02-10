import java.util.ArrayList;

public abstract class Piece {
    public String color;
    public Position position;

    Piece() {
    }

    Piece(String color, int x, int y) {
        this.color = color;
        this.position = new Position(x, y);
    }

    Piece(Piece src) {
        this.color = src.color;
        this.position = src.position;
    }

    // checks if piece is black
    public boolean isPieceBlack() {
        return (color.equals("black"));
    }

    // updates the board and the piece when moving
    public void moveUpdates(Position src, int xdest, int ydest) {
        int xsrc = position.getX();
        int ysrc = position.getY();
        //this.position = new Position(xdest, ydest);
        Main.table.updateTable(xsrc, ysrc, xdest, ydest);
        char c1 = (char) ('a' + src.getX());
        char c2 = (char) ('a' + xdest);


        System.out.println("move " + c1 + (src.getY() + 1) + c2 + (position.getY() + 1));

    }

    // returns possible moves of piece
    abstract ArrayList<Move> possibleMoves();

    // moves the piece
    abstract void move();

    // returns all valid positions having all the possible coordinates for the moves given
    public ArrayList<Move> getMoves(int[] destX, int[] destY) {

        ArrayList<Move> moves = new ArrayList<>();
        ChessTable board = Main.table;
        int xdest; // x for final destination of a move
        int ydest; // y for final destination of a move

        for (int i = 0; i < destX.length; i++) {
            boolean gasit = true;
            for (int j = 1; j < 8 && gasit == true; j++) {
                xdest = position.getX() + j * destX[i];
                ydest = position.getY() + j * destY[i];
                if (board.coordinatesValid(xdest, ydest)) {

                    if (board.pieceFromPosition(xdest, ydest) != null) {
                        if (board.pieceFromPosition(xdest, ydest).color != this.color) {
                            Move m = new Move(position, new Position(xdest, ydest));
                            moves.add(m);
                        } else {
                            gasit = false;
                        }
                    } else {
                        Move m = new Move(position, new Position(xdest, ydest));
                        moves.add(m);
                    }
                }
            }
        }



//        System.out.println("\nPozitia initiala : " + moves.get(0).getSrc().getX() + " " + moves.get(0).getSrc().getY());
//
//        for(Move i : moves) {
//            System.out.println("Pozitie : " + i.getDest().getX() + " " + i.getDest().getY());
//        }
//
//        System.out.println("\n\n");

        return moves;
    }

    public abstract Piece copy();
}