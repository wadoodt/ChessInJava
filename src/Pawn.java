import java.util.Arrays;
import java.util.Objects;

public class Pawn extends Piece{
    boolean enPassant = false;
    int[][] attacked;
    final static int[] diagonal = new int[] {-1, 1};
    public Pawn(char colour, int[] position, Board theBoard, int counter) {
        super(colour, position, theBoard, counter);
    }
    public void getMoves(){
        int dir = 1;
        if (getColour() == 'w'){dir = -1;}
        int[] pos = getPosition();
        if (-1 < pos[1] + dir && pos[1] + dir <8){
            for (int i: diagonal){
                ((King) enemyPieces[15]).protectedSquares.add(new int[] {pos[0]+i, pos[1] +dir});
                if (-1 < pos[0] + i && pos[0] + i <8){
                    String square = board[pos[0]+i][pos[1]+dir];
                    if (square.isEmpty()){
                        ((King) enemyPieces[15]).protectedSquares.add(new int[] {pos[0]+i, pos[1]+dir});
                        outerLoop:
                        for (Piece piece:enemyPieces){
                            if (piece != null && piece.getClass() == Pawn.class && ((Pawn) piece).enPassant && Arrays.equals(piece.getPosition(), new int[]{getPosition()[0] + i, getPosition()[1]})){
                                if (myPieces[15].getPosition()[1] == getPosition()[1]){
                                    int first, second, a, b, c;
                                    if (i== -1){
                                        first = getPosition()[0]-1;
                                        second = getPosition()[0];
                                    }
                                    else {
                                        first = getPosition()[0];
                                        second = getPosition()[0]+1;
                                    }
                                    if (myPieces[15].getPosition()[0] - second > 0){
                                        a = -1;
                                        b = second-myPieces[15].getPosition()[0];
                                        c = -first-2+b;}
                                    else {
                                        a= 1;
                                        b= first - myPieces[15].getPosition()[0];
                                        c = 9-second+b;}
                                    for (int h = a; h!=b;h+=a){
                                        if (!board[myPieces[15].getPosition()[0]+h][myPieces[15].getPosition()[1]].equals("")){
                                            moves.add(new int[] {pos[0]+i, pos[1] +dir});
                                            break outerLoop;
                                        }
                                    }
                                    for (int u =b+2*a; u!=c;u+=a){
                                        if (board[myPieces[15].getPosition()[0]+u][myPieces[15].getPosition()[1]].equals("")){
                                            if (u==c-a){
                                                moves.add(new int[] {pos[0]+i, pos[1] +dir});
                                                break outerLoop;
                                            }
                                        }
                                        else if (board[myPieces[15].getPosition()[0]+u][myPieces[15].getPosition()[1]].equals(String.valueOf(enemyColour(colour))+'r')
                                                || board[myPieces[15].getPosition()[0]+u][myPieces[15].getPosition()[1]].equals(String.valueOf(enemyColour(colour)) + 'q')){
                                            break outerLoop;
                                        }
                                        else{
                                            moves.add(new int[] {pos[0]+i, pos[1] +dir});
                                            break outerLoop;
                                        }
                                    }
                                }
                                else{moves.add(new int[] {pos[0]+i, pos[1] +dir});
                                    break;}
                            }
                        }
                    }
                    else if (square.charAt(0) == colour){
                        ((King) enemyPieces[15]).protectedSquares.add(new int[] {pos[0]+i, pos[1]+dir});}
                    else if (square.charAt(0) == enemyColour(colour)){
                        moves.add(new int[] {pos[0]+i,pos[1]+dir});
                        if (square.charAt(1) == 'l'){
                            ((King) this.enemyPieces[15]).checks[((King) this.enemyPieces[15]).numberOfChecks] = new int[][] {getPosition()};
                            ((King) this.enemyPieces[15]).numberOfChecks += 1;
                        }
                    }
                }
            }
            if (Objects.equals(board[pos[0]][pos[1] + dir], "")){
                moves.add(new int[] {pos[0], pos[1]+dir});
                if (-1 < pos[1]+ 2 *dir && pos[1]+2*dir < 8 && Objects.equals(board[pos[0]][pos[1] + 2 * dir], "") && !hasMoved()){
                    moves.add(new int[] {pos[0], pos[1] + 2*dir});
                }
            }
        }
    }

    public char getPiece() {
        return 'p';
    }
    @Override
    public void move(int[] to){
        board[getPosition()[0]][getPosition()[1]] = "";
        if ((to[1] - getPosition()[1]) == 2 || (to[1] - getPosition()[1]) == -2) {
            enPassant = true;
        }
        else if (!board[to[0]][to[1]].equals("")){
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
        else if (board[to[0]][to[1]].equals("") && to[1] != getPosition()[1]) {
            board[to[0]][getPosition()[1]] = "";
            for (int i = 0; i < enemyPieces.length; i++) {
                if (enemyPieces[i] != null && Arrays.equals(enemyPieces[i].getPosition(), new int[]{to[0], getPosition()[1]})) {
                    board[to[0]][getPosition()[1]] = "";
                    enemyPieces[i] = null;
                    if (colour == 'w') {
                        theBoard.killPiece(i + 16);
                        Piece.blackCounter -= 1;
                    } else {
                        theBoard.killPiece(i);
                        Piece.whiteCounter -= 1;
                    }
                    break;
                }
            }
        }
        if (to[1]==0 && this.colour =='w' || to[1]==7 && this.colour =='b'){
            board[to[0]][to[1]] = "";
        }
        else{
            board[to[0]][to[1]] = String.valueOf(colour) + getPiece();
            setPosition(to);
            setMoved(true);
        }
    }
}

