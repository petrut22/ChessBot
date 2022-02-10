import java.util.ArrayList;

public class Pawn extends Piece{
    boolean firstMove = true;

    Pawn(String color, int x, int y) {
        super(color, x, y);
    }

    public void moveUpdates(Position src, int xdest, int ydest) {
        int xsrc = position.getX();
        int ysrc = position.getY();
        Main.table.updateTable(xsrc, ysrc, xdest, ydest);
        char c1 = (char) ('a' + src.getX());
        char c2 = (char) ('a' + xdest);

        //verify if the piece can change in another piece
        if(ydest == 7 || ydest == 0) {
            System.out.println("move " + c1 + (src.getY() + 1) + c2 + (position.getY() + 1) + "b");
        } else {
            System.out.println("move " + c1 + (src.getY() + 1) + c2 + (position.getY() + 1));
        }
    }

    public Pawn copy() {
        return new Pawn(this.color, this.position.getX(), this.position.getY());
    }
    @Override
    ArrayList<Move> possibleMoves() {
        ArrayList<Move> moves= new ArrayList<>();

        // first move -> can move 2 squares if the destination is not occupied
        if (firstMove) {
            if (!isPieceBlack()) {
                if (!Main.table.isSquareOccupied(position.getX(), position.getY() + 2) && !Main.table.isSquareOccupied(position.getX(), position.getY() + 1)) {
                    Move m = new Move(position, new Position(position.getX(), position.getY() + 2));
                    moves.add(m);
                }
            } else {
                if (!Main.table.isSquareOccupied(position.getX(), position.getY() - 2) && !Main.table.isSquareOccupied(position.getX(), position.getY() - 1)) {
                    Move m = new Move(position, new Position(position.getX(), position.getY() - 2));
                    moves.add(m);
                }
            }
        }



        // any pawn can move one square in front if the destination is valid and is not occupied
            if (!isPieceBlack()) {
                if (Main.table.coordinatesValid(position.getX(), position.getY() + 1)) {
                    if (!Main.table.isSquareOccupied(position.getX(), position.getY() + 1)) {
                        Move m = new Move(position, new Position(position.getX(), position.getY() + 1));
                        moves.add(m);
                    }
                }
            } else if (Main.table.coordinatesValid(position.getX(), position.getY() - 1)) {
                if (!Main.table.isSquareOccupied(position.getX(), position.getY() - 1)) {
                    Move m = new Move(position, new Position(position.getX(), position.getY() - 1));
                    moves.add(m);
                }
            }


        // check if pawn can capture something

        if (!isPieceBlack()) {
            // check first diagonal for white pawn
            if (Main.table.coordinatesValid(position.getX() - 1, position.getY() + 1) && Main.table.capturePosition(color, position.getX() - 1, position.getY() + 1)) {
                Move m = new Move(position, new Position(position.getX() - 1, position.getY() + 1));
                moves.add(m);
            } else if(Main.table.coordinatesValid(position.getX() + 1, position.getY() + 1) && Main.table.capturePosition(color, position.getX() + 1, position.getY() + 1)) {
                // check second diagonal for white pawn
                Move m = new Move(position, new Position(position.getX() + 1, position.getY() + 1));
                moves.add(m);
            }
        } else {
            // check first diagonal for black pawn
            if (Main.table.coordinatesValid(position.getX() - 1, position.getY() - 1) && Main.table.capturePosition(color, position.getX() - 1, position.getY() - 1)) {
                Move m = new Move(position, new Position(position.getX() - 1, position.getY() - 1));
                moves.add(m);
            } else if(Main.table.coordinatesValid(position.getX() + 1, position.getY() - 1) && Main.table.capturePosition(color, position.getX() + 1, position.getY() - 1)) {
                // check second diagonal for black pawn
                Move m = new Move(position, new Position(position.getX() + 1, position.getY() - 1));
                moves.add(m);
            }
        }

        //check if there are possible moves
        if(moves.size() == 0)
            return null;

//        System.out.println("\nPozitia initiala : " + moves.get(0).getSrc().getX() + " " + moves.get(0).getSrc().getY());
//
//        for(Move i : moves) {
//            System.out.println("Pozitie : " + i.getDest().getX() + " " + i.getDest().getY());
//        }
//
//        System.out.println("\n\n");

        return moves;

    }

    @Override
    void move() {
        Position src = position;

        // check if pawn can capture something on diagonal and
        if (diagonal(src)) {
            return;
        }
        if (firstMove) {
            if (doubleMove(src))
                return;
        }
        if (simpleMove(src))
            return;

        System.out.println("resign");
    }

    //executes a simple pawn move if possible
    boolean simpleMove(Position src) {
        boolean moved = false;
        if (!isPieceBlack()) {
            if (Main.table.coordinatesValid(position.getX(), position.getY() + 1)) {
                if (!Main.table.isSquareOccupied(position.getX(), position.getY() + 1)) {
                    moveUpdates(src, position.getX(), position.getY() + 1);
                    firstMove = false;
                    moved = true;
                }
            }
        } else if (Main.table.coordinatesValid(position.getX(), position.getY() - 1)) {
            if (!Main.table.isSquareOccupied(position.getX(), position.getY() - 1)) {
                moveUpdates(src, position.getX(), position.getY() - 1);
                firstMove = false;
                moved = true;
            }
        }
        return moved;
    }

    // executes the first move (2 squares in front) for a pawn; returns true if move is possible
    boolean doubleMove(Position src) {
        boolean moved = false;
        if (!isPieceBlack()) {
            if (!Main.table.isSquareOccupied(position.getX(), position.getY() + 2)) {
                moveUpdates(src, position.getX(), position.getY() + 2);
                firstMove = false;
                moved = true;
            }
        } else {
            if (!Main.table.isSquareOccupied(position.getX(), position.getY() - 2)) {
                firstMove = false;
                moveUpdates(src, position.getX(), position.getY() - 2);
                moved = true;
            }
        }
        return moved;
    }

    // moves a pawn diagonally if possible -> there is an opponent's piece there
    boolean diagonal(Position src) {

        boolean moved = false;

        if (!isPieceBlack()) {
            // check first diagonal for white pawn
            if (Main.table.coordinatesValid(position.getX() - 1, position.getY() + 1) && Main.table.capturePosition(color, position.getX() - 1, position.getY() + 1)) {
                if (firstMove)
                    firstMove = false;
                moveUpdates(src, position.getX() - 1, position.getY() + 1);
                moved = true;
            } else if(Main.table.coordinatesValid(position.getX() + 1, position.getY() + 1) && Main.table.capturePosition(color, position.getX() + 1, position.getY() + 1)) {
                // check second diagonal for white pawn
                if (firstMove)
                    firstMove = false;
                moveUpdates(src, position.getX() + 1, position.getY() + 1);
                moved = true;
            }
        } else {
            // check first diagonal for black pawn
            if (Main.table.coordinatesValid(position.getX() - 1, position.getY() - 1) && Main.table.capturePosition(color, position.getX() - 1, position.getY() - 1)) {
                if (firstMove)
                    firstMove = false;
                moveUpdates(src, position.getX() - 1, position.getY() - 1);
                moved = true;
            } else if(Main.table.coordinatesValid(position.getX() + 1, position.getY() - 1) && Main.table.capturePosition(color, position.getX() + 1, position.getY() - 1)) {
                // check second diagonal for black pawn
                if (firstMove)
                    firstMove = false;
                moveUpdates(src, position.getX() + 1, position.getY() - 1);
                moved = true;
            }
        }
        return moved;
    }
}
