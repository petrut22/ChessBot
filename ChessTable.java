import java.util.ArrayList;

public class ChessTable {
    Piece matrix[][];
    ArrayList<Piece> player_white = new ArrayList<Piece>(16);
    ArrayList<Piece> player_black = new ArrayList<Piece>(16);


    public ChessTable() {
        matrix = new Piece[8][8];

        for(int i = 0; i < 8; i++) {
            matrix[1][i] = new Pawn("white", i, 1);
            player_white.add(matrix[1][i]);
        }

        matrix[0][0] = new Rook("white", 0, 0);
        player_white.add(matrix[0][0]);
        matrix[0][7] = new Rook("white", 7, 0);
        player_white.add(matrix[0][7]);

        matrix[0][2] = new Bishop("white", 2, 0);
        player_white.add(matrix[0][2]);
        matrix[0][5] = new Bishop("white", 5, 0);
        player_white.add(matrix[0][5]);

        matrix[0][1] = new Knight("white", 1, 0);
        player_white.add(matrix[0][1]);
        matrix[0][6] = new Knight("white", 6, 0);
        player_white.add(matrix[0][6]);

        matrix[0][3] = new Queen("white", 3, 0);
        player_white.add(matrix[0][3]);
        matrix[0][4] = new King("white", 4, 0);
        player_white.add(matrix[0][4]);



        for(int i = 0; i < 8; i++) {
            matrix[6][i] = new Pawn("black", i, 6);
            player_black.add(matrix[6][i]);
        }

        matrix[7][0] = new Rook("black", 0, 7);
        player_black.add(matrix[7][0]);
        matrix[7][7] = new Rook("black", 7, 7);
        player_black.add(matrix[7][7]);

        matrix[7][2] = new Bishop("black", 2, 7);
        player_black.add(matrix[7][2]);
        matrix[7][5] = new Bishop("black", 5, 7);
        player_black.add(matrix[7][5]);

        matrix[7][1] = new Knight("black", 1, 7);
        player_black.add(matrix[7][1]);
        matrix[7][6] = new Knight("black", 6, 7);
        player_black.add(matrix[7][6]);

        matrix[7][3] = new Queen("black", 3, 7);
        player_black.add(matrix[7][3]);
        matrix[7][4] = new King("black", 4, 7);
        player_black.add(matrix[7][4]);

    }

    // checks if the coordinates are valid
    public boolean coordinatesValid(int x, int y) {
        return (x < 8 && x >= 0 && y < 8 && y >= 0);
    }

    // checks if square is available for moving the piece there
    public boolean isSquareAvailable(Piece piece, int x, int y) {
        if (!coordinatesValid(x, y))
            return false;
        if (pieceFromPosition(x, y) != null) {
            if (pieceFromPosition(x, y).color == piece.color) {
                return false;
            }
        }
        return true;
    }

    // creates a copy of the original table with one move ahead
    ChessTable (ChessTable chessTbl, Move move) {
        this.matrix = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(chessTbl.matrix[i][j] != null) {
                    matrix[i][j] = chessTbl.matrix[i][j].copy();
//                    System.out.println("" + i + j + matrix[i][j].color + " ");
                }
            }
        }
        for (int i = 0; i < chessTbl.player_black.size(); i++) {
            this.player_black.add(pieceFromPosition(chessTbl.player_black.get(i).position.getX(), chessTbl.player_black.get(i).position.getY()));
        }

        for (int i = 0; i < chessTbl.player_white.size(); i++) {
            this.player_white.add(pieceFromPosition(chessTbl.player_white.get(i).position.getX(), chessTbl.player_white.get(i).position.getY()));
        }

        int xsrc = move.src.getX();
        int ysrc = move.src.getY();
        int xdest = move.dest.getX();
        int ydest = move.dest.getY();

        if (capturePosition(matrix[ysrc][xsrc].color, xdest, ydest)) {
            if (pieceFromPosition(xdest, ydest).isPieceBlack()) {
                player_black.remove(pieceFromPosition(xdest, ydest));
            } else {
                player_white.remove(pieceFromPosition(xdest, ydest));
            }
        }
        matrix[ydest][xdest] = matrix[ysrc][xsrc];
        (matrix[ydest][xdest]).position = new Position(xdest, ydest);
        matrix[ysrc][xsrc] = null;
        if (matrix[ydest][xdest] instanceof Pawn && ((Pawn) matrix[ydest][xdest]).firstMove == true)
            ((Pawn) matrix[ydest][xdest]).firstMove = false;
    }

    // checks if square is occupied by a piece of any color
    public boolean isSquareOccupied(int x, int y) {
        return (pieceFromPosition(x, y) != null);
    }

    // checks if moving the piece to pos means capturing an opponent's piece
    public boolean capturePosition(String color, int xdest, int ydest) {
        if (pieceFromPosition(xdest, ydest) != null) {
            if (!color.equals(pieceFromPosition(xdest, ydest).color)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // returns the piece at position(x, y) or null if there us no piece there
    public Piece pieceFromPosition(int x, int y) {

        return matrix[y][x];
    }

    // updates the board after moving piece from source to destination
    public void updateTable(int xsrc, int ysrc, int xdest, int ydest) {

        // check if moving the piece to dest means capturing and update the list of opponent's pieces
        if (capturePosition(matrix[ysrc][xsrc].color, xdest, ydest)) {
            if (pieceFromPosition(xdest, ydest).isPieceBlack()) {
                player_black.remove(pieceFromPosition(xdest, ydest));
            } else {
                player_white.remove(pieceFromPosition(xdest, ydest));
            }
        }
        System.out.println("surs: " + xsrc + " " + ysrc + " dest: " + xdest + " " + ydest);
        matrix[ydest][xdest] = matrix[ysrc][xsrc];
        (matrix[ydest][xdest]).position = new Position(xdest, ydest);
        matrix[ysrc][xsrc] = null;
        if (matrix[ydest][xdest] instanceof Pawn && ((Pawn) matrix[ydest][xdest]).firstMove == true)
            ((Pawn) matrix[ydest][xdest]).firstMove = false;

    }
    // verify if the king is in check
    public boolean checkKingSah(Position position) {

        int xsrc = position.getX();
        int ysrc = position.getY();
        int xdest; // x for final destination of a move
        int ydest; // y for final destination of a move

        int[] destX = {1, -1, 0, 0, 1, 1, -1, -1,  2, 2, -2, -2, 1, -1, 1, -1};
        int[] destY = {0, 0, 1, -1, 1, -1, 1, -1,  1, -1, 1, -1, 2, 2, -2, -2};

        for (int i = 0; i < destX.length; i++) {
            //valid verify if in a direction is a same color piece
            boolean valid = false;
            for (int j = 1; j < 8 && valid == false; j++) {
                xdest = xsrc + j * destX[i];
                ydest = ysrc + j * destY[i];
                if (coordinatesValid(xdest, ydest)) {

                    if (pieceFromPosition(xdest, ydest) != null && pieceFromPosition(xsrc, ysrc) != null) {
                        if (pieceFromPosition(xdest, ydest).color != pieceFromPosition(xsrc, ysrc).color) {

                            if(i < 4 && ((pieceFromPosition(xdest, ydest) instanceof Rook) || (pieceFromPosition(xdest, ydest) instanceof Queen))) {
                                return true;
                            }

                            if(j == 1 && i >= 4 && i < 8 && ((pieceFromPosition(xdest, ydest) instanceof Pawn) )) {
                                return true;
                            }

                            if(i >= 4 && i < 8 && ((pieceFromPosition(xdest, ydest) instanceof Bishop) || (pieceFromPosition(xdest, ydest) instanceof Queen))) {
                                return true;
                            }

                            if(j == 1 && i >= 8 && (pieceFromPosition(xdest, ydest) instanceof Knight) ) {
                                return true;
                            }

                            if (i < 8) {
                                valid = true;
                            }
                        }  else valid = true;
                    }
                }
            }
        }


        return false;
    }


    int stringUpdate( String s) {
        int xsrc, ysrc, xdst, ydst;

        xsrc = s.charAt(0) - 'a';
        ysrc = s.charAt(1) - '1';
        xdst = s.charAt(2) - 'a';
        ydst = s.charAt(3) - '1';

        updateTable(xsrc, ysrc, xdst, ydst);

        if(s.length() >= 5) {
            Piece p = matrix[ydst][xdst];
            if(p instanceof Pawn) {
                    // r-Rook
                    // n-Knight
                    // b-Bishop
                    // q-Queen
                    if(p.color == "black" && p.position.getY() == 0) {
                        pawnTransform(p, s.charAt(4));
                    }

                    if(p.color == "white" && p.position.getY() == 7) {
                        pawnTransform(p, s.charAt(4));
                    }
                }
        }

        if(pieceFromPosition(xdst, ydst).color == "black")
            return 1;
        return 0;
    }

    //in this function a pawn can be changed with another piece
    public void pawnTransform(Piece p, char typePiece) {
        int xsrc = p.position.getX();
        int ysrc = p.position.getY();

        //if the chosen piece is Rook
        if(typePiece == 'r') {
            matrix[ysrc][xsrc] = new Rook(p.color, xsrc, ysrc);
        }
        //if the chosen piece is Knight
        if(typePiece == 'n') {
            matrix[ysrc][xsrc] = new Knight(p.color, xsrc, ysrc);
        }
        //if the chosen piece is Bishop
        if(typePiece == 'b') {
            matrix[ysrc][xsrc] = new Bishop(p.color, xsrc, ysrc);
        }
        //if the chosen piece is Queen
        if(typePiece == 'q') {
            matrix[ysrc][xsrc] = new Queen(p.color, xsrc, ysrc);
        }

        (matrix[ysrc][xsrc]).position = new Position(xsrc, ysrc);

        if(p.color == "black") {
            player_black.remove(p);
            player_black.add(0, matrix[ysrc][xsrc]);

        } else {
            player_white.remove(p);
            player_white.add(0, matrix[ysrc][xsrc]);

        }


    }



}
