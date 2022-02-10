import java.util.ArrayList;

public class Bishop extends Piece{

    Bishop(String color, int x, int y) {
        super(color, x, y);
    }

    public Bishop copy() {
        return new Bishop(this.color, this.position.getX(), this.position.getY());
    }

    @Override
    ArrayList<Move> possibleMoves() {
        int[] destX = {1, 1, -1, -1};
        int[] destY = {1, -1, 1, -1};

        return getMoves(destX, destY);
    }

    @Override
    void move() {
//        Position src = position;
//
//        // check if pawn can capture something on diagonal and
//        if (diagonal(src)) {
//            return;
//        }
//
//        System.out.println("resign");
    }

    //boolean diagonal(Position src) {

        //boolean moved = false;

//        if (!isPieceBlack()) {
//            // check first diagonal for white pa
//            if (Main.table.coordinatesValid(position.getX() - 1, position.getY() + 1) && Main.table.capturePosition(color, position.getX() - 1, position.getY() + 1)) {
//                moveUpdates(src, position.getX() - 1, position.getY() + 1);
//                moved = true;
//            } else if(Main.table.coordinatesValid(position.getX() + 1, position.getY() + 1) && Main.table.capturePosition(color, position.getX() + 1, position.getY() + 1)) {
//                // check second diagonal for white pawn
//                moveUpdates(src, position.getX() + 1, position.getY() + 1);
//                moved = true;
//            }
//        } else {
//            // check first diagonal for black pawn
//            if (Main.table.coordinatesValid(position.getX() - 1, position.getY() - 1) && Main.table.capturePosition(color, position.getX() - 1, position.getY() - 1)) {
//                moveUpdates(src, position.getX() - 1, position.getY() - 1);
//                moved = true;
//            } else if(Main.table.coordinatesValid(position.getX() + 1, position.getY() - 1) && Main.table.capturePosition(color, position.getX() + 1, position.getY() - 1)) {
//                // check second diagonal for black pawn
//                moveUpdates(src, position.getX() + 1, position.getY() - 1);
//                moved = true;
//            }
//        }
//        return moved;
       // return false;
    //}
}
