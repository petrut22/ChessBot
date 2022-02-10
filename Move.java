public class Move {
    Position src;
    Position dest;
    
    Move() {

    }

    Move(Position src, Position dest) {
        this.src = new Position(src.getX(), src.getY());
        this.dest = new Position(dest.getX(), dest.getY());
    }

    public Position getDest() {
        return dest;
    }

    public Position getSrc() {
        return src;
    }

    public void setDest(Position dest) {
        this.dest = dest;
    }

    public void setSrc(Position src) {
        this.src = src;
    }
}
