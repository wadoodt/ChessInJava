import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Piece {
    abstract char getPiece();

    char colour;
    boolean Moved = false;
    int[] position;
    List<int[]> protectorLine = new ArrayList<int[]>();
    static Piece[] whitePieces = new Piece[16];
    static int whiteCounter = -1;
    static int blackCounter = -1;
    static Piece[] blackPieces = new Piece[16];
    Piece[] myPieces;
    Piece[] enemyPieces;
    public abstract void getMoves();
    Board theBoard;
    List<int[]> moves = new ArrayList<>();
    String[][] board;
    public Piece(char colour, int[] position, Board theBoard, int counter){
        this.colour = colour;
        this.position = position;
        this.theBoard = theBoard;
        this.board = theBoard.board;
        setMoved(false);
        board[position[0]][position[1]] = String.valueOf(this.getColour()) + this.getPiece();
        if (counter == -1) {
            if (colour == 'w') {
                whiteCounter += 1;
                whitePieces[whiteCounter] = this;
                myPieces = whitePieces;
                enemyPieces = blackPieces;
            } else {
                blackCounter += 1;
                blackPieces[blackCounter] = this;
                myPieces = blackPieces;
                enemyPieces = whitePieces;
            }
        }
        else {
            if (colour == 'w') {
                whitePieces[counter] = this;
                myPieces = whitePieces;
                enemyPieces = blackPieces;
            } else {
                blackPieces[counter] = this;
                myPieces = blackPieces;
                enemyPieces = whitePieces;
            }
        }
    }
    public char enemyColour(char colour){
        if (colour == 'w'){
            return 'b';
        }
        else{
            return 'w';
        }
    }
    public void move(int[] to){
        if (!board[to[0]][to[1]].equals("")){
            for (int i=0;i<enemyPieces.length;i++){
                if (enemyPieces[i]!=null && Arrays.equals(enemyPieces[i].getPosition(), to)){
                    enemyPieces[i] = null;
                    if (colour == 'w'){
                        theBoard.killPiece(i+16);
                        Piece.blackCounter -=1;}
                    else {theBoard.killPiece(i);
                        Piece.whiteCounter -=1;}
                    break;
                }
            }
        }
        board[to[0]][to[1]] = String.valueOf(getColour())+getPiece();
        board[getPosition()[0]][getPosition()[1]] = "";
        setPosition(to);
        setMoved(true);
    }
    public char getColour() {
        return colour;
    }
    public int[] getPosition() {
        return position;
    }
    public boolean hasMoved() {
        return Moved;
    }
    public void setPosition(int[] position) {
        this.position = position;
    }
    public void setMoved(boolean hasMoved) {
        this.Moved = hasMoved;
    }
}
