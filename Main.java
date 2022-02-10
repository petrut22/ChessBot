import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ChessTable table;

    public static void read() {

        Scanner input = new Scanner(System.in);
        input.useDelimiter(System.getProperty("line.separator"));
        String s;
        boolean forced = false ;
        Pawn p = null;
        int lastcolor = 1;
        while (true) {
            s = input.next();

            if (s.equals("new")) {
                table = null;
                p = null;
                table = new ChessTable();
                lastcolor = 1;
                forced = false;
            }

            if (s.equals("quit")) {
                return;
            }

            if (s.equals("force")) {
                forced = true;
            }

            if(s.equals("go")) {
                forced = false;
                p = null;
                if(lastcolor == 1) {
                    for (int i = 0; i < table.player_white.size(); i++) {
                        if (table.player_white.get(i) instanceof Pawn) {
                            p = (Pawn) table.player_white.get(i);
                            break;
                        }
                    }
                    if(p != null) {
                        System.out.println(p.position.getX() + " " + p.position.getY() + " " + p.color);
                        p.move();
                        lastcolor = 0;
                    }
                    else System.out.println("resign");
                } else {
                    for (int i = 0; i < table.player_black.size(); i++) {
                        if (table.player_black.get(i) instanceof Pawn) {
                            p = (Pawn) table.player_black.get(i);
                            break;
                        }
                    }
                    if(p != null) {
                        System.out.println(p.position.getX() + " " + p.position.getY() + " " + p.color);
                        p.move();
                        lastcolor = 1;
                    }
                    else System.out.println("resign");
                }
            }


            if (s.contains("protover")) {
                System.out.println("feature sigint=0 san=0 name=lala");
            }

            if (s.length() >= 4 && s.charAt(1) >= '0' && s.charAt(1) <= '9' && s.charAt(3) >= '0' && s.charAt(3) <= '9') {
                lastcolor = table.stringUpdate(s);
                if (!forced) {
                    if (p != null) {
                        if (lastcolor == 1) {
                            if (table.player_white.contains(p)) {
                                p.move();
                                lastcolor = 0;
                            } else {
                                System.out.println("resign");
                            }
                        } else {
                            if (table.player_black.contains(p)) {
                                p.move();
                                lastcolor = 1;
                            } else {
                                System.out.println("resign");
                            }
                        }
                    } else {
                        if(lastcolor == 1) {
                            for (int i = 0; i < table.player_white.size(); i++) {
                                if (table.player_white.get(i) instanceof Pawn) {
                                    p = (Pawn) table.player_white.get(i);
                                    break;
                                }
                            }
                            if(p != null) {
                                p.move();
                                lastcolor = 0;
                            }
                            else System.out.println("resign");
                        } else {
                            for (int i = 0; i < table.player_black.size(); i++) {
                                if (table.player_black.get(i) instanceof Pawn) {
                                    p = (Pawn) table.player_black.get(i);
                                    break;
                                }
                            }
                            if(p != null) {
                                p.move();
                                lastcolor = 1;
                            }
                            else System.out.println("resign");
                        }
                    }

                }
            }
        }


    }

    public void read2() {

        Scanner input = new Scanner(System.in);
        input.useDelimiter(System.getProperty("line.separator"));
        String s;
        Move move;
        boolean forced = false ;
        Piece p = null;
        int lastcolor = 1;
        while (true) {
            s = input.next();

            if (s.equals("new")) {
                table = null;
                p = null;
                table = new ChessTable();
                lastcolor = 1; // the first move will be for the white
                forced = false;
            }

            if (s.equals("quit")) {
                return;
            }

            if (s.equals("force")) {
                forced = true;
            }

            if(s.equals("go")) {
                forced = false;
                p = null;
                //if the last move is from black
//                move = getRandomMove(lastcolor);
                if(table.checkKingSah(getKing(lastcolor))) {
                    System.out.println("in sah");
                    move = getNotCheck(lastcolor);
                } else {
                    System.out.println("nu e in sah");
                    move = getRandomMove(lastcolor);
                }

                if(move != null) {
                    p = table.pieceFromPosition(move.getSrc().getX(), move.getSrc().getY());
                    p.moveUpdates(move.getSrc(), move.getDest().getX(), move.getDest().getY());
                    if(p instanceof Pawn) {
                        ((Pawn) p).firstMove = false;
                    }

                } else {
                    System.out.println("resign");
                }

                if(lastcolor == 1)
                    lastcolor = 0;
                else lastcolor = 1;

            }


            if (s.contains("protover")) {
                System.out.println("feature sigint=0 san=0 name=lala");
            }

            if (s.length() >= 4 && s.charAt(1) >= '0' && s.charAt(1) <= '9' && s.charAt(3) >= '0' && s.charAt(3) <= '9') {
                lastcolor = table.stringUpdate(s);


                if (!forced ) {
                    if(table.checkKingSah(getKing(lastcolor))) {
                        System.out.println("in sah");
                        move = getNotCheck(lastcolor);
                    } else {
                        System.out.println("nu e in sah");
                        move = getRandomMove(lastcolor);
                    }
                    if(move != null) {
                        p = table.pieceFromPosition(move.getSrc().getX(), move.getSrc().getY());
                        p.moveUpdates(move.getSrc(), move.getDest().getX(), move.getDest().getY());

                        if(p instanceof Pawn) {
                            ((Pawn) p).firstMove = false;
                        }

                        if(p instanceof Pawn) {
                                // r-Rook
                                // n-Knight
                                // b-Bishop
                                // q-Queen
                                if(lastcolor == 0 && p.position.getY() == 0) {
                                    table.pawnTransform(p, 'b');
                                }

                                if(lastcolor == 1 && p.position.getY() == 7) {
                                    table.pawnTransform(p, 'b');
                                }
                        }

                    } else {
                        System.out.println("resign");
                    }

                    if(lastcolor == 1)
                        lastcolor = 0;
                    else lastcolor = 1;

                }
            }
        }


    }

    public Position getKing(int lastcolor) {
        Position position = null;
        if(lastcolor == 1) {
            if(table.player_white == null) {
                return null;
            }

            for(Piece p : table.player_white) {
                if (p instanceof King) {
                    position = p.position;
                    break;
                }
            }

        } else {
            if(table.player_black == null) {
                return null;
            }
            for(Piece p : table.player_black) {
                if (p instanceof King) {
                    position = p.position;
                    break;
                }
            }
        }

        return position;

    }

    public Move getRandomMove(int lastcolor) {
        Move m = null;
        if(lastcolor == 1) {
            if(table.player_white == null) {
                return null;
            }

            for(Piece p : table.player_white) {
                if (p.possibleMoves() != null) {
                    m = p.possibleMoves().get(0);
                    break;
                }
            }

        } else {
            if(table.player_black == null) {
                return null;
            }
            for(Piece p : table.player_black) {
                if (p.possibleMoves() != null) {
                    m = p.possibleMoves().get(0);
                    break;
                }
            }
        }

        return m;
    }

    // move for when current player is in check
    public Move getNotCheck(int lastcolor) {
        Move m = null;
        Position posKing = getKing(lastcolor);
        King k = (King) table.pieceFromPosition(posKing.getX(), posKing.getY());

        if(lastcolor == 1) {
            if(table.player_white == null) {
                return null;
            }
            if (k.possibleMoves() != null) {
                return k.possibleMoves().get(0);
            }
            for(Piece p : table.player_white) {
                ArrayList<Move> possMoves= p.possibleMoves();
                if (possMoves != null) {
                    int i = 0;
                    while(i < possMoves.size()) {
                        if (posKing != possMoves.get(i).src) {
                            ChessTable chessAux = new ChessTable(table, possMoves.get(i));
                            if (!chessAux.checkKingSah(getKing(lastcolor))) {
                                return possMoves.get(i);
                            }
                        }
                        i++;
                    }
                }
            }

        } else {
            if(table.player_black == null) {
                return null;
            }
            if (k.possibleMoves() != null) {
                return k.possibleMoves().get(0);
            }
            for(Piece p : table.player_black) {
                ArrayList<Move> possMoves= p.possibleMoves();
                if (possMoves != null) {
                    int i = 0;
                    while(i < possMoves.size()) {
                        if (posKing != possMoves.get(i).src) {
                            ChessTable chessAux = new ChessTable(table, possMoves.get(i));
                            if (!chessAux.checkKingSah(getKing(lastcolor))) {
                                return possMoves.get(i);
                            }
                        }
                        i++;
                    }
                }
            }
        }
        return null;

    }
    public static void main(String[] args) {
        Main obj = new Main();
        obj.read2();
        //read();
    }
}
