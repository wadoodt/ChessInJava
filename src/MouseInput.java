import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Objects;

public class MouseInput implements MouseListener {
    private final Board theBoard;
    String[][] board;
    Piece theFirst;
    Piece[] pieces;
    boolean change =false;
    int[] changePos;
    boolean endGame;
    boolean whitesTurn, firstClick;
    public void checkForWin(){
        Piece[] somePieces;
        boolean anyMoves = false;
        if(whitesTurn){
            somePieces = Piece.whitePieces;
        }
        else {
            somePieces = Piece.blackPieces;
        }
        for (Piece piece: somePieces){
            if (piece != null && !piece.moves.isEmpty()){
                anyMoves = true;
                break;
            }}
        if (!anyMoves){
            if (((King) somePieces[15]).numberOfChecks !=0){
                endGame = true;
                if (whitesTurn){
                    theBoard.endGame = "Black Won";
                }
                else {
                    theBoard.endGame = "White Won";
                }
            }
            else{theBoard.endGame = "Draw By Stalemate";
                endGame = true;}
        }
        else if (Piece.whiteCounter ==1 && Piece.blackCounter ==1){
            theBoard.endGame = "Draw By Insufficient Material";
            endGame = true;
        }
        theBoard.repaint();
    }
    public MouseInput(Board theBoard, Piece[] pieces){
        this.theBoard = theBoard;
        this.board = theBoard.board;
        this.pieces = pieces;
        whitesTurn = true;
        firstClick = true;

    }
    public void mouseClicked(MouseEvent e) {
        int x = e.getPoint().x-8; //determined using getInsets() of JFrame (8, 31)
        int y = e.getPoint().y-31;
        int[] clickedPos = new int[] {x/100, y/100};
        if (x > 800 || endGame){return;}
        if (change){
            if (!Arrays.equals(changePos, clickedPos)){return;}
            int g;
            for (int i=0;i<pieces.length;i++){
                if (pieces[i] == theFirst){
                    char color = pieces[i].colour;
                    if (color == 'w'){
                        g = i;
                    }
                    else{
                        g = i-16;
                    }
                    pieces[i].myPieces[g] = null;
                    if (changePos[0]*100<x && changePos[0]*100+50>x){
                        if (changePos[1]*100<y && changePos[1]*100+50>y){
                            pieces[i] = new Knight(color, changePos, theBoard, g);
                        }
                        else {
                            pieces[i] = new Rook(color, changePos, theBoard, g);
                        }
                    }
                    else {
                        if (changePos[1]*100<y && changePos[1]*100+50>y){
                            pieces[i] = new Bishop(color, changePos, theBoard, g);
                        }
                        else {
                            pieces[i] = new Queen(color, changePos, theBoard, g);
                        }
                    }
                }
            }
            change = false;
            theBoard.paintPromotion = null;
            firstClick = true;
            whitesTurn = !whitesTurn;
            theBoard.repaint();
            theBoard.allMoves(pieces);
        }
        else{
            x /=100;
            y /=100;
            if (firstClick) {
                theBoard.show = null;
                if ((!Objects.equals(board[x][y], "") && ((whitesTurn && board[x][y].charAt(0) == 'w') || (!whitesTurn && board[x][y].charAt(0) == 'b')))) {
                    for (Piece piece : pieces) {
                        if (piece != null && Arrays.equals(piece.getPosition(), clickedPos)) {
                            theFirst = piece;
                            firstClick = false;
                            theBoard.show =theFirst.moves.toArray(new int[theFirst.moves.size()][2]);
                        }
                    }
                }
                theBoard.repaint();
            }
            else {
                boolean toMove = false;
                for (int[] move : theFirst.moves) {
                    if (Arrays.equals(move, clickedPos)) {
                        toMove = true;
                        break;
                    }
                }
                if (toMove) {
                    theFirst.move(clickedPos);
                    if (theFirst.getClass() == Pawn.class && (y==0 || y==7)){
                        theBoard.show = null;
                        change = true;
                        changePos = clickedPos.clone();
                        theBoard.paintPromotion = clickedPos.clone();
                    }
                    else{
                        firstClick = true;
                        whitesTurn = !whitesTurn;
                        theBoard.show = null;
                        theBoard.allMoves(pieces);
                    }
                    checkForWin();
                    theBoard.repaint();
                }
                else {
                    if ((!Objects.equals(board[x][y], "") && ((whitesTurn && board[x][y].charAt(0) == 'w') || (!whitesTurn && board[x][y].charAt(0) == 'b')))) {
                        for (Piece piece : pieces) {
                            if (piece !=null && Arrays.equals(piece.getPosition(), new int[]{x, y})) {
                                theFirst = piece;
                                theBoard.show =theFirst.moves.toArray(new int[theFirst.moves.size()][2]);
                                theBoard.repaint();
                            }
                        }
                    } else {
                        firstClick = true;
                        theBoard.show = null;
                        theBoard.repaint();
                    }
                }
            }
        }

    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}
