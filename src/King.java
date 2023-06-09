import java.util.*;


public class King extends Piece{
    int numberOfChecks = 0;
    int[][][] checks = new int[2][7][2];
    int[][] checkDirs = new int[2][2];
    List<int[][]> kingLine = new ArrayList<int[][]>();
    Set<int[]>  protectedSquares = new HashSet<>();
    final static int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    public King(char colour, int[] position, Board theBoard, int counter) {
        super(colour, position, theBoard, counter);
    }

    public void getMoves(){
        boolean castling = !hasMoved() && numberOfChecks == 0;
        for (int[] checkDirection: checkDirs){
            int[] a = new int[] {getPosition()[0] + checkDirection[0], getPosition()[1] + checkDirection[1]};
            for (int[] move:moves){
                if(Arrays.equals(move, a)){
                    moves.remove(move);
                    break;
                }
            }
        }
        if (castling && !hasMoved()){
            if (myPieces[9] != null && !myPieces[9].hasMoved() && (board[getPosition()[0] + 1][getPosition()[1]].equals("")) && board[getPosition()[0] + 2][getPosition()[1]].equals("") && !myPieces[9].hasMoved()){
                moves.add(new int[] {getPosition()[0]+2, getPosition()[1]});
            }
            if (myPieces[8] != null && !myPieces[8].hasMoved() && board[getPosition()[0] - 1][getPosition()[1]].equals("") && board[getPosition()[0] - 2][getPosition()[1]].equals("") && board[getPosition()[0] - 3][getPosition()[1]].equals("")){
                moves.add(new int[] {getPosition()[0]-2, getPosition()[1]});
            }
        }
        Set<int[]> toRemove = new HashSet<>();
        for (Piece piece:enemyPieces){
            if (piece != null && piece.getClass() != Pawn.class && piece.getClass() != King.class) {
                for (int[] attackedSquare : piece.moves){
                    for (int[] move: moves){
                        if (Arrays.equals(attackedSquare, new int[]{getPosition()[0] + 1, getPosition()[1]}) && Arrays.equals(move,new int[]{getPosition()[0] + 2, getPosition()[1]})){
                            toRemove.add(move);
                        }
                        else if (Arrays.equals(attackedSquare, new int[]{getPosition()[0] - 1, getPosition()[1]}) && Arrays.equals(move,new int[]{getPosition()[0] - 2, getPosition()[1]})){
                            toRemove.add(move);}
                        if(Arrays.equals(move, attackedSquare)){
                            toRemove.add(move);
                        }
                    }
                }
            }
        }
        for (int[] removalItem:toRemove){
            moves.removeIf(move -> removalItem == move);
        }
        int[][] pr = protectedSquares.toArray(new int[protectedSquares.size()][2]);
        for (int[] protectedSq:pr){
            for (int[] move:moves){
                if (Arrays.equals(move, protectedSq)){
                    moves.remove(move);
                    break;
                }
            }
        }
    }

    public char getPiece() {
        return 'l';
    }
    @Override
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
        if(getPosition()[0] == 4) {
            if (to[0] == 2) {
                board[3][getPosition()[1]] = String.valueOf(getColour())+'r';
                board[0][getPosition()[1]] = "";
                myPieces[8].setPosition(new int[] {3, getPosition()[1]});
            }
            else if (to[0] == 6){
                board[5][getPosition()[1]] = String.valueOf(getColour())+'r';
                board[7][getPosition()[1]] = "";
                myPieces[8].setPosition(new int[] {5, getPosition()[1]});
            }
        }
        setMoved(true);
        board[to[0]][to[1]] = String.valueOf(getColour())+getPiece();
        board[getPosition()[0]][getPosition()[1]] = "";
        setPosition(to);
    }
}
